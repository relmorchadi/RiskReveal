package com.scor.rr.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SearchQuery {


    String getSelectQuery(String schema, String table) {
        return "SELECT * FROM " + schema + "." + table;
    }

    String appendWhereStatement(String query,String[] conditions) {
        return "";
    }

    String getRecentWithOffset(String table, Integer userId,Integer size, Integer offset) {
        return "SELECT * FROM " + table + " rws where rws.userId = " + userId + " order by rws.lastOpened desc OFFSET " + offset + " ROWS FETCH NEXT " + size + " ROWS ONLY";
    }
}
