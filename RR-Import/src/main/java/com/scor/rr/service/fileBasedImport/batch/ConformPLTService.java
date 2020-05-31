package com.scor.rr.service.fileBasedImport.batch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scor.rr.configuration.file.BinFile;
import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.PLTLossData;
import com.scor.rr.domain.enums.*;
import com.scor.rr.repository.*;
import com.scor.rr.util.PathUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

@Service
@StepScope
public class ConformPLTService {

    private static final Logger log = LoggerFactory.getLogger(ConformPLTService.class);

    private static boolean DBG = true;

    @Autowired
    private PltHeaderRepository pltHeaderRepository;

    @Autowired
    private SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

    @Autowired
    private SummaryStatisticsDetailRepository summaryStatisticsDetailRepository;

    @Autowired
    private RegionPerilRepository regionPerilRepository;

    @Autowired
    private LossDataHeaderEntityRepository lossDataHeaderEntityRepository;

    @Autowired
    private EPCurveHeaderEntityRepository epCurveHeaderEntityRepository;

    private Gson gson = new Gson();

    @Autowired
    private TransformationPackageNonRMS transformationPackage;
    @Value("${ihub.treaty.out.path}") // todo change it not ihub
    private String filePath;
    @Value("#{jobParameters['fileExtension']}")
    private String fileExtension;
    @Value("#{jobParameters['importSequence']}")
    private Long importSequence;
    @Value("#{jobParameters['contractId']}")
    private String contractId;
    @Value("#{jobParameters['projectId']}")
    private Long projectId;
    @Value("#{jobParameters['uwYear']}")
    private Integer uwYear;
    @Value("#{jobParameters['clientId']}")
    private Long clientId;
    @Value("#{jobParameters['reinsuranceType']}")
    private String reinsuranceType;
    @Value("#{jobParameters['prefix']}")
    private String prefix;
    @Value("#{jobParameters['clientName']}")
    private String clientName;
    @Value("#{jobParameters['division']}")
    private String division;
    @Value("#{jobParameters['sourceVendor']}")
    private String sourceVendor;
    @Value("#{jobParameters['modelSystemVersion']}")
    private String modelSystemVersion;
    @Value("#{jobParameters['periodBasis']}")
    private String periodBasis;

    public static String makeTTFileName(
            String reinsuranceType,
            String prefix,
            String clientName,
            String contractId,
            String division,
            String uwYear,
            XLTAssetType XLTAssetType,
            Date date,
            String sourceVendor,
            String modelSystemVersion,
            String regionPeril,
            String fp,
            String currency,
            Long projectId,
            String periodBasis,
            XLTOrigin origin,
            XLTSubType subType,
            XLTOT currencySource,
            Long targetRapId,
            Integer simulationPeriod,
            PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            Long uniqueId,
            Long importSequence,
            String fileExtension) {
        return PathUtils.makeTTFileName(reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear,
                XLTAssetType,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency != null ? currency : null,
                projectId.toString(),
                periodBasis,
                origin,
                subType,
                currencySource,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum, // 0 for pure PLT
                uniqueId,
                importSequence,
                null,
                null,
                null,
                fileExtension);
    }

    public RepeatStatus conformPLT() throws Exception {
        log.debug("Start CONFORM_PLT");
        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 4 CONFORM_PLT for this analysis in loop of many analysis :
            // only valid sourceResults after step 1 are converted to bundles
//            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
//            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
//            projectImportAssetLogA.setProjectId(getProjectId());
//            projectImportAssetLogA.setStepId(4);
//            projectImportAssetLogA.setStepName(StepNonRMS.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
//            projectImportAssetLogA.setStartDate(startDate);
//            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            for (PLTBundleNonRMS pltBundle : bundle.getPltBundles()) {
                makeConformedPurePLTHeader(pltBundle, bundle);
            }

