package com.scor.rr.repository;

import com.scor.rr.domain.FinancialPerspective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinancialPerspectiveRepository extends JpaRepository<FinancialPerspective, Long> {

    FinancialPerspective findByCode(String code);

    @Query("SELECT DISTINCT code FROM FinancialPerspective")
    List<String> findAllCodes();
}
