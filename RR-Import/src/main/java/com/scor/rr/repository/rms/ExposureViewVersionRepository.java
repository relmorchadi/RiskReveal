package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.exposure.ExposureViewVersion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by U002629 on 02/04/2015.
 */
public interface ExposureViewVersionRepository extends JpaRepository<ExposureViewVersion, String> {
    ExposureViewVersion findByDefinitionIdAndCurrent(String definitionId, Boolean current);
}
