package com.scor.rr.domain.enums;

/**
 * Exposure Summary Display Style Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum ExposureSummaryDisplayStyle {

	PIECHART("PieChart"), STACKEDBARCHART("StackedBarChart"), STACKEDBARCHARTHORIZONTAL("StackedBarChartHorizontal"),
	STACKEDBARCHARTVERTICAL("StackedBarChartVertical"), BARCHART("BarChart"), TREEMAP("TreeMap"), TABLE("Table"),
	UNDEFINED("Undefined");

	private String code;

	ExposureSummaryDisplayStyle(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}