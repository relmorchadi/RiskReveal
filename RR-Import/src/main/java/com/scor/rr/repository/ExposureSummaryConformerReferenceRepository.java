package com.scor.rr.repository;

import com.scor.rr.domain.reference.ExposureSummaryConformerReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExposureSummaryConformerReferenceRepository extends JpaRepository<ExposureSummaryConformerReference, Long> {

    ExposureSummaryConformerReference findBySourceVendorAndSourceSystemAndVersionAndAxisConformerAliasAndInputCode(String sourceVendor,String sourceSystem,String version,String axisConformerAlias,String inputCode);
}
