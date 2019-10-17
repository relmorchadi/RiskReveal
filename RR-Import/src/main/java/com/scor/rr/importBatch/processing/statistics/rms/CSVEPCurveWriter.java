package com.scor.rr.importBatch.processing.statistics.rms;

import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.stat.RREPCurve;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.utils.plt.EPCurve;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 20/04/2015.
 */
public class CSVEPCurveWriter extends BaseFileWriter implements EPCurveWriter {
    private static final Logger log= LoggerFactory.getLogger(CSVEPCurveWriter.class);

    public CSVEPCurveWriter() {
    }

    public CSVEPCurveWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

//    @Override
    public void write(EPCurve epCurve, String type, String rp, String fp, String ccy){
        log.info("writing EPCurve files");
        Path eltOutDir = getDir();
        final Path fullPath = getIhubPath().resolve(eltOutDir);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths "+fullPath, e);
        }
        String eltFileName = getFileName(type, rp, fp, ccy, "MODEL", "EPC", getFileExtension());
        log.info("writing "+eltOutDir+"/"+eltFileName);
        BufferedWriter bw = null;
        try {
            bw = Files.newBufferedWriter(fullPath.resolve(eltFileName), Charset.defaultCharset());
            int cpt = 0;
            for (Map.Entry<Double, Double> entry : epCurve.convertLossAmountsByEP().entrySet()) {
                cpt++;
                StringBuilder sb = new StringBuilder();
                sb.append(entry.getKey()).append(";")
                        .append(entry.getValue());
                bw.write(sb.toString());
                bw.newLine();
                if(cpt==50){
                    bw.flush();
                    cpt=0;
                }
            }
            bw.flush();
        } catch (IOException e) {
            log.error("Exception: ",e);
        }finally {
            IOUtils.closeQuietly(bw);
        }
    }

    @Override
    public void write(EPCurve aep, EPCurve oep, EPCurve aepTCE, EPCurve oepTCE, String type, String rp, String fp, String ccy, String model) {

    }

    @Override
    public void write(Map<String, EPCurve> epCurvesByType, String type, String rp, String fp, String ccy, String model) {

    }

    @Override
    public BinFile writeELTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, String name) { // ELTEPCurve
        return null;
    }

    @Override
    public BinFile writePLTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, String name) { // PLTEPCurve
        return null;
    }

    @Override
    public BinFile writePLTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, File file) { // PLTEPCurve
        return null;
    }
}
