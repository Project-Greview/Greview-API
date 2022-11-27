package com.zreview.api.domain.review.api;

import com.zreview.api.domain.review.api.request.PostReviewRequest;

import com.zreview.api.domain.review.api.response.GetReviewResponse;
import com.zreview.api.domain.review.api.response.ReviewResultResponse;
import com.zreview.api.domain.review.app.ReviewService;
import com.zreview.api.domain.review.model.Review;
import com.zreview.api.global.security.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "review", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "리뷰 등록", tags = { "Review Controller" })
    @PostMapping("/review")
    public ResponseEntity<?> PostReview(@RequestBody PostReviewRequest postReviewRequest,
        @AuthenticationPrincipal final MemberDetails member){
        //String email= "test@naver.com"; //jwt로 변경필요
        ReviewResultResponse response= reviewService.postReview(member.getEmail(),postReviewRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "리뷰 반환", tags = { "Review Controller" })
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> GetReview(@PathVariable Long reviewId ){
        GetReviewResponse getReviewResponse = reviewService.getReview(reviewId);
        return ResponseEntity.ok(getReviewResponse);

    }




}
