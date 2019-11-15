package com.scor.rr.domain.enums;

/**
 * Axis Name Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum AxisName {

	DIMENSION1("Dimension1"), DIMENSION2("Dimension2"), DIMENSION3("Dimension3"), DIMENSION4("Dimension4"),
	FINANCIALPERSPECTIVE("FinancialPerspective"), EXPOSURECURRENCY("ExposureCurrency"),
	CONFORMEDCURRENCY("ConformedCurrency"), COUNTRY("Country"), ADMIN1CODE("Admin1Code"), PERIL("Peril"),
	REGIONPERIL("RegionPeril"), REGIONPERILGROUP("RegionPerilGroup"), ANALYSISREGION("AnalysisRegion"),
	UNDEFINED("Undefined");

	private String code;

	AxisName(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}