            // finis step 3 CONFORM_PLT for one analysis in loop for of many analysis
//            Date endDate = new Date();
//            projectImportAssetLogA.setEndDate(endDate);
//            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 4 : CONFORM_PLT for RRAnalysis : {}", bundle.getRrAnalysis().getRrAnalysisId());
        }
        return RepeatStatus.FINISHED;
    }

    public PltHeaderEntity makeConformedPurePLTHeader(PLTBundleNonRMS pltBundle, TransformationBundleNonRMS bundle) {
        PltHeaderEntity originalPLT = pltBundle.getHeader(); // not saved yet
        PltHeaderEntity conformedPLT = new PltHeaderEntity();

        // TODO a) source ccy --> target ccy
        conformedPLT.setPltHeaderId(originalPLT.getPltHeaderId());
        conformedPLT.setLossDataFilePath(null);
        conformedPLT.setLossDataFileName(null);
//        conformedPLT.setPltStatisticList(null);
//        conformedPLT.setPltGrouping(PLTGrouping.UnGrouped);
//        conformedPLT.setPltInuring(PLTInuring.None);
//        conformedPLT.setPltStatus(PLTStatus.Pending);
//        conformedPLT.setInuringPackageDefinition(null);
        conformedPLT.setPltSimulationPeriods(originalPLT.getPltSimulationPeriods());
        conformedPLT.setPltType(PLTType.Pure.toString());
        conformedPLT.setProjectId(originalPLT.getProjectId());
        conformedPLT.setModelAnalysisId(bundle.getRrAnalysis().getRrAnalysisId());
        conformedPLT.setCurrencyCode(bundle.getRrAnalysis().getTargetCurrency());
        conformedPLT.setTargetRAPId(originalPLT.getTargetRAPId());
        conformedPLT.setRegionPerilId(originalPLT.getRegionPerilId());
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            conformedPLT.setCreatedBy(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId());
        }
//        conformedPLT.setFinancialPerspective(originalPLT.getFinancialPerspective());
//        conformedPLT.setAdjustmentStructure(null);
//        conformedPLT.setCatAnalysisDefinition(null);
//        conformedPLT.setSourcePLTHeader(null);
//        conformedPLT.setSystemShortName(originalPLT.getSystemShortName());
//        conformedPLT.setUserShortName(originalPLT.getUserShortName());
        conformedPLT.setThreadName(originalPLT.getThreadName());
//        conformedPLT.setTags(null);
        conformedPLT.setCreatedDate(new Date());
//        conformedPLT.setxActPublicationDate(null);
//        conformedPLT.setxActUsed(false);
//        conformedPLT.setxActAvailable(false);
//        conformedPLT.setGeneratedFromDefaultAdjustment(false);
//        conformedPLT.setUserSelectedGrain(bundle.getRrAnalysis().getGrain());
//        conformedPLT.setExportedDPM(false);
        conformedPLT.setGeoCode(bundle.getRrAnalysis().getGeoCode());
        conformedPLT.setGeoDescription(bundle.getRrAnalysis().getGeoCode());
        conformedPLT.setPerilCode(bundle.getRrAnalysis().getPeril());
