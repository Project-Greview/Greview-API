package com.zreview.api.domain.comment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.model.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @Column(columnDefinition = "boolean default 0")
    private Boolean is_deleted;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;


    @JsonManagedReference
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public Comment(Review review,Member member,String content){
        this.review=review;
        this.member=member;
        this.content=content;
    }

    public Comment(Review review, Member member,String content,Comment comment){
        this.review=review;
        this.member=member;
        this.content=content;
        this.parent=comment;
    }

}
