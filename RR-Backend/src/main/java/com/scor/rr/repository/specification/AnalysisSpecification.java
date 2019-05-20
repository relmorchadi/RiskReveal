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
                .and(ofNullable(request.getRdmName()).map(rdmName -> AttributeEquals(AnalysisView_.rdmName, rdmName)).orElse(null));
    }
}
