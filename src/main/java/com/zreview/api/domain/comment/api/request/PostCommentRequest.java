package com.zreview.api.domain.comment.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PostCommentRequest {

    private Long reviewId;

    private String content;

    private Long parentId;
}
