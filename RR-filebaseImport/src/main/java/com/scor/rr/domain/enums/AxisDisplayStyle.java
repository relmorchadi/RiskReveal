package com.scor.rr.domain.enums;

/**
 * Axis Display Style Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum AxisDisplayStyle {

	ROW("Row"), COLUMN("Column"), UNDEFINED("Undefined");

	private String code;

	AxisDisplayStyle(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}