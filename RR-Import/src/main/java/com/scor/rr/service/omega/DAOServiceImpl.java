package com.scor.rr.service.omega;

import com.scor.rr.domain.entities.ihub.ModelingResultDataSource;
import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.ihub.RRRepresentationDataset;
import com.scor.rr.domain.entities.ihub.SourceResult;
import com.scor.rr.domain.entities.plt.PET;
import com.scor.rr.domain.entities.plt.PLTConverterProgress;
import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.rap.SourceRapMapping;
import com.scor.rr.domain.entities.references.DefaultReturnPeriod;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.references.TargetRAP;
import com.scor.rr.domain.entities.references.plt.TTFinancialPerspective;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.stat.RRStatisticHeader;
import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.repository.TTRegionPerilRepository;
import com.scor.rr.repository.cat.ModellingSystemInstanceRepository;
import com.scor.rr.repository.ihub.RRLossTableRepository;
import com.scor.rr.repository.ihub.RRRepresentationDatasetRepository;
import com.scor.rr.repository.ihub.RepresentationDatasetRepository;
import com.scor.rr.repository.ihub.SourceResultRepository;
import com.scor.rr.repository.omega.CurrencyRepository;
import com.scor.rr.repository.plt.PLTConverterProgressRepository;
import com.scor.rr.repository.plt.ScorPLTHeaderRepository;
import com.scor.rr.repository.plt.TTFinancialPerspectiveRepository;
import com.scor.rr.repository.rap.SourceRapMappingRepository;
import com.scor.rr.repository.rap.TargetRapRepository;
import com.scor.rr.repository.references.DefaultReturnPeriodRepository;
import com.scor.rr.repository.references.ModellingSystemRepository;
import com.scor.rr.repository.references.PETRepository;
import com.scor.rr.repository.rms.ModelingExposureDataSourceRepository;
import com.scor.rr.repository.rms.ModelingResultDataSourceRepository;
import com.scor.rr.repository.rms.RmsAnalysisRepository;
import com.scor.rr.repository.rms.RmsModelDatasourceRepository;
import com.scor.rr.repository.stat.RRStatisticHeaderRepository;
import com.scor.rr.repository.workspace.PortfolioRepository;
import com.scor.rr.repository.workspace.ProjectRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hamiani Mohammed
 * creation date  25/09/2019 at 16:42
 **/
@Data
@Service
public class DAOServiceImpl implements DAOService {

    private static final Logger log = LoggerFactory.getLogger(DAOServiceImpl.class);

    @Autowired
    private DefaultReturnPeriodRepository defaultReturnPeriodRepository;

    @Autowired
    private RmsModelDatasourceRepository rmsModelDatasourceRepository;

    @Autowired
    private TTRegionPerilRepository ttRegionPerilRepository;

    @Autowired
    private RmsAnalysisRepository rmsAnalysisRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SourceResultRepository sourceResultRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

//    @Autowired
//    private TargetRapDefaultModellingOptionRepository targetRapDefaultModellingOptionRepository;

    @Autowired
    private TTFinancialPerspectiveRepository ttFinancialPerspectiveRepository;

    @Autowired
    private ModelingResultDataSourceRepository modelingResultDataSourceRepository;

    @Autowired
    private ModelingExposureDataSourceRepository modelingExposureDataSourceRepository;

    @Autowired
    private ScorPLTHeaderRepository scorPLTHeaderRepository;

//    @Autowired
//    private AdjustmentStructureRepository adjustmentStructureRepository;
//
//    @Autowired
//    private AdjustmentNodeRepository adjustmentNodeRepository;
//
//    @Autowired
//    private AdjustmentLibraryRepository adjustmentLibraryRepository;

//    @Autowired
//    private SubsidiaryRepository subsidiaryRepository;

//    @Autowired
//    private DefaultAdjustmentNodeRepository defaultAdjustmentNodeRepository;
//
//    @Autowired
//    private DefaultAdjustmentStructureRepository defaultAdjustmentStructureRepository;
//
//    @Autowired
//    private AdjustmenBasisRepository adjustmenBasisRepository;
//
//    @Autowired
//    private IncludedExcludedRegionPerilRepository includedExcludedRegionPerilRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ModellingSystemInstanceRepository tmpModellingSystemInstanceRepository;

    @Autowired
    private ModellingSystemRepository tmpModellingSystemRepository;

