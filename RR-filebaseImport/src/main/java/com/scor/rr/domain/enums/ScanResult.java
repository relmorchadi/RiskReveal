package com.scor.rr.domain.enums;

/**
 * Scan Result Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ScanResult {

	SUCCESS("success"), INTERRUPTED("interrupted"), ERROR("error");

	private String code;

	ScanResult(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}