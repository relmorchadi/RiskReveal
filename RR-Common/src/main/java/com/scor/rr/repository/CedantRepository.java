package com.scor.rr.repository;

import com.scor.rr.domain.Cedant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CedantRepository extends JpaRepository<Cedant, Long> {
    Page<Cedant> findByLabelIgnoreCaseLikeOrderByLabel(String label, Pageable pageable);
}
