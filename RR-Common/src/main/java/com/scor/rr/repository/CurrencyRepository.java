package com.scor.rr.repository;

import com.scor.rr.domain.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {

    CurrencyEntity findByCode(String code);

    @Query("select ce.currencyId, ce.label from CurrencyEntity ce")
    List<Object> findReferenceCurrencies();

    @Query("SELECT c.currencyId from CurrencyEntity c")
    List<String> findAllCurrencies();

}