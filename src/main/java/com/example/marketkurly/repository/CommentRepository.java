package com.example.marketkurly.repository;


import com.example.marketkurly.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    /* 상품에 달린 모든 리뷰를 불러올 때 사용 */
    List<Comment> findAllByProductId(Long product_id);

    /* 리뷰 수정, 삭제시 리뷰 작성자 본인인지 확인할 때 사용 */
//    Optional<Comment> findByIdAndUserId(Long comment_id, Long user_id);

    Optional<Comment> findByImageUrl(String imageUrl);

}
