package com.scor.rr.service.adjustement;

import com.scor.rr.domain.DefaultAdjustmentRegionPerilEntity;
import com.scor.rr.repository.DefaultAdjustmentRegionPerilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultAdjustmentRegionPerilService {

    @Autowired
    DefaultAdjustmentRegionPerilRepository regionPerilRepository;


    public List<DefaultAdjustmentRegionPerilEntity> findAll(){
        return regionPerilRepository.findAll();
    }

    public boolean regionPerilDefaultAdjustmentExist(Integer idDefaultAdjustment,Long regionPeril) {
        return regionPerilRepository.findAll().stream()
                .noneMatch(defaultAdjustmentRegionPerilEntity -> defaultAdjustmentRegionPerilEntity.getFkDefaultAdjustmentId() == idDefaultAdjustment && defaultAdjustmentRegionPerilEntity.getRegionPeril().equals(regionPeril) && defaultAdjustmentRegionPerilEntity.getIncludedExcluded().equals("I"));
    }
}
