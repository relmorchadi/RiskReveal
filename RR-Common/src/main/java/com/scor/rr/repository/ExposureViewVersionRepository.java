package com.scor.rr.repository;

import com.scor.rr.domain.ExposureViewDefinition;
import com.scor.rr.domain.ExposureViewVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExposureViewVersionRepository extends JpaRepository<ExposureViewVersion, Long> {

    ExposureViewVersion findByExposureViewDefinitionAndCurrent(ExposureViewDefinition exposureViewDefinition, boolean current);
}
