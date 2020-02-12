package com.scor.rr.domain.enums;

/**
 * Axis Conformer Mode Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum AxisConformerMode {

	Reference("Reference"), Function("Function"), Copy("Copy"), Unknown("Unknown");

	private String code;

	AxisConformerMode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}