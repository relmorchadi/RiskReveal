package com.scor.rr.service;

import com.scor.rr.domain.AdjustmentStructureEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentstructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentstructureService {

    @Autowired
    AdjustmentstructureRepository adjustmentstructureRepository;

    public AdjustmentStructureEntity findOne(Integer id){
        return adjustmentstructureRepository.getOne(id);
    }

    public List<AdjustmentStructureEntity> findAll(){
        return adjustmentstructureRepository.findAll();
    }

    public AdjustmentStructureEntity save(AdjustmentStructureEntity adjustmentstructureModel){
        return adjustmentstructureRepository.save(adjustmentstructureModel);
    }

    public void delete(Integer id) {
        this.adjustmentstructureRepository.delete(
                this.adjustmentstructureRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
