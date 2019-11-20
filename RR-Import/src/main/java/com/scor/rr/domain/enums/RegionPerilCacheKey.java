package com.scor.rr.domain.enums;

/**
 * Region Peril Cache Key Enum
 * 
 * @author HADDINI Zakariyae
 *
 */
public enum RegionPerilCacheKey {

	REGIONPERILCODE("RegionPerilCode"), REGIONPERILID("RegionPerilID");

	private String code;

	RegionPerilCacheKey(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}