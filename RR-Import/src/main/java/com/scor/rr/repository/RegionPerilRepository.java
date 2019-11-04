package com.scor.rr.repository;

import com.scor.rr.domain.reference.RegionPeril;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionPerilRepository extends JpaRepository<RegionPeril, Integer> {

    RegionPeril findByRegionPerilCode(String rpCode);
}
