package com.scor.rr.repository;

import com.scor.rr.domain.TreatySearchItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatySearchItemRepository extends JpaRepository<TreatySearchItem, Long> {

    void deleteByTreatySearchId(Long id);
}
