package com.scor.rr.domain.dto.TargetBuild;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public abstract class WorkspaceStats {
    protected Integer expectedRegionPerils;
    protected Integer expectedExposureSummaries;
    protected Integer qualifiedPLTs;
    protected Integer expectedPublishedForPricing;
    protected Integer expectedPriced;
    protected Integer expectedAccumulation;
    protected Boolean isFavorite;
    protected Boolean isPinned;

    public void getCount(List<Map<String, Object>> arr) {
        if( arr.size() > 0 ) {
            Map<String, Object> tmp = arr.get(0);
            this.setExpectedAccumulation( tmp.get("expectedAccumulation") != null ? (Integer) tmp.get("expectedAccumulation") : null);
            this.setExpectedExposureSummaries( tmp.get("expectedExposureSummaries") != null ?  (Integer) tmp.get("expectedExposureSummaries") : null);
            this.setExpectedPriced( tmp.get("expectedPriced") != null ? (Integer) tmp.get("expectedPriced") : null );
            this.setExpectedPublishedForPricing(  tmp.get("expectedPublishedForPricing") != null ? (Integer) tmp.get("expectedPublishedForPricing"): null );
            this.setExpectedRegionPerils( tmp.get("expectedRegionPerils") != null ? (Integer) tmp.get("expectedRegionPerils") :null );
            this.setQualifiedPLTs( tmp.get("qualifiedPLTs") != null ? (Integer) tmp.get("qualifiedPLTs") : null );
            this.setIsFavorite( tmp.get("isFavorite") != null ? (Boolean) tmp.get("isFavorite") : null );
            this.setIsPinned( false );
        }
    }
}
