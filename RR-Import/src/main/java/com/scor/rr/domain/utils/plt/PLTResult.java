package com.scor.rr.domain.utils.plt;

import java.util.Objects;

import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * PLTPeriod
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PLTResult implements Comparable<PLTResult> {

	private Integer eventId;
	private Long eventDate;
	private int seq;
	private Double loss;

	public PLTResult(Integer eventId, Integer seq, Double loss, Long eventDate) {
		this.eventId = eventId;
		this.seq = seq;
		this.loss = loss;
		this.eventDate = eventDate;
	}

	public Integer getEventId() {
		return eventId;
	}

	public int getSeq() {
		return seq;
	}

	public Double getLoss() {
		return loss;
	}

	public Long getEventDate() {
		return eventDate;
	}

	@Override
	public int compareTo(PLTResult o) {
		return new CompareToBuilder().append(eventId, o.eventId).append(seq, o.seq).append(eventDate, o.eventDate)
				.toComparison();
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventId, seq, loss, eventDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!ALMFUtils.isNotNull(obj) || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass())) {
			return false;
		}

		final PLTResult other = (PLTResult) obj;

		return ALMFUtils.isNotNull(other)
				? Objects.equals(eventId, other.eventId) && Objects.equals(seq, other.seq)
						&& Objects.equals(loss, other.loss) && Objects.equals(eventDate, other.eventDate)
				: false;
	}

}
