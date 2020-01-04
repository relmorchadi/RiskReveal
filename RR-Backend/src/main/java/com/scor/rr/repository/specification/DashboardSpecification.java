package com.scor.rr.repository.specification;

import com.scor.rr.domain.dto.DashBoardFilter;
import com.scor.rr.domain.dto.SortConfig;
import com.scor.rr.domain.entities.DashboardView;
import com.scor.rr.domain.entities.DashboardView_;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class DashboardSpecification extends BaseSpecification<DashboardView, DashBoardFilter> {

    @Override
    public Specification<DashboardView> getFilter(DashBoardFilter filter) {
        return Specification
                .where(ofNullable(filter.getCarRequestId()).map(id -> assertIsLike(DashboardView_.carRequestId, id)).orElse(null))
                .and(ofNullable(filter.getContractName()).map(contractName -> assertIsLike(DashboardView_.contractName, contractName)).orElse(null))
                .and(ofNullable(filter.getProjectId()).map(projectId -> assertIsLike(DashboardView_.projectId, projectId)).orElse(null))
                .and(ofNullable(filter.getUwYear()).map(uwYear -> assertIsLike(DashboardView_.uwYear, uwYear)).orElse(null))
                .and(ofNullable(filter.getContractId()).map(contractId -> assertIsLike(DashboardView_.contractId, contractId)).orElse(null))
                .and(ofNullable(filter.getUwAnalysis()).map(uwAnalysis -> assertIsLike(DashboardView_.uwAnalysis, uwAnalysis)).orElse(null))
                .and(ofNullable(filter.getSubsidiary()).map(subsidiary -> assertIsLike(DashboardView_.subsidiary, subsidiary)).orElse(null))
                .and(ofNullable(filter.getSector()).map(sector -> assertIsLike(DashboardView_.sector, sector)).orElse(null))
                .and(ofNullable(filter.getAssignedAnalyst()).map(assignedAnalyst -> assertIsLike(DashboardView_.assignedAnalyst, assignedAnalyst)).orElse(null))
                .and(ofNullable(filter.getCarStatus()).map(carStatus -> assertIsLike(DashboardView_.contractName, carStatus)).orElse(null))
                .and(ofNullable(filter.getCreationDate()).map(creationDate -> assertIsLike(DashboardView_.creationDate, creationDate)).orElse(null))
                .and(ofNullable(filter.getLastUpdateDate()).map(lastUpdateDate -> assertIsLike(DashboardView_.lastUpdateDate, lastUpdateDate)).orElse(null));
    }

    private static Specification<DashboardView> assertIsLike(SingularAttribute<DashboardView, ?> attr, Object keyword) {
        return (Root<DashboardView> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attr).as(String.class), "%" + keyword.toString() + "%");
        };
    }
}
