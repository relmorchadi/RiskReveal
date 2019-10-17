package com.scor.rr.domain.utils.plt;

import com.scor.rr.utils.ALMFUtils;

/**
 * PLT Summary Statistic
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PLTSummaryStatistic {

	private Double purePremium;
	private Double standardDeviation;
	private Double cov;

	public PLTSummaryStatistic() {
		super();
	}

	public PLTSummaryStatistic(ELTEPSummaryStatistic summaryStatistic) {
		this(summaryStatistic.getPurePremium(), summaryStatistic.getStandardDeviation(), summaryStatistic.getCov());
	}

	public PLTSummaryStatistic(Double purePremium, Double standardDeviation, Double cov) {
		super();
		this.purePremium = purePremium;
		this.standardDeviation = standardDeviation;
		this.cov = cov;
	}

	public Double getPurePremium() {
		return purePremium;
	}

	public void setPurePremium(Double purePremium) {
		this.purePremium = purePremium;
	}

	public Double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public Double getCov() {
		return cov;
	}

	public void setCov(Double cov) {
		this.cov = cov;
	}

	public Boolean isNull() {
		return (!ALMFUtils.isNotNull(purePremium) || !ALMFUtils.isNotNull(standardDeviation)
				|| !ALMFUtils.isNotNull(cov)) ? Boolean.TRUE : Boolean.FALSE;
	}

}
