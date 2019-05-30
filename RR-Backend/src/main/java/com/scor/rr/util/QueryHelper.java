package com.scor.rr.util;

import com.scor.rr.domain.dto.ExpertModeFilter;
import com.scor.rr.domain.dto.NewWorkspaceFilter;
import com.scor.rr.domain.enums.Operator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class QueryHelper {


    String[] globalSearchColumns={"CedantName","CountryName","ProgramName","TreatyName"};
    String[] groupByColumns={"CountryName","WorkSpaceId","WorkspaceName","CedantCode","CedantName","UwYear"};
    public String generateSqlQuery(NewWorkspaceFilter filter, int offset, int size){

        String sqlWithoutOffset = generateSqlQueryWithoutOffset(filter);
        return sqlWithoutOffset + " order by c.WorkSpaceId OFFSET "+ offset +" ROWS FETCH NEXT "+ size+" ROWS ONLY";
    }

    public String generateCountQuery(NewWorkspaceFilter filter){

        String sqlWithoutOffset = generateSqlQueryWithoutOffset(filter);
        return "select count(*) from ( " +sqlWithoutOffset + " ) as i";
    }
    private String generateSqlQueryWithoutOffset(NewWorkspaceFilter filter) {

        List<String> fieldsSearchClauses = generateSearchClause(filter).stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> globalSearchClauses = new ArrayList<>(generateGlobalSearchClause(filter));
        return getQuery(fieldsSearchClauses, globalSearchClauses);
    }

    private Collection<? extends String> generateGlobalSearchClause(NewWorkspaceFilter filter) {
        List<String> gsc = new ArrayList<>();
        if(Objects.nonNull(filter.getKeyword()))
        Arrays.asList(globalSearchColumns).forEach(s -> gsc.add(generateLikeClause(s,filter.getKeyword())));
        return gsc;
    }
    private String generateLikeClause(String columnName, String keyword){
        return " lower(c." + columnName + ") like '%" + escape(keyword.toLowerCase()) + "%' ";
    }
    private String generateYearEqualClause(String columnName, String keyword){
        Integer year;
        try{
            year=Integer.parseInt(keyword);
        }catch (Exception e){
            return null;
        }
        return " c." + columnName + " = " + year + " ";
    }
    private String generateGroupByClause(){
        return Arrays.stream(groupByColumns).map(s-> "c."+s).collect(Collectors.joining(","));
    }

    private String generateSelectClause(){
        return Arrays.stream(groupByColumns).map(s-> "c."+s+" as " +s).collect(Collectors.joining(","));
    }

    private Collection<? extends String> generateSearchClause(NewWorkspaceFilter filter) {
        List<String> sc = new ArrayList<>();
        addSearchClause(filter.getWorkspaceId(),filter.getInnerWorkspaceId(),"WorkSpaceId",sc);
        addSearchClause(filter.getWorkspaceName(),filter.getInnerWorkspaceName(),"WorkspaceName",sc);
        addSearchClauseYear(filter.getYear(),filter.getInnerYear(),"UwYear",sc); // year
        addSearchClause(filter.getCedantCode(),filter.getInnerCedantCode(),"CedantCode",sc);
        addSearchClause(filter.getCedantName(),filter.getInnerCedantName(),"CedantName",sc);
        addSearchClause(filter.getCountryName(),filter.getInnerCountryName(),"CountryName",sc);
        return sc;
    }

    private String getFirstNonNull(String s1, String s2)
    { if(Objects.nonNull(s1)) return s1; else return s2; }

    private void addSearchClause(String s1, String s2,String columnName, List<String> sc){
        if(Objects.nonNull(s1) || Objects.nonNull(s2))
            sc.add(generateLikeClause(columnName,getFirstNonNull(s1,s2)));
    }

    private void addSearchClauseYear(String s1, String s2,String columnName, List<String> sc){
        if(Objects.nonNull(s1) || Objects.nonNull(s2))
            sc.add(generateYearEqualClause(columnName,getFirstNonNull(s1,s2)));
    }
    private String escape(String str){
        return str.replaceAll("'", "''");
    }

    ////////////////////////////////////////////////////////////////////////////////////

    public String generateCountQuery(List<ExpertModeFilter> filter,String keyword){

        String sqlWithoutOffset = generateSqlQueryWithoutOffset(filter,keyword);
        return "select count(*) from ( " +sqlWithoutOffset + " ) as i";
    }

    private String generateSqlQueryWithoutOffset(List<ExpertModeFilter> filter, String keyword) {

        List<String> fieldsSearchClauses = generateSearchClause(filter).stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> globalSearchClauses = new ArrayList<>(generateGlobalSearchClause(keyword));
        return getQuery(fieldsSearchClauses, globalSearchClauses);
    }

    private String getQuery(List<String> fieldsSearchClauses, List<String> globalSearchClauses) {
        String globalSearchCondition = String.join(" or ", globalSearchClauses);
        String fieldsSearchCondition = String.join(" and ", fieldsSearchClauses);
        String whereCondition = "";
        if (!fieldsSearchCondition.trim().equals("") && !globalSearchCondition.trim().equals(""))
            whereCondition = " where (" + globalSearchCondition + ") and " + fieldsSearchCondition;
        else if (!globalSearchCondition.trim().equals("")) whereCondition = " where " + globalSearchCondition;
        else if (!fieldsSearchCondition.trim().equals("")) whereCondition = " where " + fieldsSearchCondition;
        String groupByClause = generateGroupByClause();
        String selectClause = generateSelectClause();
        return "select " + selectClause + " from ContractSearchResult c " + whereCondition + " group by " + groupByClause;
    }

    public String generateSqlQuery(List<ExpertModeFilter> filter,String keyword, int offset, int size){
        String sqlWithoutOffset = generateSqlQueryWithoutOffset(filter,keyword);
        return sqlWithoutOffset + " order by c.WorkSpaceId OFFSET "+ offset +" ROWS FETCH NEXT "+ size+" ROWS ONLY";

    }

    private String generateClause(String columnName, String keyword, Operator operator){
        if(columnName.equals("year")) columnName="uwYear";
        switch (operator) {
            case EQUAL:
                return " c." + columnName + " = '" + escape(keyword) + "' ";
            case LIKE:
            default:
                return " lower(c." + columnName + ") like '%" + escape(keyword.toLowerCase()) + "%' ";
        }
    }

    private Collection<? extends String> generateSearchClause(List<ExpertModeFilter> filter) {
        List<String> sc = new ArrayList<>();
        filter.forEach(expertModeFilter -> addSearchClause(expertModeFilter,sc));
        return sc;
    }

    private void addSearchClause(ExpertModeFilter expertModeFilter, List<String> sc){
            sc.add(generateClause(expertModeFilter.getField(),expertModeFilter.getValue(),expertModeFilter.getOperator()));
    }

    private Collection<? extends String> generateGlobalSearchClause(String keyword) {
        List<String> gsc = new ArrayList<>();
        if(Objects.nonNull(keyword))
            if(!keyword.trim().equals(""))
            Arrays.asList(globalSearchColumns).forEach(s -> gsc.add(generateLikeClause(s,keyword)));
        return gsc;
    }
}
