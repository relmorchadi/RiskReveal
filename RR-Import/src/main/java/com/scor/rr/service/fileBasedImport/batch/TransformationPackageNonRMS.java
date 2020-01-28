package com.scor.rr.service.fileBasedImport.batch;

import lombok.Data;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@JobScope
@Data
public class TransformationPackageNonRMS {


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

    public Map<String, String> getMapAnalysisRRAnalysisIds() {
        return mapAnalysisRRAnalysisIds;
    }

    public void setMapAnalysisRRAnalysisIds(Map<String, String> mapAnalysisRRAnalysisIds) {
        this.mapAnalysisRRAnalysisIds = mapAnalysisRRAnalysisIds;
    }

    public Map<String, String> getMapPortfolioRRPortfolioIds() {
        return mapPortfolioRRPortfolioIds;
    }

    public void setMapPortfolioRRPortfolioIds(Map<String, String> mapPortfolioRRPortfolioIds) {
        this.mapPortfolioRRPortfolioIds = mapPortfolioRRPortfolioIds;
    }

    public TransformationPackageNonRMS() {
        this.bundles = new ArrayList<>();
    }

    public Map<String, String> getMapPortfolioTrackingRRPortfolioIds() {
        return mapPortfolioTrackingRRPortfolioIds;
    }

    public void setMapPortfolioTrackingRRPortfolioIds(Map<String, String> mapPortfolioTrackingRRPortfolioIds) {
        this.mapPortfolioTrackingRRPortfolioIds = mapPortfolioTrackingRRPortfolioIds;
    }

    public Map<String, String> getMapAnalysisTrackingRRAnalysisIds() {
        return mapAnalysisTrackingRRAnalysisIds;
    }

    public void setMapAnalysisTrackingRRAnalysisIds(Map<String, String> mapAnalysisTrackingRRAnalysisIds) {
        this.mapAnalysisTrackingRRAnalysisIds = mapAnalysisTrackingRRAnalysisIds;
    }

    public void setProjectImportLogPRId(String projectImportLogPRId) {
        this.projectImportLogPRId = projectImportLogPRId;
    }

    public List<TransformationBundleNonRMS> getBundles() {
        return bundles;
    }

    public String getProjectImportLogPRId() {
        return projectImportLogPRId;
    }

    public void setBundles(List<TransformationBundleNonRMS> bundles) {
        this.bundles = bundles;
    }

    public void addTransformationBundle(TransformationBundleNonRMS bundle) {
        this.bundles.add(bundle);
    }

    public void gc() {
        for (TransformationBundleNonRMS bundle : bundles) {
            bundle.gc();
        }
    }
}