    @Autowired
    private RRRepresentationDatasetRepository rrRepresentationDatasetRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TargetRapRepository targetRapRepository;

    @Autowired
    private PLTConverterProgressRepository pltConverterProgressRepository;

    @Autowired
    private RepresentationDatasetRepository representationDatasetRepository;

    //	// new code ri
//	@ManagedProperty(value = "#{rrLossTableService}")
//	private RRLossTableService rrLossTableService;
//
//	public void setRrLossTableService(RRLossTableService rrLossTableService) {
//		this.rrLossTableService = rrLossTableService;
//	}
    private String cerberURL;

    private String refdataURL;

    private String ihubdataURL;

    private String environment;

    private String version;

    @Override
    public String version() {
        return version;
    }

    @Override
    public String environment() {
        return environment;
    }


    // TODO - removable data sources
    @Override
    public void persistModelingExposureDataSource(ModelingExposureDataSource model) {
        modelingExposureDataSourceRepository.save(model);
    }

    @Override
    public void persistModelingExposureDataSource(Collection<ModelingExposureDataSource> models) {
        modelingExposureDataSourceRepository.saveAll(models);
    }

    @Override
    public void persistModelingResultDataSource(ModelingResultDataSource model) {
        modelingResultDataSourceRepository.save(model);
    }

    @Override
    public void persistModelingResultDataSource(Collection<ModelingResultDataSource> models) {
        modelingResultDataSourceRepository.saveAll(models);
    }

    @Override
    public void persistRRLossTable(RRLossTable rrLossTable) {

    }

    @Override
    public void persistScorPLTHeader(ScorPLTHeader scorPLTHeader) {

    }


    // new code ri
    @Override
    public void persistRepresentationDataset(RRRepresentationDataset representationDataset) {
        rrRepresentationDatasetRepository.save(representationDataset);
    }

    @Override
    public ScorPLTHeader findScorPLTHeaderById(String id) {
        return scorPLTHeaderRepository.findById(id).get();
    }

//    @Override
//    public void persistAdjustmentStructure(AdjustmentStructure structure) {
//        adjustmentStructureRepository.save(structure);
//    }
//
//    @Override
//    public void persistAdjustmentStructures(Collection<AdjustmentStructure> structures) {
//        for (AdjustmentStructure structure : structures) {
//            if (structure.getId() == null) {
//                log.info("AdjustmentStructure {} is to be persisted", structure.getId());
//            }
//        }
//        adjustmentStructureRepository.saveAll(structures);
//    }
//
//    @Override
//    public void persistAdjustmentNodes(Collection<AdjustmentNode> nodes) {
//        for (AdjustmentNode node : nodes) {
//            if (node.getId() == null) {
//                node.setNodeName("AN " + node.getId());
//                log.info("AdjustmentNode {} is to be persisted", node.getId());
//            }
//        }
//        for (AdjustmentNode node : nodes) {
//            try {
//                adjustmentNodeRepository.save(node);
//                log.info("AdjustmentNode {} is to be persisted", node.getId());
//            } catch (Exception ex) {
//                log.error("Exception at node {}", node.getId(), ex);
//                throw ex;
//            }
//        }
//    }
//
//    @Override
//    public void persistAdjustmentLibrary(Collection<AdjustmentLibrary> libraries) {
//        adjustmentLibraryRepository.saveAll(libraries);
//    }
//
//    @Override
//    public List<AdjustmentLibrary> findAdjustmentTypeByName(String typeName) {
//        return adjustmentLibraryRepository.findByTypeName(typeName);
//    }
//
//    @Override
//    public AdjustmentLibrary findAdjustmentLibraryById(String id) {
//        return adjustmentLibraryRepository.findByModelingResultDataSourceId(id).get();
//    }
//
//    @Override
//    public AdjustmentLibrary findAdjustmentLibraryBy(String type) {
//        List<AdjustmentLibrary> adjustmentLibraries = adjustmentLibraryRepository.findByTypeName(type);
//        if (adjustmentLibraries == null || adjustmentLibraries.isEmpty())
//            return null;
//        else
//            return adjustmentLibraryRepository.findByTypeName(type).get(0);
//    }
//
//    @Override
//    public AdjustmentLibrary findAdjustmentLibraryBy(Integer typeId) {
//        List<AdjustmentLibrary> adjustmentLibraries = adjustmentLibraryRepository.findByTypeId(typeId);
//        if (adjustmentLibraries == null || adjustmentLibraries.isEmpty())
//            return null;
//        else
//            return adjustmentLibraries.get(0);
//    }
//
//    @Override
//    public List<AdjustmentNode> findAdjustmentNodeByStructureId(String structureId) {
//        return adjustmentNodeRepository.findByAdjustmentStructureId(structureId);
//    }

//    @Override
//    public void deleteAdjustmentNodes(Collection<AdjustmentNode> nodes) {
//        adjustmentNodeRepository.deleteAll(nodes);
//    }
//
//    @Override
//    public AdjustmentNode findAdjustmentNodeById(Long id) {
//        return adjustmentNodeRepository.findByModelingResultDataSourceId(id).get();
//    }
//
//    @Override
//    public List<AdjustmentNode> findAdjustmentNodeById(AdjustmentStructure adjustmentStructure) {
//        List<AdjustmentNode> nodes = adjustmentNodeRepository.findByAdjustmentStructure(adjustmentStructure);
//        Set<AdjustmentNode> nodeSet = new HashSet<>(nodes);
//        for (AdjustmentNode adjustmentNode : nodeSet) {
//            if (adjustmentNode.getParentNode() != null) {
//                adjustmentNode.setParentNode(get(nodeSet, adjustmentNode.getParentNode()));
//            }
//            if (adjustmentNode.getChildrenNodes() != null && adjustmentNode.getChildrenNodes().size() != 0) {
//                List<AdjustmentNode> newLinks = new ArrayList<>();
//                for (AdjustmentNode childNode : adjustmentNode.getChildrenNodes()) {
//                    newLinks.add(get(nodeSet, childNode));
//                }
//                adjustmentNode.setChildrenNodes(newLinks);
//            }
//        }
//        return new ArrayList<>(nodeSet);
//    }
//
//    private AdjustmentNode get(Set<AdjustmentNode> nodeSet, AdjustmentNode node) {
//        for (AdjustmentNode adjustmentNode : nodeSet) {
//            if (StringUtils.equalsIgnoreCase(adjustmentNode.getId().toString(), node.getId().toString())) {
//                return adjustmentNode;
//            }
//        }
//        throw new IllegalStateException();
//    }

