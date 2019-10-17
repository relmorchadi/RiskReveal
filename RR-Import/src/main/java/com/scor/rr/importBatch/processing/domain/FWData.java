package com.scor.rr.importBatch.processing.domain;

import com.scor.rr.domain.entities.cat.CATRequest;

/**
 * Created by U002629 on 20/05/2015.
 */
public interface FWData {
    void setMyRequest(CATRequest myRequest);

    // CATAnalysisRequest originally
    CATRequest myRequest();
}
