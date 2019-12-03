package com.scor.rr.repository.counter;

import com.scor.rr.domain.counter.CountryCountView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryCountRepository extends JpaRepository<CountryCountView, String> {
    Page<CountryCountView> findByLabelIgnoreCaseLikeOrderByCountOccurDesc(String label, Pageable pageable);
}
