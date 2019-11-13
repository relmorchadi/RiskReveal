package com.scor.rr.domain.enums;

/**
 * Finacial Share Basis Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum FinacialShareBasis {

	SCOR_SHARE("Scor share"), CEDED_SHARE("100% Ceded Share"), UNKNOWN("Unknown");

	private String code;

	FinacialShareBasis(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
