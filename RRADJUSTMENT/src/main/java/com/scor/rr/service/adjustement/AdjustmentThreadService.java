package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentThreadRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentThreadService {

    @Autowired
    AdjustmentThreadRepository adjustmentthreadRepository;

    @Autowired
    ScorpltheaderRepository scorpltheaderRepository;

    public AdjustmentThreadEntity findOne(Integer id){
        return adjustmentthreadRepository.getOne(id);
    }

    public List<AdjustmentThreadEntity> findAll(){
        return adjustmentthreadRepository.findAll();
    }

    public AdjustmentThreadEntity savePurePlt(AdjustmentThreadRequest adjustmentThreadRequest){
        AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
        adjustmentThreadEntity.setThreadType(adjustmentThreadRequest.getThreadType());
        adjustmentThreadEntity.setScorPltHeaderByPurePltId(scorpltheaderRepository.getOne(adjustmentThreadRequest.getPltPureId()));
        return adjustmentthreadRepository.save(adjustmentThreadEntity);
    }

    public AdjustmentThreadEntity saveAdjustedPlt(AdjustmentThreadRequest adjustmentThreadRequest){
        AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
        adjustmentThreadEntity.setThreadType(adjustmentThreadRequest.getThreadType());
        adjustmentThreadEntity.setScorPltHeaderByThreadPltId(scorpltheaderRepository.getOne(adjustmentThreadRequest.getPltFinalId()));
        return adjustmentthreadRepository.save(adjustmentThreadEntity);
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
