package com.zreview.api.domain.like.app;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zreview.api.domain.like.dao.LikeRepository;
import com.zreview.api.domain.like.model.Like;
import com.zreview.api.domain.like.model.LikeType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;

	@Transactional()
	public void like(final LikeType likeType, final String email, final long targetId) {
		Like like = likeRepository.findByTypeAndEmailAndTargetId(likeType, email, targetId);

		if (like != null) {
			throw new IllegalArgumentException();
		}

		Like newLike = new Like(
			likeType,
			email,
			targetId
		);

		likeRepository.save(newLike);
	}

	@Transactional
	public void unlike(LikeType likeType, String email, long targetId) {
		Like like = likeRepository.findByTypeAndEmailAndTargetId(likeType, email, targetId);

		if (like == null) {
			throw new IllegalArgumentException();
		}

		likeRepository.delete(like);
	}
}
