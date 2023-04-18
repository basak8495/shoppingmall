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

    public List<ReviewResonseDto> getReviewList(Product product) {

        List<Review> reviewList = reviewRepository.findByProduct(product);

        List<ReviewResonseDto> reviewResonseDtoList = new ArrayList<>();

        for(Review review : reviewList) {
            ReviewResonseDto reviewResonseDto = new ReviewResonseDto(review);
            reviewResonseDtoList.add(reviewResonseDto);
        }

        return reviewResonseDtoList;
    }
}
