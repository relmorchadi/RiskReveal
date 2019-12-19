package com.scor.rr.repository;

import com.scor.rr.domain.Calibration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalibrationRepository extends JpaRepository<Calibration, Long> {
    List<Calibration> findByWorkspaceContextCodeAndUwYearAndPltType(String workspaceContextCode, Integer uwYear, String pltType);
}
