package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.configuration.file.BinFile;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.domain.dto.PLTLossData;
import com.scor.rr.domain.enums.*;
import com.scor.rr.repository.*;
import com.scor.rr.service.fileBasedImport.ImportFileService;
import com.scor.rr.util.PathUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.http.annotation.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
@StepScope
public class ConvertToSCORFormatService {
    private static final Logger log = LoggerFactory.getLogger(ConvertToSCORFormatService.class);

    @Autowired
    private ImportFileService importFileService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private FinancialPerspective fpUP;

    @Autowired
    private FinancialPerspectiveRepository financialPerspectiveRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private TargetRapRepository targetRapRepository;

    private TransformationPackageNonRMS transformationPackage;

    @Autowired
    RegionPerilRepository regionPerilRepository;

    @Autowired
    AnalysisIncludedTargetRAPRepository analysisIncludedTargetRAPRepository;

    @Autowired
    ModelAnalysisEntityRepository rrAnalysisRepository;

    @Value("#{jobParameters['importSequence']}")
    private Long importSequence;

    @Value("#{jobParameters['contractId']}")
    private String contractId;

    @Value("#{jobParameters['projectId']}")
    private Long projectId;

    @Value("#{jobParameters['uwYear']}")
    private Integer uwYear;

    @Value("#{jobParameters['fileExtension']}")
    private String fileExtension;

    @Value("#{jobParameters['clientId']}")
    private Long clientId;

    public RepeatStatus convertToSCORFormat() throws Exception {
        log.debug("Start CONVERT_LOSS_DATA_TO_SCOR_FORMAT");
        Date startDate = new Date();

        if (fpUP == null) {
            fpUP = financialPerspectiveRepository.findByCode("UP");
        }
        log.debug("fpUP is {}", fpUP == null ? null : fpUP.getCode());
        log.debug("nbBundles is {}", transformationPackage.getBundles() == null ? null : transformationPackage.getBundles().size());

        PLTModelingBasis modelingBasis = PLTModelingBasis.AM;
        if (contractId != null && uwYear != null) {
            String[] tokens = StringUtils.split(uwYear.toString(), '-');
            ContractEntity contract = contractRepository.findByTreatyIdAndUwYear(contractId, Integer.parseInt(tokens[0])).get();
            if (contract != null && contract.getContractSourceTypeId() != null && 5L == contract.getContractSourceTypeId()) {
                modelingBasis = PLTModelingBasis.PM;
            }
        }

        log.debug("Modeling basis: {}", modelingBasis);

        for (TransformationBundleNonRMS bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 2 CONVERT_LOSS_DATA_TO_SCOR_FORMAT for this analysis in loop of many analysis :
            // only valid sourceResults after step 1 are converted to bundles
//            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
//            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
//            projectImportAssetLogA.setProjectId(getProjectId());
//            projectImportAssetLogA.setStepId(2);
//            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
//            projectImportAssetLogA.setStartDate(startDate);
//            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            long pic = System.currentTimeMillis();
            // modelVersion new version string ? but old version --> modelVersionYear int
            List<PLTLossData> pltLossDataList = convertToScorPLTData(importFileService.parsePLTLossDataFile(bundle.getFile()), Integer.parseInt(bundle.getSourceResult().getModelVersion()));
            if (pltLossDataList != null) {
                bundle.setPltLossDataList(pltLossDataList);
                log.debug("File {}: convert {} data lines took {} ms", bundle.getFile().getName(), pltLossDataList.size(), System.currentTimeMillis() - pic);
            }  else {
                log.debug("File {}: parsing data section return null -- something wrong", bundle.getFile().getName());
            }

            List<TargetRapEntity> targetRapEntities = analysisIncludedTargetRAPRepository.findByModelAnalysisId(bundle.getRrAnalysis().getRrAnalysisId())
                    .map(AnalysisIncludedTargetRAPEntity::getTargetRAPId)
                    .map(targetRapRepository::findById)
                    .map(Optional::get)
                    .collect(toList());

            if (targetRapEntities == null || targetRapEntities.isEmpty()) {
                log.info("Finish tracking at the end of STEP 2 : CONVERT_LOSS_DATA_TO_SCOR_FORMAT for analysis {}, status {}", bundle.getRrAnalysis().getRrAnalysisId(), "Error", "stop this tracking");
                if (bundle.getRrAnalysis() != null) {
                    bundle.getRrAnalysis().setImportStatus("ERROR");
                    rrAnalysisRepository.save(bundle.getRrAnalysis());
                }
                continue;
            }

            // chua conform
            List<PltHeaderEntity> pltHeaders = makeOriginalPurePLTHeaders(bundle, targetRapEntities, modelingBasis);
            if (bundle.getPltLossDataList() != null) {
                for (PltHeaderEntity pltHeader : pltHeaders) {
                    RegionPerilEntity regionPeril = regionPerilRepository.findById(pltHeader.getRegionPerilId()).get();
                    String filename = makePLTFileName(
                            pltHeader.getCreatedDate(),
                            regionPeril.getRegionPerilCode(),
                            "UP", // pltHeader.getFinancialPerspective().getCode()
                            pltHeader.getCurrencyCode(),
                            XLTOT.ORIGINAL,
                            pltHeader.getTargetRAPId(),
                            pltHeader.getPltSimulationPeriods(),
                            PLTPublishStatus.PURE,
                            0, // pure PLT, no thread number
                            pltHeader.getPltHeaderId(),
                            fileExtension); // old getFileExtension()
                    File file = makeFullFile(getPrefixDirectory(), filename);
                    writePLTLossDataNonRMS(pltLossDataList, file);

                    BinFile newFile = new BinFile(file);
                    pltHeader.setLossDataFileName(newFile.getFileName());
                    pltHeader.setLossDataFilePath(newFile.getPath()); // todo path or other ?
                }
            }

            // truncator
            double threshold = 0.0;
            for (PltHeaderEntity pltHeader : pltHeaders) {
                PLTBundleNonRMS pltBundle = new PLTBundleNonRMS();
                pltBundle.setHeader(pltHeader);
                bundle.addPLTBundle(pltBundle);

                //Truncator
                pltHeader.setTruncationCurrency(bundle.getTruncationCurrency());
                pltHeader.setTruncationThreshold(threshold);
                pltHeader.setTruncationThreshold(threshold);
            }

            // finish step 2 CONVERT_LOSS_DATA_TO_SCOR_FORMAT for one analysis in loop for of many analysis
//            Date endDate = new Date();
//            projectImportAssetLogA.setEndDate(endDate);
//            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 2 : CONVERT_LOSS_DATA_TO_SCOR_FORMAT for RRAnalysis : {}", bundle.getRrAnalysis().getRrAnalysisId());
        }

        return RepeatStatus.FINISHED;
    }

