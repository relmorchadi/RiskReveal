package com.scor.rr.domain.enums;

/**
 * Workspace Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum WorkspaceType {
	
	CLIENT("C"), TREATY("T"), PROGRAM("P"), BOUQUET("B");

	private String code;

	WorkspaceType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}