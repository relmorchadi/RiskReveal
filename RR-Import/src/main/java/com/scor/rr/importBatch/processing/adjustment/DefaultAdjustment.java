//package com.scor.rr.importBatch.processing.adjustment;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.scor.almf.ihub.treaty.processing.batch.rms.BaseRMSBeanImpl;
//import com.scor.almf.ihub.treaty.processing.treaty.PLTBundle;
//import com.scor.almf.ihub.treaty.processing.treaty.TransformationBundle;
//import com.scor.almf.ihub.treaty.processing.treaty.TransformationPackage;
//import com.scor.almf.ihub.treaty.processing.ylt.PLTHandler;
//import com.scor.almf.treaty.cdm.domain.dss.agnostic.tracking.ProjectImportAssetLog;
//import com.scor.almf.treaty.cdm.domain.dss.agnostic.tracking.Step;
//import com.scor.almf.treaty.cdm.domain.plt.PLTConverterProgress;
//import com.scor.almf.treaty.cdm.domain.plt.ScorPLTHeader;
//import com.scor.almf.treaty.cdm.domain.rap.TargetRap;
//import com.scor.almf.treaty.cdm.repository.dss.agnostic.ProjectImportAssetLogRepository;
//import com.scor.almf.treaty.cdm.repository.rap.TargetRapRepository;
//import com.scor.almf.treaty.cdm.tools.sequence.MongoDBSequence;
//import com.scor.almf.treaty.dao.DAOService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.util.Date;
//
//public class DefaultAdjustment extends BaseRMSBeanImpl implements PLTHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(DefaultAdjustment.class);
//
//    @Autowired
//    private TransformationPackage transformationPackage;
//
//    @Autowired
//    private TargetRapRepository targetRapRepository;
//
//    @Autowired
//    private DAOService daoService;
//
//    @Autowired
//    private MongoDBSequence mongoDBSequence;
//
//    private final String defAdjURL;
//
//    public DefaultAdjustment(String batchRestHost, String batchRestPort, String batchRestDefAdj) {
//        final URI uri = UriComponentsBuilder.newInstance().scheme("http").host(batchRestHost).port(Integer.parseInt(batchRestPort)).build().toUri();
//        defAdjURL = UriComponentsBuilder.fromUri(uri).path(batchRestDefAdj).build().toString();
//    }
//
//
//    @Autowired
//    private ProjectImportAssetLogRepository projectImportAssetLogRepository;
//
//    @Override
//    public Boolean handle() {
//        log.debug("Starting DefaultAdjustment");
//
//        Date startDate = new Date();
//
//        for (TransformationBundle bundle : transformationPackage.getBundles()) {
//            // start new step (import progress) : step 16 ADJUST_DEFAULT for this analysis in loop of many analysis :
//            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
//            mongoDBSequence.nextSequenceId(projectImportAssetLogA);
//            projectImportAssetLogA.setProjectId(transformationPackage.getProjectId());
//            projectImportAssetLogA.setStepId(16);
//            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
//            projectImportAssetLogA.setStartDate(startDate);
//            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
//            // --------------------------------------------------------------------
//
//            if (bundle.getConformedRRLT() == null) {
//                log.error("ERROR in ELT {}, no PLTs found", bundle.getConformedRRLT().getId());
//
//                // must ask Viet same check error on step 14, 15
//                projectImportAssetLogA.getErrorMessages().add(String.format("ERROR in ELT %s, no PLTs found", bundle.getConformedRRLT().getId()));
//                Date endDate = new Date();
//                projectImportAssetLogA.setEndDate(endDate);
//                projectImportAssetLogRepository.save(projectImportAssetLogA);
//                log.info("Finish import progress STEP 16 : ADJUST_DEFAULT for analysis: {}", bundle.getSourceResult().getId());
//                continue;
//            }
//
//            if (bundle.getPltBundles() == null) {
//                // must ask Viet same check error on step 14, 15
//                projectImportAssetLogA.getErrorMessages().add(String.format("ERROR in ELT %s, no PLTs found", bundle.getConformedRRLT().getId()));
//                Date endDate = new Date();
//                projectImportAssetLogA.setEndDate(endDate);
//                projectImportAssetLogRepository.save(projectImportAssetLogA);
//                log.info("Finish import progress STEP 16 : ADJUST_DEFAULT for analysis: {}", bundle.getSourceResult().getId());
//                continue;
//            }
//
//            log.info("{} threads", bundle.getPltBundles().size());
//            for (PLTBundle pltBundle : bundle.getPltBundles()) {
////                log.info("plt {}, isPLTError {}", pltBundle.getHeader().getId(), pltBundle.getPltError());
////                if (pltBundle.getPltError()) {
////                    continue;
////                }
//                PLTConverterProgress pltConverterProgress = daoService.findPLTConverterProgressBy(pltBundle.getHeader100k().getId());
//                pltConverterProgress.setStartDefaultAdj(new Date());
//                daoService.persistPLTConverterProgress(pltConverterProgress);
//                pltBundle.freePLTData();
//                ScorPLTHeader pltHeader100k = pltBundle.getHeader100k();
//                TargetRap targetRap = pltHeader100k.getTargetRap();
//                if (targetRap.getTargetRapId() == null || targetRap.getTargetRapCode() == null) {
//                    targetRap = targetRapRepository.findOne(targetRap.getId());
//                }
//
//                String projectId = pltHeader100k.getProject().getId();
//                String pltHeaderId = pltHeader100k.getId();
//                Long analysisId = bundle.getRmsAnalysis().getAnalysisId();
//                String analysisName = bundle.getRmsAnalysis().getAnalysisName();
//                Integer targetRapId = targetRap.getTargetRapId();
//                String sourceConfigVendor = "RMS";
//
//                DefaultAdjustmentRequest defAdjRequest = new DefaultAdjustmentRequest(pltHeaderId);
//                defAdjRequest.setProjectId(projectId);
//                defAdjRequest.setAnalysisId(analysisId);
//                defAdjRequest.setAnalysisName(analysisName);
//                defAdjRequest.setRdmId(rdmId);
//                defAdjRequest.setRdmName(rdm);
//                defAdjRequest.setTargetRapId(targetRapId);
//
//                Gson gson = new GsonBuilder().create();
//                String jsonOutput = gson.toJson(defAdjRequest);
//
//                RestTemplate restTemplate = new RestTemplate();
//                final Integer response = restTemplate.postForObject(defAdjURL, null, Integer.class,
//                        projectId, pltHeaderId, analysisId, analysisName, rdmId, rdm, targetRapId, sourceConfigVendor);
//                log.info("rdmName {}, rdmId {}, TargetRAP {} found, 100k-PLT {}", rdm, rdmId, targetRap.getTargetRapId(), pltHeader100k.getId());
//                log.info("Response from default adjustment: {}", response);
//                pltConverterProgress.setEndDefaultAdj(new Date());
//                daoService.persistPLTConverterProgress(pltConverterProgress);
//            }
//
//            // finish step 16 ADJUST_DEFAULT for one analysis in loop for of many analysis
//            Date endDate = new Date();
//            projectImportAssetLogA.setEndDate(endDate);
//            projectImportAssetLogRepository.save(projectImportAssetLogA);
//            log.info("Finish import progress STEP 16 : ADJUST_DEFAULT for analysis: {}", bundle.getSourceResult().getId());
//        }
//        log.debug("DefaultAdjustment completed");
//        return true;
//    }
//
//    public TransformationPackage getTransformationPackage() {
//        return transformationPackage;
//    }
//
//    public void setTransformationPackage(TransformationPackage transformationPackage) {
//        this.transformationPackage = transformationPackage;
//    }
//
//}
