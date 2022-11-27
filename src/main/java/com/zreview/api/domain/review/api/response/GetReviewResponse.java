package com.zreview.api.domain.review.api.response;

import com.zreview.api.domain.review.model.Comment;
import com.zreview.api.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewResponse {

    private Review review;

}
