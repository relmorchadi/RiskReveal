package com.rr.riskreveal.repository.counter;

import com.rr.riskreveal.domain.counter.ProgramView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<ProgramView, String> {

    Page<ProgramView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);

}
