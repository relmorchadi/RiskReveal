/*
package com.scor.rr.importBatch.utils;

import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.domain.ImportFilePLTData;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by u004602 on 09/04/2018.
 *//*

public class ImportFileUtils {
    public static List<PLTLossData> convertToScorPLTData(List<ImportFilePLTData> importFilePLTDataList, int year) {
        if (importFilePLTDataList != null && !importFilePLTDataList.isEmpty()) {
            List<PLTLossData> scorPLTDataList = new ArrayList<>(importFilePLTDataList.size());
            for (ImportFilePLTData importFilePLTData : importFilePLTDataList) {
                scorPLTDataList.add(new PLTLossData(importFilePLTData.getEventId(),
                        Date.UTC(year-1900, importFilePLTData.getMonth() - 1, importFilePLTData.getDay(), 0, 0, 0),
                        importFilePLTData.getYear(),
                        importFilePLTData.getRepetition() + 1,
                        importFilePLTData.getMaxExposure(),
                        importFilePLTData.getValue()));
            }
            return scorPLTDataList;
        }
        return null;
    }
}
*/
