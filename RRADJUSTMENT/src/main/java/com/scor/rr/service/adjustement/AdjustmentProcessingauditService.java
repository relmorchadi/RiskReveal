package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentProcessingAuditEntity;
import com.scor.rr.domain.ScorPltHeaderEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentnodeRepository;
import com.scor.rr.repository.AdjustmentprocessingauditRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentProcessingauditService {
    @Autowired
    AdjustmentprocessingauditRepository adjustmentprocessingauditRepository;

    public AdjustmentProcessingAuditEntity findOne(Integer id){
        return adjustmentprocessingauditRepository.getOne(id);
    }

    public List<AdjustmentProcessingAuditEntity> findAll(){
        return adjustmentprocessingauditRepository.findAll();
    }

    public AdjustmentProcessingAuditEntity save(AdjustmentProcessingAuditEntity adjustmentprocessingauditModel){
        return adjustmentprocessingauditRepository.save(adjustmentprocessingauditModel);
    }

    public void delete(Integer id) {
        this.adjustmentprocessingauditRepository.delete(
                this.adjustmentprocessingauditRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
