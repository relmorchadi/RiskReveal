package com.scor.rr.repository.rap;

import com.scor.rr.domain.entities.rap.SourceRapMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * SourceRapMapping Repository
 *
 * @author HADDINI Zakariyae
 */
public interface SourceRapMappingRepository extends JpaRepository<SourceRapMapping, Long> {
    List<SourceRapMapping> findByProfileKey(String profileKey);

}
