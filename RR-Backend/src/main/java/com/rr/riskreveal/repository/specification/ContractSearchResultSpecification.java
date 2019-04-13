package com.rr.riskreveal.repository.specification;

import com.rr.riskreveal.domain.ContractSearchResult;
import com.rr.riskreveal.domain.ContractSearchResult_;
import com.rr.riskreveal.domain.dto.WorkspaceFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Optional.ofNullable;

public class ContractSearchResultSpecification {

    public static Specification<ContractSearchResult> filter(WorkspaceFilter workspaceFilter) {
        return Specification
                .where(ofNullable(workspaceFilter.getCedant()).map(cedant -> assertIsLike(ContractSearchResult_.cedantName, cedant)).orElse(null))
                .and(ofNullable(workspaceFilter.getCountry()).map(country -> assertIsLike(ContractSearchResult_.countryName, country)).orElse(null))
                .and(ofNullable(workspaceFilter.getYear()).map(year -> assertIsLike(ContractSearchResult_.uwYear, year)).orElse(null))
                .and(ofNullable(workspaceFilter.getProgram()).map(program -> assertIsLike(ContractSearchResult_.programName, program)).orElse(null))
                .and(ofNullable(workspaceFilter.getTreaty()).map(treaty -> assertIsLike(ContractSearchResult_.treatyName, treaty)).orElse(null));
    }

    public static Specification<ContractSearchResult> filterGlobal(String keyword) {
        return isNullOrEmpty(keyword) ? Specification.where(null) : Specification
                .where(assertIsLike(ContractSearchResult_.cedantName, keyword))
                .or(assertIsLike(ContractSearchResult_.countryName, keyword))
                .or(assertIsLike(ContractSearchResult_.uwYear, keyword))
                .or(assertIsLike(ContractSearchResult_.programName, keyword))
                .or(assertIsLike(ContractSearchResult_.treatyName, keyword));
    }


    private static Specification<ContractSearchResult> assertIsLike(SingularAttribute<ContractSearchResult, ?> attr, String keyword) {
        return (Root<ContractSearchResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attr).as(String.class), "%" + keyword + "%");
        };
    }


}
