package com.scor.rr.repository;

import com.scor.rr.domain.ScorPltHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScorpltheaderRepository extends JpaRepository<ScorPltHeaderEntity, String> {
    ScorPltHeaderEntity findByScorPltHeaderId(String s);
}

