package com.zreview.api.domain.save.dao;

import com.zreview.api.domain.save.model.Save;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveRepository extends JpaRepository<Save, Long>{
    Save findByEmailAndLocationId(String email,Long locationId);
    List<Save> findByEmail(String email);
}
