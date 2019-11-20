package com.scor.rr.domain.enums;

/**
 * Location Level Exposure Enum
 *
 * @author HADDINI Zakariyae
 */
public enum LocationLevelExposure {

    EXTRACT_PORT("RR_RL_GetEdmExtractPort"),
    EXTRACT_PORT_ACCOUNT("RR_RL_GetEdmExtractPortAccount"),
    EXTRACT_PORT_ACCOUNT_POL("RR_RL_GetEdmExtractPortAccountPol"),
    EXTRACT_PORT_ACCOUNT_POL_CVG("RR_RL_GetEdmExtractPortAccountPolCvg"),
    EXTRACT_PORT_ACCOUNT_LOC("RR_RL_GetEdmExtractPortAccountLoc"),
    EXTRACT_PORT_ACCOUNT_LOC_CVG("RR_RL_GetEdmExtractPortAccountLocCvg");

    private String code;

    LocationLevelExposure(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}