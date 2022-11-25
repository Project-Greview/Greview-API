package com.zreview.api.domain.review.dao;

import com.zreview.api.domain.review.model.Comment;
import com.zreview.api.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
