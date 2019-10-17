package com.scor.rr.importBatch.processing.domain;

import java.util.Set;

public interface PLTData
{

	void addPeriod(String rp, String region, String peril, String currency, String financialPerspective);

	Set<String> getRegionPerils();

	PLTLoss getLossDataForRP(String rp);

	void initLossDataForRP(String rp);

	void reset();

	void sortLosses();

	void sortLosses(String rp);

	void clearLossesForRP(String rp);
}
