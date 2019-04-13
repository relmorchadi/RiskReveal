package com.rr.riskreveal.repository;

import com.rr.riskreveal.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryPerilRepository extends JpaRepository<Country, String> {
    Page<Country> findByLabelIgnoreCaseLikeOrderByLabel(String label, Pageable pageable);
}
