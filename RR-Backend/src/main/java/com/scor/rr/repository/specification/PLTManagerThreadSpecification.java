package com.scor.rr.repository.specification;

import com.scor.rr.domain.dto.PLTManagerFilter;
import com.scor.rr.domain.entities.PLTManager.PLTManagerThread;
import com.scor.rr.domain.entities.PLTManager.PLTManagerThread_;
import com.scor.rr.domain.enums.NumberOperator;
import com.scor.rr.domain.requests.GridDataRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Optional.ofNullable;


@Component
public class PLTManagerThreadSpecification extends BaseSpecification<PLTManagerThread, GridDataRequest<PLTManagerFilter>> {

    @Override
    public Specification<PLTManagerThread> getData(GridDataRequest<PLTManagerFilter> request) {
        PLTManagerFilter filters = request.getFilterModel();
        Map<String, NumberOperator> operators = filters.getNumberOperators();
        return Specification
                .where(AttributeEquals(PLTManagerThread_.workspaceContextCode, filters.getWorkspaceContextCode()))
                .and(AttributeEquals(PLTManagerThread_.uwYear, filters.getUwYear()))
                .and(AttributeInLong(PLTManagerThread_.pureId, request.getPureIds()))
                .and(ofNullable(filters.getPltId()).map(attr -> AttributeEquals(PLTManagerThread_.pltId, attr)).orElse(null))
                .and(ofNullable(filters.getPltName()).map(attr -> AttributeContains(PLTManagerThread_.pltName, attr)).orElse(null))
                .and(ofNullable(filters.getPltStatus()).map(attr -> AttributeEquals(PLTManagerThread_.pltStatus, attr)).orElse(null))
                .and(ofNullable(filters.getGroupedPlt()).map(attr -> AttributeBoolean(PLTManagerThread_.groupedPlt, attr)).orElse(null))
                .and(ofNullable(filters.getGrain()).map(attr -> AttributeContains(PLTManagerThread_.grain, attr)).orElse(null))
                .and(ofNullable(filters.getXActPublication()).map(attr -> AttributeBoolean(PLTManagerThread_.xActPublication, attr)).orElse(null))
                .and(ofNullable(filters.getXActPriced()).map(attr -> AttributeBoolean(PLTManagerThread_.xActPriced, attr)).orElse(null))
                .and(ofNullable(filters.getArcPublication()).map(attr -> AttributeBoolean(PLTManagerThread_.arcPublication, attr)).orElse(null))
                .and(ofNullable(filters.getPerilGroupCode()).map(attr -> AttributeContains(PLTManagerThread_.perilGroupCode, attr)).orElse(null))
                .and(ofNullable(filters.getRegionPerilCode()).map(attr -> AttributeContains(PLTManagerThread_.regionPerilCode, attr)).orElse(null))
                .and(ofNullable(filters.getRegionPerilDesc()).map(attr -> AttributeContains(PLTManagerThread_.regionPerilDesc, attr)).orElse(null))
                .and(ofNullable(filters.getMinimumGrainRPCode()).map(attr -> AttributeContains(PLTManagerThread_.minimumGrainRPCode, attr)).orElse(null))
                .and(ofNullable(filters.getMinimumGrainRPDescription()).map(attr -> AttributeContains(PLTManagerThread_.minimumGrainRPDescription, attr)).orElse(null))
                .and(ofNullable(filters.getFinancialPerspective()).map(attr -> AttributeContains(PLTManagerThread_.financialPerspective, attr)).orElse(null))
                .and(ofNullable(filters.getTargetRAPCode()).map(attr -> AttributeContains(PLTManagerThread_.targetRAPCode, attr)).orElse(null))
                .and(ofNullable(filters.getTargetRAPDesc()).map(attr -> AttributeContains(PLTManagerThread_.targetRAPDesc, attr)).orElse(null))
                .and(ofNullable(filters.getRootRegionPeril()).map(attr -> AttributeContains(PLTManagerThread_.rootRegionPeril, attr)).orElse(null))
                .and(ofNullable(filters.getVendorSystem()).map(attr -> AttributeContains(PLTManagerThread_.vendorSystem, attr)).orElse(null))
                .and(ofNullable(filters.getModellingDataSource()).map(attr -> AttributeContains(PLTManagerThread_.modellingDataSource, attr)).orElse(null))
                .and(ofNullable(filters.getAnalysisId()).map(attr -> AttributeEquals(PLTManagerThread_.analysisId, attr)).orElse(null))
                .and(ofNullable(filters.getAnalysisName()).map(attr -> AttributeContains(PLTManagerThread_.analysisName, attr)).orElse(null))
                .and(ofNullable(filters.getDefaultOccurenceBasis()).map(attr -> AttributeContains(PLTManagerThread_.defaultOccurenceBasis, attr)).orElse(null))
                .and(ofNullable(filters.getOccurenceBasis()).map(attr -> AttributeContains(PLTManagerThread_.occurenceBasis, attr)).orElse(null))
                .and(ofNullable(filters.getBaseAdjustment()).map(attr -> AttributeBoolean(PLTManagerThread_.baseAdjustment, attr)).orElse(null))
                .and(ofNullable(filters.getDefaultAdjustment()).map(attr -> AttributeBoolean(PLTManagerThread_.defaultAdjustment, attr)).orElse(null))
                .and(ofNullable(filters.getClientAdjustment()).map(attr -> AttributeBoolean(PLTManagerThread_.clientAdjustment, attr)).orElse(null))
                .and(ofNullable(filters.getProjectId()).map(attr -> AttributeEquals(PLTManagerThread_.projectId, attr)).orElse(null))
                .and(ofNullable(filters.getProjectName()).map(attr -> AttributeContains(PLTManagerThread_.projectName, attr)).orElse(null))
                .and(ofNullable(filters.getClient()).map(attr -> AttributeContains(PLTManagerThread_.client, attr)).orElse(null))
                .and(ofNullable(filters.getClonedPlt()).map(attr -> AttributeBoolean(PLTManagerThread_.clonedPlt, attr)).orElse(null))
                .and(ofNullable(filters.getClonedSourcePlt()).map(attr -> AttributeEquals(PLTManagerThread_.clonedSourcePlt, attr)).orElse(null))
                .and(ofNullable(filters.getClonedSourceProject()).map(attr -> AttributeEquals(PLTManagerThread_.clonedSourceProject, attr)).orElse(null))
                .and(ofNullable(filters.getClonedSourceWorkspace()).map(attr -> AttributeEquals(PLTManagerThread_.clonedSourceWorkspace, attr)).orElse(null))
                .and(ofNullable(filters.getPltCcy()).map(attr -> AttributeContains(PLTManagerThread_.pltCcy, attr)).orElse(null))
                .and(ofNullable(filters.getAal()).map(attr -> AttributeNumber(PLTManagerThread_.aal, attr, operators.get(PLTManagerThread_.aal.toString()))).orElse(null))
                .and(ofNullable(filters.getCov()).map(attr -> AttributeNumber(PLTManagerThread_.cov, attr, operators.get(PLTManagerThread_.cov.toString()))).orElse(null))
                .and(ofNullable(filters.getStdDev()).map(attr -> AttributeNumber(PLTManagerThread_.stdDev, attr, operators.get(PLTManagerThread_.stdDev.toString()))).orElse(null))
                .and(ofNullable(filters.getOep10()).map(attr -> AttributeNumber(PLTManagerThread_.oep10, attr, operators.get(PLTManagerThread_.oep10.toString()))).orElse(null))
                .and(ofNullable(filters.getOep50()).map(attr -> AttributeNumber(PLTManagerThread_.oep50, attr, operators.get(PLTManagerThread_.oep50.toString()))).orElse(null))
                .and(ofNullable(filters.getOep100()).map(attr -> AttributeNumber(PLTManagerThread_.oep100, attr, operators.get(PLTManagerThread_.oep100.toString()))).orElse(null))
                .and(ofNullable(filters.getOep250()).map(attr -> AttributeNumber(PLTManagerThread_.oep250, attr, operators.get(PLTManagerThread_.oep250.toString()))).orElse(null))
                .and(ofNullable(filters.getOep500()).map(attr -> AttributeNumber(PLTManagerThread_.oep500, attr, operators.get(PLTManagerThread_.oep500.toString()))).orElse(null))
                .and(ofNullable(filters.getOep1000()).map(attr -> AttributeNumber(PLTManagerThread_.oep1000, attr, operators.get(PLTManagerThread_.oep1000.toString()))).orElse(null))
                .and(ofNullable(filters.getAep10()).map(attr -> AttributeNumber(PLTManagerThread_.aep10, attr, operators.get(PLTManagerThread_.aep10.toString()))).orElse(null))
                .and(ofNullable(filters.getAep50()).map(attr -> AttributeNumber(PLTManagerThread_.aep50, attr, operators.get(PLTManagerThread_.aep50.toString()))).orElse(null))
                .and(ofNullable(filters.getAep100()).map(attr -> AttributeNumber(PLTManagerThread_.aep100, attr, operators.get(PLTManagerThread_.aep100.toString()))).orElse(null))
                .and(ofNullable(filters.getAep250()).map(attr -> AttributeNumber(PLTManagerThread_.aep250, attr, operators.get(PLTManagerThread_.aep250.toString()))).orElse(null))
                .and(ofNullable(filters.getAep500()).map(attr -> AttributeNumber(PLTManagerThread_.aep500, attr, operators.get(PLTManagerThread_.aep500.toString()))).orElse(null))
                .and(ofNullable(filters.getAep1000()).map(attr -> AttributeNumber(PLTManagerThread_.aep1000, attr, operators.get(PLTManagerThread_.aep1000.toString()))).orElse(null))
                .and(ofNullable(filters.getCreatedDate()).map(attr -> AttributeEquals(PLTManagerThread_.createdDate, attr)).orElse(null))
                .and(ofNullable(filters.getImportedBy()).map(attr -> AttributeContains(PLTManagerThread_.importedBy, attr)).orElse(null))
                .and(ofNullable(filters.getXActPublicationDate()).map(attr -> AttributeEquals(PLTManagerThread_.xActPublicationDate, attr)).orElse(null))
                .and(ofNullable(filters.getPublishedBy()).map(attr -> AttributeContains(PLTManagerThread_.publishedBy, attr)).orElse(null))
                .and(ofNullable(filters.getArchived()).map(attr -> AttributeBoolean(PLTManagerThread_.archived, attr)).orElse(null))
                .and(ofNullable(filters.getArchivedDate()).map(attr -> AttributeEquals(PLTManagerThread_.archivedDate, attr)).orElse(null))
                .and(ofNullable(filters.getDeletedBy()).map(attr -> AttributeContains(PLTManagerThread_.deletedBy, attr)).orElse(null))
                .and(ofNullable(filters.getDeletedDue()).map(attr -> AttributeContains(PLTManagerThread_.deletedDue, attr)).orElse(null))
                .and(ofNullable(filters.getDeletedOn()).map(attr -> AttributeEquals(PLTManagerThread_.deletedOn, attr)).orElse(null));
    }
}
