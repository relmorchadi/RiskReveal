package com.scor.rr.domain.enums;

/**
 * Metric Type Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum MetricType {

	HIT("Hit"), TIMESTAMP("TimeStamp"), DURATION("Duration");

	private String code;

	MetricType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}