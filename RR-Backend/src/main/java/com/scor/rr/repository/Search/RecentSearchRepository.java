package com.scor.rr.repository.Search;

import com.scor.rr.domain.entities.Search.RecentSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
    List<RecentSearch> findByUserIdAndTypeOrderBySearchDateDesc(Long userId, String type);
    List<RecentSearch> findTop5ByUserIdAndTypeOrderBySearchDateDesc(Long userId, String type);
}
