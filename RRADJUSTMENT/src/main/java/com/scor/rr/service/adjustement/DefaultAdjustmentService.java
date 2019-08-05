package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeProcessingRequest;
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

    public List<AdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltEntity(Integer scorPltHeaderId) {
        if (scorpltheaderRepository.findById(scorPltHeaderId).isPresent()) {
            ScorPltHeaderEntity scorPltHeaderEntity = scorpltheaderRepository.findById(scorPltHeaderId).get();
            if(scorPltHeaderEntity.getEntity() != null) {
                DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentRepository.findAll().stream().filter(defaultAdjustmentEntity ->
                        defaultAdjustmentEntity.getEntity().equals(scorPltHeaderEntity.getEntity()))
                        .findAny().orElse(null);
                return getDefaultAdjustmentNode(defaultAdjustment,scorPltHeaderEntity);
            } else {
                return null;
            }
        } else return null;
    }

    public List<AdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltRPAndTRAndET(Integer scorPltHeaderId) {
        if (scorpltheaderRepository.findById(scorPltHeaderId).isPresent()) {
            ScorPltHeaderEntity scorPltHeaderEntity = scorpltheaderRepository.findById(scorPltHeaderId).get();
            DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentRepository.findAll().stream().filter(defaultAdjustmentEntity ->
                            defaultAdjustmentEntity.getTargetRap().equals(scorPltHeaderEntity.getTargetRap()) &&
                            defaultAdjustmentEntity.getEngineType().equals(scorPltHeaderEntity.getEngineType())&&
                            defaultAdjustmentRegionPerilService.regionPerilDefaultAdjustmentExist(defaultAdjustmentEntity.getIdDefaultAdjustment(),scorPltHeaderId)&&
                                    defaultAdjustmentEntity.getEntity().equals(scorPltHeaderEntity.getEntity())
                    )
                    .findAny().orElse(null);
            return getDefaultAdjustmentNode(defaultAdjustment,scorPltHeaderEntity);
        } else return null;
    }

    public List<AdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltMarketChannel(Integer scorPltHeaderId) {
        if (scorpltheaderRepository.findById(scorPltHeaderId).isPresent()) {
            ScorPltHeaderEntity scorPltHeaderEntity = scorpltheaderRepository.findById(scorPltHeaderId).get();
            DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentRepository.findAll().stream().filter(defaultAdjustmentEntity ->
                    !defaultAdjustmentEntity.getMarketChannel().equals(scorPltHeaderEntity.getMarketChannel()))
                    .findAny().orElse(null);
            return getDefaultAdjustmentNode(defaultAdjustment,scorPltHeaderEntity);
        } else return null;
    }

    private List<AdjustmentNodeEntity> getDefaultAdjustmentNode(DefaultAdjustmentEntity defaultAdjustment,ScorPltHeaderEntity purePlt) {
        if (defaultAdjustment != null) {
            List<DefaultAdjustmentVersionEntity> defaultAdjustmentVersion = defaultAdjustmentVersionRepository
                    .findAll()
                    .stream()
                    .filter(defaultAdjustmentVersionEntity ->
                            defaultAdjustmentVersionEntity.getDefaultAdjustment().getIdDefaultAdjustment() == defaultAdjustment.getIdDefaultAdjustment() && defaultAdjustmentVersionEntity.getIsactive() && new DateTime(defaultAdjustmentVersionEntity.getEffectiveFrom()).isBeforeNow())
                    .collect(Collectors.toList());
            if (!defaultAdjustmentVersion.isEmpty()) {
                List<DefaultAdjustmentNodeEntity> defaultAdjustmentNodeEntities = new ArrayList<>();
                for (DefaultAdjustmentVersionEntity defaultAdjustmentVersionEntity : defaultAdjustmentVersion) {
                    List<DefaultAdjustmentThreadEntity> defaultAdjustmentThreadEntities = defaultAdjustmentThreadRepository.findAll()
                            .stream()
                            .filter(defaultAdjustmentThreadEntity -> defaultAdjustmentThreadEntity.getDefaultAdjustmentVersionByIdDefaultAdjustmentVersion().getIdDefaultAdjustmentVersion() == defaultAdjustmentVersionEntity.getIdDefaultAdjustmentVersion())
                            .collect(Collectors.toList());
                    if (!defaultAdjustmentThreadEntities.isEmpty()) {
                        for (DefaultAdjustmentThreadEntity defaultAdjustmentThreadEntity : defaultAdjustmentThreadEntities) {
                            List<DefaultAdjustmentNodeEntity> defaultAdjustmentNode = defaultAdjustmentNodeRepository.findAll().stream().filter(defaultAdjustmentNodeEntity -> defaultAdjustmentNodeEntity.getDefaultAdjustmentThread().getIdDefaultAdjustmentThread() == defaultAdjustmentThreadEntity.getIdDefaultAdjustmentThread()).collect(Collectors.toList());
                            if (!defaultAdjustmentNode.isEmpty()) {
                                defaultAdjustmentNodeEntities.addAll(defaultAdjustmentNode);
                            }
                        }
                        if (!defaultAdjustmentNodeEntities.isEmpty()) {
                            List<AdjustmentNodeEntity> adjustmentNodeEntities = new ArrayList<>();
                            AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
                            adjustmentThreadEntity.setScorPltHeaderByIdPurePlt(purePlt);
                            adjustmentThreadEntity.setLocked(true);
                            adjustmentThreadEntity.setCreatedOn(new Timestamp(new Date().getTime()));
                            adjustmentThreadEntity.setCreatedBy("HAMZA");
                            adjustmentThreadRepository.save(adjustmentThreadEntity);
                            for(DefaultAdjustmentNodeEntity defaultAdjustmentNodeEntity : defaultAdjustmentNodeEntities) {
                                AdjustmentNodeEntity adjustmentNodeEntityDefaultRef = new AdjustmentNodeEntity(defaultAdjustmentNodeEntity.getSequence(),defaultAdjustmentNodeEntity.getCappedMaxExposure(),adjustmentThreadEntity,defaultAdjustmentNodeEntity.getAdjustmentBasis(),defaultAdjustmentNodeEntity.getAdjustmentType(),adjustmentStateRepository.getAdjustmentStateEntityByCodeValid());
                                adjustmentNodeEntityDefaultRef = adjustmentnodeRepository.save(adjustmentNodeEntityDefaultRef);
                                adjustmentNodeEntities.add(adjustmentNodeEntityDefaultRef);
                                adjustmentNodeProcessingService.saveByInputPlt(new AdjustmentNodeProcessingRequest(purePlt.getScorPltHeaderId(),adjustmentNodeEntityDefaultRef.getIdAdjustmentNode()));
                                DefaultRetPerBandingParamsEntity paramsEntity = defaultRetPerBandingParamsRepository.getByDefaultAdjustmentNodeByIdDefaultNode(defaultAdjustmentNodeEntity.getIdDefaultAdjustmentNode());
                                List<PEATData> peatData = new ArrayList<>();
                                if(paramsEntity.getPeatDataPath() != null) {
                                    File file = new File(paramsEntity.getPeatDataPath());
                                    CSVPLTFileReader csvpltFileReader =  new CSVPLTFileReader();
                                    try {
                                        peatData =  csvpltFileReader.readPeatData(file);
                                    } catch (com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
                                        e.printStackTrace();
                                    }
                                }
                                List<AdjustmentReturnPeriodBending> returnPeriodBendings = new ArrayList<>();
                                if(paramsEntity.getAdjustmentReturnPeriodPath() != null) {
                                    File file = new File(paramsEntity.getAdjustmentReturnPeriodPath());
                                    CSVPLTFileReader csvpltFileReader =  new CSVPLTFileReader();
                                    try {
                                        returnPeriodBendings =  csvpltFileReader.readAdjustmentReturnPeriodBanding(file);
                                    } catch (com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
                                        e.printStackTrace();
                                    }
                                }
                                AdjustmentNodeProcessingEntity adjustmentNodeProcessingEntity = adjustmentNodeProcessingService.saveByAdjustedPlt(new AdjustmentParameterRequest(paramsEntity.getLmf(),paramsEntity.getRpmf(),peatData,purePlt.getScorPltHeaderId(),adjustmentNodeEntityDefaultRef.getIdAdjustmentNode(),returnPeriodBendings));
                                purePlt = adjustmentNodeProcessingEntity.getScorPltHeaderByIdAdjustedPlt();
                            }
                            adjustmentThreadEntity.setScorPltHeaderByIdThreadPlt(purePlt);
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
        if(defaultAdjustmentRepository.findById(id).isPresent())
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
