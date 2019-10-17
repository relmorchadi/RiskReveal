package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentReturnPeriodBandingParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentReturnPeriodBandingParameterService {

    @Autowired
    AdjustmentReturnPeriodBandingParameterRepository parameterRepository;

    public List<AdjustmentReturnPeriodBandingParameterEntity> getAdjustmentReturnPeriodBandingParameterByNode(Integer ideNode) {
        return parameterRepository.getAdjustmentReturnPeriodBandingParameterByAdjustmentNodeBy(ideNode);
    }

    public AdjustmentReturnPeriodBandingParameterEntity save(AdjustmentReturnPeriodBandingParameterEntity parameterEntity) {
        return parameterRepository.save(parameterEntity);
    }

    public void delete(Integer id) {
        this.parameterRepository.delete(
                this.parameterRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    public void deleteByNodeId(Integer nodeId) {
        parameterRepository.deleteByAdjustmentNodeByFkAdjustmentNodeId_AdjustmentNodeId(nodeId);
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }

}
