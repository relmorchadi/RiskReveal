package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.UtilsMethode;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeProcessingRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
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
    ScorpltheaderRepository scorpltheaderRepository;

    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

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

    public List<AdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(Integer scorPltHeaderId) {
        if (scorpltheaderRepository.findById(scorPltHeaderId).isPresent()) {
            ScorPltHeaderEntity scorPltHeaderEntity = scorpltheaderRepository.findById(scorPltHeaderId).get();
            List<DefaultAdjustmentEntity> defaultAdjustmentEntities = defaultAdjustmentRepository.findByTargetRapTargetRapIdEqualsAndMarketChannel_MarketChannelIdAndEngineTypeEqualsAndEntityEntityIdEquals(
                    scorPltHeaderEntity.getTargetRap().getTargetRapId(),
                    scorPltHeaderEntity.getMarketChannel().getMarketChannelId(),
                    scorPltHeaderEntity.getEngineType(),
                    scorPltHeaderEntity.getEntity().getEntityId());
            if(defaultAdjustmentEntities != null) {
                DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentEntities.stream().filter(defaultAdjustmentEntity ->
                        defaultAdjustmentEntity.getTargetRap().equals(scorPltHeaderEntity.getTargetRap()) &&
                                defaultAdjustmentEntity.getEngineType().equals(scorPltHeaderEntity.getEngineType()) &&
                                defaultAdjustmentRegionPerilService.regionPerilDefaultAdjustmentExist(defaultAdjustmentEntity.getDefaultAdjustmentId(), scorPltHeaderId) &&
                                defaultAdjustmentEntity.getEntity().equals(scorPltHeaderEntity.getEntity())
                )
                        .findAny().orElse(null);
                return getDefaultAdjustmentNode(defaultAdjustment, scorPltHeaderEntity);
            } else return null;
        } else return null;
    }

    private List<AdjustmentNodeEntity> getDefaultAdjustmentNode(DefaultAdjustmentEntity defaultAdjustment,ScorPltHeaderEntity purePlt) {
        if (defaultAdjustment != null) {
            List<DefaultAdjustmentVersionEntity> defaultAdjustmentVersion = defaultAdjustmentVersionRepository
                    .findAll()
                    .stream()
                    .filter(defaultAdjustmentVersionEntity ->
                            defaultAdjustmentVersionEntity.getDefaultAdjustment().getDefaultAdjustmentId() == defaultAdjustment.getDefaultAdjustmentId() && defaultAdjustmentVersionEntity.getActive() && new DateTime(defaultAdjustmentVersionEntity.getEffectiveFrom()).isBeforeNow())
                    .collect(Collectors.toList());
            if (!defaultAdjustmentVersion.isEmpty()) {
                List<DefaultAdjustmentNodeEntity> defaultAdjustmentNodeEntities = new ArrayList<>();
                for (DefaultAdjustmentVersionEntity defaultAdjustmentVersionEntity : defaultAdjustmentVersion) {
                    List<DefaultAdjustmentThreadEntity> defaultAdjustmentThreadEntities = defaultAdjustmentThreadRepository.findAll()
                            .stream()
                            .filter(defaultAdjustmentThreadEntity -> defaultAdjustmentThreadEntity.getDefaultAdjustmentVersion().getDefaultAdjustmentVersionId() == defaultAdjustmentVersionEntity.getDefaultAdjustmentVersionId())
                            .collect(Collectors.toList());
                    if (!defaultAdjustmentThreadEntities.isEmpty()) {
                        for (DefaultAdjustmentThreadEntity defaultAdjustmentThreadEntity : defaultAdjustmentThreadEntities) {
                            List<DefaultAdjustmentNodeEntity> defaultAdjustmentNode = defaultAdjustmentNodeRepository.findAll().stream().filter(defaultAdjustmentNodeEntity -> defaultAdjustmentNodeEntity.getDefaultAdjustmentThread().getDefaultAdjustmentThreadId() == defaultAdjustmentThreadEntity.getDefaultAdjustmentThreadId()).collect(Collectors.toList());
                            if (!defaultAdjustmentNode.isEmpty()) {
                                defaultAdjustmentNodeEntities.addAll(defaultAdjustmentNode);
                            }
                        }
                        if (!defaultAdjustmentNodeEntities.isEmpty()) {
                            List<AdjustmentNodeEntity> adjustmentNodeEntities = new ArrayList<>();
                            AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
                            adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadPureId(purePlt);
                            adjustmentThreadEntity.setLocked(true);
                            adjustmentThreadEntity.setCreatedOn(new Timestamp(new Date().getTime()));
                            adjustmentThreadEntity.setCreatedBy("HAMZA");
                            adjustmentThreadEntity = adjustmentThreadRepository.save(adjustmentThreadEntity);
                            for (DefaultAdjustmentNodeEntity defaultAdjustmentNodeEntity : defaultAdjustmentNodeEntities) {
                                AdjustmentNodeEntity adjustmentNodeEntityDefaultRef = new AdjustmentNodeEntity(defaultAdjustmentNodeEntity.getSequence(), defaultAdjustmentNodeEntity.getCappedMaxExposure(), adjustmentThreadEntity, defaultAdjustmentNodeEntity.getAdjustmentBasis(), defaultAdjustmentNodeEntity.getAdjustmentType(), adjustmentStateRepository.getAdjustmentStateEntityByCodeValid());
                                adjustmentNodeEntityDefaultRef = adjustmentnodeRepository.save(adjustmentNodeEntityDefaultRef);
                                adjustmentNodeEntities.add(adjustmentNodeEntityDefaultRef);
                                adjustmentNodeProcessingService.saveByInputPlt(new AdjustmentNodeProcessingRequest(purePlt.getPkScorPltHeaderId(), adjustmentNodeEntityDefaultRef.getAdjustmentNodeId()));
                                DefaultRetPerBandingParamsEntity paramsEntity = defaultRetPerBandingParamsRepository.getByDefaultAdjustmentNodeByIdDefaultNode(defaultAdjustmentNodeEntity.getDefaultAdjustmentNodeId());
                                List<AdjustmentReturnPeriodBending> periodBendings = UtilsMethode.getReturnPeriodBendings(paramsEntity.getAdjustmentReturnPeriodPath());
                                AdjustmentNodeProcessingEntity adjustmentNodeProcessingEntity = adjustmentNodeProcessingService.saveByAdjustedPlt(new AdjustmentParameterRequest(paramsEntity.getLmf() != null ? paramsEntity.getLmf() : 0, paramsEntity.getRpmf() != null ? paramsEntity.getRpmf() : 0, UtilsMethode.getPeatDataFromFile(paramsEntity.getPeatDataPath()), purePlt.getPkScorPltHeaderId(), adjustmentNodeEntityDefaultRef.getAdjustmentNodeId(),periodBendings ));
                                purePlt = adjustmentNodeProcessingEntity.getScorPltHeaderByFkAdjustedPlt();
                            }
                            adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadId(purePlt);
                            adjustmentThreadRepository.save(adjustmentThreadEntity);
                            return adjustmentNodeEntities;
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }

                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return null;
    }

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

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
