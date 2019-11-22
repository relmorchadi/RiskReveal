package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentType;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentTypeRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.TYPE_NOT_FOUND;
import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentTypeService {

    @Autowired
    AdjustmentTypeRepository adjustmentTypeRepository;

    public List<AdjustmentType> findAll(){
        return adjustmentTypeRepository.findAll();
    }

    public AdjustmentType findOne(Integer id){
        return adjustmentTypeRepository.findById(id).orElseThrow(throwException(TYPE_NOT_FOUND,NOT_FOUND));
    }

    public void delete(Integer id) {
        this.adjustmentTypeRepository.delete(
                this.adjustmentTypeRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
