package com.zreview.api.domain.save.api;

import com.zreview.api.domain.like.model.LikeType;
import com.zreview.api.domain.location.model.Location;
import com.zreview.api.domain.save.app.SaveService;
import com.zreview.api.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SaveController {
    private final SaveService saveService;

    @PostMapping("/save/{locationId}")
    public ResponseEntity<?> saveLocation(@PathVariable final long locationId,
                                          @AuthenticationPrincipal final MemberDetails member) {
        saveService.save(member.getEmail(),locationId);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/save/{locationId}")
    public ResponseEntity<?> unsaveLocation(@PathVariable final long locationId,
                                            @AuthenticationPrincipal final MemberDetails member){
        saveService.unsave(member.getEmail(),locationId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/save")
    public ResponseEntity<?> getSaveLocation(@AuthenticationPrincipal final MemberDetails member){
        List<Location> locationList = saveService.getSaveLocation(member.getEmail());
        return ResponseEntity.ok(locationList);
    }

}
