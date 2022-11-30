package com.zreview.api.domain.review.api;

import com.zreview.api.domain.review.api.request.PostReviewRequest;

import com.zreview.api.domain.review.api.response.GetReviewResponse;
import com.zreview.api.domain.review.api.response.ReviewResultResponse;
import com.zreview.api.domain.review.app.ReviewService;
import com.zreview.api.domain.review.model.Review;
import com.zreview.api.global.security.MemberDetails;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    public ResponseEntity<?> PostReview(@RequestBody PostReviewRequest postReviewRequest,
      @AuthenticationPrincipal final MemberDetails member){
        //String email= "test@naver.com"; //jwt로 변경필요
        ReviewResultResponse response= reviewService.postReview(member.getEmail(),postReviewRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> GetReview(@PathVariable Long reviewId ){
        GetReviewResponse getReviewResponse = reviewService.getReview(reviewId);
        return ResponseEntity.ok(getReviewResponse);

    }

    @GetMapping("/review/location/{location}")
    public ResponseEntity<?> GetReviewByLocation(@PathVariable Long LocationId){
        List<Review> reviews= reviewService.getReviewByLocation(LocationId);
        return ResponseEntity.ok(reviews);
    }




}
