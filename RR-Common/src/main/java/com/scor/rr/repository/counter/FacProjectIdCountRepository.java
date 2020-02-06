package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.FacPltCountView;
import com.scor.rr.domain.counter.FacProjectIdCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacProjectIdCountRepository extends JpaRepository<FacProjectIdCountView, String> {
    Page<FacProjectIdCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
