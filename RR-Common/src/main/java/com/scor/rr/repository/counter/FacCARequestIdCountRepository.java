package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.CedantCodeCountView;
import com.scor.rr.domain.counter.FacCARequestIdCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacCARequestIdCountRepository extends JpaRepository<FacCARequestIdCountView, String> {
    Page<FacCARequestIdCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
