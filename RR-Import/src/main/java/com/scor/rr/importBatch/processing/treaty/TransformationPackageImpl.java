package com.scor.rr.importBatch.processing.treaty;

import com.scor.rr.utils.ALMFUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TransformationPackageImpl implements TransformationPackage {

    private String projectId;
    private String projectImportLogPRId;
    private String rrRepresentationDatasetId;
    private List<TransformationBundle> bundles;
    private Map<String, String> mapPortfolioTrackingRRPortfolioIds;
    private Map<String, String> mapAnalysisTrackingRRAnalysisIds;
    // TODO : Verify later (Long -> String)
    private Map<String, Map<String, String>> mapAnalysisRRAnalysisIds;
    private Map<String, String> mapPortfolioRRPortfolioIds;


    public TransformationPackageImpl() {
        this.bundles = new ArrayList<>();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRrRepresentationDatasetId() {
        return rrRepresentationDatasetId;
    }

    public void setRrRepresentationDatasetId(String rrRepresentationDatasetId) {
        this.rrRepresentationDatasetId = rrRepresentationDatasetId;
    }

    public String getProjectImportLogPRId() {
        return projectImportLogPRId;
    }

    public void setProjectImportLogPRId(String projectImportLogPRId) {
        this.projectImportLogPRId = projectImportLogPRId;
    }

    public List<TransformationBundle> getBundles() {
        return bundles;
    }

    public void setBundles(List<TransformationBundle> bundles) {
        this.bundles = bundles;
    }

    // TODO : Verify later (Long -> String)
    public Map<String, Map<String, String>> getMapAnalysisRRAnalysisIds() {
        return mapAnalysisRRAnalysisIds;
    }

    // TODO : Verify later (Long -> String)
    public void setMapAnalysisRRAnalysisIds(Map<String, Map<String, String>> mapAnalysisRRAnalysisIds) {
        this.mapAnalysisRRAnalysisIds = mapAnalysisRRAnalysisIds;
    }

    public Map<String, String> getMapPortfolioRRPortfolioIds() {
        return mapPortfolioRRPortfolioIds;
    }

    public void setMapPortfolioRRPortfolioIds(Map<String, String> mapPortfolioRRPortfolioIds) {
        this.mapPortfolioRRPortfolioIds = mapPortfolioRRPortfolioIds;
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

    public void addTransformationBundle(TransformationBundle bundle) {
        this.bundles.add(bundle);
    }

    public void gc() {
        if (ALMFUtils.isNotNull(bundles))
            bundles.forEach(bundle -> {
                bundle.gc();
            });
    }

}