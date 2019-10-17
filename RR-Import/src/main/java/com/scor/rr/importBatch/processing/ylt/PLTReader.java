package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.processing.treaty.loss.ScorPLTLossDataHeader;
import com.scor.rr.service.params.PLTFileParam;

import java.util.List;

/**
 * Created by U002629 on 19/02/2015.
 */
public interface PLTReader {
    PLTLoss readPLTFile(PLTFileParam pltFileParam);

    ScorPLTLossDataHeader readScorPLTLossDataHeader(ScorPLTHeader scorPLTHeader);

    List<PLTLossData> readScorPLTLossData(ScorPLTHeader scorPLTHeader);

    List<PLTLossData> readPLTLossDataV2(BinFile binFile);

}
