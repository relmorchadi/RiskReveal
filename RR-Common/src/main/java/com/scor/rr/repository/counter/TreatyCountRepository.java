package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.TreatyCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatyCountRepository extends JpaRepository<TreatyCountView, String> {
    Page<TreatyCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
