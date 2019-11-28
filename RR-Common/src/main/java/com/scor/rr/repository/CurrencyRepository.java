package com.scor.rr.repository;

import com.scor.rr.domain.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {
    CurrencyEntity findByCode(String code);
}