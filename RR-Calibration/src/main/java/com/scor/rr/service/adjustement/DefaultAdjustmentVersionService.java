package com.scor.rr.service.adjustement;


import com.scor.rr.domain.DefaultAdjustmentVersionEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.DefaultAdjustmentVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class DefaultAdjustmentVersionService {

    @Autowired
    DefaultAdjustmentVersionRepository defaultAdjustmentVersionRepository;



    public List<DefaultAdjustmentVersionEntity> findAll(){
        return defaultAdjustmentVersionRepository.findAll();
    }

    public DefaultAdjustmentVersionEntity findOne(Integer id){
        return defaultAdjustmentVersionRepository.getOne(id);
    }

    public void delete(Integer id) {
        this.defaultAdjustmentVersionRepository.delete(
                this.defaultAdjustmentVersionRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
