/*
package com.scor.rr.importBatch.processing.nonRMSbatch.workflow.step;

import com.scor.almf.cdm.repository.reference.CurrencyRepository;
import com.scor.almf.cdm.repository.reference.ExchangeRateRepository;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.bean.BaseNonRMSBean;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.workflow.bundle.PLTBundleNonRMS;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.workflow.bundle.TransformationBundleNonRMS;
import com.scor.almf.ihub.treaty.processing.nonRMSbatch.workflow.bundle.TransformationPackageNonRMS;
import com.scor.almf.ihub.treaty.processing.treaty.loss.PLTLossData;
import com.scor.almf.ihub.treaty.processing.ylt.meta.PLTPublishStatus;
import com.scor.almf.ihub.treaty.processing.ylt.meta.XLTOT;
import com.scor.almf.treaty.cdm.domain.dss.AnalysisFinancialPerspective;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ihub.LossDataFile;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ihub.RRFinancialPerspective;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ihub.RRLossTable;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ihub.RRLossTableType;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RREPCurve;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RRStatisticHeader;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RRSummaryStatistic;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.tracking.ProjectImportAssetLog;
import com.scor.almf.treaty.cdm.domain.plt.ScorPLTHeader;
import com.scor.almf.treaty.cdm.domain.plt.meta.*;
import com.scor.almf.treaty.cdm.domain.reference.meta.BinFile;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.ProjectImportAssetLogRepository;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.RRLossTableRepository;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.RRStatisticHeaderRepository;
import com.scor.almf.treaty.cdm.repository.plt.ScorPLTHeaderRepository;
import com.scor.almf.treaty.cdm.tools.sequence.MongoDBSequence;
import com.scor.almf.treaty.dao.DAOService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

*/
/**
 * Created by U005342 on 16/07/2018.
 *//*

public class PLTConformer extends BaseNonRMSBean {

    private static final Logger log = LoggerFactory.getLogger(PLTConformer.class);

    private static boolean DBG = true;

    @Autowired
    private MongoDBSequence mongoDBSequence;

    @Autowired
    private ScorPLTHeaderRepository scorPLTHeaderRepository;

    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;

    @Autowired
    private RRLossTableRepository rrLossTableRepository;

    @Autowired
    private DAOService daoService;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    private TransformationPackageNonRMS transformationPackage;

    public Boolean conformPLT() {

        log.debug("Start CONFORM_PLT");
        Date startDate = new Date();

        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 4 CONFORM_PLT for this analysis in loop of many analysis :
            // only valid sourceResults after step 1 are converted to bundles
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(4);
            projectImportAssetLogA.setStepName(StepNonRMS.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------
            // TODO logic here

            for (PLTBundleNonRMS pltBundle : bundle.getPltBundles()) {
                makeConformedPurePLTHeader(pltBundle, bundle);
            }

            // finis step 3 CONFORM_PLT for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 4 : CONFORM_PLT for RRAnalysis : {}", bundle.getRrAnalysis().getId());
        }
        return true;
    }

    public ScorPLTHeader makeConformedPurePLTHeader(PLTBundleNonRMS pltBundle, TransformationBundleNonRMS bundle) {

        ScorPLTHeader originalPLT = pltBundle.getHeader();
        ScorPLTHeader conformedPLT = new ScorPLTHeader();

        // TODO a) source ccy --> target ccy
        conformedPLT.setId(originalPLT.getId());
        conformedPLT.setPltLossDataFile(null);
        conformedPLT.setPltStatisticList(null);
        conformedPLT.setPltGrouping(PLTGrouping.UnGrouped);
        conformedPLT.setPltInuring(PLTInuring.None);
        conformedPLT.setPltStatus(PLTStatus.Pending);
        conformedPLT.setInuringPackageDefinition(null);
        // TODO how ???
        conformedPLT.setPltSimulationPeriods(bundle.getSourceResult().getPltSimulationPeriods());
        conformedPLT.setPltType(PLTType.Pure);
        conformedPLT.setProject(originalPLT.getProject());
        conformedPLT.setRrRepresentationDatasetId(transformationPackage.getRrRepresentationDatasetId());
        conformedPLT.setRrAnalysisId(bundle.getRrAnalysis().getId());
        conformedPLT.setRrLossTableId(originalPLT.getRrLossTableId());
        Currency currency = currencyRepository.findByCode(bundle.getRrAnalysis().getTargetCurrency());
        conformedPLT.setCurrency(currency);
        conformedPLT.setTargetRap(originalPLT.getTargetRap());
        conformedPLT.setRegionPeril(originalPLT.getRegionPeril());
        conformedPLT.setFinancialPerspective(originalPLT.getFinancialPerspective());
        conformedPLT.setAdjustmentStructure(null);
        conformedPLT.setCatAnalysisDefinition(null);
        conformedPLT.setSourcePLTHeader(null);
        conformedPLT.setSystemShortName(originalPLT.getSystemShortName());
        conformedPLT.setUserShortName(originalPLT.getUserShortName());
        conformedPLT.setTags(null);
        conformedPLT.setCreatedDate(new Date());
        conformedPLT.setxActPublicationDate(null);
        conformedPLT.setxActUsed(false);
        conformedPLT.setxActAvailable(false);
        conformedPLT.setGeneratedFromDefaultAdjustement(false);
        conformedPLT.setUserSelectedGrain(bundle.getRrAnalysis().getGrain());
        conformedPLT.setExportedDPM(false);

        conformedPLT.setGeoCode(bundle.getRrAnalysis().getGeoCode());
        conformedPLT.setGeoDescription(bundle.getRrAnalysis().getGeoCode());
        conformedPLT.setPerilCode(bundle.getRrAnalysis().getPeril());
        // TODO how ???
//            scorPLTHeader.setEngineType(bundle.getRmsAnalysis().getEngineType());
        conformedPLT.setInstanceId(bundle.getInstanceId());
        conformedPLT.setImportSequence(getImportSequence());
        conformedPLT.setSourceLossModelingBasis(originalPLT.getSourceLossModelingBasis());
        conformedPLT.setUdName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1.T0");
        conformedPLT.setPltName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1");


        // TODO b) apply proportion and unit multiplier
        // TODO c) calculate EPC, EPS for conformed PLT
        List<RRStatisticHeader> confRrStatisticHeaders = new ArrayList<>();
        Map<AnalysisFinancialPerspective, Map<StatisticMetric, List<RREPCurve>>> fp2Metric2EPCurves = new HashMap<>();
        Map<AnalysisFinancialPerspective, RRSummaryStatistic> fp2SumStat = new HashMap<>(); // ELTEPSummaryStatistic

        double proportion = bundle.getSourceResult().getProportion() == null ? 1: bundle.getSourceResult().getProportion().doubleValue() / 100;
        double multiplier = bundle.getSourceResult().getUnitMultiplier() == null ? 1: bundle.getSourceResult().getUnitMultiplier().doubleValue();
        log.info("Conforming EP curves and sum stats for conformedPLT {}, proportion {}, multiplier {}", conformedPLT.getId(), proportion, multiplier);

        RRSummaryStatistic confSumStat = conformSummaryStatistic(originalPLT.getPltStatisticList().get(0).getStatisticData().getSummaryStatistic(), proportion, multiplier, bundle.getRrAnalysis().getExchangeRate()); // ELTEPSummaryStatistic

        Map<StatisticMetric, List<RREPCurve>> metricToEPCurve = new HashMap<>();

        for (RRStatisticHeader rrStatisticHeader : originalPLT.getPltStatisticList()) {
            RRFinancialPerspective fp = rrStatisticHeader.getFinancialPerspective();
            StatisticMetric metric = rrStatisticHeader.getStatisticData().getStatisticMetric();
            List<RREPCurve> confEPCurves = conformELTEPCurves(rrStatisticHeader.getStatisticData().getEpCurves(), proportion, multiplier, bundle.getRrAnalysis().getExchangeRate());

            RRStatisticHeader confRrStatisticHeader = new RRStatisticHeader();
            mongoDBSequence.nextSequenceId(confRrStatisticHeader);
            confRrStatisticHeader.setProjectId(getProjectId());
            confRrStatisticHeader.setLossTableId(conformedPLT.getId());
            confRrStatisticHeader.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
            confRrStatisticHeader.setFinancialPerspective(fp);
            confRrStatisticHeader.getStatisticData().setStatisticMetric(metric);
            confRrStatisticHeader.getStatisticData().setSummaryStatistic(confSumStat);
            confRrStatisticHeader.getStatisticData().setEpCurves(confEPCurves);

            confRrStatisticHeaders.add(confRrStatisticHeader);

            metricToEPCurve.put(metric, confEPCurves);
        }
        conformedPLT.setPltStatisticList(confRrStatisticHeaders);
        log.debug("conformed PLT id {} currency {}, ", conformedPLT.getId(), conformedPLT.getCurrency());

        // TODO d) write as bin file to ihub : DAT, EPC, EPS

        String epCurveFilename = makePLTEPCurveFilename(
                conformedPLT.getCreatedDate(),
                conformedPLT.getRegionPeril().getRegionPerilCode(),
                conformedPLT.getFinancialPerspective().getFullCode(),
                conformedPLT.getCurrency().getCode(),
                XLTOT.TARGET,
                conformedPLT.getTargetRap().getTargetRapId(),
                conformedPLT.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT
                conformedPLT.getId(),
                getFileExtension());

        String sumstatFilename = makePLTSummaryStatFilename(
                conformedPLT.getCreatedDate(),
                conformedPLT.getRegionPeril().getRegionPerilCode(),
                conformedPLT.getFinancialPerspective().getFullCode(),
                conformedPLT.getCurrency().getCode(),
                XLTOT.TARGET,
                conformedPLT.getTargetRap().getTargetRapId(),
                conformedPLT.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT
                conformedPLT.getId(),
                getFileExtension());

        writePLTEPCurves(metricToEPCurve, epCurveFilename);
        writePLTSummaryStatistics(confSumStat, sumstatFilename);

        // DAT file
        //Loss data fle
        String filename = makePLTFileName(
                conformedPLT.getCreatedDate(),
                conformedPLT.getRegionPeril().getRegionPerilCode(),
                conformedPLT.getFinancialPerspective().getCode(),
                conformedPLT.getCurrency().getCode(),
                XLTOT.TARGET,
                conformedPLT.getTargetRap().getTargetRapId(),
                conformedPLT.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT, no thread number
                conformedPLT.getId(),
                getFileExtension());
        File file = makeFullFile(getPrefixDirectory(), filename);
        conformedPLT.setPltLossDataFile(new BinFile(file));

        long pic = System.currentTimeMillis();
        List<PLTLossData> pltLossDataList = bundle.getPltLossDataList();

        // conform pltLossDataList
        List<PLTLossData> confPltLossDataList = conformPltLossData(pltLossDataList, proportion, multiplier, bundle.getRrAnalysis().getExchangeRate());

        if (confPltLossDataList != null) {
            writePLTLossDataNonRMS(confPltLossDataList, file);
            log.debug("File {}: convert {} data lines took {} ms", bundle.getFile().getName(), pltLossDataList.size(), System.currentTimeMillis() - pic);
        } else {
            log.debug("File {}: parsing data section return null -- something wrong", bundle.getFile().getName());
        }

        // TODO e) persist conformed PLT into data base
        conformedPLT.setPltStatisticList(confRrStatisticHeaders);
        rrStatisticHeaderRepository.save(confRrStatisticHeaders);
        daoService.persistScorPLTHeader(conformedPLT);

        RRLossTable rrLossTable = rrLossTableRepository.findByRrAnalysisIdAndLossTableTypeAndFileDataFormatAndOriginalTarget(
                bundle.getRrAnalysis().getId(),
                RRLossTable.DATA_TYPE_PLT,
                RRLossTable.FILE_DATA_FORMAT_TREATY,
                RRLossTableType.CONFORMED.toString());
        if (rrLossTable != null) {
            rrLossTable.setLossDataFile(new LossDataFile(file.getName(),file.getParent()));
            rrLossTableRepository.save(rrLossTable);
        }

        log.info("Conformed PLT Header {}", conformedPLT.getId());
        return conformedPLT;
    }

    public void writePLTLossDataNonRMS(List<PLTLossData> list, File file) {
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        file = makeFullFile(getPrefixDirectory(), file.getName());
        Comparator<PLTLossData> cmp = new Comparator<PLTLossData>() {
            @Override
            public int compare(PLTLossData o1, PLTLossData o2) {
                return new CompareToBuilder()
                        .append(o1.getSimPeriod(), o2.getSimPeriod())
                        .append(o1.getEventDate(), o2.getEventDate())
                        .append(o1.getEventId(), o2.getEventId())
                        .append(o1.getSeq(), o2.getSeq())
                        .toComparison();
            }
        };
        Collections.sort(list, cmp);
        try {
            int size = list.size() * 26;
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (PLTLossData lossData : list) {
                buffer.putInt(lossData.getSimPeriod());
                buffer.putInt(lossData.getEventId());
                buffer.putLong(lossData.getEventDate());
                buffer.putShort((short) lossData.getSeq());
                buffer.putFloat((float) lossData.getMaxExposure());
                buffer.putFloat((float) lossData.getLoss());
            }
        } catch (IOException e) {
            if (DBG) log.error("Exception {}", e);
        } finally {
            // if (DBG) log.info("Writing PLT {}, nEvents = {}", file, list.size());
            IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
    }

    private RRSummaryStatistic conformSummaryStatistic(RRSummaryStatistic input, double proportion, double multiplier, double exchangeRate) {
        RRSummaryStatistic output = new RRSummaryStatistic();
        output.setCov(input.getCov());
        output.setPurePremium(input.getPurePremium() * proportion * multiplier * exchangeRate);
        output.setStandardDeviation(input.getStandardDeviation() * proportion * multiplier * exchangeRate);
        return output;
    }

    private List<PLTLossData> conformPltLossData(List<PLTLossData> inputs, double proportion, double multiplier, double exchangeRate) {
        List<PLTLossData> outputs = new ArrayList<>();

        for (PLTLossData input : inputs) {
            PLTLossData output = new PLTLossData(input.getEventId(),
                    input.getEventDate(),
                    input.getSimPeriod(),
                    input.getSeq(),
                    input.getMaxExposure() * proportion * multiplier * exchangeRate,
                    input.getLoss() * proportion * multiplier * exchangeRate);
            outputs.add(output);
        }
        return outputs;
    }

    private List<RREPCurve> conformELTEPCurves(List<RREPCurve> inputs, double proportion, double multiplier, double exchangeRate) {
        List<RREPCurve> outputs = new ArrayList<>();
        for (RREPCurve input : inputs) {
            RREPCurve output = new RREPCurve();
            outputs.add(output);

            output.setExceedanceProbability(input.getExceedanceProbability());
            output.setReturnPeriod(input.getReturnPeriod());
            output.setLossAmount(input.getLossAmount() * proportion * multiplier * exchangeRate);
        }
        return outputs;
    }

    public DAOService getDaoService() {
        return daoService;
    }

    public void setDaoService(DAOService daoService) {
        this.daoService = daoService;
    }

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }


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
            IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
            return new BinFile(file);
        }
    }

    protected boolean closeDirectBuffer(final ByteBuffer buffer){
        if(!buffer.isDirect())
            return false;
        final DirectBuffer dbb = (DirectBuffer)buffer;
        return AccessController.doPrivileged(
                new PrivilegedAction<Object>() {
                    public Object run() {
                        try {
                            Cleaner cleaner = dbb.cleaner();
                            if (cleaner != null) cleaner.clean();
                            return null;
                        } catch (Exception e) {
                            return dbb;
                        }
                    }
                }
        ) == null;
    }

    public BinFile writePLTSummaryStatistics(RRSummaryStatistic summaryStatistics, String filename) {
        File file = makeFullFile(filename);
        return writePLTSummaryStatistics(summaryStatistics, file);
    }

    public BinFile writePLTSummaryStatistics(RRSummaryStatistic summaryStatistics, File file) { // PLTSummaryStatistic
        FileChannel out=null;
        MappedByteBuffer buffer=null;
        try {
            log.info("Summary statistic file: {}", file);
            out = new RandomAccessFile(file, "rw").getChannel();
            int size=24;
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putDouble(summaryStatistics.getPurePremium());
            buffer.putDouble(summaryStatistics.getStandardDeviation());
            buffer.putDouble(summaryStatistics.getCov());
        } catch (IOException e) {
            log.error("Exception: ", e);
        } finally {
            IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
            return new BinFile(file);
        }
    }

    private File makeFullFile(String filename) {
        String outDir = getPrefixDirectory();
        final Path fullPath = getIhubPath().resolve(outDir);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths "+fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }
}
*/
