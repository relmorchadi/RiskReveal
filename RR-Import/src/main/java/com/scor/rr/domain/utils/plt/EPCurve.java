package com.scor.rr.domain.utils.plt;

import com.scor.rr.utils.ALMFUtils;
import com.scor.rr.utils.NumberFormatUtils;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * EPCurve
 * 
 * @author HADDINI Zakariyae
 *
 */
public class EPCurve {

	private Integer statisticMetricId;

	private Map<String, Double> lossAmountsByEP;
	private Map<Integer, Double> lossAmountsByReturnPeriod;

	public EPCurve() {
		lossAmountsByEP = new TreeMap<>();
		lossAmountsByReturnPeriod = new TreeMap<>();
	}

	public EPCurve(Integer statisticMetricId, Map<Integer, Double> lossAmountsByReturnPeriod) {
		this.statisticMetricId = statisticMetricId;
		this.lossAmountsByReturnPeriod = lossAmountsByReturnPeriod;
	}

	public Integer getStatisticMetricId() {
		return statisticMetricId;
	}

	public void setStatisticMetricId(Integer statisticMetricId) {
		this.statisticMetricId = statisticMetricId;
	}

	public Map<String, Double> getLossAmountsByEP() {
		return lossAmountsByEP;
	}

	public void setLossAmountsByEP(Map<String, Double> lossAmountsByEP) {
		this.lossAmountsByEP = lossAmountsByEP;
	}

	public Map<Integer, Double> getLossAmountsByReturnPeriod() {
		return lossAmountsByReturnPeriod;
	}

	public void setLossAmountsByReturnPeriod(Map<Integer, Double> lossAmountsByReturnPeriod) {
		this.lossAmountsByReturnPeriod = lossAmountsByReturnPeriod;
	}

	public Map<Double, Double> convertLossAmountsByEP() {
		Map<Double, Double> result = new TreeMap<>();

		for (String epKey : lossAmountsByEP.keySet()) {
			Double ep;
			ep = NumberFormatUtils.convertToDot(epKey);

			if (ep != null)
				result.put(ep, lossAmountsByEP.get(epKey));
		}

		return result;
	}

	public void addLoss(Double ep, Integer rp, Double loss) {
		addLossByEP(ep, loss);
		addLossByRP(rp, loss);
	}

	public void addLossByEP(Double ep, Double loss) {
		String epKey = NumberFormatUtils.convertFromDot(ep);

		this.lossAmountsByEP.remove(epKey);
		this.lossAmountsByEP.put(epKey, loss);
	}

	public void addLossByRP(Integer rp, Double loss) {
		this.lossAmountsByReturnPeriod.remove(rp);
		this.lossAmountsByReturnPeriod.put(rp, loss);
	}

	@Override
	public int hashCode() {
		return Objects.hash(statisticMetricId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!ALMFUtils.isNotNull(obj) || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass())) {
			return false;
		}

		final EPCurve other = (EPCurve) obj;

		return ALMFUtils.isNotNull(other) ? Objects.equals(this.statisticMetricId, other.statisticMetricId) : false;
	}

}
