package com.zreview.api.domain.location.api;

import com.zreview.api.domain.location.api.request.PostLocationRequest;
import com.zreview.api.domain.location.app.LocationService;
import com.zreview.api.domain.location.app.utils.LocationDto;
import com.zreview.api.domain.location.model.Location;
import io.swagger.v3.oas.annotations.Operation;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public ResponseEntity<?> GetLocation(@RequestParam Double latitude, @RequestParam Double longitude,@RequestParam double range) throws ParseException {
        return ResponseEntity.ok(locationService.getLocation(latitude,longitude,range));

    }

    @PostMapping("/location")
    public ResponseEntity<?> PostLocation(@RequestBody PostLocationRequest postLocationRequest) throws ParseException {
        locationService.postLocation(postLocationRequest);
        return ResponseEntity.ok(null);
    }
}
