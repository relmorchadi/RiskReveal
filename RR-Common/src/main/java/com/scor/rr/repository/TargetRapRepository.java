package com.scor.rr.repository;

import com.scor.rr.domain.TargetRapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TargetRapRepository extends JpaRepository<TargetRapEntity, Long> {
    TargetRapEntity findByTargetRAPId(Long id);
    Optional<TargetRapEntity> findByTargetRAPCode(String code);

    @Query(value = "SELECT tr.* \n" +
            "FROM  dbo.ZZ_SourceRAPMapping sr\n" +
            "INNER JOIN dbo.TargetRAP tr ON tr.SourceRAPCode = sr.SourceRapCode\n" +
            "where sr.ProfileKey=? AND tr.IsActive = 1", nativeQuery = true)
    List<TargetRapEntity> findByProfileKey(String profileKey);
}