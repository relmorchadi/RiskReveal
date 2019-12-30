package com.scor.rr.repository;

import com.scor.rr.domain.CalibrationView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalibrationRepository extends JpaRepository<CalibrationView, Long> {
    List<CalibrationView> findByWorkspaceContextCodeAndUwYearAndPltType(String workspaceContextCode, Integer uwYear, String pltType);
}
