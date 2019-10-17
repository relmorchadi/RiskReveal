package com.scor.rr.repository.references;

import com.scor.rr.domain.entities.references.cat.mapping.region.RegionPerilMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by U002629 on 01/04/2015.
 */
public interface RegionPerilMappingRepository extends JpaRepository<RegionPerilMapping, Long> {
//    RegionPerilMapping findByModellingSystemVersionIdAndCountryCodeAndSourcePerilCode(
//            String modellingSystem,
//            String sourceCountryCode,
//            String sourcePerilCode);

    RegionPerilMapping findByCountryCodeAndAdmin1CodeAndPerilCode(String country, String admin1, String peril);

    List<RegionPerilMapping> findByRegionPerilId (Integer regionPerilId);
}
