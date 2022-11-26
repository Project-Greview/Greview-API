package com.zreview.api.domain.like.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeType {
	REVIEW, COMMENT;
}
