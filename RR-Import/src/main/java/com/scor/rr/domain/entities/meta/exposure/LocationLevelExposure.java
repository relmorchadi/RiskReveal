package com.scor.rr.domain.entities.meta.exposure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by u004602 on 20/04/2017.
 */
@Component(value = "locationLevelExposure")
public class LocationLevelExposure {

    @Value(value = "${rms.ds.dbname}")
    private String dbName;

    public static final String EXTRACT_PORT = "RR_RL_GetEdmExtractPort";
    public static final String EXTRACT_PORT_ACCOUNT = "RR_RL_GetEdmExtractPortAccount";
    public static final String EXTRACT_PORT_ACCOUNT_POL = "RR_RL_GetEdmExtractPortAccountPol";
    public static final String EXTRACT_PORT_ACCOUNT_POL_CVG = "RR_RL_GetEdmExtractPortAccountPolCvg";
    public static final String EXTRACT_PORT_ACCOUNT_LOC = "RR_RL_GetEdmExtractPortAccountLoc";
    public static final String EXTRACT_PORT_ACCOUNT_LOC_CVG = "RR_RL_GetEdmExtractPortAccountLocCvg";

//    public LocationLevelExposure(String dbName) {
//        this.dbName = dbName;
//    }

    public List<String> getAllExtractNames() {
        return Arrays.asList(EXTRACT_PORT,
                EXTRACT_PORT_ACCOUNT,
                EXTRACT_PORT_ACCOUNT_POL,
                EXTRACT_PORT_ACCOUNT_POL_CVG,
                EXTRACT_PORT_ACCOUNT_LOC,
                EXTRACT_PORT_ACCOUNT_LOC_CVG);
    }

    public String getQuery(String schema) {
        switch (schema) {
            case EXTRACT_PORT:
                return "execute " + dbName + ".[dbo].[RR_RL_GetEdmExtractPort] @Edm_id=:Edm_id, @Edm_name=:Edm_name, @PortfolioID_RMS=:PortfolioID_RMS, @PortfolioID_RR=:PortfolioID_RR, @ProjectID_RR=:ProjectID_RR";
            case EXTRACT_PORT_ACCOUNT:
            case EXTRACT_PORT_ACCOUNT_LOC:
                return "execute " + dbName + ".[dbo].[" + schema + "] @Edm_id=:Edm_id, @Edm_name=:Edm_name, @PortfolioID_RMS=:PortfolioID_RMS, @PortfolioID_RR=:PortfolioID_RR";
            case EXTRACT_PORT_ACCOUNT_POL:
            case EXTRACT_PORT_ACCOUNT_POL_CVG:
            case EXTRACT_PORT_ACCOUNT_LOC_CVG:
                return "execute " + dbName + ".[dbo].[" + schema + "] @Edm_id=:Edm_id, @Edm_name=:Edm_name, @PortfolioID_RMS=:PortfolioID_RMS, @PortfolioID_RR=:PortfolioID_RR, @ConformedCcy=:ConformedCcy";
            default:
                return null;
        }
    }

    public String getExtractFileType(String schema) {
        switch (schema) {
            case EXTRACT_PORT:
                return "PORT";
            case EXTRACT_PORT_ACCOUNT:
                return "ACC";
            case EXTRACT_PORT_ACCOUNT_LOC:
                return "LOC";
            case EXTRACT_PORT_ACCOUNT_POL:
                return "POL";
            case EXTRACT_PORT_ACCOUNT_POL_CVG:
                return "POLCVG";
            case EXTRACT_PORT_ACCOUNT_LOC_CVG:
                return "LOCCVG";
            default:
                return null;
        }
    }
}
