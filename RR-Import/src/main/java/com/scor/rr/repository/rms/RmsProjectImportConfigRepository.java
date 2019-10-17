package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.rms.RmsProjectImportConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RmsProjectImportConfig Repository
 *
 * @author HADDINI Zakariyae
 */
public interface RmsProjectImportConfigRepository extends JpaRepository<RmsProjectImportConfig, String> {

    RmsProjectImportConfig findByProjectImportSourceConfigId(String id);
}