package com.scor.rr.repository;


import com.scor.rr.domain.entities.FacContractSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacContractSearchResultRepository extends JpaRepository<FacContractSearchResult, String> {

}
