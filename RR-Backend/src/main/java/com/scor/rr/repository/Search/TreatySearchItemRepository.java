package com.scor.rr.repository.Search;

import com.scor.rr.domain.entities.Search.TreatySearchItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatySearchItemRepository extends JpaRepository<TreatySearchItem, Long> {

    void deleteByTreatySearchId(Long id);
}
