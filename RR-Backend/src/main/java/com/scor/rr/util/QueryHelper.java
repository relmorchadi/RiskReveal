package com.scor.rr.util;

import com.scor.rr.domain.dto.ExpertModeFilter;
import com.scor.rr.domain.dto.ExpertModeSort;
import com.scor.rr.domain.dto.NewWorkspaceFilter;
import com.scor.rr.domain.enums.Operator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class QueryHelper {


    String[] globalSearchColumns={"WorkSpaceId","WorkspaceName","CedantCode","CedantName","CountryName","ProgramName","TreatyName"};
    String[] groupByColumns={"CountryName","WorkSpaceId","WorkspaceName","CedantCode","CedantName","UwYear"};

    Map<String, String> searchCountMapper = new HashMap();

    @PostConstruct
    private void feedCountMapper() {

        searchCountMapper.put("CLIENT_NAME", "CedantName");
        searchCountMapper.put("CLIENT_CODE", "CedantCode");
        searchCountMapper.put("UW_YEAR", "UWYear");
        searchCountMapper.put("CONTRACT_CODE", "WorkSpaceId");
        searchCountMapper.put("CONTRACT_NAME", "WorkspaceName");
    }


    private String generateLikeClause(String columnName, String keyword){
        return " lower(c." + columnName + ") like '" + escape(keyword.toLowerCase()) + "' " ;
    }

    private String generateGroupByClause(){
        return Arrays.stream(groupByColumns).map(s-> "c."+s).collect(Collectors.joining(","));
    }

    private String generateSelectClause(){
        return Arrays.stream(groupByColumns).map(s-> "c."+s+" as " +s).collect(Collectors.joining(","));
    }

    private String generateOrderClause(List<ExpertModeSort> sort){
        return sort.stream().map(col -> col.getColumnName() + " " + col.getOrder().getOrderValue()).collect(Collectors.joining(","));
    }

    private String escape(String str){
        return str.replaceAll("'", "''");
    }

    ////////////////////////////////////////////////////////////////////////////////////

    public String generateSqlQuery(List<ExpertModeFilter> filter, List<ExpertModeSort> sort, String keyword, int offset, int size){
        String sqlWithoutOffset = generateSqlQueryWithoutOffset(filter, sort,keyword);
        String orderByClause = generateOrderClause(sort);
        String sqlWithoutOffsetQuery = sqlWithoutOffset + " order by c.WorkSpaceId, c.UwYear desc ";
        String offsetQuery = " OFFSET " + offset + " ROWS FETCH NEXT " + size + " ROWS ONLY";

        String query= orderByClause.isEmpty() ? sqlWithoutOffsetQuery : sqlWithoutOffset + " order by " + orderByClause;

        return query.concat(offsetQuery);
    }

    private String generateSqlQueryWithoutOffset(List<ExpertModeFilter> filter, List<ExpertModeSort> sort, String keyword) {

        List<String> fieldsSearchClauses = generateSearchClause(filter).stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> globalSearchClauses = new ArrayList<>(generateGlobalSearchClause(keyword));
        return getQuery(fieldsSearchClauses, globalSearchClauses, sort);
    }

    private Collection<? extends String> generateSearchClause(List<ExpertModeFilter> filter) {
        List<String> sc = new ArrayList<>();
        filter.forEach(expertModeFilter -> addSearchClause(expertModeFilter,sc));
        return sc;
    }

    private void addSearchClause(ExpertModeFilter expertModeFilter, List<String> sc){
        sc.add(generateClause(expertModeFilter.getField(),expertModeFilter.getValue(),expertModeFilter.getOperator()));
    }

    private String generateClause(String columnName, String keyword, Operator operator){
        if(columnName.equals("year")) columnName="uwYear";
        switch (operator) {
            case EQUAL:
                return " c." + this.searchCountMapper.get(columnName) + " = '" + escape(keyword) + "' ";
            case LIKE:
            default:
                return " lower(c." + this.searchCountMapper.get(columnName) + ") like '%" + escape(keyword.toLowerCase()) + "%' ";
        }
    }

    private String getQuery(List<String> fieldsSearchClauses, List<String> globalSearchClauses, List<ExpertModeSort> sort) {
        String globalSearchCondition = String.join(" or ", globalSearchClauses);
        String fieldsSearchCondition = String.join(" and ", fieldsSearchClauses);
        String whereCondition = "";
        if (!fieldsSearchCondition.trim().equals("") && !globalSearchCondition.trim().equals(""))
            whereCondition = " where (" + globalSearchCondition + ") and " + fieldsSearchCondition;
        else if (!globalSearchCondition.trim().equals("")) whereCondition = " where " + globalSearchCondition;
        else if (!fieldsSearchCondition.trim().equals("")) whereCondition = " where " + fieldsSearchCondition;
        String groupByClause = generateGroupByClause();
        String selectClause = generateSelectClause();
        String query = "select " + selectClause + " from [dbo].[ContractSearchResult] c " + whereCondition + " group by " + groupByClause;

        return query;
    }

    public String generateCountQuery(List<ExpertModeFilter> filter,String keyword) {
        String sqlWithoutOffset = generateSqlQueryWithoutOffset(filter, new ArrayList<>(),keyword);
        return "select count(*) from ( " +sqlWithoutOffset + " ) as i";
    }

    private Collection<? extends String> generateGlobalSearchClause(String keyword) {
        List<String> gsc = new ArrayList<>();
        if(Objects.nonNull(keyword))
            if(!keyword.trim().equals(""))
            Arrays.asList(globalSearchColumns).forEach(s -> gsc.add(generateLikeClause(s,keyword)));
        return gsc;
    }
}
