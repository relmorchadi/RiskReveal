package com.scor.rr.repository.references;

import com.scor.rr.domain.entities.references.SourceCountryPerilRegionPerilGroupMap;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by U002629 on 27/05/2015.
 */
public interface SourceCountryPerilRegionPerilGroupMapRepository  extends JpaRepository<SourceCountryPerilRegionPerilGroupMap, Long> {
    SourceCountryPerilRegionPerilGroupMap findByModellingSystemIdAndSourceCountryCode2AndSourcePerilCode(
            String modellingSystem,
            String sourceCountryCode2,
            String sourcePerilCode);
}
