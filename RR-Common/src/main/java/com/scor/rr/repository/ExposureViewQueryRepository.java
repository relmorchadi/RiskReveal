package com.scor.rr.repository;

import com.scor.rr.domain.ExposureViewQuery;
import com.scor.rr.domain.ExposureViewVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExposureViewQueryRepository extends JpaRepository<ExposureViewQuery, Long> {

    ExposureViewQuery findByExposureViewVersion(ExposureViewVersion exposureViewVersion);
}
