package com.scor.rr.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


public class DashboardChartData {

    private String assignedAnalyst;

    private String carStatus;

    private int numberCarsPerAnalyst;

    public DashboardChartData() {
    }
}
