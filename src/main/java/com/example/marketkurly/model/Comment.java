package com.example.marketkurly.model;


import com.example.marketkurly.dto.request.CommentRequestDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped{

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String comment;

    @Column
    private String imageUrl;

    @Column
    private String filename;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;



    public Comment(CommentRequestDto commentRequestDto, Product product, User user) {
        this.title = commentRequestDto.getTitle();
        this.comment = commentRequestDto.getComment();
        this.imageUrl = commentRequestDto.getImageUrl();
        this.filename = commentRequestDto.getFilename();
        this.user = user;
        this.product = product;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.title = commentRequestDto.getTitle();
        this.comment = commentRequestDto.getComment();
        this.imageUrl = commentRequestDto.getImageUrl();
        this.filename = commentRequestDto.getFilename();
    }
}
