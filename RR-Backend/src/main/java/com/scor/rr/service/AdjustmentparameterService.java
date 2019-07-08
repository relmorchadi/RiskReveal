package com.scor.rr.service;

import com.scor.rr.domain.AdjustmentParameterEntity;
import com.scor.rr.domain.AdjustmentParameterEntityPK;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentparameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentparameterService {

    @Autowired
    AdjustmentparameterRepository adjustmentparameterRepository;

    public AdjustmentParameterEntity findOne(AdjustmentParameterEntityPK id){
        return adjustmentparameterRepository.getOne(id);
    }

    public List<AdjustmentParameterEntity> findAll(){
        return adjustmentparameterRepository.findAll();
    }

    public AdjustmentParameterEntity save(AdjustmentParameterEntity adjustmentparameterModel){
        return adjustmentparameterRepository.save(adjustmentparameterModel);
    }

    public void delete(AdjustmentParameterEntityPK id) {
        this.adjustmentparameterRepository.delete(
                this.adjustmentparameterRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
    
}
