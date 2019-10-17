package com.scor.rr.importBatch.processing.treaty.services;

//import com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle.PLTBundleNonRMS;
//import com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle.TransformationBundleNonRMS;
//import com.scor.rr.importBatch.processing.nonRMSbatch.workflow.bundle.TransformationPackageNonRMS;
//import com.scor.rr.importBatch.processing.treaty.PLTBundle;
//import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
//import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
//import com.scor.rr.importBatch.domain.AccumulationProfile;
//import com.scor.rr.importBatch.domain.AccumulationRapDetail;
//import com.scor.rr.importBatch.domain.Contract;
//import com.scor.rr.importBatch.domain.ContractSectionExpectedScope;
//import com.scor.rr.importBatch.domain.Section;
//import com.scor.rr.importBatch.domain.ScorPLTHeader;
//import com.scor.rr.importBatch.domain.DistinctRapAndAdj;
//import com.scor.rr.importBatch.domain.TargetRap;
//import com.scor.rr.importBatch.domain.RegionPeril;
//import com.scor.rr.importBatch.domain.Workspace;
//import com.scor.rr.importBatch.repository.AccumulationProfileRepository;
//import com.scor.rr.importBatch.repository.AccumulationRapDetailRepository;
//import com.scor.rr.importBatch.repository.ContractRepository;
//import com.scor.rr.importBatch.repository.ContractSectionExpectedScopeRepository;
//import com.scor.rr.importBatch.repository.SectionRepository;
//import com.scor.rr.importBatch.repository.DistinctRapAndAdjRepository;
//import com.scor.rr.importBatch.repository.TargetRapRepository;
//import com.scor.rr.importBatch.repository.TTRegionPerilRepository;
//import com.scor.rr.importBatch.repository.WorkspaceRepository;
////import com.scor.almf.treaty.cdm.tools.Utils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

/**
 * Created by u004602 on 18/07/2017.
 */
