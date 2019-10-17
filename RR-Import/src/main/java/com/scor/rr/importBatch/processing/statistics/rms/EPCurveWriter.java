package com.scor.rr.importBatch.processing.statistics.rms;

import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.stat.RREPCurve;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.utils.plt.EPCurve;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * Created by U002629 on 20/04/2015.
 */
public interface EPCurveWriter {
    void write(EPCurve aep, EPCurve oep, EPCurve aepTCE, EPCurve oepTCE, String type, String rp, String fp, String ccy, String model);

    void write(Map<String, EPCurve> epCurvesByType, String type, String rp, String fp, String ccy, String model);

    BinFile writeELTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, String name); // ELT

    BinFile writePLTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, String name); // PLT

    BinFile writePLTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, File file);
}
