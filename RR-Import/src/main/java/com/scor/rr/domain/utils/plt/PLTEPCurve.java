package com.scor.rr.domain.utils.plt;

import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * PLT EPCurve
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PLTEPCurve {

	private Double exceedanceProbability;
	private Double lossAmount;
	private Integer returnPeriod;

	public PLTEPCurve() {
	}

	public PLTEPCurve(Integer returnPeriod, Double exceedanceProbability, Double lossAmount) {
		this.returnPeriod = returnPeriod;
		this.exceedanceProbability = exceedanceProbability;
		this.lossAmount = lossAmount;
	}

	public PLTEPCurve(ELTEPCurve eltepCurve) {
		this(eltepCurve.getReturnPeriod(), eltepCurve.getExceedanceProbability(), eltepCurve.getLossAmount());
	}

	public Double getExceedanceProbability() {
		return exceedanceProbability;
	}

	public void setExceedanceProbability(Double exceedanceProbability) {
		this.exceedanceProbability = exceedanceProbability;
	}

	public Double getLossAmount() {
		return lossAmount;
	}

	public void setLossAmount(Double lossAmount) {
		this.lossAmount = lossAmount;
	}

	public Integer getReturnPeriod() {
		return returnPeriod;
	}

	public void setReturnPeriod(Integer returnPeriod) {
		this.returnPeriod = returnPeriod;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!ALMFUtils.isNotNull(o) || getClass() != o.getClass())
			return false;

		PLTEPCurve that = (PLTEPCurve) o;

		return new EqualsBuilder().append(getLossAmount(), that.getLossAmount())
								  .append(getReturnPeriod(), that.getReturnPeriod())
								  .isEquals();
	}

	@Override
	public int hashCode() {
		return returnPeriod;
	}

}
