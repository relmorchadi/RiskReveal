package com.scor.rr.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class DashboardRequest {
    DashBoardFilter filterConfig;
    List<SortConfig> sortConfig;
    Integer pageNumber;
    Integer size;
}
