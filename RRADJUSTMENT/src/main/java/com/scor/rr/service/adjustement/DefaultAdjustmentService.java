package com.scor.rr.service.adjustement;

import com.scor.rr.domain.*;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    DefaultAdjustmentRegionPerilService defaultAdjustmentRegionPerilService;

    public List<DefaultAdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltEntity(Integer scorPltHeaderId) {
        if (scorpltheaderRepository.findById(scorPltHeaderId).isPresent()) {
            ScorPltHeaderEntity scorPltHeaderEntity = scorpltheaderRepository.findById(scorPltHeaderId).get();
            if(scorPltHeaderEntity.getEntity() != null) {
                DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentRepository.findAll().stream().filter(defaultAdjustmentEntity ->
                        defaultAdjustmentEntity.getEntity().equals(scorPltHeaderEntity.getEntity()))
                        .findAny().orElse(null);
                return getDefaultAdjustmentNode(defaultAdjustment);
            } else {
                return null;
            }
        } else return null;
    }

    public List<DefaultAdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltRPAndTRAndET(Integer scorPltHeaderId) {
        if (scorpltheaderRepository.findById(scorPltHeaderId).isPresent()) {
            ScorPltHeaderEntity scorPltHeaderEntity = scorpltheaderRepository.findById(scorPltHeaderId).get();
            DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentRepository.findAll().stream().filter(defaultAdjustmentEntity ->
                            defaultAdjustmentEntity.getTargetRap().equals(scorPltHeaderEntity.getTargetRap()) &&
                            defaultAdjustmentEntity.getEngineType().equals(scorPltHeaderEntity.getEngineType())&&
                            defaultAdjustmentRegionPerilService.regionPerilDefaultAdjustmentExist(defaultAdjustmentEntity.getIdDefaultAdjustment(),scorPltHeaderId)&&
                                    defaultAdjustmentEntity.getEntity().equals(scorPltHeaderEntity.getEntity())
                    )
                    .findAny().orElse(null);
            return getDefaultAdjustmentNode(defaultAdjustment);
        } else return null;

    }

    public List<DefaultAdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltMarketChannel(Integer scorPltHeaderId) {
        if (scorpltheaderRepository.findById(scorPltHeaderId).isPresent()) {
            ScorPltHeaderEntity scorPltHeaderEntity = scorpltheaderRepository.findById(scorPltHeaderId).get();
            DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentRepository.findAll().stream().filter(defaultAdjustmentEntity ->
                    !defaultAdjustmentEntity.getMarketChannel().equals(scorPltHeaderEntity.getMarketChannel()))
                    .findAny().orElse(null);
            return getDefaultAdjustmentNode(defaultAdjustment);
        } else return null;
    }

    private List<DefaultAdjustmentNodeEntity> getDefaultAdjustmentNode(DefaultAdjustmentEntity defaultAdjustment) {
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
                            return defaultAdjustmentNodeEntities;
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
