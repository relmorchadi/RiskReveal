package com.scor.rr.domain.enums;

/**
 * KPI Constants Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum KPIConstants {

	RR_10_FW_CAR("RR_10_FW_CAR"), RR_11_RiskLink_Extractions("RR_11_RiskLink_Extractions"),
	RR_12_FW_Notifications("RR_12_FW_Notifications"), RR_13_PLT_Aggregations("RR_13_PLT_Aggregations"),
	RR_14_PLT_RP("RR_14_PLT_RP"), RR_17_Number_RegionPerils_Extraction("RR_17_Number_RegionPerils_Extraction"),
	RR_18_ELT_Extraction("RR_18_ELT_Extraction"), RR_19_PLT_Conversion("RR_19_PLT_Conversion"),
	RR_20_Number_Lines_Extraction("RR_20_Number_Lines_Extraction"),
	RR_21_Number_Losses_Conversion("RR_21_Number_Losses_Conversion"), STATUS_OK("Ok"), STATUS_KO("Ko");

	private String code;

	KPIConstants(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}