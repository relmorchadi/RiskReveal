package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentState;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.STATE_NOT_FOUND;
import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentStateService {

    @Autowired
    AdjustmentStateRepository adjustmentStateRepository;


    public AdjustmentState findOne(Integer id){
        return adjustmentStateRepository.findById(id).orElseThrow(throwException(STATE_NOT_FOUND,NOT_FOUND));
    }

    public List<AdjustmentState> findAll(){
        return adjustmentStateRepository.findAll();
    }

    public AdjustmentState save(AdjustmentState adjustmentnodeModel){
        return adjustmentStateRepository.save(adjustmentnodeModel);
    }

    public void delete(Integer id) {
        this.adjustmentStateRepository.delete(
                this.adjustmentStateRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
