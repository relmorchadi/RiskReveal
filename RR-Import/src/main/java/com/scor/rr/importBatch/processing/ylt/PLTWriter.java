package com.scor.rr.importBatch.processing.ylt;


import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.processing.treaty.loss.ScorPLTLossDataHeader;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by U002629 on 19/02/2015.
 */
public interface PLTWriter {
    Boolean writeHeader();

    Boolean batchWrite();

    void writeLossData(PLTLoss lossData, Path pltOutDir, String regionPeril, String model, String identifier);
    void writeLossData(PLTLoss lossData, String regionPeril, String model, String identifier);

    BinFile writeScorPLTLossData(ScorPLTLossDataHeader scorPLTLossDataHeader, String filename);

    BinFile writeScorPLTLossData(List<PLTLossData> sortedList, String filename);

    void writePLTLossDataV2(List<PLTLossData> list, File file);

    void writePLTLossDataNonRMS(List<PLTLossData> list, File file);

}
