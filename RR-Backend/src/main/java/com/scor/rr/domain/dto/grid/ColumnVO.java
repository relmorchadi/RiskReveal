package com.scor.rr.domain.dto.grid;

import lombok.Data;

import java.io.Serializable;

@Data
public class ColumnVO implements Serializable {
    private String id;
    private String displayName;
    private String field;
    private String aggFunc;
}
