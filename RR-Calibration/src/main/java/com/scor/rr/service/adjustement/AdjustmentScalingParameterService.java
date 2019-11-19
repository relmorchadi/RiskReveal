package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentScalingParameterEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentScalingParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentScalingParameterService {
    @Autowired
    AdjustmentScalingParameterRepository adjustmentScalingParameterRepository;

    public AdjustmentScalingParameterEntity getAdjustmentScalingParameterParameterByNode(Long ideNode) {
        return adjustmentScalingParameterRepository.findByAdjustmentNodeAdjustmentNodeId(ideNode);
    }

    public AdjustmentScalingParameterEntity save(AdjustmentScalingParameterEntity parameterEntity) {
        return adjustmentScalingParameterRepository.save(parameterEntity);
    }

    public void delete(Long id) {
        this.adjustmentScalingParameterRepository.delete(
                this.adjustmentScalingParameterRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    public void deleteByNodeId(Long nodeId) {
        adjustmentScalingParameterRepository.deleteByAdjustmentNode_AdjustmentNodeId(nodeId);
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
