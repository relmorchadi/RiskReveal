package com.scor.rr.repository.Search;

import com.scor.rr.domain.entities.Search.RecentSearch;
import com.scor.rr.domain.entities.Search.TreatySearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
    List<RecentSearch> findByUserIdOrderBySearchDateDesc(Integer userId);
    List<RecentSearch> findTop5ByUserIdOrderBySearchDateDesc(Integer userId);
}
