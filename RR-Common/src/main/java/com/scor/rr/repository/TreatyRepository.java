package com.scor.rr.repository;


import com.scor.rr.domain.TreatyView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TreatyRepository extends JpaRepository<TreatyView, String> {
    Page<TreatyView> findByLabelIgnoreCaseLikeOrderByLabel(String label, Pageable pageable);
}
