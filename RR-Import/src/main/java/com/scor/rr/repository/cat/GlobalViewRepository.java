package com.scor.rr.repository.cat;

import com.scor.rr.domain.entities.cat.GlobalView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalViewRepository extends JpaRepository<GlobalView, Integer> {

    GlobalView findByProjectProjectIdAndRunId(String projectId, Integer runId);
}
