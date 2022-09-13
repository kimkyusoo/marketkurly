package com.example.marketkurly.service;


import com.example.marketkurly.dto.request.CommentRequestDto;
import com.example.marketkurly.dto.response.CommentResponseDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.jwt.TokenProvider;
import com.example.marketkurly.model.Comment;
import com.example.marketkurly.model.Product;
import com.example.marketkurly.model.User;
import com.example.marketkurly.repository.CommentRepository;
import com.example.marketkurly.repository.ProductReposioty;
import com.example.marketkurly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductReposioty productReposioty;
    private final UserRepository userRepository;
    private final AmazonS3Service amazonS3Service;
    private final TokenProvider tokenProvider;


    /* 상품 리뷰 조회 */
    public ResponseEntity<List<CommentResponseDto>> getComment(Long product_id, HttpServletRequest request) {

        /* 헤더에 토큰이 존재하는지 여부 검증 */
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseEntity.badRequest().build();
        }

        /* 인증된 유저인지 여부 검증 */
        User user = tokenProvider.getUserFromAuthentication();
        if (null == user) {
            return ResponseEntity.notFound().build();
        }

        /* 해당 상품의 코멘트를 List에 넣어서 가져옴 */
        List<Comment> commentList = commentRepository.findAllByProductId(product_id);


        /* 보여줄 리뷰 정보 리스트 */
        List<CommentResponseDto> commentResult = new ArrayList<>();
        for(Comment comments: commentList) {
            Long commentId = comments.getId();
            Long userId = comments.getUser().getId();
            Long productId = comments.getProduct().getId();
            String title = comments.getTitle();
            String comment = comments.getComment();
            String username = comments.getUser().getUsername();
            String nickname = comments.getUser().getNickname();
            String imageUrl = comments.getImageUrl();
            LocalDateTime createdAt = comments.getCreatedAt();
            LocalDateTime modifiedAt = comments.getModifiedAt();

            CommentResponseDto commentResponseDto = new CommentResponseDto(commentId, productId, userId, title, comment, username, nickname, imageUrl, createdAt, modifiedAt);
            commentResult.add(commentResponseDto);
        }
        return ResponseEntity.ok().body(commentResult);
    }

    /* 코멘트(리뷰) 등록 */
    public ResponseEntity<CommentResponseDto> createComment(Long product_id, CommentRequestDto commentRequestDto, MultipartFile multipartFile, HttpServletRequest request) throws IOException {

        /* 헤더에 토큰이 존재하는지 여부 검증 */
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseEntity.badRequest().build();
        }

        /* 인증된 유저인지 여부 검증 */
        User user = tokenProvider.getUserFromAuthentication();
        if (null == user) {
            return ResponseEntity.notFound().build();
        }

        /* 상품이 존재하는지 여부 검증 */
        Product product = productReposioty.findById(product_id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));


        /* imageUrl과 filename을 기본적으로 null로 하고 이미지 파일을 등록할 경우 그 이미지 파일의 url와 name을 등록 */
        String imageUrl = null;
        String filename = null;
        /* 파일 등록을 했다면 S3에 업로드한 후 filename과 URL추출 */
        if(!multipartFile.isEmpty()) {
            filename = amazonS3Service.createFilename(multipartFile);
            imageUrl = amazonS3Service.upload(multipartFile, filename);
        }

        Comment comment = Comment.builder()
                .user(user)
                .title(commentRequestDto.getTitle())
                .comment(commentRequestDto.getComment())
                .product(product)
                .imageUrl(imageUrl)
                .filename(filename)
                .build();
        commentRepository.save(comment);
        return ResponseEntity.ok().body(CommentResponseDto.builder()
                .comment_id(comment.getId())
                .product_id(comment.getProduct().getId())
                .user_id(comment.getUser().getId())
                .title(comment.getTitle())
                .comment(comment.getComment())
                .username(comment.getUser().getUsername())
                .nickname(comment.getUser().getNickname())
                .imageUrl(comment.getImageUrl())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build());
    }

    /* 코멘트(리뷰) 수정 */
    public ResponseEntity<CommentResponseDto> updateComment(Long comment_id, CommentRequestDto commentRequestDto, MultipartFile multipartFile, HttpServletRequest request) throws IOException {

        /* 헤더에 토큰이 존재하는지 여부 검증 */
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseEntity.badRequest().build();
        }

        /* 인증된 유저인지 여부 검증 */
        User user = tokenProvider.getUserFromAuthentication();
        if (null == user) {
            return ResponseEntity.notFound().build();
        }

        /* 코멘트 존재 여부 검증 */
        Comment comment = commentRepository.findById(comment_id).orElseThrow
                (() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));


        /* 리뷰 작성자가 맞는지 여부 검증 */
        if(!comment.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("리뷰 작성자만 수정이 가능합니다");
        }

        String imageUrl;
        String filename;

        /* 새로운 이미지 파일 없이 수정할 경우 */
        /* 기존 이미지 파일이 없으면 null을 불러와서 계속 비워두고, 있으면 그 이미지 파일 Url을 불러와서 기존 이미지를 유지함 */
        if (multipartFile.isEmpty()) {
            imageUrl = comment.getImageUrl();
            filename = comment.getFilename();
        } else {
            /* 새로운 이미지 파일을 등록하며 수정할 경우 */
            /* 기존에 이미지 파일이 존재하는 경우 기존 파일 삭제 */
            if (comment.getImageUrl() != null) {
                amazonS3Service.deleteFile(comment.getFilename());
            }
            /* 새로운 이미지 파일 등록 */
            filename = amazonS3Service.createFilename(multipartFile);
            imageUrl = amazonS3Service.upload(multipartFile, filename);
        }

        CommentRequestDto updateComment = CommentRequestDto.builder()
                .title(commentRequestDto.getTitle())
                .comment(commentRequestDto.getComment())
                .imageUrl(imageUrl)
                .filename(filename)
                .build();
        comment.update(updateComment);
        commentRepository.save(comment);
        return ResponseEntity.ok().body(CommentResponseDto.builder()
                .comment_id(comment.getId())
                .product_id(comment.getProduct().getId())
                .user_id(comment.getUser().getId())
                .title(comment.getTitle())
                .comment(comment.getComment())
                .username(comment.getUser().getUsername())
                .nickname(comment.getUser().getNickname())
                .imageUrl(comment.getImageUrl())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build());
    }

    /* 코멘트(리뷰) 삭제 */
    public ResponseEntity<Long> deleteComment(Long comment_id, HttpServletRequest request) throws IOException {

        /* 헤더에 토큰이 존재하는지 여부 검증 */
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseEntity.badRequest().build();
        }

        /* 인증된 유저인지 여부 검증 */
        User user = tokenProvider.getUserFromAuthentication();
        if (null == user) {
            return ResponseEntity.notFound().build();
        }

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        /* 리뷰 작성자가 맞는지 여부 검증 */
        if(!comment.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("리뷰 작성자만 삭제가 가능합니다");
        }

        /* 저장된 이미지가 있으면 S3저장소에 있는 이미지 삭제하기 */
        if(comment.getImageUrl() != null) {
            amazonS3Service.deleteFile(comment.getFilename());
        }
        commentRepository.delete(comment);

        return ResponseEntity.ok().body(comment_id);
    }

}
