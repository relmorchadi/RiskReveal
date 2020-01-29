package com.scor.rr.service.adjustement;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.DefaultAdjustmentsInScopeViewDTO;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class DefaultAdjustmentService {

    @Autowired
    DefaultAdjustmentRepository defaultAdjustmentRepository;

    @Autowired
    DefaultAdjustmentVersionRepository defaultAdjustmentVersionRepository;

    @Autowired
    DefaultAdjustmentThreadRepository defaultAdjustmentThreadRepository;

    @Autowired
    DefaultAdjustmentNodeRepository defaultAdjustmentNodeRepository;

    @Autowired
    PltHeaderRepository pltHeaderRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    AdjustmentNodeRepository adjustmentNodeRepository;

    @Autowired
    DefaultAdjustmentRegionPerilService defaultAdjustmentRegionPerilService;

    @Autowired
    AdjustmentThreadRepository adjustmentThreadRepository;

    @Autowired
    AdjustmentStateRepository adjustmentStateRepository;

    @Autowired
    AdjustmentNodeProcessingService adjustmentNodeProcessingService;

    @Autowired
    DefaultRetPerBandingParamsRepository defaultRetPerBandingParamsRepository;

    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @Autowired
    ModelAnalysisEntityRepository modelAnalysisEntityRepository;

    @Autowired
    DefaultAdjustmentsInScopeRepository defaultAdjustmentsInScopeRepository;

    @Autowired
    private ModelMapper modelMapper;

    //NOTE: I think we should have two functions:
    // - one takes PLT ID as input and return a list of DefaultAdjustmentNodeEntity required by this PLT
    // - one take DefaultAdjustmentNodeEntity as input and return a Adjustment Node
    // We could have a global function that calls these two methods to take as input Pure PLT ID and return a Default Adjustment Thread / Nodes for it if any

    public List<DefaultAdjustmentNode> getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(long targetRapId,
                                                                                          long regionPerilId,
                                                                                          long marketChannelId,
                                                                                          String engineType,
                                                                                          int entityId
                                                                                                 ) throws RRException {
        List<DefaultAdjustmentNode> defaultAdjustmentNodeEntities = new ArrayList<>();
        List<DefaultAdjustmentEntity> defaultAdjustmentEntities = defaultAdjustmentRepository.findByTargetRapTargetRAPIdEqualsAndMarketChannel_MarketChannelIdAndEngineTypeEqualsAndEntityEquals(
                targetRapId,
                marketChannelId,
                engineType,
                entityId);
        if (defaultAdjustmentEntities != null) {
            DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentEntities.stream().filter(defaultAdjustmentEntity ->
                    defaultAdjustmentEntity.getTargetRap().getTargetRAPId() == targetRapId &&
                            defaultAdjustmentEntity.getEngineType().equals(engineType) &&
                            defaultAdjustmentRegionPerilService.regionPerilDefaultAdjustmentExist(defaultAdjustmentEntity.getDefaultAdjustmentId(), regionPerilId) &&
                            defaultAdjustmentEntity.getEntity() == entityId
            )
                    .findAny().orElse(null);
            if (defaultAdjustment != null) {
                List<DefaultAdjustmentVersionEntity> defaultAdjustmentVersion = defaultAdjustmentVersionRepository
                        .findAll()
                        .stream()
                        .filter(defaultAdjustmentVersionEntity ->
                                defaultAdjustmentVersionEntity.getDefaultAdjustment().getDefaultAdjustmentId() == defaultAdjustment.getDefaultAdjustmentId() && defaultAdjustmentVersionEntity.getActive() && new DateTime(defaultAdjustmentVersionEntity.getEffectiveFrom()).isBeforeNow())
                        .collect(Collectors.toList());
                if (!defaultAdjustmentVersion.isEmpty()) {
                    for (DefaultAdjustmentVersionEntity defaultAdjustmentVersionEntity : defaultAdjustmentVersion) {
                        List<DefaultAdjustmentThreadEntity> defaultAdjustmentThreadEntities = defaultAdjustmentThreadRepository.findAll()
                                .stream()
                                .filter(defaultAdjustmentThreadEntity -> defaultAdjustmentThreadEntity.getDefaultAdjustmentVersion().getDefaultAdjustmentVersionId() == defaultAdjustmentVersionEntity.getDefaultAdjustmentVersionId())
                                .collect(Collectors.toList());
                        if (!defaultAdjustmentThreadEntities.isEmpty()) {
                            for (DefaultAdjustmentThreadEntity defaultAdjustmentThreadEntity : defaultAdjustmentThreadEntities) {
                                List<DefaultAdjustmentNode> defaultAdjustmentNode = defaultAdjustmentNodeRepository.findAll().stream().filter(defaultAdjustmentNodeEntity -> defaultAdjustmentNodeEntity.getDefaultAdjustmentThread().getDefaultAdjustmentThreadId() == defaultAdjustmentThreadEntity.getDefaultAdjustmentThreadId()).collect(Collectors.toList());
                                if (!defaultAdjustmentNode.isEmpty()) {
                                    defaultAdjustmentNodeEntities.addAll(defaultAdjustmentNode);
                                }
                            }
                        }
                    }
                }
            }
        }
        return defaultAdjustmentNodeEntities;
    }

    public List<DefaultAdjustmentNode> getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(Long scorPltHeaderId) throws RRException {
        List<DefaultAdjustmentNode> defaultAdjustmentNodeEntities = new ArrayList<>();
        if (pltHeaderRepository.findById(scorPltHeaderId).isPresent()) {
            PltHeaderEntity pltHeaderEntity = pltHeaderRepository.findById(scorPltHeaderId).get();
            ProjectEntity projectEntity = projectRepository.findById(pltHeaderEntity.getProjectId()).get();
            WorkspaceEntity workspaceEntity = workspaceRepository.findById(projectEntity.getWorkspaceId()).get();
            String engineType = "";
            Optional<ModelAnalysisEntity> modelAnalysis = modelAnalysisEntityRepository.findById(pltHeaderEntity.getModelAnalysisId());
            if (modelAnalysis.isPresent()) {
                if ("ALM".equals(modelAnalysis.get().getModel())) {
                    engineType = "AGG";
                } else if ("DLM".equals(modelAnalysis.get().getModel())) {
                    engineType = "DET";
                }
            }
            if (modelAnalysisEntityRepository.findById(pltHeaderEntity.getModelAnalysisId()).isPresent()) {
                return getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(
                        pltHeaderEntity.getTargetRAPId(),
                        pltHeaderEntity.getRegionPerilId(),
<<<<<<< Updated upstream
                        ofNullable(workspaceEntity).map(we -> we.getWorkspaceMarketChannel()).map(Long::valueOf).orElse(null),
=======
                        "FAC".equals(workspaceEntity.getWorkspaceMarketChannel()) ? 2 : 1,
>>>>>>> Stashed changes
                        engineType,
                        pltHeaderEntity.getEntity() != null ? pltHeaderEntity.getEntity() : 1);
            }
        }
        return defaultAdjustmentNodeEntities;
    }


    public AdjustmentThread createDefaultThread(AdjustmentThread adjustmentThread) throws RRException {
        List<DefaultAdjustmentNode> defaultAdjustmentNodeEntities = getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(adjustmentThread.getInitialPLT().getPltHeaderId());
        for (DefaultAdjustmentNode defaultAdjustmentNodeEntity : defaultAdjustmentNodeEntities) {
            adjustmentNodeService.createAdjustmentNodeFromDefaultAdjustmentReference(adjustmentThread, defaultAdjustmentNodeEntity);
        }
        return adjustmentThread;
    }

//    private List<AdjustmentNodeEntity> createAdjustmentNodeFromDefaultAdjustmentReference(
//            PltHeaderEntity purePlt,
//            List<DefaultAdjustmentNodeEntity> defaultAdjustmentNodeEntities) throws RRException {
//        if (!defaultAdjustmentNodeEntities.isEmpty()) {
//            List<AdjustmentNodeEntity> adjustmentNodeEntities = new ArrayList<>();
//            AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
//            adjustmentThreadEntity.setFinalPLT(purePlt);
//            adjustmentThreadEntity.setIsLocked(true);
//            adjustmentThreadEntity.setCreatedOn(new Timestamp(new Date().getTime()));
//            adjustmentThreadEntity.setCreatedBy("HAMZA");
//            adjustmentThreadEntity = adjustmentThreadRepository.save(adjustmentThreadEntity);
//            for (DefaultAdjustmentNodeEntity defaultAdjustmentNodeEntity : defaultAdjustmentNodeEntities) {
//                AdjustmentNodeEntity adjustmentNodeEntityDefaultRef = new AdjustmentNodeEntity(defaultAdjustmentNodeEntity.getSequence(), defaultAdjustmentNodeEntity.getCappedMaxExposure(), adjustmentThreadEntity, defaultAdjustmentNodeEntity.getAdjustmentBasis(), defaultAdjustmentNodeEntity.getAdjustmentType(), adjustmentStateRepository.getAdjustmentStateEntityByCodeValid());
//                adjustmentNodeEntityDefaultRef = adjustmentnodeRepository.save(adjustmentNodeEntityDefaultRef);
//                adjustmentNodeEntities.add(adjustmentNodeEntityDefaultRef);
//                adjustmentNodeProcessingService.saveByInputPlt(new AdjustmentNodeProcessingRequest(purePlt.getPltHeaderId(), adjustmentNodeEntityDefaultRef.getAdjustmentNode()));
//                DefaultRetPerBandingParamsEntity paramsEntity = defaultRetPerBandingParamsRepository.getByDefaultAdjustmentNodeByIdDefaultNode(defaultAdjustmentNodeEntity.getDefaultAdjustmentNodeId());
//                List<AdjustmentReturnPeriodBending> periodBendings = UtilsMethode.getReturnPeriodBendings(paramsEntity.getAdjustmentReturnPeriodPath());
//                AdjustmentNodeProcessingEntity adjustmentNodeProcessingEntity = adjustmentNodeProcessingService.saveByAdjustedPlt(new AdjustmentParameterRequest(paramsEntity.getLmf() != null ? paramsEntity.getLmf() : 0, paramsEntity.getRpmf() != null ? paramsEntity.getRpmf() : 0, UtilsMethode.getPeatDataFromFile(paramsEntity.getPeatDataPath()), purePlt.getPltHeaderId(), adjustmentNodeEntityDefaultRef.getAdjustmentNode(),periodBendings ));
//                purePlt = adjustmentNodeProcessingEntity.getAdjustedPlt();
//            }
//            adjustmentThreadEntity.setInitialPLT(purePlt);
//            adjustmentThreadRepository.save(adjustmentThreadEntity);
//            return adjustmentNodeEntities;
//        } else {
//            return null;
//        }
//    }

    public List<DefaultAdjustmentEntity> findAll() {
        return defaultAdjustmentRepository.findAll();
    }

    public DefaultAdjustmentEntity findOne(Integer id) {
        if (defaultAdjustmentRepository.findById(id).isPresent())
            return defaultAdjustmentRepository.findById(id).get();
        else return null;
    }

    public void delete(Integer id) {
        this.defaultAdjustmentRepository.delete(
                this.defaultAdjustmentRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    public ResponseEntity<List<DefaultAdjustmentsInScopeViewDTO>> getDefaultAdjustmentsInScope(String workspaceContextCode, int uwYear) {

        try {
            return ResponseEntity.ok(
                    this.defaultAdjustmentsInScopeRepository.findByWorkspaceContextCodeAndUwYear( workspaceContextCode, uwYear)
                            .stream()
                            .map(element -> modelMapper.map(element,DefaultAdjustmentsInScopeViewDTO.class ))
                            .collect(Collectors.toList()));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
