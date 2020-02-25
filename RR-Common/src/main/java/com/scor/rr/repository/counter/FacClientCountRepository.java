package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.FacClientCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacClientCountRepository extends JpaRepository<FacClientCountView, String> {
    Page<FacClientCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
