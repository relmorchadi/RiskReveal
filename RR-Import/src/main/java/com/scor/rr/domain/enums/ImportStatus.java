package com.scor.rr.domain.enums;

/**
 * Import Status Enum
 *
 * @author HADDINI Zakariyae
 *
 */
public enum ImportStatus {

	PENDING("Pending"), INPROGRESS("InProgress"), PARTIALLYDONE("PartiallyDone"), DONE("Done"), ERROR("Error");

	private String code;

	ImportStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}