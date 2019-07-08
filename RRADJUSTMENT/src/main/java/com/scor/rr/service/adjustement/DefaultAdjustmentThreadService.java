package com.scor.rr.service.adjustement;

import com.scor.rr.domain.DefaultAdjustmentThreadEntity;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.DefaultAdjustmentThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class DefaultAdjustmentThreadService {

    @Autowired
    DefaultAdjustmentThreadRepository defaultAdjustmentThreadRepository;

    public List<DefaultAdjustmentThreadEntity> findAll(){
        return defaultAdjustmentThreadRepository.findAll();
    }

    public DefaultAdjustmentThreadEntity findOne(Integer id){
        return defaultAdjustmentThreadRepository.getOne(id);
    }

    public void delete(Integer id) {
        this.defaultAdjustmentThreadRepository.delete(
                this.defaultAdjustmentThreadRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
