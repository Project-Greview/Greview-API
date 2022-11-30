package com.zreview.api.domain.location.dao;

import com.zreview.api.domain.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long> {
    List<Location> findAllByNameContains(String name);
}
