package com.project.shoppingmall.product;

import com.project.shoppingmall.account.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {

    private Integer starScore;

    private String reviewText;

    private Product product;

    private User user;


    public ReviewResponseDto(Review review) {
        this.starScore = review.getStarScore();
        this.reviewText = review.getReviewText();
        this.product = review.getProduct();
        this.user = review.getUser();
    }
}
