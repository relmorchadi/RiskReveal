package com.scor.rr.repository;

import com.scor.rr.entity.InuringExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InuringExchangeRateVintageRepository extends JpaRepository<InuringExchangeRate, Integer > {

    @Query(value = "from InuringExchangeRate where domesticCurrency =?1 AND type =?2 AND  effectiveDate =?3")
    List<InuringExchangeRate> getExchangeRate( int domesticCurrency, String type, Date date);
}
