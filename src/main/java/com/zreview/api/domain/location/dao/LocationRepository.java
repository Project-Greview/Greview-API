package com.zreview.api.domain.location.dao;

import com.zreview.api.domain.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
