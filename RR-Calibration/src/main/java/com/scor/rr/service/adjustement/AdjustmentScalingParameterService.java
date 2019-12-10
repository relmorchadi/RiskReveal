package com.scor.rr.service.adjustement;

import com.scor.rr.domain.ScalingAdjustmentParameter;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.ScalingAdjustmentParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentScalingParameterService {
    @Autowired
    ScalingAdjustmentParameterRepository scalingAdjustmentParameterRepository;

    public ScalingAdjustmentParameter getAdjustmentScalingParameterParameterByNode(Integer ideNode) {
        return scalingAdjustmentParameterRepository.findByAdjustmentNodeAdjustmentNodeId(ideNode);
    }

    public ScalingAdjustmentParameter save(ScalingAdjustmentParameter parameterEntity) {
        return scalingAdjustmentParameterRepository.save(parameterEntity);
    }

    public void delete(Integer id) {
        this.scalingAdjustmentParameterRepository.delete(
                this.scalingAdjustmentParameterRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    public void deleteByNodeId(Integer nodeId) {
        scalingAdjustmentParameterRepository.deleteByAdjustmentNodeAdjustmentNodeId(nodeId);
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
