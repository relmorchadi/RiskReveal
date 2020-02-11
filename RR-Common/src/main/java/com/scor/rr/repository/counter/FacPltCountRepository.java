package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.CedantCodeCountView;
import com.scor.rr.domain.counter.FacPltCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacPltCountRepository extends JpaRepository<FacPltCountView, String> {
    Page<FacPltCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
