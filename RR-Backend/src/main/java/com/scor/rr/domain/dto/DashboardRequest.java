package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class DashboardRequest {

    String carStatus;
    int Entity ;
    long UserDashboardWidgetId;
    int  PageNumber;
    int  PageSize;
    String selectionList;
    boolean sortSelectedFirst;
    String sortSelectedAction;
    boolean filterByAnalyst;

}
