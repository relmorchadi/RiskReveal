package com.scor.rr.domain.utils.plt;

/**
 * ELT EPCurve
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ELTEPCurve extends PLTEPCurve {

	public ELTEPCurve() {
	}

	public ELTEPCurve(Double exceedanceProbability, Double lossAmount) {
		this(null, exceedanceProbability, lossAmount);
	}

	public ELTEPCurve(Integer returnPeriod, Double exceedanceProbability, Double lossAmount) {
		super(returnPeriod, exceedanceProbability, lossAmount);
	}
	
}
