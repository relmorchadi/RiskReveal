package com.scor.rr.domain.dto.grid;

import com.scor.rr.domain.enums.AggFunc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnVO implements Serializable {
    private String id;
    private String displayName;
    private String field;
    private AggFunc aggFunc;
}
