package com.zreview.api.domain.review.app;

import com.zreview.api.domain.location.dao.LocationRepository;
import com.zreview.api.domain.location.model.Location;
import com.zreview.api.domain.member.dao.MemberRepository;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.api.request.PostReviewRequest;
import com.zreview.api.domain.review.api.response.GetReviewResponse;
import com.zreview.api.domain.review.api.response.ReviewResultResponse;
import com.zreview.api.domain.review.dao.HashTagRepository;
import com.zreview.api.domain.review.dao.ReviewRepository;
import com.zreview.api.domain.review.model.Hashtag;
import com.zreview.api.domain.review.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ReviewService {
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final HashTagRepository hashTagRepository;

    private final LocationRepository locationRepository;

    public ReviewService(MemberRepository memberRepository, ReviewRepository reviewRepository, HashTagRepository hashTagRepository, LocationRepository locationRepository) {
        this.memberRepository = memberRepository;
        this.reviewRepository = reviewRepository;
        this.hashTagRepository = hashTagRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public ReviewResultResponse postReview(String email, PostReviewRequest postReviewRequest){
        Optional<Member> member= memberRepository.findByEmail(email);
        Review review = new Review(member.get(), postReviewRequest);
        Location location = locationRepository.findById(postReviewRequest.getLocationId()).get();

        List<String> tagNames=postReviewRequest.getHashtags();
        List<Hashtag> hashtags=new ArrayList<>();
        for (String name : tagNames){
            Hashtag hashtag =new Hashtag(review,name,location);
            hashtags.add(hashtag);
            hashTagRepository.save(hashtag);
        }
        review.setLocation(location);
        review.setHashtags(hashtags);
        reviewRepository.save(review);

        return new ReviewResultResponse(review.getId(),"리뷰 등록 완료");
    }

    public GetReviewResponse getReview(Long reviewId){
        Optional<Review> review= reviewRepository.findById(reviewId);

        GetReviewResponse getReviewResponse= new GetReviewResponse(review.get());

        return getReviewResponse;
    }
}
