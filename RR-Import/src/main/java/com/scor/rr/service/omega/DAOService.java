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
import com.scor.rr.domain.entities.references.plt.FinancialPerspective;
import com.scor.rr.domain.entities.references.plt.TTFinancialPerspective;
import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.domain.entities.stat.RRStatisticHeader;
import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.StatisticMetric;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Hamiani Mohammed
 * creation date  16/09/2019 at 16:35
 **/
public interface DAOService {
    String version();

    String environment();

    /**
     * Update full EDMs
     *
     * @param model
     */
    void persistModelingExposureDataSource(ModelingExposureDataSource model);

    void persistModelingExposureDataSource(Collection<ModelingExposureDataSource> models);

    /**
     * Update full RDMs
     *
     * @param model
     */
    void persistModelingResultDataSource(ModelingResultDataSource model);

    void persistModelingResultDataSource(Collection<ModelingResultDataSource> models);

    void persistRRLossTable(RRLossTable rrLossTable);

    void persistScorPLTHeader(ScorPLTHeader scorPLTHeader);

    void persistRepresentationDataset(RRRepresentationDataset representationDataset);

    ScorPLTHeader findScorPLTHeaderById(String id);

//    void persistAdjustmentStructure(AdjustmentStructure structure);
//
//    void persistAdjustmentStructures(Collection<AdjustmentStructure> structure);
//
//    void persistAdjustmentNodes(Collection<AdjustmentNode> nodes);
//
//    void persistAdjustmentLibrary(Collection<AdjustmentLibrary> libraries);
//
//    List<AdjustmentLibrary> findAdjustmentTypeByName(String typeName);
//
//    List<AdjustmentNode> findAdjustmentNodeByStructureId(String structureId);
//
//    void deleteAdjustmentNodes(Collection<AdjustmentNode> nodes);
//
//    AdjustmentNode findAdjustmentNodeById(Long id);
//
//    List<AdjustmentNode> findAdjustmentNodeById(AdjustmentStructure adjustmentStructure);

    RRLossTable findRRLossTableById(String id);

    List<RRLossTable> findRRLossTableByRRAnalysisId(String rrAnalysisId);

    Map<StatisticMetric, RRStatisticHeader> findRRStatisticHeadersByRRLossTable(RRLossTable rrLossTable);

    void persistRRStatisticHeaders(Collection<RRStatisticHeader> rrStatisticHeaders);

    void persistRRStatisticHeader(RRStatisticHeader rrStatisticHeader);

    void deleteRRStatisticHeaders(Specification<RRStatisticHeader> pltRRStatisticHeaders);


//    AdjustmentLibrary findAdjustmentLibraryById(String id);
//
//    AdjustmentLibrary findAdjustmentLibraryBy(Integer typeId);
//
//    AdjustmentLibrary findAdjustmentLibraryBy(String type);
//
    List<RRStatisticHeader> findRRStatisticHeader(ScorPLTHeader scorPLTHeader);
//
//    AdjustmentStructure findAdjustmentStructureById(String id);
//
//    List<AdjustmentStructure> findAdjustmentStructureBy(AdjustementStructureMode mode);
//
//    List<AdjustmentStructure> findAdjustmentStructureBy(TargetRAP targetRap, TargetRapDefaultModellingOption targetRapDefaultModellingOption);
//
//    void updateAdjustmentNode(String id, AdjustmentNodeStatus status, AdjustementNodeMode adjustementNodeMode, ScorPLTHeader sourcePLT, ScorPLTHeader adjustedPLT, List<AdjustmentNode> childrenNodes);

    List<TargetRAP> findTargetRap(String modellingVendor, String modellingSystem, String modellingSystemVersion, String sourceRapCode);

    TargetRAP findTargetRapBy(Integer targetRapId);

    TargetRAP findTargetRapBy(String id);

    TTFinancialPerspective findFinancialPerspective(String code);

    void persistFinancialPerspective(TTFinancialPerspective financialPerspective);

//    List<TargetRapDefaultModellingOption> findTargetRapDefaultModellingOptionBy(String modellingOptionCode);
//
//    List<TargetRapDefaultModellingOption> findTargetRapDefaultModellingOptionBy(Integer targetRapId);
//
//    List<TargetRapDefaultModellingOption> findTargetRapDefaultModellingOptionBy(Integer targetRapId, ExpectedState expectedState);

    SourceRapMapping findSourceRAPMapping(String profileKey);

    SourceRapMapping findSourceRapMappingById(Long id);

    PET findPETBy(TargetRAP targetRap);

    SourceResult findSourceResultBy(String id);

    Portfolio findPortfolioBy(String id);

    Project findProjectBy(String id);

    RmsAnalysis findRmsAnalysisBy(String analysisId);

    RmsAnalysis findRmsAnalysisBy(String projectId, String analysisId);

    RegionPeril findRegionPerilBy(String regionPerilCode);

    void persistSourceResult(SourceResult sourceResult);

    void persistSourceResults(Collection<SourceResult> sourceResults);

    List<DefaultReturnPeriod> findDefaultReturnPeriods();

//    DefaultAdjustmentStructure findDefaultAdjustmentStructureBy(Integer targetRapId);
//
//    List<DefaultAdjustmentStructure> findDefaultAdjustmentStructureBy(Integer targetRapId, String engineType);
//
//    List<DefaultAdjustmentNode> findDefaultAdjustmentNodeBy(Integer ajdStructureId);
//
//    AdjustmentBasis findDefaultAdjustmentBasisBy(String adjustmentBasis);
//
//    AdjustmentNode findAdjustmentNode(Long id);

//    IncludedExcludedRegionPeril findIncludedExcludedRegionPerilBy(Integer adjStructureId);

    PLTConverterProgress findPLTConverterProgressBy(String pltId);

    void persistPLTConverterProgress(PLTConverterProgress pltConverterProgress);
}
