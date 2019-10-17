package com.scor.rr.repository.ihub;

import com.scor.rr.domain.entities.ihub.LinkedDataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkedDatasetRepository extends JpaRepository<LinkedDataset, String> {

    List<LinkedDataset> findByRmsProjectImportConfigId(String id);
}
