package com.example.marketkurly.controller;


import com.example.marketkurly.dto.request.CommentRequestDto;
import com.example.marketkurly.dto.response.CommentResponseDto;
import com.example.marketkurly.model.User;
import com.example.marketkurly.model.UserDetailsImpl;
import com.example.marketkurly.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentService commentService;


    /* 리뷰(코멘트) 조회 */
    @GetMapping("/product/{product_id}/comment")
    public ResponseEntity<List<CommentResponseDto>> getComment(@PathVariable Long product_id) {

        return ResponseEntity.ok().body(commentService.getComment(product_id));
    }


    /* 리뷰(코멘트) 작성 */
    @PostMapping("/comment/{product_id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long product_id,
                                                            @RequestPart(value = "comment") CommentRequestDto commentRequestDto,
                                                            @RequestPart(value = "imageUrl", required = false) MultipartFile multipartFile,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        User user = userDetails.getUser();

        return ResponseEntity.ok().body(commentService.createComment(product_id, commentRequestDto, multipartFile, user));
    }


    /* 리뷰(코멘트) 수정 */
    @PutMapping("/comment/{comment_id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long comment_id,
                                                            @RequestPart(value = "comment") CommentRequestDto commentRequestDto,
                                                            @RequestPart(value = "imageUrl", required = false) MultipartFile multipartFile) throws IOException {

        return ResponseEntity.ok().body(commentService.updateComment(comment_id, commentRequestDto, multipartFile));
    }


    /* 리뷰(코멘트) 삭제 */
    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long comment_id) throws IOException {

        return ResponseEntity.ok().body(commentService.deleteComment(comment_id) + "번 코멘트 삭제 완료!");
    }

}
