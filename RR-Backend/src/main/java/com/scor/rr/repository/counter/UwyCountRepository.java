package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.UwyCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UwyCountRepository extends JpaRepository<UwyCountView, String> {
    Page<UwyCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);

}
