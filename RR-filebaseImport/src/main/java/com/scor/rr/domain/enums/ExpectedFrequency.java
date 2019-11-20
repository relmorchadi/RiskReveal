package com.scor.rr.domain.enums;

/**
 * Expected Frequency Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ExpectedFrequency {

	QUARTERLY("Quarterly"), MONTHLY("Monthly"), UNKNOWN("Unknown");

	private String code;

	ExpectedFrequency(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}