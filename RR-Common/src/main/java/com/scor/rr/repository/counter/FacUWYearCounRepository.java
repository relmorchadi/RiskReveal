package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.FacUWYearCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacUWYearCounRepository extends JpaRepository<FacUWYearCountView, String> {
    Page<FacUWYearCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
