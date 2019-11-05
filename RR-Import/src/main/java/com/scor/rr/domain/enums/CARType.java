package com.scor.rr.domain.enums;

/**
 * CAR Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum CARType {

	FAC("Facultative"), TTY("Treaty");

	private String code;

	CARType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}