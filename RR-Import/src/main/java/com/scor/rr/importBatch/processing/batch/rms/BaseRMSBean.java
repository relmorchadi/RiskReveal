package com.scor.rr.importBatch.processing.batch.rms;

import com.scor.rr.importBatch.processing.batch.BaseBatchBean;

/**
 * Created by U002629 on 07/04/2015.
 */
public interface BaseRMSBean extends BaseBatchBean {
    String RDM = "RDM";
    String EDM = "EDM";
    String PORTFOLIO = "PORTFOLIO";
    String ELT_FP = "ELT_FP";
    String STATS_FP = "STATS_FP";
    String LIABILITY_CCY = "LIABILITY_CCY";
    String CARID = "CARID";

    void setEdm(String edm);

    void setRdm(String rdm);

    void init();
}
