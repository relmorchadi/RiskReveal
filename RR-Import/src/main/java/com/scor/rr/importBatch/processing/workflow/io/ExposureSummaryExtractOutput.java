package com.scor.rr.importBatch.processing.workflow.io;

import com.scor.rr.domain.entities.rms.exposuresummary.ExposureSummary;
import com.scor.rr.domain.entities.rms.exposuresummary.RmsExposureSummary;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposure Summary Extract Output
 * 
 * @author HADDINI Zakariyae
 *
 */
@Data
public class ExposureSummaryExtractOutput {

	public Integer runId;
	public List<String> errorMessages = new ArrayList<>();
	public List<ExposureSummary> exposureSummaries = new ArrayList<>();
	public List<RmsExposureSummary> rmsExposureSummaries = new ArrayList<>();

}
