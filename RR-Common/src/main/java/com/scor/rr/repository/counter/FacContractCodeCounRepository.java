package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.CedantCodeCountView;
import com.scor.rr.domain.counter.FacContractCodeCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacContractCodeCounRepository extends JpaRepository<FacContractCodeCountView, String> {
    Page<FacContractCodeCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
