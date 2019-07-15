package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentBasisEntity;
import com.scor.rr.domain.AdjustmentCategoryEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentBasisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.BASISNOTFOUND;
import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentBasisService {

    @Autowired
    AdjustmentBasisRepository adjustmentBasisRepository;

    public List<AdjustmentBasisEntity> findAll(){
        return adjustmentBasisRepository.findAll();
    }

    public AdjustmentBasisEntity findOne(Integer id){
            return adjustmentBasisRepository.findById(id).orElseThrow(throwException(BASISNOTFOUND,NOT_FOUND));
    }

    public void delete(Integer id) {
        this.adjustmentBasisRepository.delete(
                this.adjustmentBasisRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
