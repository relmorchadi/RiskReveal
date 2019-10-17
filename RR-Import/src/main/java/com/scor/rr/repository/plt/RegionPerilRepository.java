package com.scor.rr.repository.plt;

import com.scor.rr.domain.entities.references.RegionPeril;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RegionPeril Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface RegionPerilRepository extends JpaRepository<RegionPeril, Long> {

	RegionPeril findByRegionPerilCode(String regionPerilCode);

}