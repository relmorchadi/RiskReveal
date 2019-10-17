package com.scor.rr.domain.utils.plt.loss;

import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Objects;

/**
 * PLTLossData
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PLTLossData implements Comparable<PLTLossData> {

	protected Integer eventId;
	protected Long eventDate;
	protected Integer simPeriod;
	protected int seq;
	protected double maxExposure;
	protected double loss;

	public PLTLossData(Integer eventId, Long eventDate, Integer simPeriod, Integer seq, Double maxExposure,
			Double loss) {
		this.eventId = eventId;
		this.eventDate = eventDate;
		this.simPeriod = simPeriod;
		this.seq = seq;
		this.maxExposure = maxExposure;
		this.loss = loss;
	}

	public PLTLossData(PLTLossData lossData) {
		this.eventId = lossData.eventId;
		this.eventDate = lossData.eventDate;
		this.simPeriod = lossData.simPeriod;
		this.seq = lossData.seq;
		this.maxExposure = lossData.maxExposure;
		this.loss = lossData.loss;
	}

	public Integer getEventId() {
		return eventId;
	}

	public Long getEventDate() {
		return eventDate;
	}

	public Integer getSimPeriod() {
		return simPeriod;
	}

	public int getSeq() {
		return seq;
	}

	public double getMaxExposure() {
		return maxExposure;
	}

	public double getLoss() {
		return loss;
	}

	@Override
	public int compareTo(PLTLossData o) {
		return new CompareToBuilder().append(loss, o.loss).append(simPeriod, o.simPeriod).append(eventId, o.eventId)
				.append(seq, o.seq).toComparison();
	}

	@Override
	public int hashCode() {
		return new StringBuilder().append(loss).append(simPeriod).append(eventId).append(seq).toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (ALMFUtils.isNotNull(obj) || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass())) {
			return false;
		}

		final PLTLossData other = (PLTLossData) obj;

		return ALMFUtils.isNotNull(other)
				? Objects.equals(loss, other.loss) && Objects.equals(simPeriod, other.simPeriod)
						&& Objects.equals(eventId, other.eventId) && Objects.equals(seq, other.seq)
				: false;
	}

}
