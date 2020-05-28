package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class TableConfigurationRequest {
    String config;
    String tableContext;
    String tableName;
}
