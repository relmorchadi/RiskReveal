package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentthreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentthreadService {

    @Autowired
    AdjustmentthreadRepository adjustmentthreadRepository;

    public AdjustmentThreadEntity findOne(Integer id){
        return adjustmentthreadRepository.getOne(id);
    }

    public List<AdjustmentThreadEntity> findAll(){
        return adjustmentthreadRepository.findAll();
    }

    public AdjustmentThreadEntity save(AdjustmentThreadEntity AdjustmentthreadModel){
        return adjustmentthreadRepository.save(AdjustmentthreadModel);
    }

    public void delete(Integer id) {
        this.adjustmentthreadRepository.delete(
                this.adjustmentthreadRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
