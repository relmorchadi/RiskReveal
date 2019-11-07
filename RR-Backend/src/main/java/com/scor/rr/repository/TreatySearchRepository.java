package com.scor.rr.repository;


import com.scor.rr.domain.TreatySearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatySearchRepository extends JpaRepository<TreatySearch, Long> {

    List<TreatySearch> findAllByUserId(Integer userId);
}
