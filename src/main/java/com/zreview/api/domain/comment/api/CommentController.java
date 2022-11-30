package com.zreview.api.domain.comment.api;

import com.zreview.api.domain.comment.api.request.PostCommentRequest;
import com.zreview.api.domain.comment.app.CommentService;
import com.zreview.api.domain.comment.model.Comment;
import com.zreview.api.domain.like.model.LikeType;
import com.zreview.api.global.security.MemberDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<?> postComment(@RequestBody PostCommentRequest postCommentRequest,
                                         @AuthenticationPrincipal final MemberDetails member) {
        commentService.postComment(member.getEmail(), postCommentRequest);
        return ResponseEntity.ok(null);
    }



    @GetMapping("/comment/{reviewId}")
    public ResponseEntity<?> getComment(@PathVariable Long reviewId) {
        List<Comment> comment= commentService.getComment(reviewId);
        return ResponseEntity.ok(comment);
    }
}
