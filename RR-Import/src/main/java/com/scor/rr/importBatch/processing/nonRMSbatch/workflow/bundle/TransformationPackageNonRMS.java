package com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle;

import java.util.List;
import java.util.Map;

/**
 * Created by U005342 on 28/07/2018.
 */
public interface TransformationPackageNonRMS {

    List<TransformationBundleNonRMS> getBundles();

    void setProjectId(String projectId);

    String getProjectId();

    void setProjectImportLogPRId(String projectImportLogPRId);

    String getRrRepresentationDatasetId();

    void setRrRepresentationDatasetId(String rrRepresentationDatasetId);

    String getProjectImportLogPRId();

    Map<String, String> getMapPortfolioTrackingRRPortfolioIds();
    void setMapPortfolioTrackingRRPortfolioIds(Map<String, String> mapPortfolioTrackingRRPortfolioIds);

    Map<String, String> getMapAnalysisTrackingRRAnalysisIds();
    void setMapAnalysisTrackingRRAnalysisIds(Map<String, String> mapAnalysisTrackingRRAnalysisIds);

    Map<String, String> getMapAnalysisRRAnalysisIds();
    void setMapAnalysisRRAnalysisIds(Map<String, String> mapAnalysisRRAnalysisIds);

    Map<String, String> getMapPortfolioRRPortfolioIds();
    void setMapPortfolioRRPortfolioIds(Map<String, String> mapPortfolioRRPortfolioIds);

    void addTransformationBundle(TransformationBundleNonRMS bundle);

    public void gc();
}
