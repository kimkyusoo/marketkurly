package com.example.marketkurly.dto.request;


import com.example.marketkurly.model.Product;
import com.example.marketkurly.model.User;
import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    private String title;
    private String comment;
    private String imageUrl;
    private String filename;

}