    @Value("${ihub.treaty.out.path}") // todo change it not ihub
    private String filePath;

    public Path getIhubPath() {
        return Paths.get(filePath);
    }

    public File makeFullFile(String prefixDirectory, String filename) {
        final Path fullPath = getIhubPath().resolve(prefixDirectory);
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

    public String getPrefixDirectory() {
        return PathUtils.getPrefixDirectory(clientName, clientId, contractId, uwYear, projectId);
    }

    private static boolean DBG = true;

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
            String projectId,
            String periodBasis,
            XLTOrigin origin,
            XLTSubType subType,
            XLTOT currencySource,
            Long targetRapId,
            Integer simulationPeriod,
            PLTPublishStatus pltPublishStatus,
            Integer threadNum, // 0 for pure PLT
            Long uniqueId, // old string
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
                currency,
                projectId,
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
                projectId.toString(),
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


    public static List<PLTLossData> convertToScorPLTData(List<ImportFilePLTData> importFilePLTDataList, int year) {
        if (importFilePLTDataList != null && !importFilePLTDataList.isEmpty()) {
            List<PLTLossData> scorPLTDataList = new ArrayList<>(importFilePLTDataList.size());
            for (ImportFilePLTData importFilePLTData : importFilePLTDataList) {
                scorPLTDataList.add(new PLTLossData(importFilePLTData.getEventId(),
                        java.sql.Date.UTC(year-1900, importFilePLTData.getMonth() - 1, importFilePLTData.getDay(), 0, 0, 0),
                        importFilePLTData.getYear(),
                        importFilePLTData.getRepetition() + 1,
                        importFilePLTData.getMaxExposure(),
                        importFilePLTData.getValue()));
            }
            return scorPLTDataList;
        }
        return null;
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

    private List<PltHeaderEntity> makeOriginalPurePLTHeaders(TransformationBundleNonRMS bundle, List<TargetRapEntity> targetRaps, PLTModelingBasis modelingBasis) {

        int i = 0;
        List<PltHeaderEntity> pltHeaders = new ArrayList<>();
        for (TargetRapEntity targetRap : targetRaps) {
            i++;

            PltHeaderEntity scorPLTHeader = new PltHeaderEntity();

            scorPLTHeader.setLossDataFileName(null);
            scorPLTHeader.setLossDataFilePath(null);
//            scorPLTHeader.setPltStatisticList(null);

//            scorPLTHeader.setPltGrouping(PLTGrouping.UnGrouped);
            scorPLTHeader.setGroupedPLT(false);
//            scorPLTHeader.setPltInuring(PLTInuring.None);
//            scorPLTHeader.setPltStatus(PLTStatus.Pending);
//            scorPLTHeader.setInuringPackageDefinition(null);
            // TODO how ???
            scorPLTHeader.setPltType(PLTType.Pure.toString());

//            ProjectEntity project = projectRepository.findById(getProjectId());
            scorPLTHeader.setProjectId(projectId);
//            scorPLTHeader.setRrRepresentationDatasetId(transformationPackage.getRrRepresentationDatasetId());
            scorPLTHeader.setModelAnalysisId(bundle.getRrAnalysis().getRrAnalysisId());

            CurrencyEntity currency = currencyRepository.findByCode(bundle.getRrAnalysis().getSourceCurrency());
            scorPLTHeader.setCurrencyCode(currency.getCode());
            scorPLTHeader.setTargetRAPId(targetRap.getTargetRAPId());

            RegionPerilEntity regionPeril = regionPerilRepository.findByRegionPerilCode(bundle.getRrAnalysis().getRegionPeril());

            scorPLTHeader.setRegionPerilId(regionPeril.getRegionPerilId());
//            scorPLTHeader.setFinancialPerspective(fpUP);

//            scorPLTHeader.setAdjustmentStructure(null);
//            scorPLTHeader.setCatAnalysisDefinition(null);

//            scorPLTHeader.setSourcePLTHeader(null);
            scorPLTHeader.setDefaultPltName("Pure-" + i); // FIXME: 16/07/2016
            scorPLTHeader.setUdName("Pure-u-" + i); // FIXME: 16/07/2016
//            scorPLTHeader.setTags(null);

            scorPLTHeader.setCreatedDate(new Date());
//            scorPLTHeader.setxActPublicationDate(null);
//            scorPLTHeader.setxActUsed(false);
//            scorPLTHeader.setxActAvailable(false);

            scorPLTHeader.setGeneratedFromDefaultAdjustment(false);
//            scorPLTHeader.setUserSelectedGrain(bundle.getRrAnalysis().getGrain());
//            scorPLTHeader.setExportedDPM(false);

            scorPLTHeader.setGeoCode(bundle.getRrAnalysis().getGeoCode());
            scorPLTHeader.setGeoDescription(bundle.getRrAnalysis().getGeoCode());
            scorPLTHeader.setPerilCode(bundle.getRrAnalysis().getPeril());

            // TODO how ???
//            scorPLTHeader.setEngineType(bundle.getRmsAnalysis().getEngineType());
//            scorPLTHeader.setInstanceId(bundle.getInstanceId());
//            scorPLTHeader.setImportSequence(getImportSequence()); importSequence
            scorPLTHeader.setImportSequence(importSequence.intValue());
            scorPLTHeader.setSourceLossModelingBasis(modelingBasis.getCode());

            scorPLTHeader.setUdName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1.T0");
//            scorPLTHeader.setPltName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1");
            scorPLTHeader.setDefaultPltName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1");
            pltHeaders.add(scorPLTHeader);

//            daoService.getMongoDBSequence().nextSequenceId(scorPLTHeader);
            log.info("PLT {} has targetRap {}: source code {}, target code {}", scorPLTHeader.getPltHeaderId(), targetRap.getTargetRAPId(), targetRap.getSourceRAPCode(), targetRap.getTargetRAPCode());
        }
        return pltHeaders;
    }

    public TransformationPackageNonRMS getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackageNonRMS transformationPackage) {
        this.transformationPackage = transformationPackage;
    }

}
