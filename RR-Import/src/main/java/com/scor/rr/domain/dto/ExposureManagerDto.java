package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureManagerDto {

    private ExposureManagerData frozenRow;
    private List<ExposureManagerData> data;
    private List<String> columns;
}
