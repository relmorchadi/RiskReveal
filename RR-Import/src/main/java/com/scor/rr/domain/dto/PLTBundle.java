package com.scor.rr.domain.dto;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.LossDataHeaderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PLTBundle {

    private Boolean pltError = Boolean.FALSE;

    private PltHeaderEntity header;

    private LossDataHeaderEntity lossDataHeaderEntity;

    private String errorMessage;
}
