package com.scor.rr.repository.rap;

import com.scor.rr.domain.entities.references.TargetRAP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hamiani Mohammed
 * creation date  17/09/2019 at 11:37
 **/
public interface TargetRapRepository extends JpaRepository<TargetRAP, Integer> {
    List<TargetRAP> findByModellingVendorAndModellingSystemAndModellingSystemVersionAndSourceRapCode(String modellingVendor, String modellingSystem, String modellingSystemVersion, String sourceRapCode);
    TargetRAP findByTargetRAPId(Integer targetRapId);
}
