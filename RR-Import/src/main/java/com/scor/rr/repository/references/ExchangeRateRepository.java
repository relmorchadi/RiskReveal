package com.scor.rr.repository.references;

import com.scor.rr.domain.entities.references.ExchangeRate;
import com.scor.rr.domain.enums.ExchangeRateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ExchangeRate Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

	//ExchangeRate findFirstByTypeAndDomesticCurrencyIdOrderByEffectiveDateDesc(ExchangeRateType type, String ccy);
	ExchangeRate findFirstByTypeAndDomesticCurrencyCurrencyIdAndEffectiveDateBetweenOrderByEffectiveDateDesc(String type, String principalCcy, Date start, Date end);
	//ExchangeRate findByExRateEUR(BigDecimal exRateEUR);

}
