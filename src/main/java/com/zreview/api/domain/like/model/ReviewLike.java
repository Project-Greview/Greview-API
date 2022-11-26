package com.zreview.api.domain.like.model;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class ReviewLike {

	private final long targetId;
	private final long count;


	@QueryProjection
	public ReviewLike(long targetId, long count) {
		this.targetId = targetId;
		this.count = count;
	}
}
