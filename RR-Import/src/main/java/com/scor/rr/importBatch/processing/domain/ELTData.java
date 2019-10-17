package com.scor.rr.importBatch.processing.domain;

import com.scor.rr.importBatch.processing.domain.loss.EventLoss;

import java.util.List;
import java.util.Set;

public interface ELTData
{

	void addRP(Integer analysisId, String r, String p, String curr, String fp, String dlm);

	void addLosData(String rp, ELTLoss data);

	Set<String> getRegionPerils();

	void addEvent(String rp, EventLoss e);

	List<EventLoss> getEvents(String rp);

	ELTLoss getLossDataForRp(String rp);

	void clearLossDataForRp(String rp);

	void reset();

}
