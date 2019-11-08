package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertModeFilterRequest {
    List<ExpertModeFilter> filter;
    List<ExpertModeSort> sort;
    String keyword;
    Integer offset;
    Integer size;
    Boolean fromSavedSearch;
}
