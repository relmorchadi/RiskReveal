package com.scor.rr.repository;

import com.scor.rr.domain.FacSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacSearchRepository extends JpaRepository<FacSearch, Long> {

    List<FacSearch> findAllByUserId(Integer userId);
}
