package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.FacPltCountView;
import com.scor.rr.domain.counter.FacProjectNameCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacProjectNameCountRepository extends JpaRepository<FacProjectNameCountView, String> {
    Page<FacProjectNameCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
