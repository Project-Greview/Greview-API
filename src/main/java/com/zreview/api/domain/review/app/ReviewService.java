package com.zreview.api.domain.review.app;

import com.zreview.api.domain.member.dao.MemberRepository;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.api.ReviewController;
import com.zreview.api.domain.review.api.request.PostReviewRequest;
import com.zreview.api.domain.review.api.response.GetReviewResponse;
import com.zreview.api.domain.review.api.response.ReviewResultResponse;
import com.zreview.api.domain.review.dao.CommentRepository;
import com.zreview.api.domain.review.dao.HashTagRepository;
import com.zreview.api.domain.review.dao.ReviewRepository;
import com.zreview.api.domain.review.model.Comment;
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

    public ReviewService(MemberRepository memberRepository, ReviewRepository reviewRepository, HashTagRepository hashTagRepository) {
        this.memberRepository = memberRepository;
        this.reviewRepository = reviewRepository;
        this.hashTagRepository = hashTagRepository;
    }

    @Transactional
    public ReviewResultResponse postReview(String email, PostReviewRequest postReviewRequest){
        Optional<Member> member= memberRepository.findByEmail(email);
        Review review = new Review(member.get(), postReviewRequest);


        List<String> tagNames=postReviewRequest.getHashtags();
        List<Hashtag> hashtags=new ArrayList<>();
        for (String name : tagNames){
            Hashtag hashtag =new Hashtag(review,name);
            hashtags.add(hashtag);
            hashTagRepository.save(hashtag);
        }
        review.setHashtags(hashtags);
        reviewRepository.save(review);
        System.out.println(review.getHashtags());
        return new ReviewResultResponse(review.getId(),"리뷰 등록 완료");
    }

    public GetReviewResponse getReview(Long reviewId){
        Optional<Review> review= reviewRepository.findById(reviewId);

        GetReviewResponse getReviewResponse= new GetReviewResponse(review.get());

        return getReviewResponse;
    }
}
