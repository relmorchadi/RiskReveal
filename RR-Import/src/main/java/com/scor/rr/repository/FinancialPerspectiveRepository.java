package com.scor.rr.repository;

import com.scor.rr.domain.RRFinancialPerspective;
import com.scor.rr.domain.reference.FinancialPerspective;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialPerspectiveRepository extends JpaRepository<FinancialPerspective, Long> {

    FinancialPerspective findByCode(String code);
}
