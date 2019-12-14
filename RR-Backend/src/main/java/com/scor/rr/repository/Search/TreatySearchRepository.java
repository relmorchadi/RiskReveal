package com.scor.rr.repository.Search;


import com.scor.rr.domain.entities.Search.TreatySearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatySearchRepository extends JpaRepository<TreatySearch, Long> {

    List<TreatySearch> findAllByUserIdOrderBySavedDateDesc(Integer userId);

    List<TreatySearch> findTop5ByUserIdOrderByCountDescSavedDateDesc(Integer userId);
}
