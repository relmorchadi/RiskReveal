package com.scor.rr.domain.enums;

/**
 * Axis Conformer Mode Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum AxisConformerMode {

	REFERENCE("Reference"), FUNCTION("Function"), COPY("Copy"), UNKNOWN("Unknown");

	private String code;

	AxisConformerMode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}