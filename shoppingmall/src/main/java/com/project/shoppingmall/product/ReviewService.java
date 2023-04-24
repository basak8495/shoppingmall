package com.project.shoppingmall.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void reviewRegister(ReviewRequestDto reviewRequestDto) {

        Review review = reviewRequestDto.dtoToEntity();

        reviewRepository.save(review);
    }

    public List<ReviewResponseDto> getReviewList(Product product) {

        List<Review> reviewList = reviewRepository.findByProduct(product);

        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        for(Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }

        return reviewResponseDtoList;
    }
}
