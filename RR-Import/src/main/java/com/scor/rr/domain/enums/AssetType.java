package com.scor.rr.domain.enums;

/**
 * Asset Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum AssetType {

	PROJECT("project"), ANALYSIS("analysis"), PORTFOLIO("portfolio");

	private String code;

	AssetType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}