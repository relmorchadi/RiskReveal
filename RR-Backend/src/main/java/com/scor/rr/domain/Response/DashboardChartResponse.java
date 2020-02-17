package com.scor.rr.domain.Response;

import lombok.Data;

@Data
public class DashboardChartResponse {
    private String assignedAnalyst;
    private int newCars;
    private int inProgress;
    private int superseded;
    private int completed;
    private int cancelled;
    private int priced;

    public DashboardChartResponse() {
    }
}
