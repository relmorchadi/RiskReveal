package com.scor.rr.importBatch.processing.domain;

import com.scor.rr.domain.entities.cat.CATRequest;

/**
 * Created by U002629 on 20/05/2015.
 */
public class FWDataImpl implements FWData {

    private CATRequest myRequest;

    @Override
    public void setMyRequest(CATRequest myRequest) {
        this.myRequest = myRequest;
    }

    @Override
    public CATRequest myRequest() {
        return myRequest;
    }
}
