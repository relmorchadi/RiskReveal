package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentEventBasedParameterEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentEventBasedParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentEventBasedParameterService {

    @Autowired
    AdjustmentEventBasedParameterRepository eventBasedParameterRepository;

    public AdjustmentEventBasedParameterEntity getAdjustmentEventBasedParameterByNode(Long idNode) {
        return eventBasedParameterRepository.findByAdjustmentNodeAdjustmentNodeId(idNode);
    }

    public AdjustmentEventBasedParameterEntity save(AdjustmentEventBasedParameterEntity parameterEntity) {
        return eventBasedParameterRepository.save(parameterEntity);
    }

    public void delete(Integer id) {
        this.eventBasedParameterRepository.delete(
                this.eventBasedParameterRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    public void deleteByNodeId(Integer nodeId) {
        eventBasedParameterRepository.deleteByAdjustmentNodeAdjustmentNodeId(nodeId);
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }

}
