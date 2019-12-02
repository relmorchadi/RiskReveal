package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.ProgramCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<ProgramCountView, String> {

    Page<ProgramCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);

}
