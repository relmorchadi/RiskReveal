package com.scor.rr.repository;


import com.scor.rr.domain.Treaty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TreatyRepository extends JpaRepository<Treaty, String> {
    Page<Treaty> findByLabelIgnoreCaseLikeOrderByLabel(String label, Pageable pageable);
}
