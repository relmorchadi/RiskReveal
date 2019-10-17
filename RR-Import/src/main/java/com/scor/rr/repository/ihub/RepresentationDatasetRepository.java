package com.scor.rr.repository.ihub;

import com.scor.rr.domain.entities.ihub.RepresentationDataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * RepresentationDataset Repository
 *
 * @author HADDINI Zakariyae
 */
public interface RepresentationDatasetRepository extends JpaRepository<RepresentationDataset, Long> {

    RepresentationDataset findByRmsProjectImportConfigId(String id);

    List<RepresentationDataset> findByProjectProjectId(String projectId);
}