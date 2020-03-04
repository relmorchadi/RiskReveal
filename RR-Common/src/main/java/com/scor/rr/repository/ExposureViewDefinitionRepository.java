package com.scor.rr.repository;


import com.scor.rr.domain.ExposureView;
import com.scor.rr.domain.ExposureViewDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExposureViewDefinitionRepository extends JpaRepository<ExposureViewDefinition, Long> {

    List<ExposureViewDefinition> findByExposureView(ExposureView exposureView);

    @Query("SELECT evd.name FROM ExposureViewDefinition evd")
    List<String> findExposureViewDefinitionsAliases();
}
