package com.scor.rr.domain.enums;

/**
 * Tracking Status Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum TrackingStatus {

	PENDING("Pending"), INPROGRESS("InProgress"), PARTIALLY_DONE("PartiallyDone"), DONE("Done"), ERROR("Error");

	private String code;

	TrackingStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}