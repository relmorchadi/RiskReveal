package com.scor.rr.domain.enums;

/**
 * RRLossTable Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum RRLossTableType {

	SOURCE("Source"), CONFORMED("Conformed");

	private String code;

	RRLossTableType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}