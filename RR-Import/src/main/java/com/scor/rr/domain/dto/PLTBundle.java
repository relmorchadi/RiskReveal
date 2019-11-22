package com.scor.rr.domain.dto;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.model.LossDataHeader;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PLTBundle {

    private Boolean pltError = Boolean.FALSE;

    private PltHeaderEntity header;

    private LossDataHeader lossDataHeader;

    private String errorMessage;
}
