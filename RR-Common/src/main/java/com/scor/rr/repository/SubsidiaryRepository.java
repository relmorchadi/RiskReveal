package com.scor.rr.repository;

import com.scor.rr.domain.SubsidiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubsidiaryRepository extends JpaRepository<SubsidiaryEntity, String> {
    SubsidiaryEntity findByCode(String code);
}

