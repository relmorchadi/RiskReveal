package com.scor.rr.repository.rms;

import com.scor.rr.domain.entities.exposure.ExposureViewExtractQuery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by U002629 on 02/04/2015.
 */
public interface ExposureViewExtractQueryRepository extends JpaRepository<ExposureViewExtractQuery, String> {
    ExposureViewExtractQuery findByModellingSystemVersionIdAndExposureViewVersionId(String msvId, String evvId);
}
