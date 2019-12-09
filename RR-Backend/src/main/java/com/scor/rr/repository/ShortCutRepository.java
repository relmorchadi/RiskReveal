package com.scor.rr.repository;

import com.scor.rr.domain.entities.Search.ShortCut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortCutRepository extends JpaRepository<ShortCut, Long> {
}
