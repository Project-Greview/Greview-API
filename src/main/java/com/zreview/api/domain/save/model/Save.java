package com.zreview.api.domain.save.model;

import com.zreview.api.domain.like.model.LikeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Save {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "target_id")
    private Long locationId; //장소 아이디

    public Save(String email, Long locationId) {
        this.email = email;
        this.locationId = locationId;
    }
}
