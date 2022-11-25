package com.zreview.api.domain.review.api;

import com.zreview.api.domain.review.api.request.PostReviewRequest;

import com.zreview.api.domain.review.api.response.ReviewResultResponse;
import com.zreview.api.domain.review.app.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "review", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "리뷰 등록", tags = { "Review Controller" })
    @PostMapping("/review")
    public ResponseEntity<?> PostReview(@RequestBody PostReviewRequest postReviewRequest){
        String email= "test@naver.com"; //jwt로 변경필요
        ReviewResultResponse response= reviewService.postReview(email,postReviewRequest);
        return ResponseEntity.ok(response);


    }




}
