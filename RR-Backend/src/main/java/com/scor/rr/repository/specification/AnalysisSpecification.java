package com.scor.rr.repository.specification;

import com.scor.rr.domain.AnalysisView;
import com.scor.rr.domain.AnalysisView_;
import com.scor.rr.domain.dto.AnalysisFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class AnalysisSpecification extends BaseSpecification<AnalysisView, AnalysisFilter> {

    @Override
    public Specification<AnalysisView> getFilter(AnalysisFilter request) {
        return Specification
                .where(ofNullable(request.getId()).map(id -> AttributeContains(AnalysisView_.id, id)).orElse(null) )
                .and(ofNullable(request.getAnalysisId()).map(analysisId -> AttributeContains(AnalysisView_.analysisId, String.valueOf(analysisId))).orElse(null))
                .and(ofNullable(request.getAnalysisName()).map(analysisName -> AttributeContains(AnalysisView_.analysisId,analysisName) ).orElse(null) )
                .and(ofNullable(request.getDescription()).map(description -> AttributeContains(AnalysisView_.description, description)).orElse(null) )
                .and(ofNullable(request.getEngineVersion()).map(engV -> AttributeContains(AnalysisView_.engineVersion, engV)).orElse(null))
                .and(ofNullable(request.getGroupType()).map(gt -> AttributeContains(AnalysisView_.groupType, gt)).orElse(null))
                .and(ofNullable(request.getCedant()).map(cdt -> AttributeContains(AnalysisView_.cedant, cdt)).orElse(null))
                .and(ofNullable(request.getRdmId()).map(rdmId -> AttributeEquals( AnalysisView_.rdmId, rdmId)).orElse(null))
                .and(ofNullable(request.getRdmName()).map(lobName -> AttributeEquals(AnalysisView_.lobName, lobName)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(statusDescription -> AttributeContains(AnalysisView_.statusDescription, statusDescription)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(analysisCurrency -> AttributeContains(AnalysisView_.analysisCurrency, analysisCurrency)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(engineType -> AttributeContains(AnalysisView_.engineType, engineType)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(grouping -> AttributeContains(AnalysisView_.grouping, grouping)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(lossAmplification -> AttributeContains(AnalysisView_.lossAmplification, lossAmplification)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(modeName -> AttributeContains(AnalysisView_.modeName, modeName)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(peril -> AttributeContains(AnalysisView_.peril, peril)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(region -> AttributeContains(AnalysisView_.region, region)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(regionName -> AttributeContains(AnalysisView_.regionName, regionName)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(runDate -> AttributeContains(AnalysisView_.runDate, runDate)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(subPeril -> AttributeContains(AnalysisView_.subPeril, subPeril)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(typeName -> AttributeContains(AnalysisView_.typeName, typeName)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(user1 -> AttributeContains(AnalysisView_.user1, user1)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(user2 -> AttributeContains(AnalysisView_.user2, user2)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(user3 -> AttributeContains(AnalysisView_.user3, user3)).orElse(null))
                .and(ofNullable(request.getEngineVersion()).map(user4 -> AttributeContains(AnalysisView_.user4, user4)).orElse(null));
    }
}
