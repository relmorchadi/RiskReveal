package com.scor.rr.service.state;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
public class TransformationPackage {

    private List<TransformationBundle> transformationBundles;

    public void addTransformationBundle(TransformationBundle bundle) {
        this.transformationBundles.add(bundle);
    }

    Map<String, Map<String, Long>> mapAnalysisRRAnalysisIds;
}
