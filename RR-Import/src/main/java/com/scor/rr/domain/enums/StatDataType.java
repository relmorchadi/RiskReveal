package com.scor.rr.domain.enums;

/**
 * Stat Data Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum StatDataType {

	STAT_DATA_TYPE_ELT("ELT"), STAT_DATA_TYPE_PLT("PLT"), STAT_DATA_TYPE_SCOR_PLT("SCOR PLT");

	private String code;

	StatDataType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}