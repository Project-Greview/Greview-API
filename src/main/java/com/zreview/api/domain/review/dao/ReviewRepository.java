package com.zreview.api.domain.review.dao;

import com.zreview.api.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {


}
