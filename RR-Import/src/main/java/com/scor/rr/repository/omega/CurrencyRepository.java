package com.scor.rr.repository.omega;

import com.scor.rr.domain.entities.references.omega.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Currency Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface CurrencyRepository extends JpaRepository<Currency, String> {

	Currency findByCode(String code);

}