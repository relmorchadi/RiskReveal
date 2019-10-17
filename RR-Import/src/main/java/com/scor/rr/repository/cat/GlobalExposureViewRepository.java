package com.scor.rr.repository.cat;

import com.scor.rr.domain.entities.cat.GlobalExposureView;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * GlobalExposureView Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface GlobalExposureViewRepository extends JpaRepository<GlobalExposureView, String> {

    GlobalExposureView findByCatRequestCatRequestIdAndDivisionNumberAndPeriodBasisPeriodBasisIdAndName(String catId, int divisionNumber, String periodId, String Name);
}