package com.scor.rr.service.state;

import com.scor.rr.domain.ModelPortfolio;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Data
public class TransformationPackage {

    private List<TransformationBundle> transformationBundles;

    public TransformationPackage(){
        transformationBundles= new ArrayList<>();
    }

    public void addTransformationBundle(TransformationBundle bundle) {
        if (transformationBundles==null)
            transformationBundles= new ArrayList<>();
        this.transformationBundles.add(bundle);
    }

    Map<String, Map<String, Long>> mapAnalysisRRAnalysisIds;
    List<ModelPortfolio> modelPortfolios;
}
