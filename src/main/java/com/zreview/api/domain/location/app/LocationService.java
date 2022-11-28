package com.zreview.api.domain.location.app;

import com.zreview.api.domain.location.api.reponse.SearchResponseDto;
import com.zreview.api.domain.location.api.request.PostLocationRequest;
import com.zreview.api.domain.location.app.utils.CardinalDirection;
import com.zreview.api.domain.location.app.utils.GeometryUtils;
import com.zreview.api.domain.location.app.utils.LocationDto;
import com.zreview.api.domain.location.app.utils.Position;
import com.zreview.api.domain.location.dao.LocationRepository;

import com.zreview.api.domain.location.model.Location;
import com.zreview.api.domain.review.dao.HashTagRepository;
import com.zreview.api.domain.review.model.Hashtag;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Service
public class LocationService {

    private final LocationRepository locationRepository;

    private final HashTagRepository hashTagRepository;

    private final EntityManager entityManager;

    public LocationService(LocationRepository locationRepository, HashTagRepository hashTagRepository, EntityManager entityManager) {
        this.locationRepository = locationRepository;
        this.hashTagRepository = hashTagRepository;
        this.entityManager = entityManager;
    }

    public List<LocationDto> getLocation(Double latitude, Double longitude, double range) throws ParseException {

        double distance = range;

        // 북동쪽 좌표 구하기
        Position northEast = GeometryUtils.calculateByDirection(latitude, longitude, distance, CardinalDirection.NORTHEAST
                .getBearing());
        Position southWest = GeometryUtils.calculateByDirection(latitude, longitude, distance, CardinalDirection.SOUTHWEST
                .getBearing());

        double x1 = northEast.getLongitude();
        double y1 = northEast.getLatitude();
        double x2 = southWest.getLongitude();
        double y2 = southWest.getLatitude();

        // native query 활용
        Query query = entityManager.createNativeQuery("" +
                        "SELECT r.id, r.name, r.rating, r.Point \n" +
                        "FROM location AS r \n" +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2) + ", r.point)"
                , Location.class);

        List<Location> results = query.getResultList();

        List<LocationDto> data=new ArrayList<>();
        for (Location result:results){
            LocationDto locationDto=new LocationDto(result);
            data.add(locationDto);
        }

        return data;

    }

    public void postLocation(PostLocationRequest postLocationRequest) throws ParseException {
        String pointWKT = String.format("POINT(%s %s)", postLocationRequest.getLongitude(),postLocationRequest.getLatitude());
        Point point=(Point)new WKTReader().read(pointWKT);
        Location location= new Location(postLocationRequest,point);
        locationRepository.save(location);
    }

    public List<SearchResponseDto> searchLocation(String search_tag){
        List<Hashtag> hashtags=hashTagRepository.findAllByName(search_tag);
        List<SearchResponseDto> searchResponseDtos= new ArrayList<>();
        for (Hashtag hashtag : hashtags){
            Double longitude=hashtag.getLocation().getPoint().getX();
            Double latitude=hashtag.getLocation().getPoint().getY();
            SearchResponseDto searchResponseDto= new SearchResponseDto(longitude,latitude);
            searchResponseDtos.add(searchResponseDto);
        }

        return searchResponseDtos;
    }
}
