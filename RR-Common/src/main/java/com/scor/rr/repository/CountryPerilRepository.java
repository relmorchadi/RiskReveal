package com.scor.rr.repository;

import com.scor.rr.domain.CountryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryPerilRepository extends JpaRepository<CountryView, String> {
    Page<CountryView> findByLabelIgnoreCaseLikeOrderByLabel(String label, Pageable pageable);
}
