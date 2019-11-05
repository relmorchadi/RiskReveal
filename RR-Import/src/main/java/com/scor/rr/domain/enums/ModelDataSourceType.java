package com.scor.rr.domain.enums;

/**
 * Model Data Source Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ModelDataSourceType {

	EDM("EDM"), RDM("RDM"), UNKNOWN("Unknown");

	private String code;

	ModelDataSourceType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}