package com.scor.rr.importBatch.processing.adjustment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by u004119 on 17/05/2016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultAdjustmentRequest {

    private String projectId;

    private List<String> structureIds;

    private String scorPLTHeaderId;

    private String analysisId;

    private String analysisName;

    private String rdmName;

    private Long rdmId;

    private Integer targetRapId;

    public DefaultAdjustmentRequest(String scorPLTHeaderId) {
        this.scorPLTHeaderId = scorPLTHeaderId;
    }

}
