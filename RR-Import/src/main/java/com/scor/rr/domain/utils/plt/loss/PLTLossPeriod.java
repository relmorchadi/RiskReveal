package com.scor.rr.domain.utils.plt.loss;

import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.*;

/**
 * PLTLossPeriod
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PLTLossPeriod implements Comparable<PLTLossPeriod> {

	private Integer simPeriod;
	private List<PLTLossData> pltLossDataByPeriods;

	public PLTLossPeriod(Integer period) {
		pltLossDataByPeriods = new ArrayList<>();

		this.simPeriod = period;
	}

	public Integer getSimPeriod() {
		return simPeriod;
	}

	public void setSimPeriod(Integer simPeriod) {
		this.simPeriod = simPeriod;
	}

	public List<PLTLossData> getPltLossDataByPeriods() {
		return pltLossDataByPeriods;
	}

	public synchronized void addPLTLossData(PLTLossData data) {
		pltLossDataByPeriods.add(data);
	}

	@Override
	public int compareTo(PLTLossPeriod o) {
		return new CompareToBuilder().append(simPeriod, o.simPeriod).toComparison();
	}

	@Override
	public int hashCode() {
		return Objects.hash(simPeriod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!ALMFUtils.isNotNull(obj) || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass())) {
			return false;
		}

		final PLTLossPeriod other = (PLTLossPeriod) obj;

		return ALMFUtils.isNotNull(other) ? Objects.equals(simPeriod, other.simPeriod) : false;
	}

	public Double maxLoss(PLTLossPeriod pltLossPeriod) {
		PLTLossData pltLossData = Collections.max(pltLossPeriod.getPltLossDataByPeriods(),
				new Comparator<PLTLossData>() {
					@Override
					public int compare(PLTLossData o1, PLTLossData o2) {
						return Double.compare(o1.getLoss(), o2.getLoss());
					}
				});

		return pltLossData.getLoss();
	}

}
