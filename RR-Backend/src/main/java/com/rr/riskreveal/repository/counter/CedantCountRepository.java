package com.rr.riskreveal.repository.counter;

import com.rr.riskreveal.domain.counter.CedantCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CedantCountRepository extends JpaRepository<CedantCountView,String> {
    Page<CedantCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
