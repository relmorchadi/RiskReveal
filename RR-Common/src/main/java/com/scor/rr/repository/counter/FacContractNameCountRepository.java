package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.CedantCodeCountView;
import com.scor.rr.domain.counter.FacContractNameCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacContractNameCountRepository extends JpaRepository<FacContractNameCountView, String> {
    Page<FacContractNameCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
