package com.scor.rr.domain.enums;

/**
 * Adjustment Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum AdjustmentType {

	NONE("None"), LINEAR("Linear"), EV_SPEC("Event Driven"), RP_BANDING_EEF("Return Period Banding Severity (EEF)"),
	RP_BANDING_OEP("Return Period Banding Severity (OEP)"), FREQUENCY_EEF("Frequency (EEF)"), CAT_XL("CAT XL"),
	QUOTA_SHARE("Quota Share"), CEP("CEP");

	private String code;

	AdjustmentType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}