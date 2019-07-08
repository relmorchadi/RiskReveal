package com.scor.rr.service;

import com.scor.rr.domain.AdjustmentNodeProcessingEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentnodeprocessingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentnodeprocessingService {

    @Autowired
    AdjustmentnodeprocessingRepository adjustmentnodeprocessingRepository;

   public AdjustmentNodeProcessingEntity findOne(Integer id){
       return adjustmentnodeprocessingRepository.getOne(id);
   }

    public List<AdjustmentNodeProcessingEntity> findAll(){
        return adjustmentnodeprocessingRepository.findAll();
    }

    public AdjustmentNodeProcessingEntity save(AdjustmentNodeProcessingEntity adjustmentnodeprocessingModel){
       return adjustmentnodeprocessingRepository.save(adjustmentnodeprocessingModel);
    }

    public void delete(Integer id) {
        this.adjustmentnodeprocessingRepository.delete(
                this.adjustmentnodeprocessingRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }


}
