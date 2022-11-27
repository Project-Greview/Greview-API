package com.zreview.api.domain.comment.app;

import com.zreview.api.domain.comment.api.request.PostCommentRequest;
import com.zreview.api.domain.comment.dao.CommentRepository;
import com.zreview.api.domain.comment.model.Comment;
import com.zreview.api.domain.member.dao.MemberRepository;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.dao.ReviewRepository;
import com.zreview.api.domain.review.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    public CommentService(MemberRepository memberRepository, CommentRepository commentRepository, ReviewRepository reviewRepository) {
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
    }


    @Transactional
    public void postComment(String email, PostCommentRequest postCommentRequest) {
        Optional<Member> member = memberRepository.findByEmail(email);
        Long reviewId = postCommentRequest.getReviewId();
        Optional<Review> review = reviewRepository.findById(reviewId);

        if (postCommentRequest.getParentId() == null) { //대댓글이 아닌경우
            Comment comment = new Comment(review.get(), member.get(), postCommentRequest.getContent());
            commentRepository.save(comment);
        } else { //대댓글인 경우
            Optional<Comment> parent = commentRepository.findById(postCommentRequest.getParentId());
            Comment comment = new Comment(review.get(), member.get(), postCommentRequest.getContent(), parent.get());
            commentRepository.save(comment);
            List<Comment> children = parent.get().getChildren();
            children.add(comment);
            parent.get().setChildren(children);
        }
    }


    public List<Comment> getComment(Long reviewId){
        Optional<Review> review = reviewRepository.findById(reviewId);
        List<Comment> comments =commentRepository.findAllByReviewAndParentId(review.get(),null);
        return comments;
    }
}
