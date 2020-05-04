package com.scor.rr.repository.Search;

import com.scor.rr.domain.entities.Search.SavedSearchItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedSearchItemRepository extends JpaRepository<SavedSearchItem, Long> {
}
