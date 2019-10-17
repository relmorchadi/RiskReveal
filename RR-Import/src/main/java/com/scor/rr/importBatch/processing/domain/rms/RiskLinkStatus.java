package com.scor.rr.importBatch.processing.domain.rms;

/**
 * Created by U002629 on 05/03/2015.
 */
public enum RiskLinkStatus {
            INVALID(0	,   "Invalid"),
            NOT_ANALYZED(100,	"Not analyzed"),
            ERROR_IN_ANALYZING(101,	"Error in analyzing"),
            ANALYZED(102,	"Analyzed"),
            ERROR_IN_PERSISTING(103,	"Error in persisting"),
            RUNNING_IFG(104,	"Running IFG"),
            FINISHED_IFG(105,	"Finished IFG"),
            RUNNING_EVENT_DETERMINATION(106,	"Running event determination"),
            FINISHED_EVENT_DETERMINATION(107,	"Finished event determination"),
            WRITING_LOSSES(108,	"Writing losses"),
            FINISHED_WRITING_LOSSES(109,	"Finished writing losses"),
            RUNNING_EP_ENGINE(110,	"Running EP engine");

    Integer statusCode;
    String description;

    RiskLinkStatus(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