    @Autowired
    RRLossTableRepository rrLossTableRepository;

    @Autowired
    private RRStatisticHeaderRepository rrStatisticHeaderRepository;


    // new code ri
    @Override
    public RRLossTable findRRLossTableById(String id) {
        return rrLossTableRepository.findById(id).get();
    }

    // new code ri
    @Override
    public List<RRLossTable> findRRLossTableByRRAnalysisId(String rrAnalysisId) {
        return rrLossTableRepository.findByRrAnalysisId(rrAnalysisId);
    }

    // new code ri
    @Override
    public Map<StatisticMetric, RRStatisticHeader> findRRStatisticHeadersByRRLossTable(RRLossTable rrLossTable) {
        List<RRStatisticHeader> rrStatisticHeaders = rrStatisticHeaderRepository.findByLossTableId(rrLossTable.getRrLossTableId());
        if (rrStatisticHeaders == null || rrStatisticHeaders.size() == 0) {
            return null;
        }
        Map<StatisticMetric, RRStatisticHeader> map = new HashMap<>();
        for (RRStatisticHeader rrStatisticHeader : rrStatisticHeaders) {
            if (map.containsKey(rrStatisticHeader.getStatisticData().getStatisticMetric())) {
                throw new IllegalStateException();
            } else {
                map.put(rrStatisticHeader.getStatisticData().getStatisticMetric(), rrStatisticHeader);
            }
        }
        return map;
    }

    // new code ri
    @Override
    public void persistRRStatisticHeaders(Collection<RRStatisticHeader> rrStatisticHeaders) {

        rrStatisticHeaderRepository.saveAll(rrStatisticHeaders);
    }


    @Override
    public void persistRRStatisticHeader(RRStatisticHeader rrStatisticHeader) {
        rrStatisticHeaderRepository.save(rrStatisticHeader);
    }

