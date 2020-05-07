package com.scor.rr.repository.Search;


import com.scor.rr.domain.entities.Search.SavedSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedSearchRepository extends JpaRepository<SavedSearch, Long> {

    List<SavedSearch> findAllByUserIdAndTypeOrderBySavedDateDesc(Long userId, String type);
    List<SavedSearch> findTop5ByUserIdAndTypeOrderByCountDescSavedDateDesc(Long userId, String type);
}
