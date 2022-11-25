package com.zreview.api.domain.review.app;

import com.zreview.api.domain.member.dao.MemberRepository;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.api.request.PostReviewRequest;
import com.zreview.api.domain.review.api.response.ReviewResultResponse;
import com.zreview.api.domain.review.dao.HashTagRepository;
import com.zreview.api.domain.review.dao.ReviewRepository;
import com.zreview.api.domain.review.model.Hashtag;
import com.zreview.api.domain.review.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
