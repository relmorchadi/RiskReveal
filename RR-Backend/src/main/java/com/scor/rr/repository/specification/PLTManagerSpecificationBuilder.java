package com.scor.rr.repository.specification;

import com.scor.rr.domain.dto.ColumnFilter;
import com.scor.rr.domain.dto.FieldFilter;
import com.scor.rr.domain.dto.FilterType;
import com.scor.rr.domain.dto.grid.ColumnVO;
import com.scor.rr.domain.entities.PLTManager.PLTManagerAll;
import com.scor.rr.domain.enums.AggFunc;
import com.scor.rr.domain.enums.ComparisonOperator;
import com.scor.rr.domain.enums.Operator;
import com.scor.rr.domain.requests.GridDataRequest;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PLTManagerSpecificationBuilder {

    private final GridDataRequest<List<ColumnFilter>> request;
    private final EntityManager em;
    private final CriteriaBuilder cb;
    private final List<ColumnFilter> whereColumnsFromFilters;
    private final List<ColumnFilter> whereColumnsFromGrouping;
    private final Boolean isGrouping;
    private final Boolean isFullGrouping;
    private final List<String> rowGroups;
    private final List<String> rowGroupsToIncludeByField;
    private final List<ColumnVO> rowGroupsToIncludeByColumn;

    public PLTManagerSpecificationBuilder(
            GridDataRequest<List<ColumnFilter>> request,
            EntityManager em
    ) {
        whereColumnsFromFilters = new ArrayList<ColumnFilter>();
        whereColumnsFromGrouping = new ArrayList<ColumnFilter>();

        this.request = request;
        this.em = em;
        this.cb = em.getCriteriaBuilder();

        this.isFullGrouping = request.getGroupKeys().size() == request.getRowGroupCols().size();

        this.rowGroups = request.getRowGroupCols().stream().map(ColumnVO::getField).collect(toList());
        this.isGrouping = rowGroups.size() > request.getGroupKeys().size();

        this.rowGroupsToIncludeByField = rowGroups.stream()
                .limit(this.request.getGroupKeys().size() + 1)
                .collect(toList());
        this.rowGroupsToIncludeByColumn = request.getRowGroupCols().stream()
                .limit(this.request.getGroupKeys().size() + 1)
                .collect(toList());

        this.initFilters();
    }

    public void appendFilter(String key, FilterType filterType, Operator operator, List<FieldFilter> conditions) {
        whereColumnsFromFilters.add(new ColumnFilter(key, filterType, operator, conditions));
    }

    private void initFilters(){
        //Filters
        for (int i = 0; i< request.getFilterModel().size(); i++) {
            ColumnFilter columnFilter = request.getFilterModel().get(i);
            this.appendFilter(columnFilter.getKey(), columnFilter.getFilterType(),columnFilter.getOperator(), columnFilter.getConditions());
        }
        //Filter from Grouping
        for (int i = 0; i< request.getGroupKeys().size(); i++) {
            ColumnVO columnVO = request.getRowGroupCols().get(i);
            Object valueCol = request.getGroupKeys().get(i);
            this.whereColumnsFromGrouping.add(
                    new ColumnFilter(
                            columnVO.getField(),
                            FilterType.TEXT,
                            null,
                            Stream.of(new FieldFilter(ComparisonOperator.EQ, valueCol, columnVO.getField(), FilterType.TEXT)).collect(Collectors.toList())
                    ));
        }
    }

    public List<Object> getResultList(boolean paginated) {

        CriteriaQuery<Object> cq;
        Root<PLTManagerAll> root;

        //Generate From
        cq = cb.createQuery(Object.class);
        root = cq.from(PLTManagerAll.class);

        //Generate Select
        if(isFullGrouping) cq.select(root);
        else {
            List<Selection<?>> s = new LinkedList<Selection<?>>();
            ColumnVO columnVO = request.getRowGroupCols().get(request.getGroupKeys().size());
            s.add(PLTManagerSpecification.getSelection(columnVO, root, cb));
            s.add(PLTManagerSpecification.getSelection(
                    new ColumnVO(
                            columnVO.getId(),
                            columnVO.getDisplayName(),
                            columnVO.getField(),
                            AggFunc.count
                    )
                    , root, cb));
            cq.multiselect(s);
        }


        //Generate Where
        List<Specification> specs = Stream.concat(whereColumnsFromFilters.stream(), whereColumnsFromGrouping.stream())
                .map(PLTManagerSpecificationBuilder::compose)
                .collect(Collectors.toList());

        Specification<PLTManagerAll> builtSpecs = null;
        int whereClauseSize = whereColumnsFromFilters.size() + whereColumnsFromGrouping.size();

        for (int i = 0; i < whereClauseSize; i++) {
            builtSpecs = Specification
                    .where(builtSpecs)
                    .and(specs.get(i));
        }
        Predicate whereClause = builtSpecs.toPredicate(root, cq, cb);

        cq.where(whereClause);

        //Generate Group By
        if(!isFullGrouping) {
            PLTManagerSpecification.resolveGrouping(
                    this.rowGroupsToIncludeByField,
                    root,
                    cq,
                    cb
            );
        }

        //Generate Order By
        if(isFullGrouping) {
            PLTManagerSpecification.resolveSorting(
                    request.getSortModel(),
                    root,
                    cq,
                    cb
            );
        }

        TypedQuery<Object> typedQuery = em.createQuery(cq);

//        if(paginated) {
//            typedQuery.setFirstResult(request.getStartRow());
//            typedQuery.setMaxResults(request.getEndRow() - request.getStartRow());
//        }

        List<Object> res = typedQuery
                .getResultStream()
                .collect(
                        Collectors.toList()
                );

        if(isFullGrouping) return  res;
        else {
            return res.stream()
                    .map(row -> {
                        Object[] newRow = (Object[]) row;
                        Map<String, Object> dto = new HashMap<>();
                        dto.put(request.getRowGroupCols().get(request.getGroupKeys().size()).getField(), newRow[0]);
                        dto.put("childCount", newRow[1]);
                        return dto;
                    }).collect(Collectors.toList());
        }
    }

    public long getCount(){
//        //Generate Where Clause
//        CriteriaQuery<Long> countCq;
//        Root<PLTManagerAll> root;
//
//        countCq = cb.createQuery(Long.class);
//        root = countCq.from(PLTManagerAll.class);
//
//        //Generate Where
//
//        List<Specification> specs = whereColumnsFromFilters.stream()
//                .map(PLTManagerSpecificationBuilder::compose)
//                .collect(Collectors.toList());
//
//        Specification<PLTManagerAll> builtSpecs = null;
//
//        for (int i = 0; i < whereColumnsFromFilters.size(); i++) {
//            builtSpecs = Specification
//                    .where(builtSpecs)
//                    .and(specs.get(i));
//        }
//        Predicate whereClause = builtSpecs.toPredicate(root, countCq, cb);
//
//        countCq.where(whereClause);
//
//        List<ColumnVO> selectCols;
//        if (!request.isPivotMode() || request.getPivotCols().isEmpty()) {
//
//
//            selectCols = Stream.concat(rowGroupsToIncludeByColumn.stream(), request.getValueCols().stream())
//                    .collect(toList());
//
//            if(selectCols.isEmpty()) countCq.select(cb.countDistinct(root));
//            else {
//                List<Selection<?>> s = new LinkedList<Selection<?>>();
//
//                for (ColumnVO column : selectCols) {
//                    s.add(PLTManagerSpecification.getSelection(column, root, cb));
//                }
//
//                countCq.select(cb.countDistinct());
//            }
//        }
//
//        countCq.where(whereClause);
//        Long count = em.createQuery(countCq).getSingleResult();

        return this.getResultList(false).size();
    }

    static Specification<Object> compose(ColumnFilter filter) {
        List<Specification<Object>> specs = filter.getConditions().stream()
                .map(PLTManagerSpecification::resolveFilter)
                .collect(Collectors.toList());

        if(filter.getOperator() == null) return specs.get(0);
        else {
            Specification<Object> res = specs.get(0);

            List<Specification<Object>> restOfSpecs = specs.subList(1, specs.size());

            for( int i=0; i< restOfSpecs.size(); i++) {
                switch (filter.getOperator()) {
                    case OR:
                        res= Specification
                                .where(res)
                                .or(restOfSpecs.get(i));
                    case AND:
                        res= Specification
                                .where(res)
                                .and(restOfSpecs.get(i));

                    default:
                        res= Specification
                                .where(res)
                                .and(restOfSpecs.get(i));
                }
            }
            return res;
        }

    }
}
