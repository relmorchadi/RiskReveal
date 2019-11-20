package com.scor.rr.domain.enums;

/**
 * Contract Source Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ContractSource {

	OMEGA("Omega"), CUSTOM("Custom");

	private String code;

	ContractSource(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
