package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.WorkspaceIdCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceIdCountViewRepository extends JpaRepository<WorkspaceIdCountView, String> {
    Page<WorkspaceIdCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
