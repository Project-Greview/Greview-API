package com.zreview.api.domain.like.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zreview.api.domain.like.app.LikeService;
import com.zreview.api.domain.like.model.LikeType;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.global.security.MemberDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/reviews/{reviewId}")
	public ResponseEntity<Boolean> likeReview(@PathVariable final long reviewId,
		@AuthenticationPrincipal final MemberDetails member) {
		likeService.like(LikeType.REVIEW, member.getEmail(), reviewId);
		return ResponseEntity.ok(Boolean.TRUE);
	}

	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<Boolean> unlikeReview(@PathVariable final long reviewId,
		@AuthenticationPrincipal final MemberDetails member) {
		likeService.unlike(LikeType.REVIEW, member.getEmail(), reviewId);
		return ResponseEntity.ok(Boolean.TRUE);
	}
}
