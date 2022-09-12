package com.example.marketkurly.dto.request;


import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    private String title;
    private String comment;
    private String nickname;
    private String imageUrl;
    private String filename;

}
