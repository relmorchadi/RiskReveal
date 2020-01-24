package com.scor.rr.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalDataSourceDto {
    private Boolean isSummary;
    private List<RLDataSourcesDto> content;

}
