package com.scor.rr.repository;

import com.scor.rr.domain.PltHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PltHeaderRepository extends JpaRepository<PltHeaderEntity, Long> {
    PltHeaderEntity findByPltHeaderId(Long pkScorPltHeaderId);
}

