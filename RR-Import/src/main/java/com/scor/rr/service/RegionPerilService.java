package com.scor.rr.service;

import com.scor.rr.domain.RegionPerilMapping;
import com.scor.rr.domain.RegionPerilEntity;
import com.scor.rr.repository.RegionPerilMappingRepository;
import com.scor.rr.repository.RegionPerilRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegionPerilService {

    @Autowired
    private RegionPerilMappingRepository regionPerilMappingRepository;

    @Autowired
    private RegionPerilRepository regionPerilRepository;

    public RegionPerilEntity findRegionPerilByCountryCodeAdmin1CodePerilCode(String countryCode, String admin1Code, String perilCode) {

        RegionPerilEntity result = null;
        RegionPerilMapping rpm = this.findRegionPerilMappingByCountryCodeAdmin1CodePerilCode(countryCode, admin1Code, perilCode);
        if (rpm != null) {
            result = this.findRegionPerilByRegionPerilID(rpm.getRegionPerilID());
            if (result == null) {
                log.warn("no RegionPeril found for ID#'{}'", new Object[]{rpm.getRegionPerilID()});
            }
        } else {
            log.warn("no RegionPerilMapping found for '{}':'{}':'{}'", new Object[]{countryCode, admin1Code, perilCode});
        }
        return result;
    }


    public RegionPerilMapping findRegionPerilMappingByCountryCodeAdmin1CodePerilCode(String countryCode,String admin1Code,String perilCode)
    {
        return regionPerilMappingRepository.findByCountryCodeAndAdmin1CodeAndPerilCode(countryCode, admin1Code, perilCode).orElse(null);
    }

    public RegionPerilEntity findRegionPerilByRegionPerilID(Long regionPerilID)
    {
        return regionPerilRepository.findById(regionPerilID).orElse(null);
    }

    public RegionPerilEntity findRegionPerilByRegionPerilCode(String rpCode)
    {
        return regionPerilRepository.findByRegionPerilCode(rpCode);
    }
}
