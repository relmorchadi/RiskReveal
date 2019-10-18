package com.scor.rr.repository.plt;

import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.enums.PLTType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ScorPLTHeader Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface ScorPLTHeaderRepository extends JpaRepository<ScorPLTHeader, String> {

    List<ScorPLTHeader> findByProjectProjectIdAndPltType(String projectId, PLTType pltType);
}