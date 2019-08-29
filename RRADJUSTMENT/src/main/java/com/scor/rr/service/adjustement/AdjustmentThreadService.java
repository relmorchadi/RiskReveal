package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.ScorPltHeaderEntity;
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

import static com.scor.rr.exceptions.ExceptionCodename.PLTNOTFOUNT;
import static com.scor.rr.exceptions.ExceptionCodename.THREADNOTFOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentThreadService {

    @Autowired
    AdjustmentThreadRepository adjustmentthreadRepository;

    @Autowired
    AdjustmentNodeService nodeService;

    @Autowired
    ScorpltheaderRepository scorpltheaderRepository;

    public AdjustmentThreadEntity findOne(Integer id){
        return adjustmentthreadRepository.findById(id).orElseThrow(throwException(THREADNOTFOUND,NOT_FOUND));
    }

    public List<AdjustmentThreadEntity> findAll(){
        return adjustmentthreadRepository.findAll();
    }

    //NOTE: thread is used to group a list of in-order nodes. How the implementation is done for persisting a thread with these nodes ?
    //Refactor need to be done (methods name does not refer to what they are doing)
    //Done

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
        AdjustmentThreadEntity adjustmentThreadEntity = adjustmentthreadRepository.getOne(adjustmentThreadRequest.getAdjustmentThreadId());
        if(adjustmentThreadEntity != null) {
            adjustmentThreadEntity.setThreadType(adjustmentThreadRequest.getThreadType());
            adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadId(scorpltheaderRepository.getOne(adjustmentThreadRequest.getPltFinalId()));
            return adjustmentthreadRepository.save(adjustmentThreadEntity);
        } else {
            throwException(THREADNOTFOUND, NOT_FOUND);
            return null;
        }
    }

    public void cloneThread(ScorPltHeaderEntity purePlt) {
       List<AdjustmentThreadEntity> threads =  adjustmentthreadRepository.getAdjustmentThreadEntitiesByScorPltHeaderByFkScorPltHeaderThreadId(purePlt);
       for(AdjustmentThreadEntity threadParent:threads) {
           AdjustmentThreadEntity threadClone = new AdjustmentThreadEntity();
           threadClone.setThreadType(threadParent.getThreadType());
           threadClone.setLocked(threadParent.getLocked());
           threadClone.setScorPltHeaderByFkScorPltHeaderThreadId(purePlt);
           threadClone = adjustmentthreadRepository.save(threadClone);
           nodeService.cloneNode(threadParent,threadClone);
       }
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
