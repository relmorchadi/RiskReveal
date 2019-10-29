package com.scor.rr.util;

import com.scor.rr.domain.enums.*;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PathUtils {
    public static String makeEpCurveFileName(
            String financialPersp,
            Date creationDate,
            String fileExtension) {
        // @TODO Review the required params for this
        return "EP_CURVE"+String.valueOf(creationDate.getTime()).concat(financialPersp).concat(".").concat(fileExtension);
    }

    public static String makeEpSummaryStatFileName(
            String financialPersp,
            Date creationDate,
            String fileExtension) {
        // @TODO Review the required params for this
        return "SUMMARY_STAT"+String.valueOf(creationDate.getTime()).concat(financialPersp).concat(".").concat(fileExtension);
    }

    public static String makePLTFileName(
            String financialPersp,
            Date creationDate,
            String fileExtension) {
        // @TODO Review the required params for this
        return "PLT"+String.valueOf(creationDate.getTime()).concat(financialPersp).concat(".").concat(fileExtension);
    }
}
