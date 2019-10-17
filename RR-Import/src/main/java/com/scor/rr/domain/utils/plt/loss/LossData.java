package com.scor.rr.domain.utils.plt.loss;

/**
 * LossData
 * 
 * @author HADDINI Zakariyae
 *
 */
public class LossData {

	private String region;
	private String peril;
	private String currency;
	private String financialPerspective;
	private Integer truncatedEvents;
	private Double truncatedAAL;
	private Double truncatedSD;
	private Double truncationThreshold;
	private String truncationCurrency;

	public LossData() {
	}

	public LossData(String region, String peril, String currency, String financialPerspective) {
		this.region = region;
		this.peril = peril;
		this.currency = currency;
		this.financialPerspective = financialPerspective;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPeril() {
		return peril;
	}

	public void setPeril(String peril) {
		this.peril = peril;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFinancialPerspective() {
		return financialPerspective;
	}

	public void setFinancialPerspective(String financialPerspective) {
		this.financialPerspective = financialPerspective;
	}

	public Integer getTruncatedEvents() {
		return truncatedEvents;
	}

	public void setTruncatedEvents(Integer truncatedEvents) {
		this.truncatedEvents = truncatedEvents;
	}

	public Double getTruncatedAAL() {
		return truncatedAAL;
	}

	public void setTruncatedAAL(Double truncatedAAL) {
		this.truncatedAAL = truncatedAAL;
	}

	public Double getTruncatedSD() {
		return truncatedSD;
	}

	public void setTruncatedSD(Double truncatedSD) {
		this.truncatedSD = truncatedSD;
	}

	public Double getTruncationThreshold() {
		return truncationThreshold;
	}

	public void setTruncationThreshold(Double truncationThreshold) {
		this.truncationThreshold = truncationThreshold;
	}

	public String getTruncationCurrency() {
		return truncationCurrency;
	}

	public void setTruncationCurrency(String truncationCurrency) {
		this.truncationCurrency = truncationCurrency;
	}

}
