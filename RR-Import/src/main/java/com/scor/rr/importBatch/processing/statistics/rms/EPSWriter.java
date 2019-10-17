package com.scor.rr.importBatch.processing.statistics.rms;

import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.stat.RRSummaryStatistic;
import com.scor.rr.domain.utils.plt.SummaryStatistics;

import java.io.File;

/**
 * Created by U002629 on 22/09/2015.
 */
public interface EPSWriter {
    void write(SummaryStatistics summaryStatistics, String type, String rp, String fp, String ccy, String model);

    BinFile writeELTSummaryStatistics(RRSummaryStatistic summaryStatistics, String filename); // ELTEPSummaryStatistic

    BinFile writePLTSummaryStatistics(RRSummaryStatistic summaryStatistics, String filename); // PLTSummaryStatistic

    BinFile writePLTSummaryStatistics(RRSummaryStatistic summaryStatistics, File file); // PLTSummaryStatistic
}
