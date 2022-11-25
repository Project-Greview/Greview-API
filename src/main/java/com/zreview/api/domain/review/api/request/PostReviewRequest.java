package com.zreview.api.domain.review.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Queue;

@Getter
@Setter
@RequiredArgsConstructor
public class PostReviewRequest {

    @NotNull@NotBlank
    private String title;

    @NotNull
    private String content;

    private String x;

    private String y;

    @NotNull
    private int rating; //별점

    @NotNull
    private Queue<String> hashtags; //해시태그


}

