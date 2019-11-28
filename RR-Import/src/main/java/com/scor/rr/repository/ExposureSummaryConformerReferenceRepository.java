package com.scor.rr.repository;

import com.scor.rr.domain.reference.ExposureSummaryConformerReferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExposureSummaryConformerReferenceRepository extends JpaRepository<ExposureSummaryConformerReferenceEntity, Long> {

    ExposureSummaryConformerReferenceEntity findBySourceVendorAndSourceSystemAndVersionAndAxisConformerAliasAndInputCode(String sourceVendor, String sourceSystem, String version, String axisConformerAlias, String inputCode);
}
