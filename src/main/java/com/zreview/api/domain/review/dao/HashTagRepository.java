package com.zreview.api.domain.review.dao;

import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.model.Hashtag;
import com.zreview.api.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<Hashtag,Long> {

    List<Hashtag> findByReview(Review review);
    List<Hashtag> findAllByName(String name);

}