    // new code ri
    @Override
    public void deleteRRStatisticHeaders(Specification<RRStatisticHeader> pltRRStatisticHeaders) {
        if (pltRRStatisticHeaders == null) {
            return;
        }
        Collection<RRStatisticHeader> deletables = rrStatisticHeaderRepository.findAll();
        rrStatisticHeaderRepository.deleteAll(deletables);
    }

//	@Override
//	public List<PLTEPHeader> findPLTEPHeader(ScorPLTHeader scorPLTHeader) {
//		return pltEPHeaderRepository.findByScorPLTHeader(scorPLTHeader);
//	}


    @Override
    public List<RRStatisticHeader> findRRStatisticHeader(ScorPLTHeader scorPLTHeader) {
        return rrStatisticHeaderRepository.findByLossTableId(scorPLTHeader.getScorPLTHeaderId());
    }

//    @Override
//    public AdjustmentStructure findAdjustmentStructureById(String id) {
//        return adjustmentStructureRepository.findByModelingResultDataSourceId(id).get();
//    }
//
//    @Override
//    public List<AdjustmentStructure> findAdjustmentStructureBy(TargetRAP targetRap, TargetRapDefaultModellingOption targetRapDefaultModellingOption) {
//        return adjustmentStructureRepository.findByTargetRapAndTargetRapDefaultModellingOption(targetRap, targetRapDefaultModellingOption);
//    }
//
//    @Override
//    public void updateAdjustmentNode(String id, AdjustmentNodeStatus status, AdjustementNodeMode adjustementNodeMode, ScorPLTHeader sourcePLT, ScorPLTHeader adjustedPLT, List<AdjustmentNode> childrenNodes) {
//
//    }

//    @Override
//    public void updateAdjustmentNode(String id, AdjustmentNodeStatus status, AdjustementNodeMode adjustementNodeMode, ScorPLTHeader sourcePLT, ScorPLTHeader adjustedPLT, List<AdjustmentNode> childrenNodes) {
//        Query query = new Query(Criteria.where("_id").is(id));
//        Update update = new Update();
//        if (status != null) {
//            update.set("status", status);
//        }
//        if (adjustementNodeMode != null) {
//            update.set("adjustementNodeMode", adjustementNodeMode);
//        }
//        if (sourcePLT != null) {
//            update.set("sourcePLT", sourcePLT);
//        }
//        if (adjustedPLT != null) {
//            update.set("adjustedPLT", adjustedPLT);
//        }
//        if (childrenNodes != null && childrenNodes.size() != 0) {
//            for (AdjustmentNode childrenNode : childrenNodes) {
//                if (childrenNode.getId() == null) {
//                    mongoDBSequence.nextSequenceId(childrenNode);
//                }
//            }
//            update.set("childrenNodes", childrenNodes);
//        }
//        AdjustmentNode adjustmentNode = mongoOperations.findAndModify(query, update, AdjustmentNode.class);
//        if (adjustmentNode == null) {
//            throw new IllegalStateException();
//        }
//    }

    @Override
    public List<TargetRAP> findTargetRap(String modellingVendor, String modellingSystem, String modellingSystemVersion, String sourceRapCode) {
        return targetRapRepository.findByModellingVendorAndModellingSystemAndModellingSystemVersionAndSourceRapCode(modellingVendor, modellingSystem, modellingSystemVersion, sourceRapCode);
    }

    @Override
    public TargetRAP findTargetRapBy(Integer targetRapId) {
        return targetRapRepository.findByTargetRAPId(targetRapId);
    }

    @Override
    public TargetRAP findTargetRapBy(String id) {
        return null;
    }

    @Override
    public TTFinancialPerspective findFinancialPerspective(String code) {
        return ttFinancialPerspectiveRepository.findByCodeAndTreatyId(code, null);
    }

    @Override
    public void persistFinancialPerspective(TTFinancialPerspective financialPerspective) {
        if (financialPerspective.getCode() == null) {
            throw new IllegalArgumentException();
        }
        ttFinancialPerspectiveRepository.save(financialPerspective);
    }

//    @Override
//    public List<TargetRapDefaultModellingOption> findTargetRapDefaultModellingOptionBy(String modellingOptionCode) {
//        return targetRapDefaultModellingOptionRepository.findByModellingOptionCode(modellingOptionCode);
//    }
//
//
//    @Override
//    public List<TargetRapDefaultModellingOption> findTargetRapDefaultModellingOptionBy(Integer targetRapId) {
//        return targetRapDefaultModellingOptionRepository.findByTargetRAPId(targetRapId);
//    }
//
//    @Override
//    public List<TargetRapDefaultModellingOption> findTargetRapDefaultModellingOptionBy(Integer targetRapId, ExpectedState expectedState) {
//        return targetRapDefaultModellingOptionRepository.findByTargetRapIdAndExpectedState(targetRapId, expectedState);
//    }
//
//    @Override
//    public List<AdjustmentStructure> findAdjustmentStructureBy(AdjustementStructureMode mode) {
//        return adjustmentStructureRepository.findByStructureMode(mode);
//    }

