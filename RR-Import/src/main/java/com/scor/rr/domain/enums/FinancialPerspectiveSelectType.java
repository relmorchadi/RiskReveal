package com.scor.rr.domain.enums;

/**
 * Financial Perspective Select Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum FinancialPerspectiveSelectType {

	FOR_THIS_ANALYSIS_ONLY("For this analysis only"),
	FOR_ALL_ANALYSES_IN_FILTER_VIEW("For all analysis for selected filter"),
	FOR_ALL_ANALYSES_IN_THIS_RDM("For all analysis in this RDM"),
	FOR_ALL_ANALYSES_IN_ALL_RDMS("For all analysis in all RDMs"),
	FOR_ALL_ANALYSES_WITHOUT_ASSIGNED_FP("For all unassigned analysis"), NOT_IMPLEMENTED("New method not implemented"),

	ADD_TO_SELECTED_ANALYSIS("add to selected analysis"), REPLACE_ON_SELECTED_ANALYSIS("replace on selected analysis"),
	ADD_TO_ALL_IN_RDM("add to all in RDM"), REPLACE_ON_ALL_IN_RDM("replace on all in RDM"),
	ADD_TO_ALL_IN_ALL_RDM("add to all in all RDM"), REPLACE_ON_ALL_IN_ALL_RDM("replace on all in all RDM");

	private String code;

	FinancialPerspectiveSelectType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}