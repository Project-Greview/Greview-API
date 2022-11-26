package com.zreview.api.domain.location.app.utils;

import com.zreview.api.domain.location.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {

    private Long id;
    private String name;
    private int rating;

    private Double longitude;

    private Double latitude;

    public LocationDto (Location location) {
        this.id= location.getId();
        this.name= location.getName();
        this.rating= location.getRating();
        this.longitude=location.getPoint().getX();
        this.latitude=location.getPoint().getY();
    }
}
