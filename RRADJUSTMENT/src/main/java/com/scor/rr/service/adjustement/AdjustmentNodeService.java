package com.scor.rr.service.adjustement;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentNodeService {
    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

    @Autowired
    AdjustmentnodeprocessingRepository adjustmentnodeprocessingRepository;

    @Autowired
    AdjustmentBasisRepository adjustmentBasisRepository;

    @Autowired
    AdjustmentThreadRepository adjustmentThread;

    @Autowired
    AdjustmentStateRepository adjustmentStateRepository;

    @Autowired
    AdjustmentCategoryRepository adjustmentCategoryRepository;

    @Autowired
    AdjustmentTypeRepository adjustmentTypeRepository;


    public AdjustmentNodeEntity findOne(Long id){
        return adjustmentnodeRepository.getOne(id);
    }

    public List<AdjustmentNodeEntity> findAll(){
        return adjustmentnodeRepository.findAll();
    }

    public AdjustmentNodeEntity save(AdjustmentNodeRequest adjustmentNodeRequest){
        AdjustmentNodeEntity adjustmentNodeEntity = new AdjustmentNodeEntity();
        adjustmentNodeEntity.setLayer(adjustmentNodeRequest.getLayer());
        adjustmentNodeEntity.setSequence(adjustmentNodeRequest.getSequence());
        adjustmentNodeEntity.setInputChanged(adjustmentNodeRequest.getInputChanged());
        adjustmentNodeEntity.setAdjustmentParamsSource(adjustmentNodeRequest.getAdjustmentParamsSource());
        adjustmentNodeEntity.setLossNetFlag(adjustmentNodeRequest.getLossNetFlag());
        adjustmentNodeEntity.setHasNewParamsFile(adjustmentNodeRequest.getHasNewParamsFile());
        adjustmentNodeEntity.setAdjustmentType(adjustmentTypeRepository.getOne(adjustmentNodeRequest.getAdjustmentType()));
        adjustmentNodeEntity.setAdjustmentBasis(adjustmentBasisRepository.getOne(adjustmentNodeRequest.getAdjustmentBasis()));
        adjustmentNodeEntity.setAdjustmentCategory(adjustmentCategoryRepository.getOne(adjustmentNodeRequest.getAdjustmentCategory()));
        adjustmentNodeEntity.setAdjustmentState(adjustmentStateRepository.getOne(adjustmentNodeRequest.getAdjustmentState()));
        adjustmentNodeEntity.setAdjustmentThread(adjustmentThread.getOne(adjustmentNodeRequest.getAdjustmentThreadId()));
        return adjustmentnodeRepository.save(adjustmentNodeEntity);
    }

    public void delete(Long id) {
        this.adjustmentnodeRepository.delete(
                this.adjustmentnodeRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
