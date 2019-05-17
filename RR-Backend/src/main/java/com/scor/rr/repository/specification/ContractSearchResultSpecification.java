package com.scor.rr.repository.specification;

import com.google.common.base.Supplier;
import com.scor.rr.domain.ContractSearchResult;
import com.scor.rr.domain.ContractSearchResult_;
import com.scor.rr.domain.dto.WorkspaceFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Optional.ofNullable;

public class ContractSearchResultSpecification {

    public static Specification<ContractSearchResult> filter(WorkspaceFilter workspaceFilter) {
        return Specification
                .where(selectAndGroupFields())
                .and(ofNullable(workspaceFilter.getCedant()).map(cedant -> assertIsLike(ContractSearchResult_.cedantName, cedant)).orElse(null))
                .and(ofNullable(workspaceFilter.getCountry()).map(country -> assertIsLike(ContractSearchResult_.countryName, country)).orElse(null))
                .and(ofNullable(workspaceFilter.getYear()).map(year -> assertIsLike(ContractSearchResult_.uwYear, year)).orElse(null))
                .and(ofNullable(workspaceFilter.getProgram()).map(program -> assertIsLike(ContractSearchResult_.programName, program)).orElse(null))
                .and(ofNullable(workspaceFilter.getTreaty()).map(treaty -> assertIsLike(ContractSearchResult_.treatyName, treaty)).orElse(null));
    }


    public static Specification<ContractSearchResult> filterByWorkspaceIdAndUwy(String worspaceId, String uwy) {
        return Specification
                .where(assertIsEqual(ContractSearchResult_.workSpaceId, worspaceId))
                .and(assertIsEqual(ContractSearchResult_.uwYear, uwy));
    }

    public static Specification<ContractSearchResult> filterGlobal(String keyword) {
        return isNullOrEmpty(keyword) ? Specification.where(null) : Specification
                .where(assertIsLike(ContractSearchResult_.cedantName, keyword))
                .or(assertIsLike(ContractSearchResult_.countryName, keyword))
                .or(assertIsLike(ContractSearchResult_.uwYear, keyword))
                .or(assertIsLike(ContractSearchResult_.programName, keyword))
                .or(assertIsLike(ContractSearchResult_.treatyName, keyword));
    }

    public static Specification<ContractSearchResult> selectAndGroupFields() {
        return (Root<ContractSearchResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Supplier<Stream<SingularAttribute<ContractSearchResult, ?>>> groupFields = () -> Stream.of(ContractSearchResult_.id, ContractSearchResult_.workSpaceId, ContractSearchResult_.workspaceName, ContractSearchResult_.cedantCode, ContractSearchResult_.cedantName, ContractSearchResult_.uwYear);
            List<Selection<?>> selections = groupFields.get().map(root::get).collect(Collectors.toList());
            List<Expression<?>> groupments = groupFields.get().map(root::get).collect(Collectors.toList());
            return query.multiselect(selections).distinct(true)//.groupBy(groupments)
                    .where(cb.like(root.get(ContractSearchResult_.uwYear).as(String.class), "2015"))
                    .getRestriction();
        };
    }


    private static Specification<ContractSearchResult> assertIsLike(SingularAttribute<ContractSearchResult, ?> attr, String keyword) {
        return (Root<ContractSearchResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attr).as(String.class), "%" + keyword + "%");
        };
    }

    private static Specification<ContractSearchResult> loadContracts(SingularAttribute<ContractSearchResult, ?> attr, String keyword) {
        return (Root<ContractSearchResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attr).as(String.class), "%" + keyword + "%");
        };
    }

    private static Specification<ContractSearchResult> assertIsEqual(SingularAttribute<ContractSearchResult, ?> attr, String keyword) {
        return (Root<ContractSearchResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get(attr).as(String.class), keyword);
        };
    }


}
