package com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by U005342 on 28/07/2018.
 */
public class TransformationPackageNonRMSImpl implements TransformationPackageNonRMS {

        private List<TransformationBundleNonRMS> bundles;

        private String projectId;
        private String projectImportLogPRId;
        private String rrRepresentationDatasetId;

        private Map<String, String> mapPortfolioTrackingRRPortfolioIds;
        private Map<String, String> mapAnalysisTrackingRRAnalysisIds;

        Map<String, String> mapAnalysisRRAnalysisIds;
        Map<String, String> mapPortfolioRRPortfolioIds;


    public String getRrRepresentationDatasetId() {
        return rrRepresentationDatasetId;
    }

    public void setRrRepresentationDatasetId(String rrRepresentationDatasetId) {
        this.rrRepresentationDatasetId = rrRepresentationDatasetId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public Map<String, String> getMapAnalysisRRAnalysisIds() {
        return mapAnalysisRRAnalysisIds;
    }

    @Override
    public void setMapAnalysisRRAnalysisIds(Map<String, String> mapAnalysisRRAnalysisIds) {
        this.mapAnalysisRRAnalysisIds = mapAnalysisRRAnalysisIds;
    }

    @Override
    public Map<String, String> getMapPortfolioRRPortfolioIds() {
        return mapPortfolioRRPortfolioIds;
    }

    @Override
    public void setMapPortfolioRRPortfolioIds(Map<String, String> mapPortfolioRRPortfolioIds) {
        this.mapPortfolioRRPortfolioIds = mapPortfolioRRPortfolioIds;
    }

    public TransformationPackageNonRMSImpl() {
        this.bundles = new ArrayList<>();
    }

    @Override
    public Map<String, String> getMapPortfolioTrackingRRPortfolioIds() {
        return mapPortfolioTrackingRRPortfolioIds;
    }

    @Override
    public void setMapPortfolioTrackingRRPortfolioIds(Map<String, String> mapPortfolioTrackingRRPortfolioIds) {
        this.mapPortfolioTrackingRRPortfolioIds = mapPortfolioTrackingRRPortfolioIds;
    }

    @Override
    public Map<String, String> getMapAnalysisTrackingRRAnalysisIds() {
        return mapAnalysisTrackingRRAnalysisIds;
    }

    @Override
    public void setMapAnalysisTrackingRRAnalysisIds(Map<String, String> mapAnalysisTrackingRRAnalysisIds) {
        this.mapAnalysisTrackingRRAnalysisIds = mapAnalysisTrackingRRAnalysisIds;
    }

    @Override
    public void setProjectImportLogPRId(String projectImportLogPRId) {
        this.projectImportLogPRId = projectImportLogPRId;
    }

    public List<TransformationBundleNonRMS> getBundles() {
        return bundles;
    }

    @Override
    public String getProjectImportLogPRId() {
        return projectImportLogPRId;
    }

    public void setBundles(List<TransformationBundleNonRMS> bundles) {
        this.bundles = bundles;
    }

    @Override
    public void addTransformationBundle(TransformationBundleNonRMS bundle) {
        this.bundles.add(bundle);
    }

    @Override
    public void gc() {
        for (TransformationBundleNonRMS bundle : bundles) {
            //bundle.gc();
        }
    }
}
