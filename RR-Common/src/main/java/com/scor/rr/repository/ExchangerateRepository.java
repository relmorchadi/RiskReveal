package com.scor.rr.repository;

import com.scor.rr.domain.ExchangerateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangerateRepository extends JpaRepository<ExchangerateEntity, String> {
}