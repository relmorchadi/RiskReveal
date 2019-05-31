package com.scor.rr.service;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentnodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentnodeService {
    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

    public AdjustmentNodeEntity findOne(Long id){
        return adjustmentnodeRepository.getOne(id);
    }

    public List<AdjustmentNodeEntity> findAll(){
        return adjustmentnodeRepository.findAll();
    }

    public AdjustmentNodeEntity save(AdjustmentNodeEntity adjustmentnodeModel){
        return adjustmentnodeRepository.save(adjustmentnodeModel);
    }

    public void delete(Long id) {
        this.adjustmentnodeRepository.delete(
                this.adjustmentnodeRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
