package com.scor.rr.repository.specification;

import com.scor.rr.domain.dto.FieldFilter;
import com.scor.rr.domain.dto.grid.ColumnVO;
import com.scor.rr.domain.dto.grid.SortModel;
import com.scor.rr.domain.entities.PLTManager.PLTManagerAll;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PLTManagerSpecification {

    public static Specification<Object> resolveFilter(FieldFilter criteria) {
        return (Root<Object> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            switch (criteria.getComparisonOperator()) {
                case EQ_BOOL:
                    return builder.equal(
                            resolveExpressionByType(root,builder, criteria), resolveValueByType(criteria));
                case EQ:
                    return builder.equal(
                            resolveExpressionByType(root,builder, criteria), resolveValueByType(criteria));

                case NOT_EQUAL:
                    return builder.notEqual(
                            resolveExpressionByType(root,builder, criteria), resolveValueByType(criteria));

                case CONTAINS:
                    return builder.like(
                            resolveExpressionByType(root,builder, criteria), "%" + resolveValueByType(criteria) + "%");

                case NOT_CONTAINS:
                    return builder.like(
                            resolveExpressionByType(root,builder, criteria), "%" + resolveValueByType(criteria) + "%");

                case STARTS_WITH:
                    return builder.like(
                            resolveExpressionByType(root,builder, criteria), resolveValueByType(criteria) + "%");

                case ENDS_WITH:
                    return builder.like(
                            resolveExpressionByType(root,builder, criteria), "%" + resolveValueByType(criteria));

                case LT:
                    return builder.lessThan(
                            resolveExpressionByType(root,builder, criteria), (String) resolveValueByType(criteria));

                case LT_OR_EQ:
                    return builder.lessThanOrEqualTo(
                            resolveExpressionByType(root,builder, criteria), (String) resolveValueByType(criteria));

                case GT:
                    return builder.greaterThan(
                            resolveExpressionByType(root,builder, criteria), (String) resolveValueByType(criteria));

                case GT_OR_EQ:
                    return builder.greaterThanOrEqualTo(
                            resolveExpressionByType(root,builder, criteria), (String) resolveValueByType(criteria));

                default:
                    return builder.equal(
                            resolveExpressionByType(root,builder, criteria), resolveValueByType(criteria));
            }
        };
    }

    static Object resolveValueByType(FieldFilter criteria) {
        switch (criteria.getFilterType()) {
            case DATE:
                return new Date((Long) criteria.getValue()).toString();
            case TEXT:
            case NUMBER:
                return criteria.getValue().toString();
            case BOOLEAN:
                return criteria.getValue();
            default:
                return criteria.getValue().toString();
        }
    }

    static Expression resolveExpressionByType(Root root, CriteriaBuilder cb, FieldFilter criteria) {
        switch (criteria.getFilterType()) {
            case DATE:
                return cb.function(
                        "format",
                        String.class,
                        root.get(criteria.getKey()).as(Date.class),
                        cb.literal("YYYY-MM-DD HH12:MI:SS"));
            case TEXT:
            case NUMBER:
            case BOOLEAN:
                return root.<String>get(criteria.getKey());
            default:
                return root.<String>get(criteria.getKey());
        }
    }


    public static void resolveGrouping(List<String> rowGroupsToInclude, Root<PLTManagerAll> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        rowGroupsToInclude.forEach(key -> {
            criteriaQuery.groupBy(root.get(key));
        });
    }

    public static void resolveSorting(List<SortModel> sortModel, Root<PLTManagerAll> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (!sortModel.isEmpty()) {
            criteriaQuery.orderBy(
                    sortModel.stream()
                            .map( sort -> PLTManagerSpecification.getOrder(sort, root, criteriaBuilder))
                            .collect(toList())
            );
        }
    }

    public static Order getOrder(SortModel sort, Root<PLTManagerAll> root, CriteriaBuilder cb) {
        switch (sort.getSort()) {
            case asc:
                return cb.asc(root.get(sort.getColId()));
            case desc:
                return cb.desc(root.get(sort.getColId()));
                default:
                    return cb.asc(root.get(sort.getColId()));
        }
    }

    public static Selection getSelection(ColumnVO columnVO, Root<PLTManagerAll> root, CriteriaBuilder cb) {
        if(columnVO.getAggFunc() == null) return root.get(columnVO.getField());
        switch (columnVO.getAggFunc()) {
            case max:
                return cb.max( root.get(columnVO.getField()));
            case min:
                return cb.min( root.get(columnVO.getField()));
            case sum:
                return cb.sum( root.get(columnVO.getField()));
            case count:
                return cb.count(root.get(columnVO.getField()));
                default:
                    return root.get(columnVO.getField());
        }
    }


}
