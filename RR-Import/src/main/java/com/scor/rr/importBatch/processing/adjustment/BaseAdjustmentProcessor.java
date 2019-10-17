/*
package com.scor.rr.importBatch.processing.adjustment;

import com.scor.almf.cdm.domain.adjustments.AdjustmentStep;
import com.scor.almf.cdm.domain.adjustments.AdjustmentTree;
import com.scor.almf.cdm.domain.adjustments.PLTDataState;
import com.scor.almf.cdm.domain.cat.*;
import com.scor.almf.cdm.domain.reference.DefaultReturnPeriod;
import com.scor.almf.cdm.domain.reference.ModellingSystemInstance;
import com.scor.almf.cdm.repository.adjustments.AdjustmentTreeRepository;
import com.scor.almf.cdm.repository.cat.CATAnalysisRequestRepository;
import com.scor.almf.cdm.repository.cat.CATObjectGroupRepository;
import com.scor.almf.cdm.repository.reference.CurrencyRepository;
import com.scor.almf.cdm.repository.reference.DefaultReturnPeriodRepository;
import com.scor.almf.cdm.repository.reference.ModellingSystemInstanceRepository;
import com.scor.almf.ihub.treaty.businessrules.IBusinessRulesService;
import com.scor.almf.ihub.treaty.businessrules.bean.BusinessRulesBean;
import com.scor.almf.ihub.treaty.processing.batch.BaseBatchBeanImpl;
import com.scor.almf.ihub.treaty.processing.batch.Notifier;
import com.scor.almf.ihub.treaty.processing.domain.PLTLoss;
import com.scor.almf.ihub.treaty.processing.statistics.rms.EPCurveWriter;
import com.scor.almf.ihub.treaty.processing.statistics.rms.EPSWriter;
import com.scor.almf.ihub.treaty.processing.treaty.adjustment.logic.BaseAdjustment;
import com.scor.almf.ihub.treaty.processing.treaty.loss.PLTLossData;
import com.scor.almf.ihub.treaty.processing.treaty.services.TransformationUtils;
import com.scor.almf.ihub.treaty.processing.ylt.*;
import com.scor.almf.ihub.treaty.processing.ylt.meta.PLTPublishStatus;
import com.scor.almf.ihub.treaty.processing.ylt.meta.XLTOT;
import com.scor.almf.ihub.treaty.processing.ylt.meta.XLTSubType;
import com.scor.almf.treaty.cdm.domain.adjustment.AdjustmentLibrary;
import com.scor.almf.treaty.cdm.domain.adjustment.AdjustmentNode;
import com.scor.almf.treaty.cdm.domain.adjustment.AdjustmentStructure;
import com.scor.almf.treaty.cdm.domain.adjustment.meta.AdjustementNodeMode;
import com.scor.almf.treaty.cdm.domain.adjustment.meta.AdjustmentCategory;
import com.scor.almf.treaty.cdm.domain.adjustment.meta.AdjustmentNodeStatus;
import com.scor.almf.treaty.cdm.domain.dss.FinancialPerspective;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ProjectImportRun;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ihub.RRFinancialPerspective;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.ihub.RRLossTable;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.meta.RRAnalysis;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RREPCurve;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RRStatisticHeader;
import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RRSummaryStatistic;
import com.scor.almf.treaty.cdm.domain.omega.client.Client;
import com.scor.almf.treaty.cdm.domain.omega.contract.Section;
import com.scor.almf.treaty.cdm.domain.plt.ScorPLTHeader;
import com.scor.almf.treaty.cdm.domain.plt.meta.PLTInuring;
import com.scor.almf.treaty.cdm.domain.plt.meta.PLTStatus;
import com.scor.almf.treaty.cdm.domain.plt.meta.PLTType;
import com.scor.almf.treaty.cdm.domain.plt.meta.StatisticMetric;
import com.scor.almf.treaty.cdm.domain.rap.TargetRap;
import com.scor.almf.treaty.cdm.domain.reference.meta.BinFile;
import com.scor.almf.treaty.cdm.domain.region.RegionPeril;
import com.scor.almf.treaty.cdm.domain.region.WorkspaceMinimumGrain;
import com.scor.almf.treaty.cdm.domain.workspace.Project;
import com.scor.almf.treaty.cdm.domain.workspace.Workspace;
import com.scor.almf.treaty.cdm.repository.adjustment.AdjustmentNodeRepository;
import com.scor.almf.treaty.cdm.repository.dss.RmsProjectImportConfigRepository;
import com.scor.almf.treaty.cdm.repository.dss.TTFinancialPerspectiveRepository;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.ProjectImportRunRepository;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.RRAnalysisRepository;
import com.scor.almf.treaty.cdm.repository.dss.agnostic.RRStatisticHeaderRepository;
import com.scor.almf.treaty.cdm.repository.omega.client.ClientRepository;
import com.scor.almf.treaty.cdm.repository.omega.treaty.ContractRepository;
import com.scor.almf.treaty.cdm.repository.omega.treaty.SectionRepository;
import com.scor.almf.treaty.cdm.repository.plt.ScorPLTHeaderRepository;
import com.scor.almf.treaty.cdm.repository.rap.TargetRapRepository;
import com.scor.almf.treaty.cdm.repository.region.TTRegionPerilRepository;
import com.scor.almf.treaty.cdm.repository.region.WorkspaceMinimumGrainRepository;
import com.scor.almf.treaty.cdm.repository.workspace.ProjectRepository;
import com.scor.almf.treaty.cdm.repository.workspace.WorkspaceRepository;
import com.scor.almf.treaty.cdm.tools.AdjustmentUtils;
import com.scor.almf.treaty.cdm.tools.sequence.MongoDBSequence;
import com.scor.almf.treaty.dao.DAOService;
import com.scor.rr.domain.adjustments.AdjustmentTree;
import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.references.DefaultReturnPeriod;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.references.TargetRAP;
import com.scor.rr.domain.entities.references.cat.ModellingSystemInstance;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.references.plt.FinancialPerspective;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.entities.workspace.Workspace;
import com.scor.rr.importBatch.businessrules.IBusinessRulesService;
import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.batch.Notifier;
import com.scor.rr.importBatch.processing.statistics.rms.EPCurveWriter;
import com.scor.rr.importBatch.processing.statistics.rms.EPSWriter;
import com.scor.rr.importBatch.processing.ylt.*;
import com.scor.rr.repository.TTRegionPerilRepository;
import com.scor.rr.repository.cat.CATObjectGroupRepository;
import com.scor.rr.repository.cat.CATRequestRepository;
import com.scor.rr.repository.cat.ModellingSystemInstanceRepository;
import com.scor.rr.repository.omega.ContractRepository;
import com.scor.rr.repository.omega.CurrencyRepository;
import com.scor.rr.repository.omega.SectionRepository;
import com.scor.rr.repository.plt.ProjectImportRunRepository;
import com.scor.rr.repository.plt.RRAnalysisRepository;
import com.scor.rr.repository.plt.ScorPLTHeaderRepository;
import com.scor.rr.repository.plt.TTFinancialPerspectiveRepository;
import com.scor.rr.repository.rap.TargetRapRepository;
import com.scor.rr.repository.references.DefaultReturnPeriodRepository;
import com.scor.rr.repository.rms.RmsProjectImportConfigRepository;
import com.scor.rr.repository.stat.RRStatisticHeaderRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import com.scor.rr.repository.workspace.WorkspaceRepository;
import com.scor.rr.service.omega.DAOService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import com.scor.almf.treaty.cdm.repository.dss.ProjectDatasourceAssociationRepository;

public class BaseAdjustmentProcessor extends BaseBatchBeanImpl implements AdjustmentProcessor {

    private static Logger log = LoggerFactory.getLogger(BaseAdjustmentProcessor.class);

    static int N_CPU = Runtime.getRuntime().availableProcessors();

    static int getThreadPoolSize() {
        float cpuUtilization = 1f; // 0 to 1
        float waitToComputeTimeRatio = 1f;
        int size = (int) (N_CPU * cpuUtilization * (1 + waitToComputeTimeRatio));
        log.info("getThreadPoolSize {}", size);
        return size;
    }

    IBusinessRulesService businessRulesService;
    PLTReader pltReader;
    EPCurveCalculator epCurveCalculator;
    EPCurveWriter epCurveWriter;
    EPSWriter epsWriter;
    PLTWriter pltWriter;
    PLTSaver pltSaver;
    Notifier stepNotifier;
    Notifier treeNotifier;
    AdjustmentTreeRepository repository;
    CATRequestRepository catAnalysisRequestRepository;
    CATObjectGroupRepository catObjectGroupRepository;

//    @Autowired
//    private PLTEPHeaderRepository pltepHeaderRepository;

    @Autowired
    private ScorPLTHeaderRepository scorPLTHeaderRepository;

    @Autowired
    private AdjustmentNodeRepository adjustmentNodeRepository;

//    @Autowired
//    private MongoDBSequence mongoDBSequence;

    @Autowired
    private TTFinancialPerspectiveRepository ttFinancialPerspectiveRepository;

    @Autowired
    private TTRegionPerilRepository ttRegionPerilRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TargetRapRepository targetRapRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RRAnalysisRepository rrAnalysisRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private RmsProjectImportConfigRepository rmsProjectImportConfigRepository;

    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;

    @Autowired
    private WorkspaceMinimumGrainRepository workspaceMinimumGrainRepository;

    private FinancialPerspective fpUF;

    private FinancialPerspective fpUA;

    private Currency currency;

    private TargetRAP targetRap;

    private RegionPeril regionPeril;

    private static final int FIXED_POOL_SIZE = 100;
    private volatile ExecutorService pool; //TODO: pool should be static and sharing between instances ?
    private volatile AdjustmentTree mTree;

    private String ihubPath;

    private DAOService daoService;

    private PLTEPCurveCalculator pltEPCurveCalculator;

    private DefaultReturnPeriodRepository drpRepo;

    private AdjustmentStructure adjustmentStructure;
    private BinFile purePLTLossFile;
    private ScorPLTHeader purePLTHeader;
//    private ELTHeader pureELTHeader;
    private RRLossTable pureRRLT;
    private Project project;
    private Map<BigDecimal, Integer> epRPMap;
    private Map<Integer, Double> rpEPMap;
    private Integer nNodes;
    private volatile Integer nRemainingNodes;

    public RRLossTable getPureRRLT() {
        return pureRRLT;
    }

    public void setPureRRLT(RRLossTable pureRRLT) {
        this.pureRRLT = pureRRLT;
    }

    private void extractEPRPMap() {
        if (epRPMap != null && rpEPMap != null) {
            return;
        }
        MathContext mc = MathContext.DECIMAL64;
        List<DefaultReturnPeriod> defaultRPS = drpRepo.findAll();
        epRPMap = new HashMap<>(defaultRPS.size());
        rpEPMap = new HashMap<>(defaultRPS.size());
        for (DefaultReturnPeriod drp : defaultRPS) {
            epRPMap.put(new BigDecimal(drp.getExcedanceProbability(), mc).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros(), drp.getReturnPeriod());
            rpEPMap.put(drp.getReturnPeriod(), drp.getExcedanceProbability());
        }
    }

    private File makeFullFile(String prefixDirectory, String filename) {
        final Path fullPath = Paths.get(ihubPath).resolve(prefixDirectory);
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


    */
