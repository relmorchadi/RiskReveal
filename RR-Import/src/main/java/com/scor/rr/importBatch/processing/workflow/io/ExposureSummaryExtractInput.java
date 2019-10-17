package com.scor.rr.importBatch.processing.workflow.io;

import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposure Summary Extract Input
 *
 * @author HADDINI Zakariyae
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureSummaryExtractInput {

    private Integer runId;
    private ModelingExposureDataSource modelingExposureDataSource;
    private List<PortfolioExposureSummaryExtractInput> portfolioList = new ArrayList<>();

    public ExposureSummaryExtractInput(ModelingExposureDataSource meds) {
        this.modelingExposureDataSource = meds;
    }

    public void addToPortfolioList(PortfolioExposureSummaryExtractInput portfolioExposureSummaryExtractInput) {
        portfolioList.add(portfolioExposureSummaryExtractInput);
    }

}