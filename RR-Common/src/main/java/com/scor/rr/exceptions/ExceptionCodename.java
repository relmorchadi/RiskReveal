package com.scor.rr.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCodename {
    /*
        This section is delegated to WorkSpace Exceptions
    * */
    WORKSPACE_NOT_FOUND("WORKSPACE NOT FOUND"),
    /*
        This section is delegated to Projetc Exceptions
    * */

    PROJECT_NOT_FOUND("PROJECT NOT FOUND"),

    /*
    *  Generals Exceptions
    * */
    UNKNOWN("ERROR.UNKNOWN"),
    PLT_NOT_FOUND("PLT NOT FOUND"),
    MODEL_ANALYSIS_NOT_FOUND("MODEL ANALYSIS NOT FOUND"),
    NODE_NOT_FOUND("NODE NOT FOUND"),
    TYPE_NOT_FOUND("ADJUSTMENT TYPE NOT FOUND"),
    BASIS_NOT_FOUND("ADJUSTMENT BASIS NOT FOUND"),
    STATE_NOT_FOUND("ADJUSTMENT STATE NOT FOUND"),
    THREAD_NOT_FOUND("ADJUSTMENT THREAD NOT FOUND"),
    CATEGORY_NOT_FOUND("ADJUSTMENT CATEGORY NOT FOUND"),
    PARAMETER_NOT_FOUND("ADJUSTMENT PARAMETER NOT FOUND"),
    BIN_FILE_EXCEPTION("BIN FILE NOT SAVED"),
    NODE_PROCESSING_NOT_FOUND("NODE PROCESSING NOT FOUND"),
    ORDER_EXIST("ORDER EXIST"),
    PLT_TYPE_NOT_CORRECT("PLT Type Not correct"),
    LMF_MUST_BE_POSITIVE("LMF MUST BE POSITIVE"),

    /*
        This section is delegated to PLT File Exceptions
     */
    EVENT_DATE_FORMAT_WRONG("Event Date format wrong"),
    NUMBER_FORMAT_WRONG("Number Format wrong"),
    PLT_DATA_NULL("PLT Data is null"),
    PLT_FILE_CORRUPTED("PLT file was corrupted or truncated"),
    PLT_FILE_EXT_NOT_SUPPORTED("PLT file extension not supported"),
    PLT_FILE_NOT_FOUND("PLT file not found"),
    PLT_FILE_WRITE_ERROR("Error while writing to file"),



    /* this section is for DashboardExceptions*/

    USER_NOT_FOUND_EXCEPTION("User not found"),
    COLUMN_NOT_FOUND("Column not found"),
    DASHBOARD_WIDGET_NOT_FOUND("Widget not found"),
    PARAM_NOT_FOUND("No param found"),
    INVALID_WIDGET_TYPE("Invalid widget type"),
    DASHBOARD_DELETE_IMPOSSIBLE("Impossible delete"),
    DASHBOARD_NOT_FOUND("Dashboard not found"),




    /*
        This section is delegated to Inuring Exceptions
     */
    INURING_INVALID_NUMBER_PLTS("Invalid number of PLTS"),
    CONTRIBUTION_NOT_FOUND_EXCEPTION("contribution not found"),
    INURING_FINAL_ATTACHED_PLT_NOT_FOUND("plt not found"),
    EXCHANGE_RATE_TYPE_NOT_FOUND("ExchangeRate Type not found"),
    INURING_STRUCTURE_NOT_VALID("Inuring structure not valid"),
    INURING_CONTRACT_LAYER_PARAM_NOT_FOUND("Inuring contractLayer Param not found"),
    INURING_GROUPED_PLT_ALREADY_EXISTS("Invalid name of PLT"),
    INURING_PLT_NOT_FOUND("Inuring Plt Not found"),
    INURING_PACKAGE_NOT_FOUND("Inuring Package not found"),
    CURRENCY_NOT_FOUND("Currency exchange rate not found "),
    CONTRACT_TYPE_NOT_FOUND("Contract type not found"),
    INURING_NOTE_NOT_FOUND("Inuring Note not found"),
    INURING_INPUT_NODE_NOT_FOUND("Inuring Input Node not found"),
    DELETE_LAST_LAYER_IMPOSSIBLE("Impossible delete of last layer"),
    INURING_CONTRACT_NODE_NOT_FOUND("Inuring Contract Node not found"),
    INURING_ILLEGAL_REQUEST("Illegal request"),
    INURING_CONTRACT_LAYER_NOT_FOUND("Inuring Contract Layer not found"),
    INURING_FILTER_CRITERIA_NOT_FOUND("Inuring Filter Criteria not found"),
    INURING_SINGLE_LAYER_CONTRACT_NODE("Inuring Contract Node Accepts One Layer Only"),
    INURING_FINAL_NODE_NOT_FOUND("Inuring Final Node not found"),
    INURING_EDGE_NOT_FOUND("Inuring Edge not found"),
    ILLOGICAL_EDGE_CREATION("Illogical edge creation"),
    UNREASONABLE_CHANGE("Unreasonable change detected"),
    INURING_ILLEGAL_MODIFICATION("Illegal modification request"),
    INURING_NODE_NOT_FOUND("Inuring Node not found"),

    ;
    private final String exception;

    ExceptionCodename(final String exception) {
        this.exception = exception;
    }

    @JsonValue
    public String exception() {
        return this.exception;
    }
}
