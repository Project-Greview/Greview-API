package com.zreview.api.domain.location.model;

import com.zreview.api.domain.location.api.request.PostLocationRequest;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.api.request.PostReviewRequest;
import com.zreview.api.domain.review.model.Hashtag;
import com.zreview.api.domain.review.model.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "integer default 0",nullable = false)
    private int rating; //별점

    @OneToMany(mappedBy = "location")
    private List<Review> reviews = new ArrayList<>(); //리뷰

    private Point point; //위도, 경도

    @OneToMany(mappedBy = "location")
    private List<Hashtag> hashtags= new ArrayList<>(); //해쉬태그

    public Location(PostLocationRequest postLocationRequest,Point point) {
        this.name=postLocationRequest.getName();
        this.point=point;
    }
}
