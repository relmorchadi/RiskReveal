package com.scor.rr.domain.dto;

import com.scor.rr.domain.enums.ComparisonOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldFilter {
    ComparisonOperator comparisonOperator;
    Object value;
    String key;
    FilterType filterType;
}
