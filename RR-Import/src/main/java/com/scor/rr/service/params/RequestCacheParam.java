package com.scor.rr.service.params;

/**
 * RequestCacheParam
 * 
 * @author HADDINI Zakariyae
 *
 */
public class RequestCacheParam {

	private static final String DEFAULT;
	private static final String GLOBAL_BROKER_PURE;

	private String catRequestId;
	private String division;
	private String periodBasis;

	static {
		// @formatter:off
		DEFAULT 		   = "Default";
		GLOBAL_BROKER_PURE = "Global Broker Pure";
		// @formatter:on
	}

	public RequestCacheParam() {
	}

	public RequestCacheParam(String catRequestId, String division, String periodBasis) {
		super();
		this.catRequestId = catRequestId;
		this.division = division;
		this.periodBasis = periodBasis;
	}

	public String getCatRequestId() {
		return catRequestId;
	}

	public void setCatRequestId(String catRequestId) {
		this.catRequestId = catRequestId;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPeriodBasis() {
		return periodBasis;
	}

	public void setPeriodBasis(String periodBasis) {
		this.periodBasis = periodBasis;
	}

	/**
	 * generate key
	 * 
	 * @return
	 */
	public String generateKey() {
		return catRequestId.concat("_").concat(division).concat("_").concat(periodBasis);
	}

	/**
	 * generate DEFAULT key
	 * 
	 * @return
	 */
	public String generateDefaultKey() {
		return catRequestId.concat("_").concat(division).concat("_").concat(periodBasis).concat("_").concat(DEFAULT);
	}

	/**
	 * generate GLOBAL BROKER PURE key
	 * 
	 * @return
	 */
	public String generateGlobalBPKey() {
		return catRequestId.concat("_").concat(division).concat("_").concat(periodBasis).concat("_")
				.concat(GLOBAL_BROKER_PURE);
	}

}
