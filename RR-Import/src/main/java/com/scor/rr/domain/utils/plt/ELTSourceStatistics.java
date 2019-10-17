package com.scor.rr.domain.utils.plt;

import com.scor.rr.utils.ALMFUtils;

import java.util.Objects;

/**
 * ELTSourceMetadata
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ELTSourceStatistics {

	private Integer statisticMetricId;
	private Double purePremium;
	private Double standardDeviation;
	private Double covariance;
	private Integer pdSplit;
	private Integer biSplit;

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

	public Integer getPdSplit() {
		return pdSplit;
	}

	public void setPdSplit(Integer pdSplit) {
		this.pdSplit = pdSplit;
	}

	public Integer getBiSplit() {
		return biSplit;
	}

	public void setBiSplit(Integer biSplit) {
		this.biSplit = biSplit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(statisticMetricId, purePremium, standardDeviation, covariance);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass()))
			return false;

		ELTSourceStatistics other = (ELTSourceStatistics) obj;

		return Objects.equals(statisticMetricId, other.statisticMetricId)
				&& Objects.equals(purePremium, other.purePremium)
				&& Objects.equals(standardDeviation, other.standardDeviation)
				&& Objects.equals(covariance, other.covariance);
	}

}
