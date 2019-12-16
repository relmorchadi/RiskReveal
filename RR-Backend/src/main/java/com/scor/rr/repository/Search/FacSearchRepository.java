package com.scor.rr.repository.Search;

import com.scor.rr.domain.entities.Search.FacSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacSearchRepository extends JpaRepository<FacSearch, Long> {

    List<FacSearch> findAllByUserIdOrderBySavedDateDesc(Integer userId);
    List<FacSearch> findTop5ByUserIdOrderByCountDescSavedDateDesc(Integer userId);
}
