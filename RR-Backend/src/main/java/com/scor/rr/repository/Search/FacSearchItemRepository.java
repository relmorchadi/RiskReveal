package com.scor.rr.repository.Search;

import com.scor.rr.domain.entities.Search.FacSearchItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacSearchItemRepository extends JpaRepository<FacSearchItem, Long> {

    void deleteByFacSearchId(Long facSearchId);
}
