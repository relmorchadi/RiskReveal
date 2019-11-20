package com.scor.rr.domain.enums;

/**
 * Exposure Summary Extract Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ExposureSummaryExtractType {
	
	DETAILED_EXPOSURE_SUMMARY("DetailedExposureSummary"),
	LOCATION_LEVEL_EXPOSURE_DETAILS("LocationLevelExposureDetails");

	private String code;

	ExposureSummaryExtractType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}