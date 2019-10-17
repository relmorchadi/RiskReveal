package com.scor.rr.repository;

import com.scor.rr.domain.entities.references.RegionPeril;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hamiani Mohammed
 * creation date  25/09/2019 at 17:35
 **/
public interface TTRegionPerilRepository extends JpaRepository<RegionPeril, Long> {


    RegionPeril findByRegionPerilId(Integer regionPerilID);

    RegionPeril findByRegionPerilCode(String regionPerilCode);

    List<RegionPeril> findByHierarchyParentCode(String rp);
}
