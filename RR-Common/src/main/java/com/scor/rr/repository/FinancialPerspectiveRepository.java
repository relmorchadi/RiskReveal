package com.scor.rr.repository;

import com.scor.rr.domain.FinancialPerspective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface FinancialPerspectiveRepository extends JpaRepository<FinancialPerspective, Long> {

    FinancialPerspective findByCode(String code);

    // TODO ajouter la condition userSelectableForElt
    @Query("SELECT DISTINCT code FROM FinancialPerspective WHERE userSelectableForElt='1'")
    List<String> findAllCodes();

    @Query("SELECT fp.code as code, fp.description as desc  FROM FinancialPerspective fp")
    List<Map<String, String>> findAllCodesAndDesc();
}
