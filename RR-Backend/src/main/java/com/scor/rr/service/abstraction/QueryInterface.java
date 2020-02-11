package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.ExpertModeFilter;
import com.scor.rr.domain.dto.ExpertModeSort;
import com.scor.rr.domain.enums.Operator;

import java.util.Collection;
import java.util.List;

public interface QueryInterface {

    String generateSqlQuery(List<ExpertModeFilter> filter, List<ExpertModeSort> sort, String keyword, int offset, int size);

    String generateSqlQueryWithoutOffset(List<ExpertModeFilter> filter, List<ExpertModeSort> sort, String keyword);

    Collection<? extends String> generateSearchClause(List<ExpertModeFilter> filter);

    void addSearchClause(ExpertModeFilter expertModeFilter, List<String> sc);

    String generateClause(String columnName, String keyword, Operator operator);

    String getQuery(List<String> fieldsSearchClauses, List<String> globalSearchClauses, List<ExpertModeSort> sort);

    Collection<? extends String> generateGlobalSearchClause(String keyword);

    String generateCountQuery(List<ExpertModeFilter> filter,String keyword);
    }
