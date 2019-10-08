package com.scor.rr.repository;

import com.scor.rr.domain.PltHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScorpltheaderRepository extends JpaRepository<PltHeaderEntity, Integer> {
    PltHeaderEntity findByPkScorPltHeaderId(int pkScorPltHeaderId);
}

