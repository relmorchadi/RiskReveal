package com.scor.rr.domain.utils.plt;

import com.scor.rr.utils.ALMFUtils;

import java.util.Objects;

/**
 * SummaryStatistics
 * 
 * @author HADDINI Zakariyae
 *
 */
public class SummaryStatistics {

	private Integer statisticMetricId;
	private Double purePremium;
	private Double standardDeviation;
	private Double covariance;
	private Double rp100;
	private Double rp250;
	private Double rp500;

	public SummaryStatistics() {
	}

	public SummaryStatistics(Integer statisticMetricId, Double purePremium, Double standardDeviation, Double covariance,
			Double rp100, Double rp250, Double rp500) {
		this.statisticMetricId = statisticMetricId;
		this.purePremium = purePremium;
		this.standardDeviation = standardDeviation;
		this.covariance = covariance;
		this.rp100 = rp100;
		this.rp250 = rp250;
		this.rp500 = rp500;
	}

	public Integer getStatisticMetricId() {
		return statisticMetricId;
	}

	public void setStatisticMetricId(Integer statisticMetricId) {
		this.statisticMetricId = statisticMetricId;
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

	public Double getCovariance() {
		return covariance;
	}

	public void setCovariance(Double covariance) {
		this.covariance = covariance;
	}

	public Double getRp100() {
		return rp100;
	}

	public void setRp100(Double rp100) {
		this.rp100 = rp100;
	}

	public Double getRp250() {
		return rp250;
	}

	public void setRp250(Double rp250) {
		this.rp250 = rp250;
	}

	public Double getRp500() {
		return rp500;
	}

	public void setRp500(Double rp500) {
		this.rp500 = rp500;
	}

	@Override
	public int hashCode() {
		return Objects.hash(statisticMetricId, purePremium);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!ALMFUtils.isNotNull(obj) || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass())) {
			return false;
		}

		final SummaryStatistics other = (SummaryStatistics) obj;

		return ALMFUtils.isNotNull(other)
				? Objects.equals(statisticMetricId, other.statisticMetricId)
						&& Objects.equals(purePremium, other.purePremium)
				: false;
	}

}
