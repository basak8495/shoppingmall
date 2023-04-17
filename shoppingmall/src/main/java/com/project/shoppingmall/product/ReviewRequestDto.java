package com.project.shoppingmall.product;

import com.project.shoppingmall.account.User;
import com.project.shoppingmall.goods.Goods;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    private Long id;

    private Integer starScore;

    private String reviewText;

    private Product product;

    private User user;


    public Review dtoToEntity() {
        Review review = Review.builder()
                .starScore(starScore)
                .reviewText(reviewText)
                .product(product)
                .user(user)
                .build();
        return review;
    }

}
