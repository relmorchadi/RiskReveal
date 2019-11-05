package com.scor.rr.domain.enums;

/**
 * Process Status Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ProcessStatuses {

	RUNNING("Running"), FAILED("Failed"), OK("Ok"), PREP("Prep"), COMPLETE("Complete");

	private String code;

	ProcessStatuses(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}