//        conformedPLT.setEngineType(bundle.getRrAnalysis().getModel());
//        conformedPLT.setInstanceId(bundle.getInstanceId());
        conformedPLT.setImportSequence(importSequence.intValue());
        conformedPLT.setSourceLossModelingBasis(originalPLT.getSourceLossModelingBasis());
        conformedPLT.setUdName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1.T0");
        conformedPLT.setDefaultPltName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1");
        pltHeaderRepository.saveAndFlush(conformedPLT);

        // TODO b) apply proportion and unit multiplier
        // TODO c) calculate EPC, EPS for conformed PLT
        double proportion = bundle.getSourceResult().getProportion() == null ? 1 : bundle.getSourceResult().getProportion().doubleValue() / 100;
        double multiplier = bundle.getSourceResult().getUnitMultiplier() == null ? 1 : bundle.getSourceResult().getUnitMultiplier().doubleValue();
        double exchangeRate = bundle.getRrAnalysis().getExchangeRate();
        log.info("Conforming EP curves and sum stats for conformedPLT {}, proportion {}, multiplier {}", conformedPLT.getPltHeaderId(), proportion, multiplier);

        // only one - one : orig - conf SummaryStatisticHeaderEntity
        LossDataHeaderEntity lossDataHeaderEntity = lossDataHeaderEntityRepository.findByModelAnalysisIdList(bundle.getRrAnalysis().getRrAnalysisId()).get(0);
        // To check if it's valid
        SummaryStatisticHeaderEntity origSummaryStatisticHeaderEntity = summaryStatisticHeaderRepository.findById(pltBundle.getSummaryStatisticHeaderId()).get();
        SummaryStatisticHeaderEntity confSummaryStatisticHeaderEntity = new SummaryStatisticHeaderEntity();
        confSummaryStatisticHeaderEntity.setCov(origSummaryStatisticHeaderEntity.getCov());
        confSummaryStatisticHeaderEntity.setPurePremium(origSummaryStatisticHeaderEntity.getPurePremium() * proportion * multiplier * exchangeRate);
        confSummaryStatisticHeaderEntity.setStandardDeviation(origSummaryStatisticHeaderEntity.getStandardDeviation() * proportion * multiplier * exchangeRate);
        // todo calculate OEP, AEP 10 50 100 250 500 1000
        summaryStatisticHeaderRepository.saveAndFlush(confSummaryStatisticHeaderEntity);

        // List<AnalysisEpCurves> list : years of 1 metric --> write to file EPC
        Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurve = new HashMap<>();
        List<EPCurveHeaderEntity> origEPCurveHeaderEntities = epCurveHeaderEntityRepository.findByLossDataId(lossDataHeaderEntity.getLossDataHeaderId());
        List<EPCurveHeaderEntity> conformedEpCurvesHeaders = new ArrayList<>();
        List<AnalysisEpCurves> confEPCurvesList = new ArrayList<>();
        for (EPCurveHeaderEntity epCurveHeaderEntity : origEPCurveHeaderEntities) {
            Type listType = new TypeToken<ArrayList<RREPCurve>>() {
            }.getType();
            List<AnalysisEpCurves> confEPCurves =
                    conformELTEPCurves(
                            gson.fromJson(epCurveHeaderEntity.getEPCurves(), listType),
                            proportion,
                            multiplier,
                            exchangeRate);
            confEPCurvesList.addAll(confEPCurves);
            EPCurveHeaderEntity conformedEPCurvesHeader = EPCurveHeaderEntity.builder()
                    .entity(epCurveHeaderEntity.getEntity())
                    .lossDataType(epCurveHeaderEntity.getLossDataType())
                    .statisticMetric(epCurveHeaderEntity.getStatisticMetric())
                    .ePCurves(gson.toJson(confEPCurves))
                    .lossDataId(conformedPLT.getPltHeaderId())
                    .financialPerspective(epCurveHeaderEntity.getFinancialPerspective())
                    .build();
            conformedEpCurvesHeaders.add(conformedEPCurvesHeader);

            // SummaryStatisticsDetail
            SummaryStatisticsDetail confRrStatisticDetail = new SummaryStatisticsDetail();
            confRrStatisticDetail.setSummaryStatisticHeaderId(confSummaryStatisticHeaderEntity.getSummaryStatisticHeaderId());
            confRrStatisticDetail.setPltHeaderId(conformedPLT.getPltHeaderId());
            confRrStatisticDetail.setLossType("PLT");
            StatisticMetric metric = epCurveHeaderEntity.getStatisticMetric();
            confRrStatisticDetail.setCurveType(metric.toString());
            // todo fill 630 RP2, ..., RP 100000
            summaryStatisticsDetailRepository.saveAndFlush(confRrStatisticDetail);

            // put into a map to write to file
            metricToEPCurve.put(metric, confEPCurves);
        }
        epCurveHeaderEntityRepository.saveAll(conformedEpCurvesHeaders);

        // TODO d) write as bin file to ihub : DAT, EPC, EPS
        // EPC file : write all 4 metrics into 1 file
        RegionPerilEntity regionPerilEntity = regionPerilRepository.findById(conformedPLT.getRegionPerilId()).get();
        String epCurveFilename = makePLTEPCurveFilename(
                conformedPLT.getCreatedDate(),
                regionPerilEntity.getRegionPerilCode(),
                "FP", // conformedPLT.getFinancialPerspective().getFullCode(),
                conformedPLT.getCurrencyCode(),
                XLTOT.TARGET,
                conformedPLT.getTargetRAPId(),
                conformedPLT.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT
                conformedPLT.getPltHeaderId(),
                getFileExtension());

        BinFile pltEPCFile = writePLTEPCurves(metricToEPCurve, epCurveFilename);
        conformedEpCurvesHeaders.forEach(epCurveHeaderEntity -> {
            epCurveHeaderEntity.setEPCFilePath(pltEPCFile.getPath());
            epCurveHeaderEntity.setEPCFileName(pltEPCFile.getFileName());
        });

        // EPS file : write all into 1 file : cov, pure premium, standard deviation
        String sumstatFilename = makePLTSummaryStatFilename(
                conformedPLT.getCreatedDate(),
                regionPerilEntity.getRegionPerilCode(), // conformedPLT.getRegionPeril().getRegionPerilCode(),
                "FP",
                conformedPLT.getCurrencyCode(),
                XLTOT.TARGET,
                conformedPLT.getTargetRAPId(),
                conformedPLT.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT
                conformedPLT.getPltHeaderId(),
                getFileExtension());
        BinFile pltEPSFile = writePLTSummaryStatistics(confSummaryStatisticHeaderEntity, sumstatFilename);
        confSummaryStatisticHeaderEntity.setEPSFileName(pltEPSFile.getFileName());
        confSummaryStatisticHeaderEntity.setEPSFilePath(pltEPSFile.getPath());
        summaryStatisticHeaderRepository.save(confSummaryStatisticHeaderEntity);

        // DAT file
        //Loss data fle
        String filename = makePLTFileName(
                conformedPLT.getCreatedDate(),
                regionPerilEntity.getRegionPerilCode(), // conformedPLT.getRegionPeril().getRegionPerilCode(),
                "FP", // conformedPLT.getFinancialPerspective().getCode()
                conformedPLT.getCurrencyCode(),
                XLTOT.TARGET,
                conformedPLT.getTargetRAPId(),
                conformedPLT.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT, no thread number
                conformedPLT.getPltHeaderId(),
                getFileExtension());
        File file = makeFullFile(getPrefixDirectory(), filename);
