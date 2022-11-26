package com.zreview.api.domain.like.dao;

import java.util.List;

import com.zreview.api.domain.like.model.LikeType;
import com.zreview.api.domain.like.model.ReviewLike;

public interface CustomLikeRepository {

	List<ReviewLike> findAllByReviewIds(LikeType type, List<Long> reviewIds);
}
