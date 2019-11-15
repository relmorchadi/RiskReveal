package com.scor.rr.repository;


import com.scor.rr.domain.ExposureView;
import com.scor.rr.domain.ExposureViewDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExposureViewDefinitionRepository extends JpaRepository<ExposureViewDefinition, Long> {

    List<ExposureViewDefinition> findByExposureView(ExposureView exposureView);
}
