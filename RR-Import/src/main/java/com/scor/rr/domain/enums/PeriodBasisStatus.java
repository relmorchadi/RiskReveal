package com.scor.rr.domain.enums;

/**
 * PeriodBasis Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum PeriodBasisStatus {

	FULL("FullTerm"), Y1("Y1");

	private String code;

	PeriodBasisStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}