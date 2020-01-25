package com.scor.rr.repository.specification;

import com.scor.rr.domain.dto.RLAnalysisDto;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLAnalysis_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class RLAnalysisSpecification extends BaseSpecification<RLAnalysis, RLAnalysisDto> {

    public RLAnalysisSpecification() {
    }

    @Override
    public Specification<RLAnalysis> getFilter(RLAnalysisDto filter) {
        if(filter == null)
            return Specification.where(null);
        return Specification.where(ofNullable(filter.getRlId()).map(rlId -> evaluateSpecialChars(RLAnalysis_.rlId, String.valueOf(rlId)) ).orElse(null) )
                .and(ofNullable(filter.getAnalysisName()).map(name -> evaluateSpecialChars(RLAnalysis_.analysisName, name) ).orElse(null) )
                .and(ofNullable(filter.getAnalysisDescription()).map(desc -> evaluateSpecialChars(RLAnalysis_.analysisDescription, desc)).orElse(null))
                .and(ofNullable(filter.getEngineType()).map(engineType -> evaluateSpecialChars(RLAnalysis_.engineType, engineType)).orElse(null))
                .and(ofNullable(filter.getAnalysisType()).map(analysisType -> evaluateSpecialChars(RLAnalysis_.analysisType, analysisType)).orElse(null))
                .and(ofNullable(filter.getPeril()).map(peril -> evaluateSpecialChars(RLAnalysis_.peril, peril)).orElse(null))
                .and(ofNullable(filter.getSubPeril()).map(subPeril -> evaluateSpecialChars(RLAnalysis_.subPeril, subPeril)).orElse(null))
                .and(ofNullable(filter.getLossAmplification()).map(lossAmplification -> evaluateSpecialChars(RLAnalysis_.lossAmplification, lossAmplification)).orElse(null))
                .and(ofNullable(filter.getRegion()).map(region -> evaluateSpecialChars(RLAnalysis_.region, region)).orElse(null))
                .and(ofNullable(filter.getAnalysisCurrency()).map(currency -> evaluateSpecialChars(RLAnalysis_.analysisCurrency, currency)).orElse(null));
    }

    public Specification<RLAnalysis> getFilter(RLAnalysisDto filter, Long rlModelDataSourceId) {
        return getFilter(filter).and(AttributeEquals(RLAnalysis_.rlModelDataSourceId, rlModelDataSourceId));
    }

}
