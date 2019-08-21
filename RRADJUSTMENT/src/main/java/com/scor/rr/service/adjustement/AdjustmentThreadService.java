package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentThreadRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentThreadService {

    @Autowired
    AdjustmentThreadRepository adjustmentthreadRepository;

    @Autowired
    ScorpltheaderRepository scorpltheaderRepository;

    public AdjustmentThreadEntity findOne(Integer id){
        return adjustmentthreadRepository.findById(id).orElseThrow(throwException(THREADNOTFOUND,NOT_FOUND));
    }

    public List<AdjustmentThreadEntity> findAll(){
        return adjustmentthreadRepository.findAll();
    }

    public AdjustmentThreadEntity savePurePlt(AdjustmentThreadRequest adjustmentThreadRequest){
        AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
        adjustmentThreadEntity.setThreadType(adjustmentThreadRequest.getThreadType());
        adjustmentThreadEntity.setCreatedOn(adjustmentThreadRequest.getCreatedOn());
        adjustmentThreadEntity.setCreatedBy(adjustmentThreadRequest.getCreatedBy());
        adjustmentThreadEntity.setLocked(adjustmentThreadRequest.getLocked());
        if(scorpltheaderRepository.findById(adjustmentThreadRequest.getPltPureId()).isPresent()) {
            adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadPureId(scorpltheaderRepository.findById(adjustmentThreadRequest.getPltPureId()).get());
            return adjustmentthreadRepository.save(adjustmentThreadEntity);
        } else {
            throwException(PLTNOTFOUNT, NOT_FOUND);
            return null;
        }
    }

    public AdjustmentThreadEntity saveAdjustedPlt(AdjustmentThreadRequest adjustmentThreadRequest){
        AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
        adjustmentThreadEntity.setThreadType(adjustmentThreadRequest.getThreadType());
        adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadId(scorpltheaderRepository.getOne(adjustmentThreadRequest.getPltFinalId()));
        return adjustmentthreadRepository.save(adjustmentThreadEntity);
    }

    public void delete(Integer id) {
        this.adjustmentthreadRepository.delete(
                this.adjustmentthreadRepository.
                        findById(id)
                        .orElseThrow(throwException(THREADNOTFOUND, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
