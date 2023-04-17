package com.project.shoppingmall.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void reviewRegister(ReviewRequestDto reviewRequestDto) {

        Review review = reviewRequestDto.dtoToEntity();

        reviewRepository.save(review);
    }
}