//        conformedPLT.setPltLossDataFile(new BinFile(file));
        conformedPLT.setLossDataFileName(file.getName());
        conformedPLT.setLossDataFilePath(file.getParent());

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
        pltHeaderRepository.save(conformedPLT);

        log.info("Conformed PLT Header {}", conformedPLT.getPltHeaderId());
        return conformedPLT;
    }

    public void writePLTLossDataNonRMS(List<PLTLossData> list, File file) {
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        file = makeFullFile(getPrefixDirectory(), file.getName());
        Comparator<PLTLossData> cmp = (o1, o2) -> new CompareToBuilder()
                .append(o1.getSimPeriod(), o2.getSimPeriod())
                .append(o1.getEventDate(), o2.getEventDate())
                .append(o1.getEventId(), o2.getEventId())
                .append(o1.getSeq(), o2.getSeq())
                .toComparison();
        list.sort(cmp);
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

    private List<AnalysisEpCurves> conformELTEPCurves(List<AnalysisEpCurves> inputs, double proportion, double multiplier, double exchangeRate) {
        List<AnalysisEpCurves> outputs = new ArrayList<>();
        for (AnalysisEpCurves input : inputs) {
            AnalysisEpCurves output = new AnalysisEpCurves();

            output.setExeceedanceProb(input.getExeceedanceProb());
            output.setLoss(input.getLoss() * proportion * multiplier * exchangeRate);

            output.setAnalysisId(input.getAnalysisId());
            output.setEpTypeCode(input.getEpTypeCode());
            output.setFinPerspCode(input.getFinPerspCode());

            outputs.add(output);
        }
        return outputs;
    }

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }

    public BinFile writePLTEPCurves(Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurve, String filename) {
        File file = makeFullFile(getPrefixDirectory(), filename);
        return writePLTEPCurves(metricToEPCurve, file);
    }

    // todo from BaseNonRMSBean
    public File makeFullFile(String prefixDirectory, String filename) {
        final Path fullPath = getIhubPath().resolve(prefixDirectory);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths " + fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }

    public BinFile writePLTEPCurves(Map<StatisticMetric, List<AnalysisEpCurves>> metricToEPCurve, File file) {
        List<AnalysisEpCurves> curveAEP = metricToEPCurve.get(StatisticMetric.AEP);
        List<AnalysisEpCurves> curveOEP = metricToEPCurve.get(StatisticMetric.OEP);
        List<AnalysisEpCurves> curveTVarAEP = metricToEPCurve.get(StatisticMetric.TVAR_AEP);
        List<AnalysisEpCurves> curveTVarOEP = metricToEPCurve.get(StatisticMetric.TVAR_OEP);
        List<AnalysisEpCurves> curveEEF = metricToEPCurve.get(StatisticMetric.EEF);
        List<AnalysisEpCurves> curveCEP = metricToEPCurve.get(StatisticMetric.CEP);

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
                for (AnalysisEpCurves pltepCurve : curveAEP) {
                    buffer.putShort(codeAEP);
                    buffer.putDouble(pltepCurve.getExeceedanceProb().doubleValue());
                    buffer.putDouble(pltepCurve.getLoss());
                }
            }
            if (curveOEP != null) {
                for (AnalysisEpCurves pltepCurve : curveOEP) {
                    buffer.putShort(codeOEP);
                    buffer.putDouble(pltepCurve.getExeceedanceProb().doubleValue());
                    buffer.putDouble(pltepCurve.getLoss());
                }
            }
            if (curveTVarAEP != null) {
                for (AnalysisEpCurves pltepCurve : curveTVarAEP) {
                    buffer.putShort(codeAEPTCE);
                    buffer.putDouble(pltepCurve.getExeceedanceProb().doubleValue());
                    buffer.putDouble(pltepCurve.getLoss());
                }
            }
            if (curveTVarOEP != null) {
                for (AnalysisEpCurves pltepCurve : curveTVarOEP) {
                    buffer.putShort(codeOEPTCE);
                    buffer.putDouble(pltepCurve.getExeceedanceProb().doubleValue());
                    buffer.putDouble(pltepCurve.getLoss());
                }
            }
            if (curveEEF != null) {
                for (AnalysisEpCurves pltepCurve : curveEEF) {
                    buffer.putShort(codeEEF);
                    buffer.putDouble(pltepCurve.getExeceedanceProb().doubleValue());
                    buffer.putDouble(pltepCurve.getLoss());
                }
            }
            if (curveCEP != null) {
                for (AnalysisEpCurves pltepCurve : curveCEP) {
                    buffer.putShort(codeCEP);
                    buffer.putDouble(pltepCurve.getExeceedanceProb().doubleValue());
                    buffer.putDouble(pltepCurve.getLoss());
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

    protected boolean closeDirectBuffer(final ByteBuffer buffer) {
        if (!buffer.isDirect())
            return false;
        final DirectBuffer dbb = (DirectBuffer) buffer;
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

    public BinFile writePLTSummaryStatistics(SummaryStatisticHeaderEntity summaryStatistics, String filename) {
        File file = makeFullFile(filename);
        return writePLTSummaryStatistics(summaryStatistics, file);
    }

    public BinFile writePLTSummaryStatistics(SummaryStatisticHeaderEntity summaryStatistics, File file) { // PLTSummaryStatistic
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            log.info("Summary statistic file: {}", file);
            out = new RandomAccessFile(file, "rw").getChannel();
            int size = 24;
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
            throw new RuntimeException("error creating paths " + fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }

    public Path getIhubPath() {
        return Paths.get(filePath);
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getPrefixDirectory() {
        return PathUtils.getPrefixDirectory(clientName, clientId, contractId, uwYear, projectId);
    }

    protected synchronized String makePLTSummaryStatFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus, Integer threadNum, // 0 for pure PLT
            Long uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear.toString(),
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.EPS,
                xltot,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum,
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makePLTEPCurveFilename(
            Date date, String regionPeril, String fp, String currency, XLTOT xltot, Long targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus, Integer threadNum, // 0 for pure PLT
            Long uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear.toString(),
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.EPC,
                xltot,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum,
                uniqueId,
                importSequence,
                fileExtension
        );
    }

    protected synchronized String makePLTFileName(
            Date date, String regionPeril, String fp, String currency, XLTOT currencySource, Long targetRapId, Integer simulationPeriod, PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            Long uniqueId, String fileExtension) {
        return makeTTFileName(
                reinsuranceType,
                prefix,
                clientName,
                contractId,
                division,
                uwYear.toString(),
                XLTAssetType.PLT,
                date,
                sourceVendor,
                modelSystemVersion,
                regionPeril,
                fp,
                currency,
                projectId,
                periodBasis,
                XLTOrigin.INTERNAL,
                XLTSubType.DAT,
                currencySource,
                targetRapId,
                simulationPeriod,
                pltPublishStatus,
                threadNum, // 0 for pure PLT
                uniqueId,
                importSequence,
                fileExtension
        );
    }

}
