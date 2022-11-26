package com.zreview.api.domain.like.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zreview.api.domain.like.model.Like;
import com.zreview.api.domain.like.model.LikeType;

public interface LikeRepository extends JpaRepository<Like, Long>, CustomLikeRepository {

	Like findByTypeAndEmailAndTargetId(LikeType type, String email, long targetId);
}
