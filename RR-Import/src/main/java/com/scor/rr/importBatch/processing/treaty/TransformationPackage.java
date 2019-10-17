package com.scor.rr.importBatch.processing.treaty;

import java.util.List;
import java.util.Map;

public interface TransformationPackage {

    /**
     * get Bundles
     *
     * @return
     */
    List<TransformationBundle> getBundles();

    /**
     * get ProjectId
     *
     * @return
     */
    String getProjectId();

    /**
     * set ProjectId
     *
     * @param projectId
     */
    void setProjectId(String projectId);

    /**
     * get RRRepresentationDatasetId
     *
     * @return
     */
    String getRrRepresentationDatasetId();

    /**
     * set RRRepresentationDatasetId
     *
     * @param rrRepresentationDatasetId
     */
    void setRrRepresentationDatasetId(String rrRepresentationDatasetId);

    /**
     * get ProjectImportLogPRId
     *
     * @return
     */
    String getProjectImportLogPRId();

    /**
     * set ProjectImportLogPRId
     *
     * @param projectImportLogPRId
     */
    void setProjectImportLogPRId(String projectImportLogPRId);

    /**
     * get MapPortfolioTrackingRRPortfolioIds
     *
     * @return
     */
    Map<String, String> getMapPortfolioTrackingRRPortfolioIds();

    /**
     * set MapPortfolioTrackingRRPortfolioIds
     *
     * @param mapPortfolioTrackingRRPortfolioIds
     */
    void setMapPortfolioTrackingRRPortfolioIds(Map<String, String> mapPortfolioTrackingRRPortfolioIds);

    /**
     * get MapAnalysisTrackingRRAnalysisIds
     *
     * @return
     */
    Map<String, String> getMapAnalysisTrackingRRAnalysisIds();

    /**
     * set MapAnalysisTrackingRRAnalysisIds
     *
     * @param mapAnalysisTrackingRRAnalysisIds
     */
    void setMapAnalysisTrackingRRAnalysisIds(Map<String, String> mapAnalysisTrackingRRAnalysisIds);

    /**
     * get MapPortfolioRRPortfolioIds
     *
     * @return
     */
    Map<String, String> getMapPortfolioRRPortfolioIds();

    /**
     * set MapPortfolioRRPortfolioIds
     *
     * @param mapPortfolioRRPortfolioIds
     */
    void setMapPortfolioRRPortfolioIds(Map<String, String> mapPortfolioRRPortfolioIds);

    /**
     * get MapAnalysisRRAnalysisIds
     *
     * @return
     */
    Map<String, Map<String, String>> getMapAnalysisRRAnalysisIds();

    /**
     * set MapAnalysisRRAnalysisIds
     *
     * @param mapAnalysisRRAnalysisIds
     */
    void setMapAnalysisRRAnalysisIds(Map<String, Map<String, String>> mapAnalysisRRAnalysisIds);

    /**
     * add TransformationBundle
     *
     * @param bundle
     */
    void addTransformationBundle(TransformationBundle bundle);

    /**
     * gc
     */
    void gc();

}
