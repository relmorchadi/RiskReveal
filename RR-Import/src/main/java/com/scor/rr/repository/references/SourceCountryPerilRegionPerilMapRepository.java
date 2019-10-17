package com.scor.rr.repository.references;

import com.scor.rr.domain.entities.references.SourceCountryPerilRegionPerilMap;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by U002629 on 06/03/2015.
 */
public interface SourceCountryPerilRegionPerilMapRepository extends JpaRepository<SourceCountryPerilRegionPerilMap, Long> {

    SourceCountryPerilRegionPerilMap findByModellingSystemIdAndSourceCountryCode2AndSourcePerilCode(
            String modellingSystem,
            String sourceCountryCode2,
            String sourcePerilCode);

//    SourceCountryPerilRegionPerilMap findByModellingSystemIdAndSourceCountryCode2AndSourceCountryCode3AndSourcePerilCode(
//            String modellingSystem,
//            String sourceCountryCode2,
//            String sourceCountryCode3,
//            String sourcePerilCode);
}