public class PostImportService {
//    private static final Logger log= LoggerFactory.getLogger(PostImportService.class);
//
//    @Autowired
//    private WorkspaceRepository workspaceRepository;
//    @Autowired
//    private SectionRepository sectionRepository;
//    @Autowired
//    private ContractRepository contractRepository;
//    @Autowired
//    private ContractSectionExpectedScopeRepository contractSectionExpectedScopeRepository;
//    @Autowired
//    private DistinctRapAndAdjRepository distinctRapAndAdjRepository;
//    @Autowired
//    private AccumulationProfileRepository accumulationProfileRepository;
//    @Autowired
//    private AccumulationRapDetailRepository accumulationRapDetailRepository;
//    @Autowired
//    private TargetRapRepository targetRapRepository;
//    @Autowired
//    private TTRegionPerilRepository ttRegionPerilRepository;
//
//
//    private TransformationPackage transformationPackage;
//    private TransformationPackageNonRMS transformationPackageNonRMS;
//    private String projectId;
//
//    public TransformationPackage getTransformationPackage() {
//        return transformationPackage;
//    }
//
//    public void setTransformationPackage(TransformationPackage transformationPackage) {
//        this.transformationPackage = transformationPackage;
//    }
//
//    public TransformationPackageNonRMS getTransformationPackageNonRMS() {
//        return transformationPackageNonRMS;
//    }
//
//    public void setTransformationPackageNonRMS(TransformationPackageNonRMS transformationPackageNonRMS) {
//        this.transformationPackageNonRMS = transformationPackageNonRMS;
//    }
//
//    public String getProjectId() {
//        return projectId;
//    }
//
//    public void setProjectId(String projectId) {
//        this.projectId = projectId;
//    }
//
//    public void updateContractSectionExpectedScope() { // TODO TIEN must ask Viet
//        log.debug("=== Started updateContractSectionExpectedScope");
//        Workspace workspace = workspaceRepository.findByProjectProjectId(getProjectId());
//        if (workspace == null) {
//            log.error("Something wrong: workspace not found !!!");
//            return;
//        }
//        List<Section> listTreatySection = new ArrayList<>();
//        if (workspace.getContractId() != null) {
//            List<Contract> contracts = null;
//            if ("Bouquet".equals(workspace.getWorkspaceContextFlag().toString())) {
//                contracts = contractRepository.findContractsByBouquetIdAndUwYear(workspace.getWorkspaceContextCode(), workspace.getWorkspaceUwYear());
//            } else if ("Program".equals(workspace.getWorkspaceContextFlag().toString())) {
//                contracts = contractRepository.findContractsByProgramIdAndUwYear(workspace.getWorkspaceContextCode(), workspace.getWorkspaceUwYear());
//            } else {
//                contracts = contractRepository.findContractsByTreatyIdAndUwYear(workspace.getWorkspaceContextCode(), workspace.getWorkspaceUwYear());
//            }
//
//            if (contracts != null && !contracts.isEmpty()) {
//                for (Contract contract : contracts) {
//                    List<Section> sections = sectionRepository.findByContract(contract);
//                    if (sections != null && !sections.isEmpty()) {
//                        listTreatySection.addAll(sections);
//                    }
//                }
//            }
//        }
//
//
//        Map<String, RegionPeril> minimumGrainImported = new HashMap<>();
//        if (transformationPackage != null) {
//            for (TransformationBundle bundle : transformationPackage.getBundles()) {
//                if (bundle.getPltBundles() != null && !bundle.getPltBundles().isEmpty()) {
//                    for (PLTBundle pltBundle : bundle.getPltBundles()) {
//                        ScorPLTHeader scorPLTHeader = pltBundle.getHeader();
//                        if (scorPLTHeader == null || scorPLTHeader.getRegionPeril() == null) {
//                            log.error("Something wrong !!!");
//                            continue;
//                        }
//                        String minimumGrain = scorPLTHeader.getRegionPeril().getParentMinimumGrainRegionPeril();
//                        if (!StringUtils.isEmpty(minimumGrain)) {
//                            minimumGrainImported.put(minimumGrain, scorPLTHeader.getRegionPeril());
//                        }
//                    }
//                }
//            }
//        } else if (transformationPackageNonRMS != null) {
//            for (TransformationBundleNonRMS bundle : transformationPackageNonRMS.getBundles()) {
//                for (PLTBundleNonRMS pltBundle : bundle.getPltBundles()) {
//                    ScorPLTHeader scorPLTHeader = pltBundle.getHeader();
//                    if (scorPLTHeader == null || scorPLTHeader.getRegionPeril() == null) {
//                        log.error("Something wrong !!!");
//                        continue;
//                    }
//                    String minimumGrain = scorPLTHeader.getRegionPeril().getParentMinimumGrainRegionPeril();
//                    if (!StringUtils.isEmpty(minimumGrain)) {
//                        minimumGrainImported.put(minimumGrain, scorPLTHeader.getRegionPeril());
//                    }
//                }
//            }
//        }
//
//        for (Map.Entry<String, RegionPeril> entry : minimumGrainImported.entrySet()) {
//            String minimumGrain = entry.getKey();
//            List<ContractSectionExpectedScope> contractSectionExpectedScopes = contractSectionExpectedScopeRepository.findByWorkspaceIdAndMinimumGrainRegionPerilCode(workspace.getId(), minimumGrain);
//            if (contractSectionExpectedScopes != null && !contractSectionExpectedScopes.isEmpty()) {
//                for (ContractSectionExpectedScope contractSectionExpectedScope : contractSectionExpectedScopes) {
//                    contractSectionExpectedScope.setImported(Boolean.TRUE);
//                }
//                contractSectionExpectedScopeRepository.save(contractSectionExpectedScopes);
//            } else {
//                log.debug("ContractSectionExpectedScope not found for workspace {} and minimun grain {} - create new one", workspace.getId(), minimumGrain);
//                List<AccumulationProfile> accumulationProfiles = accumulationProfileRepository.findByMinimumGrainRegionPerilCode(minimumGrain);
//                List<DistinctRapAndAdj> distinctRapAndAdjs = distinctRapAndAdjRepository.findByMinimalGrainCode(minimumGrain);
//                if (accumulationProfiles != null && !accumulationProfiles.isEmpty()) {
//                    for (Section section : listTreatySection) {
//                        for (AccumulationProfile accumulationProfile : accumulationProfiles) {
//                            ContractSectionExpectedScope contractSectionExpectedScope = new ContractSectionExpectedScope();
//                            AccumulationRapDetail accumulationRapDetail = accumulationRapDetailRepository.findByAccumulationRapId(accumulationProfile.getAccumulationRAPid());
//                            contractSectionExpectedScope.setWorkspaceId(workspace.getId());
//                            TargetRap targetRap = null;
//                            if (accumulationRapDetail != null) {
//                                targetRap = targetRapRepository.findByTargetRAPId(accumulationRapDetail.getTargetRapId());
//                            }
//                            if (targetRap != null) {
//                                contractSectionExpectedScope.setModellingVendor(targetRap.getModellingVendor());
//                                contractSectionExpectedScope.setModellingSystem(targetRap.getModellingSystem());
//                            } else {
//                                contractSectionExpectedScope.setModellingVendor("RMS");
//                                contractSectionExpectedScope.setModellingSystem("RL");
//                            }
//
//                            RegionPeril regionPeril = entry.getValue();
//                            RegionPeril minimunRP = null;
//                            if (regionPeril.getParentMinimumGrainRegionPeril() != null) {
//                                minimunRP = ttRegionPerilRepository.findByRegionPerilCode(regionPeril.getParentMinimumGrainRegionPeril());
//                            }
//
//                            contractSectionExpectedScope.setPerilCode(minimunRP != null ? minimunRP.getPerilCode() : regionPeril.getPerilCode());
//                            contractSectionExpectedScope.setRootRegionPerilCode(Utils.rootRegionPerilFromRegionPeril(regionPeril, ttRegionPerilRepository));
//                            contractSectionExpectedScope.setRegionPerilId(regionPeril.getRegionPerilID());
//                            contractSectionExpectedScope.setRegionPerilCode(regionPeril.getRegionPerilCode());
//                            contractSectionExpectedScope.setRegionPerilDesc(regionPeril.getRegionPerilDesc());
//                            contractSectionExpectedScope.setMinimumGrainRegionPerilCode(regionPeril.getParentMinimumGrainRegionPeril());
//                            contractSectionExpectedScope.setAccumulationRapId(accumulationProfile.getAccumulationRAPid());
//                            contractSectionExpectedScope.setAccumulationRapCode(accumulationProfile.getAccumulationRapCode());
//                            contractSectionExpectedScope.setAccumulationProfile(accumulationProfile);
//                            contractSectionExpectedScope.setBaseAdjustments(accumulationProfile.getBaseAdjustments());
//                            contractSectionExpectedScope.setDefaultAdjustments(accumulationProfile.getDefaultAdjustments());
//                            contractSectionExpectedScope.setAnalystAdjustments(accumulationProfile.getAnalystAdjustments());
//                            contractSectionExpectedScope.setClientAdjustments(accumulationProfile.getClientAdjustments());
//                            contractSectionExpectedScope.setOmega(Boolean.FALSE);
//                            contractSectionExpectedScope.setImported(Boolean.TRUE);
//                            contractSectionExpectedScope.setSection(section);
//                            contractSectionExpectedScope.calcId();
//                            contractSectionExpectedScopeRepository.save(contractSectionExpectedScope);
//                            log.trace("Save contract section expected scope {}", contractSectionExpectedScope.getId());
//                        }
//                    }
//                }
//            }
//        }
//
//        log.debug("=== Completed updateContractSectionExpectedScope");
//    }
}
