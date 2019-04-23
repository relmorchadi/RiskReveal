package com.rr.riskreveal.repository.counter;

import com.rr.riskreveal.domain.counter.WorkspaceNameCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceNameCountViewRepository extends JpaRepository<WorkspaceNameCountView, String> {
    Page<WorkspaceNameCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
