package com.scor.rr.domain.enums;

/**
 * Exposure Summary Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ExposureSummaryType {

	SOURCE("Source"), DERIVED("Derived"), CONDITIONNAL("Conditionnal"), UNKNOWN("Unknown");

	private String code;

	ExposureSummaryType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}