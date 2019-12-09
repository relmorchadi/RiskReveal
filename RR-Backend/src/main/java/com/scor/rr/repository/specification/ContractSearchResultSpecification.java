package com.scor.rr.repository.specification;

import com.google.common.base.Supplier;
import com.scor.rr.domain.entities.ContractSearchResult;
import com.scor.rr.domain.entities.ContractSearchResult_;
import com.scor.rr.domain.dto.NewWorkspaceFilter;
import com.scor.rr.domain.dto.WorkspaceFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Optional.ofNullable;

@Component
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


    public Specification<ContractSearchResult> getFilter(NewWorkspaceFilter filter) {
        return (root, query, cb) -> Specification.where(Specification.where(
                (contractSearchResultContains(ContractSearchResult_.cedantName,filter.getKeyword()))
                        .or(contractSearchResultContains(ContractSearchResult_.countryName,filter.getKeyword()))
                        .or(contractSearchResultContains(ContractSearchResult_.programName,filter.getKeyword()))
                        .or(contractSearchResultContains(ContractSearchResult_.treatyName,filter.getKeyword()))
                        .or(contractSearchResultContains(ContractSearchResult_.cedantCode,filter.getKeyword()))
                        .or(contractSearchResultYearEquals(ContractSearchResult_.uwYear,filter.getKeyword())))
                .and(contractSearchResultYearEquals(ContractSearchResult_.uwYear,filter.getInnerYear()))
                .and(contractSearchResultYearEquals(ContractSearchResult_.uwYear,filter.getYear()))
                .and(contractSearchResultContains(ContractSearchResult_.cedantName,filter.getInnerCedantName()))
                .and(contractSearchResultContains(ContractSearchResult_.cedantName,filter.getCedantName()))
                .and(contractSearchResultContains(ContractSearchResult_.countryName,filter.getCountryName()))
                .and(contractSearchResultContains(ContractSearchResult_.countryName,filter.getInnerCountryName()))
                .and(contractSearchResultContains(ContractSearchResult_.workSpaceId,filter.getInnerWorkspaceId()))
                .and(contractSearchResultContains(ContractSearchResult_.workSpaceId,filter.getWorkspaceId()))
                .and(contractSearchResultContains(ContractSearchResult_.cedantCode,filter.getCedantCode()))
                .and(contractSearchResultContains(ContractSearchResult_.cedantCode,filter.getInnerCedantCode()))

        ).toPredicate(root, query, cb);

    }

    private Specification<ContractSearchResult> contractSearchResultContains(SingularAttribute attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<ContractSearchResult> contractSearchResultYearEquals(SingularAttribute attribute, String value) {
        return (root, query, cb) -> {
            Integer year;
            try{
                year=Integer.parseInt(value);
            }catch (Exception e){
                return null;
            }
            return cb.equal(
                    root.get(attribute),
                    year
            );
        };
    }

    private String containsLowerCase(String searchField) {
        return  "%" + searchField.toLowerCase() + "%";
    }
}
