package com.scor.rr.repository;

import com.scor.rr.domain.ExchangeRateEntity;
import com.scor.rr.domain.dto.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ExchangerateRepository extends JpaRepository<ExchangeRateEntity, String> {


    @Transactional
    @Query(value = "exec dbonew.usp_FindExchangeRatesBySourceCurrencies @effectiveDate=:effectiveDate, @currencies=:currencies", nativeQuery = true)
    List<Map<String, Object>> findExchangeRatesBySourceCurrenciesAndEffectiveDate(@Param("effectiveDate") Date effectiveDate, @Param("currencies") String currencies);


}