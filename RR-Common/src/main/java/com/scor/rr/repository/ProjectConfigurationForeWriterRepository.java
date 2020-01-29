package com.scor.rr.repository;

import com.scor.rr.domain.ProjectConfigurationForeWriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectConfigurationForeWriterRepository extends JpaRepository<ProjectConfigurationForeWriter, Long> {

    Boolean existsByProjectId(Long projectId);

    ProjectConfigurationForeWriter findByProjectId(Long projectId);

    ProjectConfigurationForeWriter findByCaRequestId(String carId);
}
