package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.ProjectImportRunEntity;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.riskLink.*;
import com.scor.rr.domain.views.RLImportedDataSourcesAndAnalysis;
import com.scor.rr.domain.views.RLSourceEpHeaderView;
import com.scor.rr.repository.*;
import com.scor.rr.repository.specification.RLAnalysisSpecification;
import com.scor.rr.repository.specification.RLPortfolioSpecification;
import com.scor.rr.service.abstraction.ConfigurationService;
import com.scor.rr.service.abstraction.DivisionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

/**
 * @author Ayman IKAR
 * @created 19/12/2019
 */

@Service
@Slf4j
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private RLAnalysisRepository rlAnalysisRepository;

    @Autowired
    private RLPortfolioRepository rlPortfolioRepository;

    @Autowired
    private RLModelDataSourceRepository rlModelDataSourceRepository;

    @Autowired
    private RLAnalysisToTargetRAPRepository rlAnalysisToTargetRAPRepository;

    @Autowired
    private RLAnalysisToRegionPerilsRepository rlAnalysisToRegionPerilsRepository;

    @Autowired
    private RLSourceEPHeaderViewRepository rlSourceEPHeaderViewRepository;

    @Autowired
    private ProjectConfigurationForeWriterDivisionRepository projectConfigurationForeWriterDivisionRepository;

    @Autowired
    private RLSavedDataSourceRepository rlSavedDataSourceRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private RLImportedDataSourcesAndAnalysisRepository rlImportedDataSourcesAndAnalysisRepository;

    @Autowired
    private RLImportSelectionRepository rlImportSelectionRepository;

    @Autowired
    private RLPortfolioSelectionRepository rlPortfolioSelectionRepository;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private RmsService rmsService;

    @Autowired
    private FinancialPerspectiveRepository financialPerspectiveRepository;

    @Autowired
    private RLSourceEpHeaderRepository rlSourceEpHeaderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RLAnalysisSpecification rlAnalysisSpecification;

    @Autowired
    private RLPortfolioSpecification rlPortfolioSpecification;

    @Override
    public List<RLAnalysisDto> getRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId) {
        RLModelDataSource rlModelDataSource = rlModelDataSourceRepository.findByInstanceIdAndProjectIdAndRlId(instanceId, projectId, rmsId);
        if (rlModelDataSource != null) {
            log.debug("Fetching RLAnalysis for rlModelDataSource {}", rlModelDataSource.getRlModelDataSourceId());
            return rlAnalysisRepository.findByRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId()).stream()
                    .map(rlAnalysis -> modelMapper.map(rlAnalysis, RLAnalysisDto.class)).collect(toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<RLPortfolioDto> getRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long rmsId) {
        RLModelDataSource rlModelDataSource = rlModelDataSourceRepository.findByInstanceIdAndProjectIdAndRlId(instanceId, projectId, rmsId);
        if (rlModelDataSource != null) {
            log.debug("Fetching RLPortfolio for rlModelDataSource {}", rlModelDataSource.getRlModelDataSourceId());
            return rlPortfolioRepository.findByRlModelDataSourceRlModelDataSourceId(rlModelDataSource.getRlModelDataSourceId()).stream()
                    .map(rlPortfolio -> modelMapper.map(rlPortfolio, RLPortfolioDto.class)).collect(toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<RLAnalysisToTargetRAPDto> getTargetRapByAnalysisId(Long rlAnalysisId) {
        return rlAnalysisToTargetRAPRepository.findByRlAnalysisId(rlAnalysisId).stream()
                .map(element -> modelMapper.map(element, RLAnalysisToTargetRAPDto.class))
                .collect(toList());
    }

    @Override
    public List<RegionPerilDto> getRegionPeril(Long rlAnalysisId) {
        return rlAnalysisToRegionPerilsRepository.findByRlModelAnalysisIdOrderByRegionPerilCode(rlAnalysisId).stream()
                .map(element -> modelMapper.map(element, RegionPerilDto.class))
                .collect(toList());
    }

    @Override
    public List<RLSourceEpHeaderView> getSourceEpHeadersByAnalysis(Long rlAnalysisId) {

        // TODO eppoint = 1/10 ....
        List<Float> epPoints = Arrays.asList(1 / 10f, 1 / 50f, 1 / 100f, 1 / 250f, 1 / 500f, 1 / 1000f);
        List<String> fpCodes = financialPerspectiveRepository.findSelectableCodes();
        Optional<RLAnalysis> rlAnalysisOptional = rlAnalysisRepository.findById(rlAnalysisId);

        if (rlAnalysisOptional.isPresent()) {
            List<Long> analysis = Collections.singletonList(rlAnalysisOptional.get().getRlId());
            Optional<RLModelDataSource> rlModelDataSourceOp = rlModelDataSourceRepository.findById(rlAnalysisOptional.get().getRlModelDataSourceId());
            if (rlModelDataSourceOp.isPresent()) {
                rlSourceEpHeaderRepository.deleteByRLAnalysisIdList(analysis);
                rmsService.extractSourceEpHeaders(rlModelDataSourceOp.get().getInstanceId(), rlModelDataSourceOp.get().getRlId(),
                        rlModelDataSourceOp.get().getRlDataSourceName(), rlAnalysisOptional.get().getProjectId(), epPoints, analysis, fpCodes);
                return rlSourceEPHeaderViewRepository.findByRLAnalysisId(rlAnalysisId);
            } else
                return new ArrayList<>();

        } else
            return new ArrayList<>();

    }

    // TODO : Review this later
    @Override
    public List<CARDivisionDto> getDivisions(String carId) {
        return divisionService.getDivisions(carId);
    }

    @Override
    public Map<Long, List<RegionPerilDto>> getRegionPerilForMultiAnalysis(List<Long> rlAnalysisIds) {
        Map<Long, List<RegionPerilDto>> result = new HashMap<>();
        rlAnalysisIds.forEach(id -> result.put(id, this.getRegionPeril(id)));
        return result;
    }

    @Override
    @Transactional(transactionManager = "rrTransactionManager")
    public void saveDefaultDataSources(DataSourcesDto dataSourcesDto) {
        Long userId = SecurityContextHolder.getContext().getAuthentication() != null ?
                ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId() :
                dataSourcesDto.getUserId();
        rlSavedDataSourceRepository.deleteByUserId(userId);

        if (dataSourcesDto.getDataSources() != null && !dataSourcesDto.getDataSources().isEmpty()) {
            dataSourcesDto.getDataSources().forEach(dataSource -> {
                RLSavedDataSource rlSavedDataSource = modelMapper.map(dataSource, RLSavedDataSource.class);
                rlSavedDataSource.setProjectId(dataSourcesDto.getProjectId());
                rlSavedDataSource.setUserId(userId);
                rlSavedDataSourceRepository.save(rlSavedDataSource);
            });
        }
    }

    @Override
    public List<RLDataSourcesDto> getDefaultDataSources(Long userId) {

        if (SecurityContextHolder.getContext().getAuthentication() != null)
            userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId();

        return rlSavedDataSourceRepository.findByUserId(userId).stream()
                .map(RLDataSourcesDto::new).collect(toList());
    }

    @Override
    public ProjectImportRunEntity checkIfProjectHasBeenImportedBefore(Long projectId) {
        return projectImportRunRepository.findFirstByProjectIdOrderByRunId(projectId);
    }

    @Override
    public boolean checkIfProjectHasConfigurations(Long projectId) {
        return !rlImportSelectionRepository.findByProjectId(projectId).isEmpty() ||
                !rlPortfolioSelectionRepository.findRLPortfolioSelectionIdByProjectId(projectId).isEmpty();
    }

    @Override
    public boolean checkIfProjectHasScannedDataSources(Long projectId) {
        return !rlModelDataSourceRepository.findByProjectId(projectId).isEmpty();
    }

    @Override
    public List<RLDataSourcesDto> getDataSourcesWithSelectedAnalysis(Long projectId) {

        List<RLImportedDataSourcesAndAnalysis> data = rlImportedDataSourcesAndAnalysisRepository.findByProjectId(projectId);
        List<RLDataSourcesDto> importedDataSources = new ArrayList<>();

        if (data != null && !data.isEmpty()) {
            data.forEach(element -> {

                RLDataSourcesDto importedDataSourceDto = importedDataSources.stream()
                        .filter(ele -> ele.getRlDataSourceId().equals(element.getRlDataSourceId())
                                && ele.getDataSourceName().equals(element.getRlDataSourceName())
                                && ele.getDataSourceId().equals(element.getRlDatabaseId())).findFirst().orElse(null);

                if (importedDataSourceDto == null) {
                    importedDataSourceDto = new RLDataSourcesDto(element);
                    importedDataSources.add(importedDataSourceDto);
                } else
                    importedDataSourceDto.addToRlModelIdList(element.getRlModelId());

            });
        }
        return importedDataSources;
    }

    @Override
    public List<RLImportSelectionDtoWithAnalysisInfo> getRLModelAnalysisConfigs(Long projectId) {
        List<RLImportSelection> data = rlImportSelectionRepository.findByProjectId(projectId);
        List<RLImportSelectionDtoWithAnalysisInfo> importedAnalysis = new ArrayList<>();

        if (data != null && !data.isEmpty()) {
            data.forEach(element -> {
                RLImportSelectionDtoWithAnalysisInfo rlImportSelection = importedAnalysis.stream()
                        .filter(ele -> ele.getRlAnalysisId().equals(element.getRlAnalysis().getRlAnalysisId())
                                && ele.getProjectId().equals(element.getProjectId())).findFirst().orElse(null);

                if (rlImportSelection == null) {
                    rlImportSelection = new RLImportSelectionDtoWithAnalysisInfo(element);
                    importedAnalysis.add(rlImportSelection);
                } else {

                    if (element.getDivision() != null)
                        rlImportSelection.addToDivisionList(element.getDivision());

                    if (element.getTargetRaps() != null && !element.getTargetRaps().isEmpty()) {
                        for (RLImportTargetRAPSelection targetRap : element.getTargetRaps()) {
                            if (!rlImportSelection.getTargetRAPCodes().contains(targetRap.getTargetRAPCode()))
                                rlImportSelection.addToTargetRapsList(targetRap.getTargetRAPCode());
                        }
                    }

                    if (element.getFinancialPerspective() != null && !rlImportSelection.getFinancialPerspectives().contains(element.getFinancialPerspective()))
                        rlImportSelection.addToFPList(element.getFinancialPerspective());
                }

                rlImportSelection.setReferenceTargetRaps(this.getTargetRapByAnalysisId(element.getRlAnalysis().getRlAnalysisId()));
            });
        }
        return importedAnalysis;
    }

    @Override
    public List<RLPortfolioSelectionDtoWithPortfolioInfo> getRLPortfolioConfigs(Long projectId) {
        List<RLPortfolioSelection> data = rlPortfolioSelectionRepository.findByProjectId(projectId);
        List<RLPortfolioSelectionDtoWithPortfolioInfo> importedPortfolios = new ArrayList<>();

        if (data != null && !data.isEmpty()) {
            data.forEach(element -> {
                RLPortfolioSelectionDtoWithPortfolioInfo rlImportSelection = importedPortfolios.stream()
                        .filter(ele -> ele.getRlPortfolioId().equals(element.getRlPortfolio().getRlPortfolioId())
                                && ele.getProjectId().equals(element.getProjectId())).findFirst().orElse(null);

                if (rlImportSelection == null) {
                    rlImportSelection = new RLPortfolioSelectionDtoWithPortfolioInfo(element);
                    importedPortfolios.add(rlImportSelection);
                } else {

                    if (element.getDivision() != null)
                        rlImportSelection.addToDivisionList(element.getDivision());
                }
            });
        }
        return importedPortfolios;
    }

    @Override
    public Page<RLAnalysisDto> filterRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long userId, Long rdmId, RLAnalysisDto filter, Pageable pageable) {
        return rlAnalysisRepository.findAll(rlAnalysisSpecification.getFilter(filter, this.getModelDsId(instanceId, projectId, userId, rdmId)), pageable)
                .map((item) ->
                        modelMapper.map(item, RLAnalysisDto.class)
                );
    }

    @Override
    public List<RLAnalysisDto> filterRLAnalysisByRLModelDataSourceId(String instanceId, Long projectId, Long userId, Long rdmId, RLAnalysisDto filter) {
        return rlAnalysisRepository.findAll(rlAnalysisSpecification.getFilter(filter, this.getModelDsId(instanceId, projectId, userId, rdmId))).stream()
                .map(item -> modelMapper.map(item, RLAnalysisDto.class))
                .collect(toList());
    }

    @Override
    public Page<RLPortfolioDto> filterRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long userId, Long edmId, RLPortfolioDto filter, Pageable pageable) {
        return rlPortfolioRepository.findAll(rlPortfolioSpecification.getFilter(filter, this.getModelDsId(instanceId, projectId, userId, edmId)), pageable)
                .map(item -> modelMapper.map(item, RLPortfolioDto.class));
    }

    @Override
    public List<RLPortfolioDto> filterRLPortfolioByRLModelDataSourceId(String instanceId, Long projectId, Long userId, Long edmId, RLPortfolioDto filter) {
        return rlPortfolioRepository.findAll(rlPortfolioSpecification.getFilter(filter, this.getModelDsId(instanceId, projectId, userId, edmId)))
                .stream()
                .map(item -> modelMapper.map(item, RLPortfolioDto.class))
                .collect(toList());
    }

    @Override
    public Map<Long, AnalysisPortfolioDto> getAutoAttach(String wsId, List<Long> edmIds, List<Long> rdmIds, List<Long> divisionsIds) {
        Map<Long, AnalysisPortfolioDto> analysisPortfolioByDivision = new HashMap<>();
        divisionsIds.forEach(divisionNumber -> {
            String keyword = wsId + "_" + String.format("%02d", divisionNumber);
            AnalysisPortfolioDto data = new AnalysisPortfolioDto();
            edmIds.forEach(edmId -> {
                data.getRlPortfolios().addAll(findPortfolios(edmId, keyword));
            });
            rdmIds.forEach(rdmId -> {
                data.getRlAnalyses().addAll(findAnalysis(rdmId, keyword));
            });
            analysisPortfolioByDivision.put(divisionNumber, data);
        });
        return analysisPortfolioByDivision;
    }

    @Override
    public void deleteRlDataSource(Long rlDataSourceId) {
        Integer status = rlModelDataSourceRepository.deleteRLModelDataSourceById(rlDataSourceId);
        if (status == -1)
            throw new RuntimeException("Failed delete for RL DataSource with ID : " + rlDataSourceId);
    }

    private List<RLAnalysisDto> findAnalysis(Long rdmId, String keyword) {
        return rlAnalysisRepository.findByRdmIdAndName(rdmId, keyword)
                .stream()
                .map(analysis -> modelMapper.map(analysis, RLAnalysisDto.class))
                .collect(toList());
    }

    private List<RLPortfolioDto> findPortfolios(Long edmId, String keyword) {
        return rlPortfolioRepository.findByEdmIdAndNumber(edmId, keyword)
                .stream()
                .map(p -> modelMapper.map(p, RLPortfolioDto.class))
                .collect(toList());
    }

    private Long getModelDsId(String instanceId, Long projectId, Long userId, Long rmsId) {
        return ofNullable(rlModelDataSourceRepository.findByInstanceIdAndProjectIdAndRlId(instanceId, projectId, rmsId))
                .map(RLModelDataSource::getRlModelDataSourceId)
                .orElseThrow(() -> new RuntimeException("No available DataSource For given params : " + instanceId + "/" + projectId + "/" + rmsId));

    }

    @Override
    public void clearProjectAndLoadDefaultDataSources(Long projectId) {
        rlModelDataSourceRepository.findByProjectId(projectId)
                .stream()
                .forEach(ds -> {
                    rlModelDataSourceRepository.deleteRLModelDataSourceById(ds.getRlModelDataSourceId());
                });
    }

    @Override
    public void deleteAnalysisSummary(List<Long> rlAnalysisId, Long projectId) {
        rlAnalysisId.stream()
                .forEach(id -> rlImportSelectionRepository.deleteByRlAnalysisIdAndProjectId(id, projectId));
    }

    @Override
    public void deletePortfolioSummary(List<Long> rlPortfolioId, Long projectId) {
        rlPortfolioId.stream()
                .forEach(id -> rlPortfolioSelectionRepository.deleteByPortfolioIdAndProjectId(id,projectId));
    }
}
