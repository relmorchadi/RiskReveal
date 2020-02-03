package com.scor.rr.service.fileBasedImport.batch;

import com.scor.rr.configuration.file.BinFile;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.PLTLossData;
import com.scor.rr.domain.enums.*;
import com.scor.rr.repository.ContractRepository;
import com.scor.rr.repository.CurrencyRepository;
import com.scor.rr.repository.ProjectRepository;
import com.scor.rr.repository.TargetRapRepository;
import com.scor.rr.service.fileBasedImport.ImportFileService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.http.annotation.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

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
    private TTFinancialPerspectiveRepository ttFinancialPerspectiveRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private TargetRapRepository targetRapRepository;

    private TransformationPackageNonRMS transformationPackage;

    @Autowired
    TTRegionPerilRepository ttRegionPerilRepository;

    public RepeatStatus convertToSCORFormat() throws Exception {
        log.debug("Start CONVERT_LOSS_DATA_TO_SCOR_FORMAT");
        Date startDate = new Date();

        if (fpUP == null) {
            fpUP = ttFinancialPerspectiveRepository.findByCode("UP");
        }
        log.debug("fpUP is {}", fpUP == null ? null : fpUP.getFullCode());
        log.debug("nbBundles is {}", transformationPackage.getBundles() == null ? null : transformationPackage.getBundles().size());

        PLTModelingBasis modelingBasis = PLTModelingBasis.AM;
        if (getContractId() != null && getUwYear() != null) {
            String[] tokens = StringUtils.split(getUwYear(), '-');
            Contract contract = contractRepository.findByTreatyIdAndUwYear(getContractId(), Integer.parseInt(tokens[0]));
            if (contract != null && contract.getContractSourceType() != null && "5".equals(contract.getContractSourceType().getId())) {
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
            List<PLTLossData> pltLossDataList = ImportFileUtils.convertToScorPLTData(importFileService.parsePLTLossDataFile(bundle.getFile()), bundle.getSourceResult().getModelVersionYear());
            if (pltLossDataList != null) {
                bundle.setPltLossDataList(pltLossDataList);
                log.debug("File {}: convert {} data lines took {} ms", bundle.getFile().getName(), pltLossDataList.size(), System.currentTimeMillis() - pic);
            }  else {
                log.debug("File {}: parsing data section return null -- something wrong", bundle.getFile().getName());
            }

            List<TargetRapEntity> targetRaps = new ArrayList<>();
            for (String targetRapId : bundle.getRrAnalysis().getIncludedTargetRapIds()) {
                TargetRapEntity targetRap = targetRapRepository.findByTargetRapId(Integer.parseInt(targetRapId));
                targetRaps.add(targetRap);
            }

            // chua conform
            List<PltHeaderEntity> pltHeaders = makeOriginalPurePLTHeaders(bundle, targetRaps, modelingBasis);

            if (bundle.getPltLossDataList() != null) {
                for (PltHeaderEntity pltHeader : pltHeaders) {
                    String filename = makePLTFileName(
                            pltHeader.getCreatedDate(),
                            pltHeader.getRegionPeril().getRegionPerilCode(),
                            pltHeader.getFinancialPerspective().getCode(),
                            pltHeader.getCurrency().getCode(),
                            XLTOT.ORIGINAL,
                            pltHeader.getTargetRap().getTargetRapId(),
                            pltHeader.getPltSimulationPeriods(),
                            PLTPublishStatus.PURE,
                            0, // pure PLT, no thread number
                            pltHeader.getId(),
                            getFileExtension());
                    File file = makeFullFile(getPrefixDirectory(), filename);
                    writePLTLossDataNonRMS(pltLossDataList, file);

                    pltHeader.setPltLossDataFile(new BinFile(file));
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
                pltHeader.setTruncationThresholdEur(threshold);
            }

            // finish step 2 CONVERT_LOSS_DATA_TO_SCOR_FORMAT for one analysis in loop for of many analysis
//            Date endDate = new Date();
//            projectImportAssetLogA.setEndDate(endDate);
//            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 2 : CONVERT_LOSS_DATA_TO_SCOR_FORMAT for RRAnalysis : {}", bundle.getRrAnalysis().getRrAnalysisId());
        }

        return RepeatStatus.FINISHED;
    }

    private static boolean DBG = true;

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

            scorPLTHeader.setPltLossDataFile(null);
            scorPLTHeader.setPltStatisticList(null);

            scorPLTHeader.setPltGrouping(PLTGrouping.UnGrouped);
            scorPLTHeader.setPltInuring(PLTInuring.None);
            scorPLTHeader.setPltStatus(PLTStatus.Pending);
            scorPLTHeader.setInuringPackageDefinition(null);
            // TODO how ???
            scorPLTHeader.setPltType(PLTType.Pure);

            ProjectEntity project = projectRepository.findById(getProjectId());
            scorPLTHeader.setProject(project);
            scorPLTHeader.setRrRepresentationDatasetId(transformationPackage.getRrRepresentationDatasetId());
            scorPLTHeader.setRrAnalysisId(bundle.getRrAnalysis().getId());

            CurrencyEntity currency = currencyRepository.findByCode(bundle.getRrAnalysis().getSourceCurrency());
            scorPLTHeader.setCurrency(currency);
            scorPLTHeader.setTargetRap(targetRap);

            RegionPerilEntity regionPeril = ttRegionPerilRepository.findByRegionPerilCode(bundle.getRrAnalysis().getRegionPeril());

            scorPLTHeader.setRegionPeril(regionPeril);
            scorPLTHeader.setFinancialPerspective(fpUP);

            scorPLTHeader.setAdjustmentStructure(null);
            scorPLTHeader.setCatAnalysisDefinition(null);

            scorPLTHeader.setSourcePLTHeader(null);
            scorPLTHeader.setSystemShortName("Pure-" + i); // FIXME: 16/07/2016
            scorPLTHeader.setUserShortName("Pure-u-" + i); // FIXME: 16/07/2016
            scorPLTHeader.setTags(null);

            scorPLTHeader.setCreatedDate(new Date());
            scorPLTHeader.setxActPublicationDate(null);
            scorPLTHeader.setxActUsed(false);
            scorPLTHeader.setxActAvailable(false);

            scorPLTHeader.setGeneratedFromDefaultAdjustement(false);
            scorPLTHeader.setUserSelectedGrain(bundle.getRrAnalysis().getGrain());
            scorPLTHeader.setExportedDPM(false);

            scorPLTHeader.setGeoCode(bundle.getRrAnalysis().getGeoCode());
            scorPLTHeader.setGeoDescription(bundle.getRrAnalysis().getGeoCode());
            scorPLTHeader.setPerilCode(bundle.getRrAnalysis().getPeril());

            // TODO how ???
//            scorPLTHeader.setEngineType(bundle.getRmsAnalysis().getEngineType());
            scorPLTHeader.setInstanceId(bundle.getInstanceId());
            scorPLTHeader.setImportSequence(getImportSequence());

            scorPLTHeader.setSourceLossModelingBasis(modelingBasis);

            scorPLTHeader.setUdName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1.T0");
            scorPLTHeader.setPltName(bundle.getRrAnalysis().getRegionPeril() + "_" + bundle.getRrAnalysis().getFinancialPerspective() + "_LMF1");
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
