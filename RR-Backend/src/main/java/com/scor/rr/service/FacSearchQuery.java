package com.scor.rr.service;

import com.scor.rr.domain.dto.ExpertModeFilter;
import com.scor.rr.domain.dto.ExpertModeSort;
import com.scor.rr.domain.enums.Operator;
import com.scor.rr.service.abstraction.QueryInterface;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FacSearchQuery implements QueryInterface {

    String[] globalSearchColumns= {"Client", "UWYear", "WorkspaceContextCode", "WorkspaceName", "UwAnalysis", "CARequestId", "CARStatus", "AssignedUser"};
    String[] groupByColumns= {"Client", "UWYear", "WorkspaceContextCode", "WorkspaceName", "UwAnalysis", "CARequestId", "CARStatus", "AssignedUser"};

    @Override
    public String generateSqlQuery(List<ExpertModeFilter> filter, List<ExpertModeSort> sort, String keyword, int offset, int size) {
        String sqlWithoutOffset = generateSqlQueryWithoutOffset(filter, sort,keyword);
        String orderByClause = generateOrderClause(sort);
        String sqlWithoutOffsetQuery = sqlWithoutOffset + " order by c.WorkspaceContextCode, c.UWYear desc ";
        String offsetQuery = " OFFSET " + offset + " ROWS FETCH NEXT " + size + " ROWS ONLY";

        String query= orderByClause.isEmpty() ? sqlWithoutOffsetQuery : sqlWithoutOffset + " order by " + orderByClause;

        return query.concat(offsetQuery);
    }

    @Override
    public String generateSqlQueryWithoutOffset(List<ExpertModeFilter> filter, List<ExpertModeSort> sort, String keyword) {
        List<String> fieldsSearchClauses = generateSearchClause(filter).stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> globalSearchClauses = new ArrayList<>(generateGlobalSearchClause(keyword));
        return getQuery(fieldsSearchClauses, globalSearchClauses, sort);
    }

    @Override
    public Collection<? extends String> generateSearchClause(List<ExpertModeFilter> filter) {
        List<String> sc = new ArrayList<>();
        filter.forEach(expertModeFilter -> addSearchClause(expertModeFilter,sc));
        return sc;
    }

    @Override
    public void addSearchClause(ExpertModeFilter expertModeFilter, List<String> sc) {
        sc.add(generateClause(expertModeFilter.getField(),expertModeFilter.getValue(),expertModeFilter.getOperator()));
    }

    @Override
    public String generateClause(String columnName, String keyword, Operator operator) {
        switch (operator) {
            case EQUAL:
                return " c." + columnName + " = '" + escape(keyword) + "' ";
            case LIKE:
            default:
                return " lower(c." + columnName + ") like '%" + escape(keyword.toLowerCase()) + "%' ";
        }
    }

    @Override
    public String getQuery(List<String> fieldsSearchClauses, List<String> globalSearchClauses, List<ExpertModeSort> sort) {
        String globalSearchCondition = String.join(" or ", globalSearchClauses);
        String fieldsSearchCondition = String.join(" and ", fieldsSearchClauses);
        String whereCondition = "";
        if (!fieldsSearchCondition.trim().equals("") && !globalSearchCondition.trim().equals(""))
            whereCondition = " where (" + globalSearchCondition + ") and " + fieldsSearchCondition;
        else if (!globalSearchCondition.trim().equals("")) whereCondition = " where " + globalSearchCondition;
        else if (!fieldsSearchCondition.trim().equals("")) whereCondition = " where " + fieldsSearchCondition;
        String groupByClause = generateGroupByClause();
        String selectClause = generateSelectClause();
        String query = "select " + selectClause + " from [dbonew].[vw_FacContractSearchResult] c " + whereCondition + " group by " + groupByClause;

        return query;
    }

    @Override
    public Collection<? extends String> generateGlobalSearchClause(String keyword) {
        List<String> gsc = new ArrayList<>();
        if(Objects.nonNull(keyword))
            if(!keyword.trim().equals(""))
                Arrays.asList(globalSearchColumns).forEach(s -> gsc.add(generateLikeClause(s,keyword)));
        return gsc;
    }

    @Override
    public String generateCountQuery(List<ExpertModeFilter> filter, String keyword) {
        String sqlWithoutOffset = generateSqlQueryWithoutOffset(filter, new ArrayList<>(),keyword);
        return "select count(*) from ( " +sqlWithoutOffset + " ) as i";
    }

    //Helpers

    private String generateOrderClause(List<ExpertModeSort> sort){
        return sort.stream().map(col -> col.getColumnName() + " " + col.getOrder().getOrderValue()).collect(Collectors.joining(","));
    }

    private String escape(String str){
        return str.replaceAll("'", "''");
    }

    private String generateGroupByClause(){
        return Arrays.stream(groupByColumns).map(s-> "c."+s).collect(Collectors.joining(","));
    }

    private String generateSelectClause(){
        return Arrays.stream(groupByColumns).map(s-> "c."+s+" as " +s).collect(Collectors.joining(","));
    }

    private String generateLikeClause(String columnName, String keyword){
        return " lower(c." + columnName + ") like '" + escape(keyword.toLowerCase()) + "' " ;
    }
}
