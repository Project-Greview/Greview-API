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
        reviewRepository.save(review);

        int tag_size=postReviewRequest.getHashtags().size();

        if(tag_size != 0){
            for(int i=0; i<tag_size; i++){
                Hashtag hashtag=
                        new Hashtag(review,postReviewRequest.getHashtags().poll());
                hashTagRepository.save(hashtag);
            }
        }

        return new ReviewResultResponse(review.getId(),"리뷰 등록 완료");
    }

    public GetReviewResponse getReview(Long reviewId){
        Optional<Review> review= reviewRepository.findById(reviewId);

        List<Hashtag> hashtags=hashTagRepository.findByReview(review.get());

        List<String> tag_result=new ArrayList<>();

        for (Hashtag hashtag:hashtags){
            tag_result.add(hashtag.getName());
        }

        GetReviewResponse getReviewResponse= new GetReviewResponse(review.get(),tag_result);

        return getReviewResponse;
    }
}
