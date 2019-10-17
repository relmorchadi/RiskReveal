package com.scor.rr.utils;

/**
 * Step
 * 
 * @author HADDINI Zakariyae
 *
 */
public class Step {

	/**
	 * get Step Name from Step Id for Analysis
	 * 
	 * @param stepId
	 * @return
	 */
	public static String getStepNameFromStepIdForAnalysis(int stepId) {
		// @formatter:off
		switch (stepId) {
			case 1:
				return "LOAD_REGION_PERIL";
			case 2:
				return "EXTRACT_EPCURVE_STATS";
			case 3:
				return "EXTRACT_RMS_EXCHANGE_RATE";
			case 4:
				return "EXTRACT_ELT";
			case 5:
				return "TRUNCATE_ELT";
			case 6:
				return "CONFORM_ELT";
			case 7:
				return "EXTRACT_CONFORMED_EPCURVE_STATS";
			case 8:
				return "EXTRACT_MODELING_OPTIONS";
			case 9:
				return "WRITE_ELT_BINARY";
			case 10:
				return "WRITE_ELT_HEADER";
			case 11:
				return "CLOSE_BARRIER";
			case 12:
				return "CONVERT_ELT_TO_PLT";
			case 13:
				return "OPEN_OR_ERROR_BARRIER";
			case 14:
				return "WRITE_PLT_HEADER";
			case 15:
				return "CALCULATE_EPCURVE";
			case 16:
				return "ADJUST_DEFAULT";
			default:
				return null;
		}
		// @formatter:on
	}

	/**
	 * get Step Name from Step Id for Portfolio
	 * 
	 * @param stepId
	 * @return
	 */
	public static String getStepNameFromStepIdForPortfolio(int stepId) {
		// @formatter:off
		switch (stepId) {
			case 1:
				return "EXTRACT_AND_IMPORT_EXPOSURE_SUMMARY";
			default:
				return null;
		}
		// @formatter:on
	}

}
