package com.zreview.api.domain.like.dao;

import static com.zreview.api.domain.like.model.QLike.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zreview.api.domain.like.model.LikeType;
import com.zreview.api.domain.like.model.QReviewLike;
import com.zreview.api.domain.like.model.ReviewLike;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomLikeRepositoryImpl implements CustomLikeRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ReviewLike> findAllByReviewIds(LikeType type, List<Long> reviewIds) {
		return queryFactory.select(
			new QReviewLike(
				like.targetId,
				like.id.count()
			))
			.from(like)
			.where(
				like.type.eq(type)
			)
			.groupBy(like.targetId)
			.fetch();
	}
}
