package com.zreview.api.domain.review.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zreview.api.domain.location.model.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //해쉬태그명

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Hashtag(Review review,String name,Location location) {
        this.review=review;
        this.name = name;
        this.location=location;
    }
}
