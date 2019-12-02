package com.scor.rr.repository;

import com.scor.rr.domain.CedantView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CedantRepository extends JpaRepository<CedantView, Long> {
    Page<CedantView> findByLabelIgnoreCaseLikeOrderByLabel(String label, Pageable pageable);
}
