package com.scor.rr.repository.Search;

import com.scor.rr.domain.entities.Search.RecentSearchItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentSearchItemRepository extends JpaRepository<RecentSearchItem, Long> {
}
