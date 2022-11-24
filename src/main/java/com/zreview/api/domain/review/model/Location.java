package com.zreview.api.domain.review.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
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
}
