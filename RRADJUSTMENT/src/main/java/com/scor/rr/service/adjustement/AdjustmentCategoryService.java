package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentCategoryEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.CATEGORYNOTFOUND;
import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentCategoryService {

    @Autowired
    AdjustmentCategoryRepository adjustmentCategoryRepository;

    public List<AdjustmentCategoryEntity> findAll(){
        return adjustmentCategoryRepository.findAll();
    }

    public AdjustmentCategoryEntity findOne(Integer id){
        return adjustmentCategoryRepository.findById(id).orElseThrow(throwException(CATEGORYNOTFOUND,NOT_FOUND));
    }

    public void delete(Integer id) {
        this.adjustmentCategoryRepository.delete(
                this.adjustmentCategoryRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }

}
