package com.zreview.api.domain.comment.dao;

import com.zreview.api.domain.comment.model.Comment;
import com.zreview.api.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByReviewAndParentId(Review review,Long ParentId);
}
