package com.example.marketkurly.controller;


import com.example.marketkurly.dto.request.CommentRequestDto;
import com.example.marketkurly.dto.response.CommentResponseDto;
import com.example.marketkurly.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        return new ResponseEntity<>(commentService.getComment(product_id), HttpStatus.OK);
    }


    /* 리뷰(코멘트) 작성 */
    @PostMapping("/comment/{product_id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long product_id,
                                                     @RequestPart(value = "comment") CommentRequestDto commentRequestDto,
                                                     @RequestPart(value = "imageUrl", required = false) MultipartFile multipartFile) throws IOException {

        return commentService.createComment(product_id, commentRequestDto, multipartFile);
    }


    /* 리뷰(코멘트) 수정 */
    @PutMapping("/comment/{comment_id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long comment_id,
                                                     @RequestPart(value = "comment") CommentRequestDto commentRequestDto,
                                                     @RequestPart(value = "imageUrl", required = false) MultipartFile multipartFile) throws IOException {

        return commentService.updateComment(comment_id, commentRequestDto, multipartFile);
    }


    /* 리뷰(코멘트) 삭제 */
    @DeleteMapping("/comment/{comment_id}")
    public void deleteComment(@PathVariable Long comment_id) throws IOException {
        commentService.deleteComment(comment_id);
    }

}