    @Autowired
    private SourceRapMappingRepository sourceRapMappingRepository;

    @Override
    public SourceRapMapping findSourceRAPMapping(String profileKey) {
        List<SourceRapMapping> sourceRapMappings = sourceRapMappingRepository.findByProfileKey(profileKey);
        log.info("Found {} SourceRapMappings", sourceRapMappings.size());
        if (sourceRapMappings.size() != 1) {
            throw new IllegalStateException();
        }
        return sourceRapMappings.get(0);
    }

    @Override
    public SourceRapMapping findSourceRapMappingById(Long id) {
        return sourceRapMappingRepository.findById(id).get();
    }

    @Autowired
    private PETRepository petRepository;

    @Override
    public PET findPETBy(TargetRAP targetRap) {
        return petRepository.findById(String.valueOf(targetRap.getPetId())).get();
    }

    @Override
    public SourceResult findSourceResultBy(String id) {
        return sourceResultRepository.findById(id).get();
    }

    @Override
    public Portfolio findPortfolioBy(String id) {
        return portfolioRepository.findById(id).get();
    }

    @Override
    public Project findProjectBy(String id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public RmsAnalysis findRmsAnalysisBy(String analysisId) {
        return rmsAnalysisRepository.findByRmsAnalysisId(analysisId);
    }

    @Override
    public RmsAnalysis findRmsAnalysisBy(String projectId, String analysisId) {
        return rmsAnalysisRepository.findByProjectProjectIdAndAnalysisId(projectId, analysisId);
    }

    @Override
    public RegionPeril findRegionPerilBy(String regionPerilCode) {
        return ttRegionPerilRepository.findByRegionPerilCode(regionPerilCode);
    }

    @Override
    public void persistSourceResult(SourceResult sourceResult) {
        sourceResultRepository.save(sourceResult);
    }

    @Override
    public void persistSourceResults(Collection<SourceResult> sourceResults) {
        sourceResultRepository.saveAll(sourceResults);
    }

    @Override
    public List<DefaultReturnPeriod> findDefaultReturnPeriods() {
        return (List<DefaultReturnPeriod>) defaultReturnPeriodRepository.findAll();
    }

//    @Override
//    public DefaultAdjustmentStructure findDefaultAdjustmentStructureBy(Integer targetRapId) {
//        return defaultAdjustmentStructureRepository.findByTargetRAPId(targetRapId);
//    }
//
//    @Override
//    public List<DefaultAdjustmentStructure> findDefaultAdjustmentStructureBy(Integer targetRapId, String engineType) {
//        return defaultAdjustmentStructureRepository.findByTargetRapIdAndEngineType(targetRapId, engineType);
//    }
//
//    @Override
//    public List<DefaultAdjustmentNode> findDefaultAdjustmentNodeBy(Integer ajdStructureId) {
//        return defaultAdjustmentNodeRepository.findByAjdStructureId(ajdStructureId);
//    }
//
//    @Override
//    public AdjustmentBasis findDefaultAdjustmentBasisBy(String adjustmentBasis) {
//        return adjustmenBasisRepository.findByAdjustmentBasis(adjustmentBasis);
//    }
//
//    @Override
//    public AdjustmentNode findAdjustmentNode(Long id) {
//        return adjustmentNodeRepository.findByModelingResultDataSourceId(id).get();
//    }
//
//    @Override
//    public IncludedExcludedRegionPeril findIncludedExcludedRegionPerilBy(Integer adjStructureId) {
//        return includedExcludedRegionPerilRepository.findByAdjStructureId(adjStructureId);
//    }

    @Override
    public PLTConverterProgress findPLTConverterProgressBy(String pltId) {
        return pltConverterProgressRepository.findByPltId(pltId);
    }

    @Override
    public void persistPLTConverterProgress(PLTConverterProgress pltConverterProgress) {
        pltConverterProgressRepository.save(pltConverterProgress);
    }
}
