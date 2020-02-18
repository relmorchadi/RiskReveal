package com.scor.rr.domain.enums;

/**
 * Axis Name Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum AxisName {

	Dimension1("Dimension1"), Dimension2("Dimension2"), Dimension3("Dimension3"), Dimension4("Dimension4"),
	FinancialPerspective("FinancialPerspective"), ExposureCurrency("ExposureCurrency"),
	ConformedCurrency("ConformedCurrency"), Country("CountryView"), Admin1Code("Admin1Code"), Peril("Peril"),
	RegionPeril("RegionPeril"), RegionPerilGroup("RegionPerilGroup"), AnalysisRegion("AnalysisRegion"),
	Undefined("Undefined");

	private String code;

	AxisName(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}