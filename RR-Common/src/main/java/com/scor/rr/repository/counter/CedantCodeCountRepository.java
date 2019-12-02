package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.CedantCodeCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CedantCodeCountRepository extends JpaRepository<CedantCodeCountView, String> {
    Page<CedantCodeCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
