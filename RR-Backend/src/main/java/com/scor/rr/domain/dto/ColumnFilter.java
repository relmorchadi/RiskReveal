package com.scor.rr.domain.dto;

import com.scor.rr.domain.enums.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnFilter {
    String key;
    FilterType filterType;
    Operator operator;
    List<FieldFilter> conditions;
}
