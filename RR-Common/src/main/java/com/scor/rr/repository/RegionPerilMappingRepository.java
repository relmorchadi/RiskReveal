package com.scor.rr.repository;

import com.scor.rr.domain.RegionPerilMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionPerilMappingRepository extends JpaRepository<RegionPerilMapping, Long> {

    Optional<RegionPerilMapping> findByCountryCodeAndAdmin1CodeAndPerilCode(String countryCode, String admin1Code, String perilCode);

    Optional<RegionPerilMapping> findByCountryCodeAndPerilCode(String countryCode, String perilCode);
}
