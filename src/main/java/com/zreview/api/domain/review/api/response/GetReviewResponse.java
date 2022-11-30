package com.zreview.api.domain.review.api.response;

import com.zreview.api.domain.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewResponse {

    private Review review;

}
