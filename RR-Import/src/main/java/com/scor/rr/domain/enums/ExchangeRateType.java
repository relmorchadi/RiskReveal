package com.scor.rr.domain.enums;

/**
 * ExchangeRate Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ExchangeRateType {
	
	MONTHLY("MONTHLY"), QUARTLY("QUARTLY"), YEARLY("YEARLY");

	private String code;

	ExchangeRateType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}