package com.scor.rr.domain.utils.plt;

import java.util.ArrayList;
import java.util.List;

import com.scor.rr.domain.utils.plt.loss.PLTLossData;
import com.scor.rr.domain.utils.plt.loss.PLTLossPeriod;

/**
 * ScorPLTLossDataHeader
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ScorPLTLossDataHeader {

	private String region;
	private String peril;
	private String currency;
	private String financialPerspective;
	private Integer truncatedEvents;
	private Double truncatedAAL;
	private Double truncatedSD;
	private Double truncationThreshold;
	private String truncationCurrency;

	private List<PLTLossPeriod> pltLossPeriods;

	public ScorPLTLossDataHeader() {
	}

	public ScorPLTLossDataHeader(List<PLTLossPeriod> pltLossPeriods) {
		this.pltLossPeriods = pltLossPeriods;
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

	public List<PLTLossPeriod> getPltLossPeriods() {
		return pltLossPeriods;
	}

	public void setPltLossPeriods(List<PLTLossPeriod> pltLossPeriods) {
		this.pltLossPeriods = pltLossPeriods;
	}

	public synchronized void addPLTLossPeriod(PLTLossPeriod lossPeriod) {
		if (pltLossPeriods == null) {
			pltLossPeriods = new ArrayList<>();
		}

		pltLossPeriods.add(lossPeriod);
	}

	public synchronized void addPLTLossPeriods(List<PLTLossPeriod> lossPeriods) {
		if (pltLossPeriods == null) {
			pltLossPeriods = new ArrayList<>();
		}

		pltLossPeriods.addAll(lossPeriods);
	}

	public synchronized List<PLTLossData> getSortedLossData() {
		List<PLTLossData> lossDatas = new ArrayList<>();

		for (PLTLossPeriod pltLossPeriod : pltLossPeriods) {
			lossDatas.addAll(pltLossPeriod.getPltLossDataByPeriods());
		}

		return lossDatas;
	}

}
