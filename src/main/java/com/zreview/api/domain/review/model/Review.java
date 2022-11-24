package com.zreview.api.domain.review.model;

import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.api.request.PostReviewRequest;
import com.zreview.api.domain.upload.File;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="location_id")
    private Location location;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @Column(columnDefinition = "integer default 0",nullable = false)
    private int views; //조회수

    @Column(columnDefinition = "integer default 0",nullable = false)
    private int rating; //별점

    @OneToMany(mappedBy = "review")
    private List<Hashtag> hashtags = new ArrayList<>();

    @OneToMany
    private List<File> file= new ArrayList<>();

    public Review(Member member, PostReviewRequest postReviewRequest) {
        this.member=member;
        this.title=postReviewRequest.getTitle();
        this.content=postReviewRequest.getContent();
        this.rating=postReviewRequest.getRating();
        this.views=0;
    }

    public Review(Review review, List<File> urls) {
        this.member=review.getMember();
        this.title=review.getTitle();
        this.content=review.getContent();
        this.rating= review.getRating();
        this.views= review.getRating();
        this.file=urls;
    }
}
