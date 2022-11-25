package com.zreview.api.domain.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long> {
    List<File> findAllByreviewId(@Param("id") Long id);
}
