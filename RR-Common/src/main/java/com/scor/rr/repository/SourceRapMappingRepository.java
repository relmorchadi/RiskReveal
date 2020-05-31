package com.scor.rr.repository;

import com.scor.rr.domain.SourceRapMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceRapMappingRepository extends JpaRepository<SourceRapMappingEntity, Long> {
    List<SourceRapMappingEntity> findByProfileKey(String profileKey);
}
