package com.rr.riskreveal.repository.specification;

import com.rr.riskreveal.domain.ContractSearchResult;
import com.rr.riskreveal.domain.ContractSearchResult_;
import com.rr.riskreveal.domain.WorkspaceView;
import com.rr.riskreveal.domain.WorkspaceView_;
import com.rr.riskreveal.domain.dto.WorkspaceFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import static java.util.Optional.ofNullable;

public class WorkspaceViewSpecification {


    public static Specification<WorkspaceView> filter(WorkspaceFilter workspaceFilter) {
        return Specification
                .where(ofNullable(workspaceFilter.getCedant()).map(cedant -> assertIsLike(WorkspaceView_.cedantName, cedant)).orElse(null))
                .and(ofNullable(workspaceFilter.getCountry()).map(country -> assertIsLike(WorkspaceView_.countryName, country)).orElse(null))
                .and(ofNullable(workspaceFilter.getYear()).map(year -> assertIsLike(WorkspaceView_.uwYear, year)).orElse(null))
                .and(ofNullable(workspaceFilter.getProgram()).map(program -> assertIsLike(WorkspaceView_.programName, program)).orElse(null))
                .and(ofNullable(workspaceFilter.getTreaty()).map(treaty -> assertIsLike(WorkspaceView_.treatyName, treaty)).orElse(null))
                .and(ofNullable(workspaceFilter.getProgramId()).map(programid -> assertIsLike(WorkspaceView_.programid, programid)).orElse(null));
    }

    private static Specification<WorkspaceView> assertIsLike(SingularAttribute<WorkspaceView, ?> attr, String keyword) {
        return (Root<WorkspaceView> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attr).as(String.class), "%" + keyword + "%");
        };
    }

    private static Specification<WorkspaceView> assertIsEqual(SingularAttribute<WorkspaceView, ?> attr, String keyword) {
        return (Root<WorkspaceView> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get(attr).as(String.class), keyword);
        };
    }
}
