package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.CedantCodeCountView;
import com.scor.rr.domain.counter.FacUwAnalysisCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacUwAnalysisCountRepository extends JpaRepository<FacUwAnalysisCountView, String> {
    Page<FacUwAnalysisCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
