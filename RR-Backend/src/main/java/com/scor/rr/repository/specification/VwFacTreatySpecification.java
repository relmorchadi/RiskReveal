package com.scor.rr.repository.specification;

import com.scor.rr.domain.dto.VwFacTreatyFilter;
import com.scor.rr.domain.views.VwFacTreaty;
import com.scor.rr.domain.views.VwFacTreaty_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VwFacTreatySpecification extends BaseSpecification<VwFacTreaty,VwFacTreatyFilter>{

    @Override
    public Specification<VwFacTreaty> getFilter(VwFacTreatyFilter filter) {
        return (root, query, cb) ->
         Specification.where( Specification.where(
                 AttributeContains(VwFacTreaty_.id, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.analysisName, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.analysisCtrBusinessType, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.analysisCtrId, filter.keyword))
                 .or(AttributeContainsBoolean(VwFacTreaty_.analysisCtrEndorsementNmber, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.analysisCtrFacNumber, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.analysisCtrInsured, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.analysisCtrLabel, filter.keyword))
                 .or(AttributeContainsLong(VwFacTreaty_.analysisCtrLob, filter.keyword))
                 .or(AttributeContainsBoolean(VwFacTreaty_.analysisCtrOrderNumber, filter.keyword))
                 .or(AttributeContainsLong(VwFacTreaty_.analysisCtrSubsidiary, filter.keyword))
                 .or(AttributeContainsInteger(VwFacTreaty_.analysisCtrYear, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.assignDate, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.assignedTo, filter.keyword))
                 //.or(AttributeContains(VwFacTreaty_.lastUpdateDate, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.lastUpdatedBy, filter.keyword))
                 .or(AttributeContains(VwFacTreaty_.modellingSystemInstance, filter.keyword))
         )
                 .and(AttributeContains(VwFacTreaty_.id, filter.id))
                .and(AttributeContains(VwFacTreaty_.analysisName, filter.analysisName))
                .and(AttributeContains(VwFacTreaty_.analysisCtrBusinessType, filter.analysisCtrBusinessType))
                .and(AttributeContains(VwFacTreaty_.analysisCtrId, filter.analysisCtrId))
                .and(AttributeEquals(VwFacTreaty_.analysisCtrEndorsementNmber, filter.analysisCtrEndorsementNmber))
                .and(AttributeContains(VwFacTreaty_.analysisCtrFacNumber, filter.analysisCtrFacNumber))
                .and(AttributeContains(VwFacTreaty_.analysisCtrInsured, filter.analysisCtrInsured))
                .and(AttributeContains(VwFacTreaty_.analysisCtrLabel, filter.analysisCtrLabel))
                .and(AttributeEquals(VwFacTreaty_.analysisCtrLob, filter.analysisCtrLob))
                .and(AttributeEquals(VwFacTreaty_.analysisCtrOrderNumber, filter.analysisCtrOrderNumber))
                .and(AttributeEquals(VwFacTreaty_.analysisCtrSubsidiary, filter.analysisCtrSubsidiary))
                .and(AttributeEquals(VwFacTreaty_.analysisCtrYear, filter.analysisCtrYear))
                .and(AttributeContains(VwFacTreaty_.assignDate, filter.assignDate))
                .and(AttributeContains(VwFacTreaty_.assignedTo, filter.assignedTo))
                //.and(AttributeContains(VwFacTreaty_.lastUpdateDate, filter.lastUpdateDate))
                .and(AttributeContains(VwFacTreaty_.lastUpdatedBy, filter.lastUpdatedBy))
                .and(AttributeContains(VwFacTreaty_.modellingSystemInstance, filter.modellingSystemInstance))
                .toPredicate(root, query, cb);
    }
}
