package com.zreview.api.domain.review.dao;

import com.zreview.api.domain.review.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<Hashtag,Long> {


}
