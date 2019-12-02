package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.CedantNameCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CedantNameCountRepository extends JpaRepository<CedantNameCountView,String> {
    Page<CedantNameCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