/**
     * To be injected before any calculations
     *
     * @param properties
     *//*

    @Override
    public void setNamingProperties(Map<String, Object> properties) {
        setReinsuranceType((String) properties.get("reinsuranceType"));
        setPrefix((String) properties.get("prefix"));
        setClientName((String) properties.get("clientName"));
        setClientId((String) properties.get("clientId"));
        setContractId((String) properties.get("contractId"));
        setDivision((String) properties.get("division"));
        setUwYear((String) properties.get("uwYear"));
        setSourceVendor((String) properties.get("sourceVendor"));
        setModelSystemVersion((String) properties.get("modelSystemVersion"));
        setProjectId((String) properties.get("projectId"));
        setPeriodBasis((String) properties.get("periodBasis"));
        setImportSequence((Long) properties.get("importSequence"));
    }

    private Map<String, Object> extractNamingProperties(String projectId, String instanceId, Long importSequence, String rrAnalysisId) {
        Project project = projectRepository.findOne(projectId);
        Workspace myWorkspace = workspaceRepository.findByProjectId(projectId);
        if (myWorkspace == null) {
            log.error("Error. No workspace found");
            return null;
        }
        String workspaceCode = myWorkspace.getWorkspaceContextCode();
        String contractId = myWorkspace.getContractId();

        ModellingSystemInstance modellingSystemInstance = null;
        if (instanceId != null) {
            modellingSystemInstance = modellingSystemInstanceRepository.findOne(instanceId);
        }

        Section section = sectionRepository.findOne(contractId);
        com.scor.almf.treaty.cdm.domain.omega.contract.Contract contract = contractRepository.findOne(section.getContract().getId());
        Client client = clientRepository.findOne(contract.getClient().getId());

        String reinsuranceType = "T"; // fixed for TT
        String division = "01"; // fixed for TT

        RRAnalysis rrAnalysis = rrAnalysisRepository.findOne(rrAnalysisId);
        ProjectImportRun projectImportRun = projectImportRunRepository.findOne(rrAnalysis.getProjectImportRunId());
        String sourceVendor = !projectImportRun.getSourceConfigVendor().equals("Non RMS") ? "RMS-RiskLink" : "Non-RMS";

        String periodBasis = "FT"; // fixed for TT
        String prefix = myWorkspace.getWorkspaceContextFlag().getValue();
        String uwYear = myWorkspace.getWorkspaceUwYear() + "-01";
        String modelSystemVersion = modellingSystemInstance != null ? modellingSystemInstance.getModellingSystemVersion().getId() : null;

        String clientName = client.getClientShortName();
        String clientId = client.getId();

        log.info("reinsuranceType {}, prefix {}, clientName {}, clientId {}, contractId {}, division {}, uwYear {}, sourceVendor {}, modelSystemVersion {}, periodBasis {}, importSequence {}",
                reinsuranceType, prefix, clientName, clientId, contractId, division, uwYear, sourceVendor, modelSystemVersion, periodBasis, importSequence);

        Map<String, Object> map = new HashMap<>();
        map.put("reinsuranceType", reinsuranceType);
        map.put("prefix", prefix);
        map.put("clientName", clientName);
        map.put("clientId", clientId);
        map.put("contractId", workspaceCode);
        map.put("division", division);
        map.put("uwYear", uwYear);
        map.put("sourceVendor", sourceVendor);
        map.put("modelSystemVersion", modelSystemVersion);
        map.put("periodBasis", periodBasis);
        map.put("importSequence", importSequence);
        map.put("projectId", projectId);
        map.put("instanceId", instanceId);

        return map;
    }


    @Override
    public int processTree(String structureId) {
        log.info("PROCESS NODE OF STRUCTURE " + structureId);
        List<AdjustmentNode> nodes =  daoService.findAdjustmentNodeByStructureId(structureId);
        nNodes = nodes.size();
        nRemainingNodes = 0;
        AdjustmentNode topNode = null;
        for (AdjustmentNode node : nodes) {
            if (node.getParentNode() == null) {
                topNode = node;
                break;
            }
        }

        adjustmentStructure = topNode.getAdjustmentStructure();
        purePLTHeader = daoService.findScorPLTHeaderById(topNode.getSourcePLT().getId());
        purePLTLossFile = purePLTHeader.getPltLossDataFile();
//        pureELTHeader = daoService.findELTHeaderById(purePLTHeader.getEltHeader().getId());

        RRAnalysis rrAnalysis = rrAnalysisRepository.findOne(purePLTHeader.getRrAnalysisId());
        ProjectImportRun projectImportRun = projectImportRunRepository.findOne(rrAnalysis.getProjectImportRunId());

        if (! "Non RMS".equals(projectImportRun.getSourceConfigVendor())) {
            pureRRLT = daoService.findRRLossTableById(purePLTHeader.getRrLossTableId());
        } else {
            pureRRLT = null;
        }

//        project = pureELTHeader.getProject();
        project = purePLTHeader.getProject();
        if (fpUF == null) {
            fpUF = ttFinancialPerspectiveRepository.findByCode("UF");
        }
        if (fpUA == null) {
            fpUA = ttFinancialPerspectiveRepository.findByCode("UA");
        }
        if (currency == null) {
            if (purePLTHeader.getCurrency().getCode() == null) {
                currency = currencyRepository.findOne(purePLTHeader.getCurrency().getId());
            } else {
                currency = purePLTHeader.getCurrency();
            }
        }
        if (targetRap == null) {
            if (purePLTHeader.getTargetRap().getTargetRapCode() == null) {
                targetRap = targetRapRepository.findOne(purePLTHeader.getTargetRap().getId());
            } else {
                targetRap = purePLTHeader.getTargetRap();
            }
        }
        if (regionPeril == null) {
            if (purePLTHeader.getRegionPeril() == null) {
                regionPeril = ttRegionPerilRepository.findOne(purePLTHeader.getRegionPeril().getId());
            } else {
                regionPeril = purePLTHeader.getRegionPeril();
            }
        }

        Map<String, Object> properties = extractNamingProperties(project.getId(), purePLTHeader.getInstanceId(), purePLTHeader.getImportSequence(), purePLTHeader.getRrAnalysisId());
        if (properties == null) {
            return 0;
        }
        setNamingProperties(properties);

        log.info("adjustmentStructure {}, purePLTHeader {}, purePLTLossFile {}, pureELTHeader {}",
                adjustmentStructure.getId(),
                purePLTHeader.getId(),
                purePLTLossFile.getPath() + "//" + purePLTLossFile.getFileName(),
                pureRRLT != null ? pureRRLT.getId() : null);

        extractEPRPMap();

        pool = Executors.newFixedThreadPool(getThreadPoolSize());
        pool.execute(new RunAdjustment(null, topNode));
        pool.shutdown();
//        try {
//            pool.submit(new RunAdjustment(null, topNode)).get();
//        } catch (InterruptedException e) {
//            log.debug("Exception in processTree {}: {}", structureId, e);
//        } catch (ExecutionException e) {
//            log.debug("Exception in processTree {}: {}", structureId, e);
//        }

        return 1;
    }

    class RunAdjustment implements Runnable {
        AdjustmentNode parentNode;
        AdjustmentNode processNode;
        ExecutorService pool;

        public RunAdjustment(AdjustmentNode parentNode, AdjustmentNode processNode) {
            log.info("Thread {} initialized", processNode.getId());
            this.parentNode = parentNode;
            this.processNode = processNode;
            this.pool = Executors.newFixedThreadPool(getThreadPoolSize());
        }

        @Override
        public void run() {
            try {
                log.info("Thread {}-{} started", processNode.getId(), processNode.getId());
                processTreeSub(parentNode, processNode);
                log.info("thread {}-{} ended", processNode.getId(), processNode.getId());
            } catch (Exception e) {
                log.info("Thread {}-{} started", processNode.getId(), processNode.getId());
                e.printStackTrace();
            }
        }

        public void processTreeSub(AdjustmentNode parentNode, AdjustmentNode node) {
            try {
                processOne(parentNode, node);
            } catch (Exception e) {
                log.error("Error when processing node id {}: {}", node.getId(), e);
                e.printStackTrace();
                node.setStatus(AdjustmentNodeStatus.Error);
                adjustmentNodeRepository.save(node);
                //error in calculating this node, so status of their pending children will be invalid
                setChildrenPendingNodeStatus(node, AdjustmentNodeStatus.Error);
                return;
            }
            nRemainingNodes++;
            Integer percent = Integer.valueOf((int) (100d * nRemainingNodes / nNodes));
//            stepNotifier.notifyEvent(percent.toString());

            log.info("Progress = {}", percent);

            List<AdjustmentNode> childrenNodes = node.getChildrenNodes();
            if (childrenNodes == null || childrenNodes.size() == 0) {
                return;
            }
            for (AdjustmentNode childrenNode : childrenNodes) {
                //process only pending nodes
                if (!AdjustmentNodeStatus.Pending.equals(childrenNode.getStatus()) && !AdjustmentNodeStatus.Valid.equals(childrenNode.getStatus()))
                    continue;

                if (childrenNodes.size() == 1) {
                    processTreeSub(node, childrenNode);
                    return;
                }
                this.pool.execute(new RunAdjustment(node, childrenNode));
//                try {
//                    pool.submit(new RunAdjustment(node, childrenNode)).get();
//                } catch (InterruptedException e) {
//                    log.debug("Exception in processTreeSub node {}: {}", node.getId(), e);
//                } catch (ExecutionException e) {
//                    log.debug("Exception in processTreeSub node {}: {}", node.getId(), e);
//                }
            }
            this.pool.shutdown();
        }

        private void setChildrenPendingNodeStatus(AdjustmentNode parentNode, AdjustmentNodeStatus status) {
            List<AdjustmentNode> childrenNodes = parentNode.getChildrenNodes();
            if (childrenNodes != null && childrenNodes.size() != 0) {
                for (AdjustmentNode childrenNode : childrenNodes) {
                    if (childrenNode.getStatus().equals(AdjustmentNodeStatus.Pending)) {
                        childrenNode.setStatus(status);
                        adjustmentNodeRepository.save(childrenNode);
                        setChildrenPendingNodeStatus(childrenNode, status);
                    }
                }

            }
        }

        public void processOne(AdjustmentNode parentNode, AdjustmentNode node) throws Exception{
            // for logging
            String id = node.getId();
            String srcPLTid = null;
            String adjPLTid = null;

            if (AdjustmentUtils.isPureNode(node)) {
                ScorPLTHeader sourcePLT = node.getSourcePLT();
                ScorPLTHeader adjustedPLT = node.getAdjustedPLT();

                // for logging
                srcPLTid = node.getSourcePLT() == null ? null : node.getSourcePLT().getId();
                adjPLTid = node.getAdjustedPLT() == null ? null : node.getAdjustedPLT().getId();

                if (!AdjustmentNodeStatus.Valid.equals(AdjustmentUtils.checkStatus(node, daoService))) {
                    log.info("Pure Node {}, sourcePLT {}, adjustedPLT {}: invalid", id, srcPLTid, adjPLTid);
                } else {
                    log.info("Pure Node {}, sourcePLT {}, adjustedPLT {}: valid", id, srcPLTid, adjPLTid);
                }
                return;
            }
            if (AdjustmentUtils.isNullNode(node)) {
                if (AdjustmentNodeStatus.Valid.equals(node.getStatus())) {
                    log.info("Null Node {}, sourcePLT {}, adjustedPLT {}: unchanged", id, srcPLTid, adjPLTid);
                    return;
                }
                node.setSourcePLT(parentNode.getAdjustedPLT());
                node.setAdjustedPLT(parentNode.getAdjustedPLT());
                node.setStatus(AdjustmentNodeStatus.Valid);

                adjustmentNodeRepository.save(node);

                // for logging
                srcPLTid = node.getSourcePLT() == null ? null : node.getSourcePLT().getId();
                adjPLTid = node.getAdjustedPLT() == null ? null : node.getAdjustedPLT().getId();
                log.info("Null Node {}, sourcePLT {}, adjustedPLT {}: ignored", id, srcPLTid, adjPLTid);
                return;
            }

            if (AdjustmentUtils.isThreadNode(node)) {
                boolean updateThreadPLT = true;
                if (AdjustmentNodeStatus.Valid.equals(node.getStatus())) {
                    log.info("Thread Node {}, sourcePLT {}, adjustedPLT {}: unchanged", id, srcPLTid, adjPLTid);
                    return;
                }

                node.setStatus(AdjustmentNodeStatus.Processing);
                adjustmentNodeRepository.save(node);

                ScorPLTHeader parentPLT = scorPLTHeaderRepository.findOne(parentNode.getAdjustedPLT().getId());
                ScorPLTHeader threadPLT = node.getAdjustedPLT();
                if (threadPLT == null) {
                    // duplicate PLT for fast retrieval of thread nodes based on PLT

                    threadPLT = scorPLTHeaderRepository.findOne(parentPLT.getId());// duplicate PLT for fast retrieval of thread nodes based on PLT
                    String oldPLTId = threadPLT.getId();

                    mongoDBSequence.nextSequenceId(threadPLT);
                    updateThreadPLT = false;
                    log.info("Old PLT {}, new PLT {}", oldPLTId, threadPLT.getId());
                }


                //delete the prior PLT loss data file before create a new one
                if (updateThreadPLT) {
                    final BinFile oldBinFile = threadPLT.getPltLossDataFile();
                    File oldDATFile = new File(oldBinFile.getPath(), oldBinFile.getFileName());
                    oldDATFile.delete();
                    log.info("Deleted file {}", oldDATFile);
                    String oldEPCName = oldBinFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPC + "$2");
                    File oldEPCFile = new File(oldBinFile.getPath(), oldEPCName);
                    oldEPCFile.delete();
                    log.info("Deleted file {}", oldEPCFile);
                    String oldEPSName = oldBinFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPS + "$2");
                    File oldEPSFile = new File(oldBinFile.getPath(), oldEPSName);
                    oldEPSFile.delete();
                    log.info("Deleted file {}", oldEPSFile);

                }

//                List<PLTEPHeader> epHeaders = pltepHeaderRepository.findByScorPLTHeader(parentPLT);
                List<RRStatisticHeader> rrStatisticHeaders = rrStatisticHeaderRepository.findByLossTableId(parentPLT.getId());

//                for (PLTEPHeader epHeader : epHeaders) {
//                    epHeader.setScorPLTHeader(threadPLT);
//                    epHeader.setId(null);
//                    mongoDBSequence.nextSequenceId(epHeader);
//                }

                for (RRStatisticHeader rrStatisticHeader : rrStatisticHeaders) {
                    rrStatisticHeader.setLossTableId(threadPLT.getId());
                    rrStatisticHeader.setId(null);
                    mongoDBSequence.nextSequenceId(rrStatisticHeader);
                }

                Date createdDate = new Date();
                final BinFile srcBinFile = parentPLT.getPltLossDataFile();

                File srcFile = new File(srcBinFile.getPath(), srcBinFile.getFileName());
                File destFile = makeFullFile(getPrefixDirectory(), makePLTFileName(
                        createdDate,
                        regionPeril.getRegionPerilCode(),
                        fpUF.getFullCode(),
                        currency.getCode(),
                        XLTOT.TARGET,
                        targetRap.getTargetRapId(),
                        purePLTHeader.getPltSimulationPeriods(),
                        PLTPublishStatus.PURE,
                        node.getThreadId(),
                        threadPLT.getId(),
                        getFileExtension()));
                try {
                    TransformationUtils.copyFile(srcFile, destFile);
                    log.info("Creating file {} by copying {}", destFile, srcFile);
                } catch (IOException e) {
                    log.debug("Exception in copying file: {}", e);
                    node.setStatus(AdjustmentNodeStatus.Error);
                    adjustmentNodeRepository.save(node);
                    return;
                }

                // Modify new fields only
                threadPLT.setPltLossDataFile(new BinFile(destFile));
//                threadPLT.setPLTEPHeaders(epHeaders);
                threadPLT.setPltStatisticList(rrStatisticHeaders);

                threadPLT.setPltStatus(PLTStatus.ValidFull);
                threadPLT.setPltType(PLTType.Thread);

                // Tien :
//                List<ScorPLTHeader> threadScorPLTHeadersForCount = new ArrayList<>();
                // TODO code not good in comments, must take into account AdjustmentStructure
//                threadScorPLTHeadersForCount = scorPLTHeaderRepository.findByProjectAndPltType(project, "Thread");
//                int countThreadPLT = threadScorPLTHeadersForCount.size() + 1;
//                threadPLT.setThreadName("T" + countThreadPLT);
                threadPLT.setThreadName("T" + node.getThreadId());

                // PLT Naming for ProcessOne
                String pltName = null;
                String threadName = null;
                String regionPerilForPLTName = null;
                regionPerilForPLTName = purePLTHeader.getRegionPeril().getRegionPerilCode();

                String sourceFinPersp = null;
                if(threadPLT.getRrAnalysisId() != null) {
                    RRAnalysis rrAnalysis = rrAnalysisRepository.findOne(threadPLT.getRrAnalysisId());
                    if (rrAnalysis != null) {
                        sourceFinPersp = "_" + rrAnalysis.getFpDisplayCode();
                    }
                }

                String threadId = ".T" + node.getThreadId();

                List<AdjustmentNode> listParentFinalNodeRaw = findParentsFinalNode(node);
                List<AdjustmentNode> listParentFinalNode = new ArrayList<>();
                for (AdjustmentNode adjustmentNode: listParentFinalNodeRaw) {
                    AdjustmentNode newNode = adjustmentNodeRepository.findOne(adjustmentNode.getId());
                    listParentFinalNode.add(newNode);
                }

                boolean defaultAdjustment = checkDefaultAdjustment(listParentFinalNode);
                String returnPeriodAdj = buildReturnPeriodAdj(listParentFinalNode);
                String simpleInuringAdj = buildSimpleInuringAdj(listParentFinalNode);
                String overallLMF = buildOverallLMF(listParentFinalNode, defaultAdjustment);
                String pltNameSuffix = node.getPltNameSuffix();
                if (pltNameSuffix != null && !("").equals(pltNameSuffix.trim())) {
                    pltNameSuffix = "." + pltNameSuffix.trim();
                } else {
                    pltNameSuffix = null;
                }

                pltName = regionPerilForPLTName + sourceFinPersp + overallLMF + Objects.toString(returnPeriodAdj, "") + Objects.toString(simpleInuringAdj, "");

                if (BooleanUtils.isTrue(node.getGeneratedBySystemPltNameLAC())) {
                    threadName = pltName + Objects.toString(pltNameSuffix, "") + threadId;

                    node.setDefaultPltName(pltName);
                    node.setPltName(pltName);
                    node.setUserDefinedThreadName(threadName);

                    threadPLT.setDefaultPltName(node.getDefaultPltName());
                    threadPLT.setPltName(node.getPltName());
                    threadPLT.setUdName(node.getUserDefinedThreadName());

                } else {
                    threadName = node.getPltName() + Objects.toString(pltNameSuffix, "") + threadId;

                    node.setDefaultPltName(pltName);
                    node.setUserDefinedThreadName(threadName);

                    threadPLT.setDefaultPltName(node.getDefaultPltName());
                    threadPLT.setPltName(node.getPltName());
                    threadPLT.setUdName(node.getUserDefinedThreadName());
                }

                threadPLT.setSourcePLTHeader(Arrays.asList(purePLTHeader));
                threadPLT.setSystemShortName("ThreadPLT");
                threadPLT.setUserShortName("UserShortName");

                threadPLT.setCreatedDate(createdDate);
                // threadPLT.setxActPublicationDate(null);
                threadPLT.setxActModelVersion(purePLTHeader.getxActModelVersion());
                threadPLT.setRmsSimulationSet(purePLTHeader.getRmsSimulationSet());
                threadPLT.setFinancialPerspective(fpUF);
                threadPLT.setAdjustmentStructure(adjustmentStructure);

                threadPLT.setInstanceId(purePLTHeader.getInstanceId());
                threadPLT.setEngineType(purePLTHeader.getEngineType());

                // unnecessary
                threadPLT.setGeoCode(purePLTHeader.getGeoCode());
                threadPLT.setGeoDescription(purePLTHeader.getGeoDescription());
                threadPLT.setPerilCode(purePLTHeader.getPerilCode());
                threadPLT.setCurrency(purePLTHeader.getCurrency());

                threadPLT.setSourceLossModelingBasis(purePLTHeader.getSourceLossModelingBasis());

                if (BooleanUtils.isTrue(node.isAddedToBasket())) {
                    threadPLT.setAddedToBasket(true);
                } else {
                    threadPLT.setAddedToBasket(false);
                }

//                if(threadPLT.getSourceResult() != null && threadPLT.getSourceResult().getRmsAnalysis() != null && threadPLT.getSourceResult().getRmsAnalysis().getAnalysisName() != null){
//                	threadPLT.setUserShortName(threadPLT.getSourceResult().getRmsAnalysis().getAnalysisName());
//                }else{
//                	threadPLT.setUserShortName(null);
//                }

                if(threadPLT.getRrAnalysisId() != null) {
                    RRAnalysis rrAnalysis = rrAnalysisRepository.findOne(threadPLT.getRrAnalysisId());
                    if (rrAnalysis != null) {
                        if (rrAnalysis.getAnalysisName() != null) {
                            threadPLT.setUserShortName(rrAnalysis.getAnalysisName());
                        } else {
                            threadPLT.setUserShortName(null);
                        }
                    }
                }

                log.info("Thread Node {}, old sourcePLT {}, old adjustedPLT {}", id, node.getSourcePLT() == null ? null : node.getSourcePLT().getId(),
                        node.getAdjustedPLT() == null ? null : node.getAdjustedPLT().getId());

                node.setSourcePLT(threadPLT);
                node.setAdjustedPLT(threadPLT);


                // TODO - output files
                scorPLTHeaderRepository.save(threadPLT);

                // Tien changed
//                pltepHeaderRepository.save(epHeaders);
                rrStatisticHeaderRepository.save(rrStatisticHeaders);

                // for logging
                srcPLTid = node.getSourcePLT() == null ? null : node.getSourcePLT().getId();
                adjPLTid = node.getAdjustedPLT() == null ? null : node.getAdjustedPLT().getId();
                log.info("Thread Node {}, sourcePLT {}, adjustedPLT {}: creating Thread PLT", id, srcPLTid, adjPLTid);

                BinFile parentPLTFile = parentPLT.getPltLossDataFile();
                String path = parentPLTFile.getPath();
                String epcOldName = parentPLTFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPC + "$2");
                String epsOldName = parentPLTFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPS + "$2");
                String epcNewName = makePLTEPCurveFilename(
                        createdDate,
                        regionPeril.getRegionPerilCode(),
                        fpUF.getFullCode(),
                        currency.getCode(),
                        XLTOT.TARGET,
                        targetRap.getTargetRapId(),
                        purePLTHeader.getPltSimulationPeriods(),
                        PLTPublishStatus.PURE,
                        node.getThreadId(),
                        threadPLT.getId(),
                        getFileExtension());
                String epsNewName = makePLTSummaryStatFilename(
                        createdDate,
                        regionPeril.getRegionPerilCode(),
                        fpUF.getFullCode(),
                        currency.getCode(), // old code currency
                        XLTOT.TARGET,
                        targetRap.getTargetRapId(),
                        purePLTHeader.getPltSimulationPeriods(),
                        PLTPublishStatus.PURE,
                        node.getThreadId(),
                        threadPLT.getId(),
                        getFileExtension());
                try {
                    TransformationUtils.copyFile(new File(path, epcOldName), new File(path, epcNewName));
                    log.info("Creating EPCurve file {} by copying {}", epcNewName, epcOldName);
                } catch (IOException e) {
                    log.error("Error creating EPCurve file {} from {}, {}", epcNewName, epcOldName, e);
                    e.printStackTrace();
                    node.setStatus(AdjustmentNodeStatus.Error);
                    adjustmentNodeRepository.save(node);
                    return;
                }
                try {
                    TransformationUtils.copyFile(new File(path, epsOldName), new File(path, epsNewName));
                    log.info("Creating sum stat file {} by copying {}", epsNewName, epsOldName);
                } catch (IOException e) {
                    log.error("Error creating sum stat file {} from {}, {}", epsNewName, epsOldName, e);
                    e.printStackTrace();
                    node.setStatus(AdjustmentNodeStatus.Error);
                    adjustmentNodeRepository.save(node);
                    return;
                }

                node.setStatus(AdjustmentNodeStatus.Valid);
                adjustmentNodeRepository.save(node);

                updateMinimumGrainImportedPlt();
                return;
            }

            // for logging
            srcPLTid = node.getSourcePLT() == null ? null : node.getSourcePLT().getId();
            adjPLTid = node.getAdjustedPLT() == null ? null : node.getAdjustedPLT().getId();

            // START TREATMENT FOR NON-NULL NODES
            if (AdjustmentNodeStatus.Valid.equals(node.getStatus())) {
                log.info("Midd Node {}, sourcePLT {}, adjustedPLT {}: unchanged", id, srcPLTid, adjPLTid);
                return;
            }

            node.setStatus(AdjustmentNodeStatus.Processing);
            adjustmentNodeRepository.save(node);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            ScorPLTHeader sourcePLTHeader = parentNode.getAdjustedPLT();
            log.info("Midd Node {}, sourcePLT {}, adjustedPLT {}: preparing", id, srcPLTid, adjPLTid);

            final List<PLTLossData> sortedSrcLossData = pltReader.readPLTLossDataV2(sourcePLTHeader.getPltLossDataFile());
            TransformationUtils.sortReverse(sortedSrcLossData);

            ScorPLTHeader resultPLTHeader = node.getAdjustedPLT();
            boolean isNewPLT = false;
            if (resultPLTHeader == null) {
                isNewPLT = true;
                resultPLTHeader = new ScorPLTHeader();
                daoService.getMongoDBSequence().nextSequenceId(resultPLTHeader);
            }
            log.info("Updated adjustedPLT id {}", resultPLTHeader.getId());

            // prepare output objects
            Map<StatisticMetric, List<RREPCurve>> resultMetricToEPCurve = new HashMap<>(); // PLTEPCurve
            RRSummaryStatistic resultPLTSummaryStatistic = new RRSummaryStatistic(); // PLTSummaryStatistic

            Date createdDate = new Date();
            String pattern = "(.+)(.bin)";
            String interimPltFileName = makePLTFileName(
                    createdDate,
                    regionPeril.getRegionPerilCode(),
                    fpUF.getFullCode(),
                    currency.getCode(),
                    XLTOT.TARGET,
                    targetRap.getTargetRapId(),
                    purePLTHeader.getPltSimulationPeriods(),
                    PLTPublishStatus.PURE,
                    node.getThreadId(),
                    resultPLTHeader.getId(),
                    getFileExtension()).replaceAll(pattern, "$1" + "_" + node.getId() + "$2");
            File pltFile = makeFullFile(getPrefixDirectory(), interimPltFileName);

            // validation
            AdjustmentLibrary adjustmentLibrary = node.getAdjustmentLibrary();
            if (adjustmentLibrary == null) {
                log.debug("Adjustment library was not set for node {}", node.getId());
                node.setStatus(AdjustmentNodeStatus.Error);
                adjustmentNodeRepository.save(node);
                //error in calculating this node, so status of their pending children will be invalid
                setChildrenPendingNodeStatus(node, AdjustmentNodeStatus.Error);
                return;
            }

            com.scor.almf.ihub.treaty.processing.treaty.adjustment.logic.AdjustmentFactory adjFactory = new com.scor.almf.ihub.treaty.processing.treaty.adjustment.logic.AdjustmentFactory(node, adjustmentLibrary);
            BaseAdjustment adjustment = adjFactory.getAdjustment();
            if (adjustment == null) {
                log.debug("Adjustment not found for node {}", node.getId());
                node.setStatus(AdjustmentNodeStatus.Error);
                adjustmentNodeRepository.save(node);
                //error in calculating this node, so status of their pending children will be invalid
                setChildrenPendingNodeStatus(node, AdjustmentNodeStatus.Error);
                return;
            }


            // ADJUSTMENT
            log.info("Midd Node {}: adjusting", id);
            List<PLTLossData> sortedResultLossData = adjustment.adjustPLT(sortedSrcLossData);

            // EXTRACT STATISTICS
            log.info("Midd Node {}: extracting statistics", id);
            log.debug("runCalculationForLosses for ptlHearer id {} - {} simulation periods", sourcePLTHeader.getId(), sourcePLTHeader.getPltSimulationPeriods());
            pltEPCurveCalculator.runCalculationForLosses(sourcePLTHeader.getPltSimulationPeriods(), sortedResultLossData, epRPMap, rpEPMap, resultPLTSummaryStatistic, resultMetricToEPCurve);


//            List<PLTEPHeader> resultPLTEPHeaders = resultPLTHeader.getPLTEPHeaders();
            List<RRStatisticHeader> rrStatisticHeaders = resultPLTHeader.getPltStatisticList();

            if (rrStatisticHeaders != null) {
                rrStatisticHeaderRepository.delete(rrStatisticHeaders);
            }
            rrStatisticHeaders = new ArrayList<>();

            for (Map.Entry<StatisticMetric, List<RREPCurve>> entry : resultMetricToEPCurve.entrySet()) { // PLTEPCurve
                // old code
//                PLTEPHeader epHeader = new PLTEPHeader();
//                daoService.getMongoDBSequence().nextSequenceId(epHeader);
//                resultPLTEPHeaders.add(epHeader);
//                epHeader.setFinancialPerspective(resultPLTHeader.getFinancialPerspective());
//                epHeader.setStatisticMetric(entry.getKey());
//                epHeader.setEpCurves(entry.getValue());
//                epHeader.setSummaryStatistic(resultPLTSummaryStatistic);
//                epHeader.setScorPLTHeader(resultPLTHeader);

                RRStatisticHeader rrStatisticHeader = new RRStatisticHeader();
                daoService.getMongoDBSequence().nextSequenceId(rrStatisticHeader);
                rrStatisticHeader.setProjectId(project.getId());
                rrStatisticHeader.setLossDataType(RRStatisticHeader.STAT_DATA_TYPE_PLT);
                rrStatisticHeader.setFinancialPerspective(resultPLTHeader.getFinancialPerspective() != null ? new RRFinancialPerspective(resultPLTHeader.getFinancialPerspective()) : null);
                rrStatisticHeader.getStatisticData().setStatisticMetric(entry.getKey());
                rrStatisticHeader.getStatisticData().setEpCurves(entry.getValue());
                rrStatisticHeader.getStatisticData().setSummaryStatistic(resultPLTSummaryStatistic);
                rrStatisticHeader.setLossTableId(resultPLTHeader.getId());
                rrStatisticHeaders.add(rrStatisticHeader);
            }


            node.setSourcePLT(sourcePLTHeader);
            node.setAdjustedPLT(resultPLTHeader);

            resultPLTHeader.setPeqtFile(sourcePLTHeader.getPeqtFile());
            resultPLTHeader.setPltStatisticList(rrStatisticHeaders); // deleted old ones and write new ones
            resultPLTHeader.setPltLossDataFile(new BinFile(pltFile));

            resultPLTHeader.setPltGrouping(sourcePLTHeader.getPltGrouping());
            resultPLTHeader.setPltInuring(PLTInuring.None);
            resultPLTHeader.setPltStatus(PLTStatus.ValidFull);
            resultPLTHeader.setPltSimulationPeriods(sourcePLTHeader.getPltSimulationPeriods());
            resultPLTHeader.setInuringPackageDefinition(null);
            resultPLTHeader.setPltType(PLTType.Interim); // INTERIM

            resultPLTHeader.setProject(project);
//            resultPLTHeader.setEltHeader(pureELTHeader);
            resultPLTHeader.setRrLossTableId(pureRRLT != null ? pureRRLT.getId() : null);
            resultPLTHeader.setCurrency(sourcePLTHeader.getCurrency());
            resultPLTHeader.setTargetRap(sourcePLTHeader.getTargetRap());
//            resultPLTHeader.setRepresentationDataset(sourcePLTHeader.getRepresentationDataset());
            resultPLTHeader.setRrRepresentationDatasetId(sourcePLTHeader.getRrRepresentationDatasetId());
            resultPLTHeader.setRegionPeril(sourcePLTHeader.getRegionPeril());
//            resultPLTHeader.setSourceResult(sourcePLTHeader.getSourceResult());
            resultPLTHeader.setRrAnalysisId(sourcePLTHeader.getRrAnalysisId());
            resultPLTHeader.setFinancialPerspective(fpUA != null ? fpUA : fpUF);

            resultPLTHeader.setAdjustmentStructure(adjustmentStructure);
            resultPLTHeader.setCatAnalysisDefinition(null);

            resultPLTHeader.setSourcePLTHeader(Arrays.asList(purePLTHeader));
            resultPLTHeader.setSystemShortName(null);
            resultPLTHeader.setUserShortName(null);
            resultPLTHeader.setTags(purePLTHeader.getTags());

            resultPLTHeader.setInstanceId(purePLTHeader.getInstanceId());
            resultPLTHeader.setEngineType(purePLTHeader.getEngineType());

            resultPLTHeader.setTruncationCurrency(purePLTHeader.getTruncationCurrency());
            resultPLTHeader.setTruncationThreshold(purePLTHeader.getTruncationThreshold());
            resultPLTHeader.setTruncationThresholdEur(purePLTHeader.getTruncationThresholdEur());

            resultPLTHeader.setCreatedDate(createdDate);
            resultPLTHeader.setxActUsed(false);
            resultPLTHeader.setxActAvailable(false);
            resultPLTHeader.setxActModelVersion(purePLTHeader.getxActModelVersion());
            // resultPLTHeader.setxActPublicationDate(null);

            if (AdjustementNodeMode.DefaultAdjustement == node.getAdjustementNodeMode())
                resultPLTHeader.setGeneratedFromDefaultAdjustement(true);
            else
                resultPLTHeader.setGeneratedFromDefaultAdjustement(sourcePLTHeader.getGeneratedFromDefaultAdjustement());
            resultPLTHeader.setUserSelectedGrain(sourcePLTHeader.getUserSelectedGrain());
            resultPLTHeader.setExportedDPM(false);
            resultPLTHeader.setRmsSimulationSet(purePLTHeader.getRmsSimulationSet());

            resultPLTHeader.setGeoCode(purePLTHeader.getGeoCode());
            resultPLTHeader.setGeoDescription(purePLTHeader.getGeoDescription());
            resultPLTHeader.setPerilCode(purePLTHeader.getPerilCode());

            resultPLTHeader.setSourceLossModelingBasis(purePLTHeader.getSourceLossModelingBasis());
            resultPLTHeader.setImportSequence(purePLTHeader.getImportSequence());

            // persistence
            log.info("Midd Node {}: persisting", id);
            pltWriter.writePLTLossDataV2(sortedResultLossData, pltFile);

            String interimEPCFileName = interimPltFileName.replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPC + "$2");;
            File epCurveFile = makeFullFile(getPrefixDirectory(), interimEPCFileName);

            String interimEPSFileName = interimPltFileName.replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPS + "$2");;
            File sumstatFile = makeFullFile(getPrefixDirectory(), interimEPSFileName);

            epCurveWriter.writePLTEPCurves(resultMetricToEPCurve, epCurveFile);
            epsWriter.writePLTSummaryStatistics(resultPLTSummaryStatistic, sumstatFile);

            scorPLTHeaderRepository.save(resultPLTHeader); // new?
            rrStatisticHeaderRepository.save(rrStatisticHeaders);

            // stepNotifier.notifyEvent(step.getId());
            node.setStatus(AdjustmentNodeStatus.Valid);
            adjustmentNodeRepository.save(node);
            log.info("Midd Node {}: done", id);

            // TEST VALIDATION
//            String path = "C:\\dev\\projects\\testALMF\\test_elt_plt";
//            writePLT(sortedResultLossData, path, pltFile.getFileName());
//            writeEPCurve(resultMetricToEPCurve, path, node.getAdjustmentType(), pltFile.getFileName());
//            writeSummaryStats(resultPLTSummaryStatistic, path, pltFile.getFileName());
        }

    }

    public void processTreeFAC(String treeId) {
        int n = getThreadPoolSize();
        System.out.println("Thread pool size = " + n);
        mTree = repository.findOne(treeId);

        final AdjustmentStep start = mTree.getStart();
        if(start==null)
            return;

        final CATAnalysisRequest car = catAnalysisRequestRepository.findOne(mTree.getCarId());
        final CATObjectGroup catObjectGroup = catObjectGroupRepository.findOne(mTree.getCatObjectGroup());
        final String regionPeril = mTree.getRegionPeril();
        final ModellingResult modellingResult = catObjectGroup.getModellingResultsByRegionPeril().get(regionPeril);
        final String region = modellingResult.getRegionPeril().getRegionHierarchy().getCode();
        final String peril =  modellingResult.getRegionPeril().getGroup().getPeril().getCode();
        final String ccy = modellingResult.getAnalysisCurrency().getCode();
        final String fp = modellingResult.getFinancialPerspectiveELT().getCode();

        final Date runDate = new Date();
        final String contractID = car.getuWanalysis().getContract().getContractID();
        final String fpELT = catObjectGroup.getFinancialPerspectiveELT().getCode();
        final String fpStats = catObjectGroup.getFinancialPerspectiveStats().getCode();
        final String instanceId = catObjectGroup.getModelDatasource().getModellingSystemInstance().getId();
        setupBean(pltSaver, mTree, runDate, contractID, fpELT, fpStats, instanceId);
        setupBean((BaseBatchBeanImpl) epCurveCalculator, mTree, runDate, contractID, fpELT, fpStats, instanceId);
        setupBean((BaseBatchBeanImpl) epCurveWriter, mTree, runDate, contractID, fpELT, fpStats, instanceId);
        setupBean((BaseBatchBeanImpl) epsWriter, mTree, runDate, contractID, fpELT, fpStats, instanceId);
        setupBean((BaseBatchBeanImpl) pltWriter, mTree, runDate, contractID, fpELT, fpStats, instanceId);

        pool = Executors.newFixedThreadPool(FIXED_POOL_SIZE);
        StepRunnable runnable = new StepRunnable(null, start, mTree.getPurePLT(), regionPeril, region, peril, ccy, fp);
        pool.execute(runnable);
//        repository.save(mTree);
//        treeNotifier.notifyEvent(mTree.getId());
    }


    private static int getTreeSize(AdjustmentTree tree) {
        if (tree.getStart() == null) {
            return 0;
        }
        return getStepSize(tree.getStart());
    }

    private static int getStepSize(AdjustmentStep step) {
        if (step == null) {
            return 0;
        }
        int size = 1;
        for (Object childStep : step.getChildren().values()) {
            size += getStepSize((AdjustmentStep) childStep);
        }
        return size;
    }

    private void setupBean(BaseBatchBeanImpl bean, AdjustmentTree tree, Date runDate, String portfolio, String fpELT, String fpStats, String instanceId) {
        bean.setCatReqId(tree.getCarId());
        bean.setDivision(tree.getDivision().toString());
        bean.setPeriodBasis(tree.getPeriodBasis());
        bean.setRunDate(runDate);
        bean.setPortfolio(portfolio);
        bean.setFpELT(fpELT);
        bean.setFpStats(fpStats);
        bean.setInstanceId(instanceId);
    }

    class StepRunnable implements Runnable {
        private CountingSemaphore semaphore;
        private AdjustmentStep step;
        private PLT parentPLT;
        private String regionPerilCode;
        private String region;
        private String peril;
        private String ccy;
        private String fp;

        public StepRunnable(CountingSemaphore semaphore, AdjustmentStep step, PLT plt, String regionPerilCode, String region, String peril, String ccy, String fp) {
            this.semaphore = semaphore;
            this.step = step;
            this.parentPLT = plt;
            this.regionPerilCode = regionPerilCode;
            this.region = region;
            this.peril = peril;
            this.ccy = ccy;
            this.fp = fp;
        }

        @Override
        public void run() {
            semaphore.take();
            System.out.println("Start thread " + step.getId());
            processSubtree(semaphore, step, parentPLT, regionPerilCode, region, peril, ccy, fp);
            System.out.println("Finish thread " + step.getId());
            try {
                semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void processSubtree(CountingSemaphore semaphore, AdjustmentStep adjustmentStep, PLT inputPLT, String regionPerilCode, String region, String peril, String ccy, String fp){
            processStep(adjustmentStep, inputPLT, regionPerilCode, region, peril, ccy, fp);
            semaphore.updateProgress();
            if (adjustmentStep.getChildren()==null||adjustmentStep.getChildren().size()==0)
                return;
            else {
                int nChildren = adjustmentStep.getChildren().size();
                if (nChildren == 0) {
                    return;
                }
                for (Object childrenStep : adjustmentStep.getChildren().values()) {
                    if (nChildren == 1) {
                        processSubtree(semaphore, (AdjustmentStep) childrenStep, adjustmentStep.getAjustedPLT(), regionPerilCode, region, peril, ccy, fp);
                        return;
                    }
                    StepRunnable runnable = new StepRunnable(semaphore, (AdjustmentStep) childrenStep, adjustmentStep.getAjustedPLT(), regionPerilCode, region, peril, ccy, fp);
                    pool.execute(runnable);
                    //processSubtree((AdjustmentStep) childrenStep, adjustmentStep.getAjustedPLT(), regionPerilCode, region, peril, ccy, fp);
                }
            }
        }

        void processStep(AdjustmentStep step, PLT inputPLT, String regionPerilCode, String region, String peril, String ccy, String fp){
            // TODO delete all obsolete files
            // identifier aka stepId, is used to discriminate files written to hard drive
            // beforehand, the bug was the override of new data into the same file at all step cause wrong calculation
            final String identifier = StringUtils.isEmpty(step.getParentId()) ? "" : "_ID_" + step.getId();
            final String model = "INTERNAL_ADJ_" + step.getAdjustmentDefinition().label();
            if (step.getDataState() == PLTDataState.VALID) {
                System.out.println("Unchanged, step " + step.getId());
                return;
            }
            //||step.getAdjustmentClass().equals(AdjustmentStep.AdjustmentClass.PURE)||step.getAdjustmentClass().equals(AdjustmentStep.AdjustmentClass.FINAL))
            if (step.getDataState() == PLTDataState.CLONING) {
                System.out.println("Cloning, step " + step.getId());
                step.setDataState(PLTDataState.VALID);
                // Copy data files in case cloning
                PLT clonedPLT = step.getAjustedPLT(); // cloned PLT with old filename
                final PLTLoss pltLoss = pltReader.readPLTFile(clonedPLT, region, peril, ccy, fp); // input plt of cloned step
                final PLT newPLTLoss = pltSaver.buildPLT(regionPerilCode, pltLoss, inputPLT.getModelRAPSource(), inputPLT.getModelRAP(), model, identifier);
                clonedPLT.setDataFile(newPLTLoss.getDataFile());
                pltWriter.writeLossData(pltLoss, regionPerilCode, model, identifier);
                // TODO - copy and write files with epCurveWriter & epsWriter
                return;
            }
            //read input plt
            final PLTLoss pltLoss = pltReader.readPLTFile(inputPLT,region, peril, ccy, fp);
            PLT outputPLT=step.getAjustedPLT();
            if(outputPLT==null){
                outputPLT = pltSaver.buildPLT(regionPerilCode, pltLoss, inputPLT.getModelRAPSource(),inputPLT.getModelRAP(), model, identifier);
                step.setAjustedPLT(outputPLT);
            }

            Map<String, Object> ruleParams = new HashMap<>();
            ruleParams.put("adjustmentType", step.getAdjustmentDefinition().getType());
            final Map<String, Object> convertFunctionFactory = fireRules(ruleParams, "adjustment");
            AdjustmentFactory factory = (AdjustmentFactory)convertFunctionFactory.get("adjustmentFactory");
            final Adjustment adjustment = factory.buildAdjustment(step.getAdjustmentDefinition());

            //do sdjustment
            final PLTLoss adjLoss = adjustment.adjustPLT(pltLoss);

            //build multiple types of EPCurves for PLT
            SummaryStatistics s = epCurveCalculator.runCalculationForLosses(adjLoss, outputPLT.getEpCurvesByType());

            //write to files
            pltWriter.writeLossData(adjLoss, regionPerilCode, model, identifier);

            // TODO - add identifier to output files
            epCurveWriter.write(outputPLT.getEpCurvesByType(), "PLT", regionPerilCode, adjLoss.getFinancialPerspective(), adjLoss.getCurrency(), model);
            epsWriter.write(s, "PLT", regionPerilCode, adjLoss.getFinancialPerspective(), adjLoss.getCurrency(), model);

            step.setDataState(PLTDataState.VALID);
//            stepNotifier.notifyEvent(step.getId());
            System.out.println("Calculation, step " + step.getId());
        }
    }

    protected synchronized Map<String, Object> fireRules(Map<String, Object> inputParameters, String... ruleNames){
        if (inputParameters == null) {
            inputParameters = new HashMap<>();
        }
//        final CATRequest request = loadRequest();
//        inputParameters.put("domain", "FAC");
//        inputParameters.put("jobType", jobType);
//        inputParameters.put("fpELT", fpELT);
//        inputParameters.put("fpStats", fpStats);
//        inputParameters.put("division", division);
//        inputParameters.put("periodBasis", periodBasis);
//        inputParameters.put("instanceId", instanceId);
//        inputParameters.put("portfolio", portfolio);
//        inputParameters.put("vendor", ModellingVendor.getVendorFromInstance(request.getModellingSystemInstance()));
//        inputParameters.put("system", ModellingSystem.getSystemFromInstance(request.getModellingSystemInstance()));
//        inputParameters.put("version", ModellingSystemVersion.getVersionFromInstance(request.getModellingSystemInstance()));
//        inputParameters.put("extractionDate", runDate);
//        inputParameters.put("carID", catReqId);
//        inputParameters.put("catRequest", request);
        BusinessRulesBean businessBean = new BusinessRulesBean();
        businessBean.setInputData(inputParameters);
        for (String ruleName : ruleNames) {
            businessRulesService.runRuleByName(businessBean, ruleName);
        }
        return businessBean.getOutputData();
    }

    // Getters setters

    public IBusinessRulesService getBusinessRulesService() {
        return businessRulesService;
    }

    public void setBusinessRulesService(IBusinessRulesService businessRulesService) {
        this.businessRulesService = businessRulesService;
    }

    public PLTReader getPltReader() {
        return pltReader;
    }

    public void setPltReader(PLTReader pltReader) {
        this.pltReader = pltReader;
    }

    public EPCurveCalculator getEpCurveCalculator() {
        return epCurveCalculator;
    }

    public void setEpCurveCalculator(EPCurveCalculator epCurveCalculator) {
        this.epCurveCalculator = epCurveCalculator;
    }

    public EPCurveWriter getEpCurveWriter() {
        return epCurveWriter;
    }

    public void setEpCurveWriter(EPCurveWriter epCurveWriter) {
        this.epCurveWriter = epCurveWriter;
    }

    public EPSWriter getEpsWriter() {
        return epsWriter;
    }

    public void setEpsWriter(EPSWriter epsWriter) {
        this.epsWriter = epsWriter;
    }

    public PLTWriter getPltWriter() {
        return pltWriter;
    }

    public void setPltWriter(PLTWriter pltWriter) {
        this.pltWriter = pltWriter;
    }

    public AdjustmentTreeRepository getRepository() {
        return repository;
    }

    public void setRepository(AdjustmentTreeRepository repository) {
        this.repository = repository;
    }

    public PLTSaver getPltSaver() {
        return pltSaver;
    }

    public void setPltSaver(PLTSaver pltSaver) {
        this.pltSaver = pltSaver;
    }

    public Notifier getStepNotifier() {
        return stepNotifier;
    }

    public void setStepNotifier(Notifier stepNotifier) {
        this.stepNotifier = stepNotifier;
    }

    public Notifier getTreeNotifier() {
        return treeNotifier;
    }

    public void setTreeNotifier(Notifier treeNotifier) {
        this.treeNotifier = treeNotifier;
    }

    public CATAnalysisRequestRepository getCatAnalysisRequestRepository() {
        return catAnalysisRequestRepository;
    }

    public void setCatAnalysisRequestRepository(CATAnalysisRequestRepository catAnalysisRequestRepository) {
        this.catAnalysisRequestRepository = catAnalysisRequestRepository;
    }

    public CATObjectGroupRepository getCatObjectGroupRepository() {
        return catObjectGroupRepository;
    }

    public void setCatObjectGroupRepository(CATObjectGroupRepository catObjectGroupRepository) {
        this.catObjectGroupRepository = catObjectGroupRepository;
    }

    public String getIhubPath() {
        return ihubPath;
    }

    public void setIhubPath(String ihubPath) {
        this.ihubPath = ihubPath;
    }

    public DAOService getDaoService() {
        return daoService;
    }

    public void setDaoService(DAOService daoService) {
        this.daoService = daoService;
    }

    public PLTEPCurveCalculator getPltEPCurveCalculator() {
        return pltEPCurveCalculator;
    }

    public void setPltEPCurveCalculator(PLTEPCurveCalculator pltEPCurveCalculator) {
        this.pltEPCurveCalculator = pltEPCurveCalculator;
    }

    public DefaultReturnPeriodRepository getDrpRepo() {
        return drpRepo;
    }

    public void setDrpRepo(DefaultReturnPeriodRepository drpRepo) {
        this.drpRepo = drpRepo;
    }

    private void updateMinimumGrainImportedPlt() {
        log.debug("=== Started updateMinimumGrainImportedPlt");
        Workspace workspace = workspaceRepository.findByProjectId(getProjectId());
        if (workspace == null) {
            log.error("Something wrong: workspace not found !!!");
            return;
        }

        Project project = projectRepository.findById(getProjectId());
        if (workspace == null) {
            log.error("Something wrong: project not found !!!");
            return;
        }

        List<ScorPLTHeader> scorPLTHeaders = scorPLTHeaderRepository.findByProjectAndPltType(project, "Thread");
        if (scorPLTHeaders != null && !scorPLTHeaders.isEmpty()) {
            for (ScorPLTHeader scorPLTHeader : scorPLTHeaders) {
                RegionPeril targetRP = scorPLTHeader.getRegionPeril();
                if (targetRP != null && !org.apache.commons.lang.StringUtils.isEmpty(targetRP.getParentMinimumGrainRegionPeril())) {
                    WorkspaceMinimumGrain workspaceMinimalGrain = workspaceMinimumGrainRepository.findByWorkspaceIdAndMinimumGrain(workspace.getId(), targetRP.getParentMinimumGrainRegionPeril());
                    if (workspaceMinimalGrain == null) {
                        workspaceMinimalGrain = new WorkspaceMinimumGrain();
                        workspaceMinimalGrain.setWorkspaceId(workspace.getId());
                        workspaceMinimalGrain.setMinimumGrain(targetRP.getParentMinimumGrainRegionPeril());
                        workspaceMinimalGrain.setId(workspace.getId() + "_" + targetRP.getParentMinimumGrainRegionPeril());
                        workspaceMinimumGrainRepository.save(workspaceMinimalGrain);
                    }
                }
//                MinimumGrainOfImportedEltPlts minimumGrainOfImportedEltPlts = minimumGrainOfImportedEltPltsRepository.findByScorPLTHeader(scorPLTHeader);
//                if (minimumGrainOfImportedEltPlts == null) {
//                    if (scorPLTHeader.getRegionPeril() != null && scorPLTHeader.getRegionPeril().getRegionPerilCode() != null) {
//                        RegionPeril targetRP = scorPLTHeader.getRegionPeril();
//                        minimumGrainOfImportedEltPlts = new MinimumGrainOfImportedEltPlts();
//                        minimumGrainOfImportedEltPlts.setWorkspaceId(workspace.getId());
//                        minimumGrainOfImportedEltPlts.setProjectId(project.getId());
//                        minimumGrainOfImportedEltPlts.setScorPLTHeader(scorPLTHeader);
//                        minimumGrainOfImportedEltPlts.setPeril(scorPLTHeader.getPerilCode());
//                        String minimumRegionPeril = targetRP.getMinimumGrainRegionPeril() ? targetRP.getRegionPerilCode() : targetRP.getParentMinimumGrainRegionPeril();
//                        minimumGrainOfImportedEltPlts.setMinimumRegionPeril(minimumRegionPeril);
//                        minimumGrainOfImportedEltPlts.setRootRegionPeril(targetRP.getHierarchyLevel() == 0 ? targetRP.getRegionPerilCode() : targetRP.getHierarchyParentCode());
//                        minimumGrainOfImportedEltPlts.setId(workspace.getId() + "-" + project.getId() + "-" + scorPLTHeader.getId());
//                        minimumGrainOfImportedEltPltsRepository.save(minimumGrainOfImportedEltPlts);
//
//                        if (targetRP != null && !org.apache.commons.lang.StringUtils.isEmpty(targetRP.getParentMinimumGrainRegionPeril())) {
//                            WorkspaceMinimumGrain workspaceMinimalGrain = workspaceMinimumGrainRepository.findByWorkspaceIdAndMinimumGrain(workspace.getId(), targetRP.getParentMinimumGrainRegionPeril());
//                            if (workspaceMinimalGrain == null) {
//                                workspaceMinimalGrain = new WorkspaceMinimumGrain();
//                                workspaceMinimalGrain.setWorkspaceId(workspace.getId());
//                                workspaceMinimalGrain.setMinimumGrain(targetRP.getParentMinimumGrainRegionPeril());
//                                workspaceMinimalGrain.setId(workspace.getId() + "_" + targetRP.getParentMinimumGrainRegionPeril());
//                                workspaceMinimumGrainRepository.save(workspaceMinimalGrain);
//                            }
//                        }
//                    }
//                }
            }
        }
        log.debug("=== Completed updateMinimumGrainImportedPlt");
    }

    private List<AdjustmentNode> findParentsFinalNode(AdjustmentNode adjNode) {
        List<AdjustmentNode> nodes = new ArrayList<>();
        if (adjNode != null && adjNode.getId() != null) {
            adjNode = adjustmentNodeRepository.findOne(adjNode.getId());
            if (adjNode != null && adjNode.getId() != null) {
                if (AdjustmentUtils.isPureNode(adjNode.getParentNode()))
                    return nodes;
                else {
                    nodes.add(adjNode.getParentNode());
                    nodes.addAll(findParentsFinalNode(adjNode.getParentNode()));
                }
            }
        }
        return nodes;
    }

    private boolean checkDefaultAdjustment(List<AdjustmentNode> listParentFinalNode) {
        boolean defaultAdjustment = false;
        for (AdjustmentNode adjustmentNode : listParentFinalNode) {
            if (AdjustmentCategory.DEFAULT.equals(adjustmentNode.getAdjustmentCategory())) {
                if (!adjustmentNode.getAdjustmentType().equals("None")) {
                    defaultAdjustment = true;
                    break;
                }
            }
        }
        return defaultAdjustment;
    }

    private String buildOverallLMF(List<AdjustmentNode> listParentFinalNode, boolean defaultAdjustment) {
        String overallLMFStr = null;
        double overallLMF = 1.00;
        double adjustmentParam = 1.00;
        int countLinearAdj = 0;

        for (AdjustmentNode adjustmentNode1 : listParentFinalNode) {
            if (adjustmentNode1.getAdjustmentType().equals("Linear")) {
                countLinearAdj++;
                adjustmentParam = (double) adjustmentNode1.getAdjustmentParam();
                overallLMF = overallLMF * adjustmentParam;
            }
        }

        DecimalFormat formatter = new DecimalFormat("0.00");
        String lmf = (formatter.format(overallLMF)).replaceAll(",", ".");
        if (countLinearAdj == 0) {
            lmf = "1";
        }

        if (defaultAdjustment) {
            overallLMFStr = "_DefAdj_LMF" + lmf;
        } else {
            overallLMFStr = "_LMF" + lmf;
        }

        return overallLMFStr;
    }

    private String buildReturnPeriodAdj(List<AdjustmentNode> listParentFinalNode) {
        String returnPeriodAdj = null;
        for (AdjustmentNode adjustmentNode1 : listParentFinalNode) {
            if (adjustmentNode1.getAdjustmentType().equals("Return Period Banding Severity (OEP)") || adjustmentNode1.getAdjustmentType().equals("Return Period Banding Severity (EEF)")) {
                returnPeriodAdj = "_RPadj";
                break;
            }
        }
        return returnPeriodAdj;
    }

    private String buildSimpleInuringAdj(List<AdjustmentNode> listParentFinalNode) {
        String simpleInuringAdj = null;
        for (AdjustmentNode adjustmentNode1 : listParentFinalNode) {
            if (adjustmentNode1.getAdjustmentType().equals("CAT XL") || adjustmentNode1.getAdjustmentType().equals("Quota Share")) {
                simpleInuringAdj = "_Simple";
                break;
            }
        }
        return simpleInuringAdj;
    }
}
*/
