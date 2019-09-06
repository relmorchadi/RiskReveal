package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.ScorPltHeaderEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentThreadRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import com.scor.rr.service.cloning.CloningScorPltHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

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

    @Autowired
    CloningScorPltHeader cloningScorPltHeader;

    public AdjustmentThreadEntity findOne(Integer id){
        return adjustmentthreadRepository.findById(id).orElseThrow(throwException(THREADNOTFOUND,NOT_FOUND));
    }

    public List<AdjustmentThreadEntity> findAll(){
        return adjustmentthreadRepository.findAll();
    }

    //NOTE: thread is used to group a list of in-order nodes. How the implementation is done for persisting a thread with these nodes ?
    //Refactor need to be done (methods name does not refer to what they are doing)
    //Done

    public AdjustmentThreadEntity savePurePlt(AdjustmentThreadRequest adjustmentThreadRequest) throws RRException {
        AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
        adjustmentThreadEntity.setThreadType(adjustmentThreadRequest.getThreadType());
        adjustmentThreadEntity.setCreatedOn(adjustmentThreadRequest.getCreatedOn());
        adjustmentThreadEntity.setCreatedBy(adjustmentThreadRequest.getCreatedBy());
        adjustmentThreadEntity.setLocked(adjustmentThreadRequest.getLocked());
        if(scorpltheaderRepository.findById(adjustmentThreadRequest.getPltPureId()).isPresent()) {
            adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadPureId(scorpltheaderRepository.findById(adjustmentThreadRequest.getPltPureId()).get());
            return adjustmentthreadRepository.save(adjustmentThreadEntity);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLTNOTFOUNT,1);
        }
    }

    public AdjustmentThreadEntity getByScorPltHeader(int scorPltHeaderId){
        return adjustmentthreadRepository.getAdjustmentThreadEntityByScorPltHeaderByFkScorPltHeaderThreadId_PkScorPltHeaderId(scorPltHeaderId);
    }

    public AdjustmentThreadEntity saveAdjustedPlt(AdjustmentThreadRequest adjustmentThreadRequest) throws RRException {
        AdjustmentThreadEntity adjustmentThreadEntity = adjustmentthreadRepository.getOne(adjustmentThreadRequest.getAdjustmentThreadId());
        if(adjustmentThreadEntity != null) {
            adjustmentThreadEntity.setThreadType(adjustmentThreadRequest.getThreadType());
            adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadId(scorpltheaderRepository.getOne(adjustmentThreadRequest.getPltFinalId()));
            return adjustmentthreadRepository.save(adjustmentThreadEntity);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREADNOTFOUND,1);
        }
    }

    public AdjustmentThreadEntity cloneThread(Integer initialPlt,ScorPltHeaderEntity clonedPlt) throws RRException {
       AdjustmentThreadEntity thread =  adjustmentthreadRepository.getAdjustmentThreadEntityByScorPltHeaderByFkScorPltHeaderThreadId_PkScorPltHeaderId(initialPlt);
       if(thread!=null) {
           AdjustmentThreadEntity threadClone = new AdjustmentThreadEntity();
           threadClone.setThreadType(thread.getThreadType());
           threadClone.setLocked(thread.getLocked());
           threadClone.setScorPltHeaderByFkScorPltHeaderThreadId(clonedPlt);
           threadClone.setScorPltHeaderByFkScorPltHeaderThreadPureId(cloningScorPltHeader.cloneScorPltHeader(thread.getScorPltHeaderByFkScorPltHeaderThreadPureId()));
           return adjustmentthreadRepository.save(threadClone);

       } else {
           throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREADNOTFOUND,1);
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
