package com.scor.rr.repository;

import com.scor.rr.domain.TargetBuild.Search.FacSearchItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacSearchItemRepository extends JpaRepository<FacSearchItem, Long> {

    void deleteByFacSearchId(Long facSearchId);
}
