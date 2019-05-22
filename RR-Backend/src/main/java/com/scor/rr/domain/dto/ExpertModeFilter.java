package com.scor.rr.domain.dto;

import com.scor.rr.domain.enums.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertModeFilter {

    String field;
    String value;
    Operator operator;
}
