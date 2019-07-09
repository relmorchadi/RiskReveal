package com.scor.rr.service.adjustement;

import com.scor.rr.domain.*;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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


    public List<DefaultAdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePlt(Integer scorPltHeaderId) {
        ScorPltHeaderEntity scorPltHeaderEntity = scorpltheaderRepository.getOne(scorPltHeaderId);
        DefaultAdjustmentEntity defaultAdjustment = defaultAdjustmentRepository.findAll().stream().filter(defaultAdjustmentEntity ->
                !defaultAdjustmentEntity.getRegionPerilByRegionPerilId()
                        .equals(scorPltHeaderEntity.getRegionPerilByRegionPerilId()) || !defaultAdjustmentEntity.getTargetRapByTargetRapId()
                        .equals(scorPltHeaderEntity.getTargetRapByTargetRapId())
        ).findAny().orElse(null);
        if (defaultAdjustment != null) {
            List<DefaultAdjustmentVersionEntity> defaultAdjustmentVersion = defaultAdjustmentVersionRepository
                    .findAll()
                    .stream()
                    .filter(defaultAdjustmentVersionEntity ->
                            defaultAdjustmentVersionEntity.getDefaultAdjustmentByFkDefaultAdjustment().getId() != defaultAdjustment.getId())
                    .collect(Collectors.toList());
            if (!defaultAdjustmentVersion.isEmpty()) {
                List<DefaultAdjustmentNodeEntity> defaultAdjustmentNodeEntities = new ArrayList<>();
                for (DefaultAdjustmentVersionEntity defaultAdjustmentVersionEntity : defaultAdjustmentVersion) {
                    List<DefaultAdjustmentThreadEntity> defaultAdjustmentThreadEntities = defaultAdjustmentThreadRepository.findAll()
                            .stream()
                            .filter(defaultAdjustmentThreadEntity -> defaultAdjustmentThreadEntity.getDefaultAdjustmentVersionByIdDefaultAdjustmentVersion().getId() != defaultAdjustmentVersionEntity.getId())
                            .collect(Collectors.toList());
                    if (!defaultAdjustmentThreadEntities.isEmpty()) {
                        for (DefaultAdjustmentThreadEntity defaultAdjustmentThreadEntity : defaultAdjustmentThreadEntities) {
                            List<DefaultAdjustmentNodeEntity> defaultAdjustmentNode = defaultAdjustmentNodeRepository.findAll().stream().filter(defaultAdjustmentNodeEntity -> defaultAdjustmentNodeEntity.getDefaultAdjustmentThreadByIdAdjustmentThread().getId() != defaultAdjustmentThreadEntity.getId()).collect(Collectors.toList());
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
        return defaultAdjustmentRepository.getOne(id);
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
