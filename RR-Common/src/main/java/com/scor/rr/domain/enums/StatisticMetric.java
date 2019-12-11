package com.scor.rr.domain.enums;

/**
 * Statistic Metric Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum StatisticMetric {

	EEF("EEF"), OEP("OEP"), AEP("AEP"), TVAR_OEP("TVaR-OEP"), TVAR_AEP("TVaR-AEP"), CEP("CEP");

	private String code;

	StatisticMetric(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static StatisticMetric getFrom(int code) {
		// @formatter:off
		switch (code) {
			case 0:
				return AEP;
			case 1:
				return OEP;
			case 10:
				return TVAR_AEP;
			case 11:
				return TVAR_OEP;
			case 20:
				return EEF;
			case 21:
				return CEP;
			default:
				return null;
		}
		// @formatter:on
	}

	public static int getFrom(StatisticMetric statisticMetric) {
		// @formatter:off
		switch (statisticMetric) {
			case AEP:
				return 0;
			case OEP:
				return 1;
			case TVAR_AEP:
				return 10;
			case TVAR_OEP:
				return 11;
			case EEF:
				return 20;
			case CEP:
				return 21;
			default:
				return 404;
		}
		// @formatter:on
	}

}