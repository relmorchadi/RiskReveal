package com.scor.rr.importBatch.processing.statistics.rms;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.stat.RREPCurve;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.utils.plt.EPCurve;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 07/09/2015.
 */
public class EPCurveBinaryWriter extends BaseFileWriter implements EPCurveWriter {

    private static final Logger log = LoggerFactory.getLogger(EPCurveBinaryWriter.class);

//    private Function<ELTEPCurve, PLTEPCurve> epCurveFunction = new Function<ELTEPCurve, PLTEPCurve>() {
//        @Override
//        public PLTEPCurve apply(ELTEPCurve input) {
//            return new PLTEPCurve(input);
//        }
//    };

    private Function<RREPCurve, RREPCurve> epCurveFunction = new Function<RREPCurve, RREPCurve>() {
        @Override
        public RREPCurve apply(RREPCurve input) {
            return new RREPCurve(input);
        }
    };

    public EPCurveBinaryWriter() {
    }

    public EPCurveBinaryWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    @Override
    public void write(EPCurve aep, EPCurve oep, EPCurve aepTCE, EPCurve oepTCE, String type, String rp, String fp, String ccy, String model) {
        log.info("writing EPCurve files");

        String eltFileName = getFileName(type, rp, fp, ccy, model, "EPC", getFileExtension());
        File file = makeFullFile(getPrefixDirectory(), eltFileName);
        log.info("writing {}", file);

        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            int size = 18 * (aep.getLossAmountsByEP().size() + oep.getLossAmountsByEP().size() + aepTCE.getLossAmountsByEP().size() + oepTCE.getLossAmountsByEP().size());
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            final short aepCode = Integer.valueOf(0).shortValue();
            final short oepCode = Integer.valueOf(1).shortValue();
            final short aepTCode = Integer.valueOf(10).shortValue();
            final short oepTCode = Integer.valueOf(11).shortValue();
            for (Map.Entry<Double, Double> epEntry : aep.convertLossAmountsByEP().entrySet()) {
                buffer.putShort(aepCode);
                buffer.putDouble(epEntry.getKey());
                buffer.putDouble(epEntry.getValue());
            }
            for (Map.Entry<Double, Double> epEntry : oep.convertLossAmountsByEP().entrySet()) {
                buffer.putShort(oepCode);
                buffer.putDouble(epEntry.getKey());
                buffer.putDouble(epEntry.getValue());
            }
            for (Map.Entry<Double, Double> epEntry : aepTCE.convertLossAmountsByEP().entrySet()) {
                buffer.putShort(aepTCode);
                buffer.putDouble(epEntry.getKey());
                buffer.putDouble(epEntry.getValue());
            }
            for (Map.Entry<Double, Double> epEntry : oepTCE.convertLossAmountsByEP().entrySet()) {
                buffer.putShort(oepTCode);
                buffer.putDouble(epEntry.getKey());
                buffer.putDouble(epEntry.getValue());
            }

            log.info("finished writing: {}", file);
        } catch (IOException e) {
            log.error("Exception: ", e);
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }

    }

    @Override
    public void write(Map<String, EPCurve> epCurvesByType, String type, String rp, String fp, String ccy, String model) {
        write(epCurvesByType.get(StatisticMetric.AEP), epCurvesByType.get(StatisticMetric.OEP), epCurvesByType.get(StatisticMetric.TVAR_AEP), epCurvesByType.get(StatisticMetric.TVAR_OEP), type, rp, fp, ccy, model);
    }

    @Override
    public BinFile writeELTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, String filename) {
        Map<StatisticMetric, List<RREPCurve>> metricToPLTEPCurve = new HashMap<>();

        for (Map.Entry<StatisticMetric, List<RREPCurve>> entry : metricToEPCurve.entrySet()) {
            metricToPLTEPCurve.put(entry.getKey(), Lists.transform(entry.getValue(), epCurveFunction));
        }

        return writePLTEPCurves(metricToPLTEPCurve, filename);
    }


    @Override
    public BinFile writePLTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, String filename) {
        File file = makeFullFile(getPrefixDirectory(), filename);
        return writePLTEPCurves(metricToEPCurve, file);
    }

    public BinFile writePLTEPCurves(Map<StatisticMetric, List<RREPCurve>> metricToEPCurve, File file) {
        List<RREPCurve> curveAEP = metricToEPCurve.get(StatisticMetric.AEP);
        List<RREPCurve> curveOEP = metricToEPCurve.get(StatisticMetric.OEP);
        List<RREPCurve> curveTVarAEP = metricToEPCurve.get(StatisticMetric.TVAR_AEP);
        List<RREPCurve> curveTVarOEP = metricToEPCurve.get(StatisticMetric.TVAR_OEP);
        List<RREPCurve> curveEEF = metricToEPCurve.get(StatisticMetric.EEF);
        List<RREPCurve> curveCEP = metricToEPCurve.get(StatisticMetric.CEP);

        int nCurveAEP = curveAEP == null ? 0 : curveAEP.size();
        int nCurveOEP = curveOEP == null ? 0 : curveOEP.size();
        int nCurveTVarAEP = curveTVarAEP == null ? 0 : curveTVarAEP.size();
        int nCurveTVarOEP = curveTVarOEP == null ? 0 : curveTVarOEP.size();
        int nCurveEEF = curveEEF == null ? 0 : curveEEF.size();
        int nCurveCEP = curveCEP == null ? 0 : curveCEP.size();

        log.info("Curve size: curveAEP {}, curveOEP {}, curveTVarAEP {}, curveTVarOEP {}, curveEEF {}, curveCEP {}", nCurveAEP, nCurveOEP, nCurveTVarAEP, nCurveTVarOEP, nCurveEEF, nCurveCEP);

        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            log.info("EPCurve file: {}", file);
            out = new RandomAccessFile(file, "rw").getChannel();
            int size = 18 * (nCurveAEP + nCurveOEP + nCurveEEF + nCurveCEP + nCurveTVarAEP + nCurveTVarOEP);
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            final short codeAEP = Integer.valueOf(0).shortValue();
            final short codeOEP = Integer.valueOf(1).shortValue();
            final short codeAEPTCE = Integer.valueOf(10).shortValue();
            final short codeOEPTCE = Integer.valueOf(11).shortValue();
            final short codeEEF = Integer.valueOf(20).shortValue();
            final short codeCEP = Integer.valueOf(21).shortValue();
            if (curveAEP != null) {
                for (RREPCurve pltepCurve : curveAEP) {
                    buffer.putShort(codeAEP);
                    buffer.putDouble(pltepCurve.getExceedanceProbability());
                    buffer.putDouble(pltepCurve.getLossAmount());
                }
            }
            if (curveOEP != null) {
                for (RREPCurve pltepCurve : curveOEP) {
                    buffer.putShort(codeOEP);
                    buffer.putDouble(pltepCurve.getExceedanceProbability());
                    buffer.putDouble(pltepCurve.getLossAmount());
                }
            }
            if (curveTVarAEP != null) {
                for (RREPCurve pltepCurve : curveTVarAEP) {
                    buffer.putShort(codeAEPTCE);
                    buffer.putDouble(pltepCurve.getExceedanceProbability());
                    buffer.putDouble(pltepCurve.getLossAmount());
                }
            }
            if (curveTVarOEP != null) {
                for (RREPCurve pltepCurve : curveTVarOEP) {
                    buffer.putShort(codeOEPTCE);
                    buffer.putDouble(pltepCurve.getExceedanceProbability());
                    buffer.putDouble(pltepCurve.getLossAmount());
                }
            }
            if (curveEEF != null) {
                for (RREPCurve pltepCurve : curveEEF) {
                    buffer.putShort(codeEEF);
                    buffer.putDouble(pltepCurve.getExceedanceProbability());
                    buffer.putDouble(pltepCurve.getLossAmount());
                }
            }
            if (curveCEP != null) {
                for (RREPCurve pltepCurve : curveCEP) {
                    buffer.putShort(codeCEP);
                    buffer.putDouble(pltepCurve.getExceedanceProbability());
                    buffer.putDouble(pltepCurve.getLossAmount());
                }
            }
        } catch (IOException e) {
            log.error("Exception: ", e);
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
            return new BinFile(file);
        }

    }

}
