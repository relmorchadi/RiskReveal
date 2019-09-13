package com.scor.rr.repository.specification;

import com.scor.rr.domain.FacContract;
import com.scor.rr.domain.FacContract_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class FacContractSpecification extends BaseSpecification<FacContract, FacContract> {


    @Override
    public Specification<FacContract> getFilter(FacContract request) {
        return Specification
                .where(ofNullable(request.getId()).map(id -> AttributeContains(FacContract_.id, id)).orElse(null))
                .and(ofNullable(request.getLastUpdateDate()).map(lastUpdateDate -> AttributeContains(FacContract_.lastUpdateDate, lastUpdateDate)).orElse(null))
                .and(ofNullable(request.getLastUpdatedBy()).map(lastUpdatedBy -> AttributeContains(FacContract_.lastUpdatedBy, lastUpdatedBy)).orElse(null))
                .and(ofNullable(request.getReauestedByFirstName()).map(reauestedByFirstName -> AttributeContains(FacContract_.reauestedByFirstName, reauestedByFirstName)).orElse(null))
                .and(ofNullable(request.getReauestedByLastName()).map(reauestedByLastName -> AttributeContains(FacContract_.reauestedByLastName, reauestedByLastName)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractBusinessType()).map(uWanalysisContractBusinessType -> AttributeContains(FacContract_.uWanalysisContractBusinessType, uWanalysisContractBusinessType)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractContractId()).map(uWanalysisContractContractId -> AttributeContains(FacContract_.uWanalysisContractContractId, uWanalysisContractContractId)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractEndorsementNumber()).map(uWanalysisContractEndorsementNumber -> AttributeEquals(FacContract_.uWanalysisContractEndorsementNumber, uWanalysisContractEndorsementNumber)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractFacNumber()).map(uWanalysisContractFacNumber -> AttributeContains(FacContract_.uWanalysisContractFacNumber, uWanalysisContractFacNumber)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractInsured()).map(uWanalysisContractInsured -> AttributeContains(FacContract_.uWanalysisContractInsured, uWanalysisContractInsured)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractLabel()).map(uWanalysisContractLabel -> AttributeContains(FacContract_.uWanalysisContractLabel, uWanalysisContractLabel)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractLob()).map(uWanalysisContractLob -> AttributeContains(FacContract_.uWanalysisContractLob, uWanalysisContractLob)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractOrderNumber()).map(uWanalysisContractOrderNumber -> AttributeEquals(FacContract_.uWanalysisContractOrderNumber, uWanalysisContractOrderNumber)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractSector()).map(uWanalysisContractSector -> AttributeContains(FacContract_.uWanalysisContractSector, uWanalysisContractSector)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractSubsidiary()).map(uWanalysisContractSubsidiary -> AttributeContains(FacContract_.uWanalysisContractSubsidiary, uWanalysisContractSubsidiary)).orElse(null))
                .and(ofNullable(request.getUWanalysisContractYear()).map(uWanalysisContractYear -> AttributeEquals(FacContract_.uWanalysisContractYear, uWanalysisContractYear)).orElse(null))
                .and(ofNullable(request.getRequestCreationDate()).map(requestCreationDate -> AttributeEquals(FacContract_.requestCreationDate, requestCreationDate)).orElse(null));
    }


    //    @Override
//    public Specification<> getFilter(AnalysisFilter request) {
//        return Specification
//                .where(ofNullable(request.getId()).map(id -> AttributeContains(AnalysisView_.id, id)).orElse(null) )
//                .and(ofNullable(request.getAnalysisId()).map(analysisId -> AttributeContains(AnalysisView_.analysisId, String.valueOf(analysisId))).orElse(null))
//                .and(ofNullable(request.getAnalysisName()).map(analysisName -> AttributeContains(AnalysisView_.analysisId,analysisName) ).orElse(null) )
//                .and(ofNullable(request.getDescription()).map(description -> AttributeContains(AnalysisView_.description, description)).orElse(null) )
//                .and(ofNullable(request.getEngineVersion()).map(engV -> AttributeContains(AnalysisView_.engineVersion, engV)).orElse(null))
//                .and(ofNullable(request.getGroupType()).map(gt -> AttributeContains(AnalysisView_.groupType, gt)).orElse(null))
//                .and(ofNullable(request.getCedant()).map(cdt -> AttributeContains(AnalysisView_.cedant, cdt)).orElse(null))
//                .and(ofNullable(request.getRdmId()).map(rdmId -> AttributeEquals( AnalysisView_.rdmId, rdmId)).orElse(null))
//                .and(ofNullable(request.getLobName()).map(lobName -> AttributeEquals(AnalysisView_.lobName, lobName)).orElse(null))
//                .and(ofNullable(request.getStatusDescription()).map(statusDescription -> AttributeContains(AnalysisView_.statusDescription, statusDescription)).orElse(null))
//                .and(ofNullable(request.getAnalysisCurrency()).map(analysisCurrency -> AttributeContains(AnalysisView_.analysisCurrency, analysisCurrency)).orElse(null))
//                .and(ofNullable(request.getEngineType()).map(engineType -> AttributeContains(AnalysisView_.engineType, engineType)).orElse(null))
//                .and(ofNullable(request.getGrouping()).map(grouping -> AttributeContains(AnalysisView_.grouping, grouping)).orElse(null))
//                .and(ofNullable(request.getLossAmplification()).map(lossAmplification -> AttributeContains(AnalysisView_.lossAmplification, lossAmplification)).orElse(null))
//                .and(ofNullable(request.getModeName()).map(modeName -> AttributeContains(AnalysisView_.modeName, modeName)).orElse(null))
//                .and(ofNullable(request.getPeril()).map(peril -> AttributeContains(AnalysisView_.peril, peril)).orElse(null))
//                .and(ofNullable(request.getRegion()).map(region -> AttributeContains(AnalysisView_.region, region)).orElse(null))
//                .and(ofNullable(request.getRegionName()).map(regionName -> AttributeContains(AnalysisView_.regionName, regionName)).orElse(null))
//                .and(ofNullable(request.getRunDate()).map(runDate -> AttributeContains(AnalysisView_.runDate, runDate)).orElse(null))
//                .and(ofNullable(request.getSubPeril()).map(subPeril -> AttributeContains(AnalysisView_.subPeril, subPeril)).orElse(null))
//                .and(ofNullable(request.getTypeName()).map(typeName -> AttributeContains(AnalysisView_.typeName, typeName)).orElse(null))
//                .and(ofNullable(request.getUser1()).map(user1 -> AttributeContains(AnalysisView_.user1, user1)).orElse(null))
//                .and(ofNullable(request.getUser2()).map(user2 -> AttributeContains(AnalysisView_.user2, user2)).orElse(null))
//                .and(ofNullable(request.getUser3()).map(user3 -> AttributeContains(AnalysisView_.user3, user3)).orElse(null))
//                .and(ofNullable(request.getUser4()).map(user4 -> AttributeContains(AnalysisView_.user4, user4)).orElse(null));
//    }
}
