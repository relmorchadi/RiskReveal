package com.scor.rr.service.adjustement;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadCreationRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadUpdateRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentThreadRepository;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.service.cloning.CloningScorPltHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.THREAD_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentThreadService {

    @Autowired
    private AdjustmentThreadRepository adjustmentthreadRepository;

    @Autowired
    private AdjustmentNodeService nodeService;

    @Autowired
    private PltHeaderRepository pltHeaderRepository;

    @Autowired
    private CloningScorPltHeader cloningScorPltHeader;

    @Autowired
    private DefaultAdjustmentService defaultAdjustmentService;

    public AdjustmentThreadEntity findOne(Integer id){
        return adjustmentthreadRepository.findById(id).orElseThrow(throwException(THREAD_NOT_FOUND,NOT_FOUND));
    }

    public List<AdjustmentThreadEntity> findAll(){
        return adjustmentthreadRepository.findAll();
    }

    //NOTE: thread is used to group a list of in-order nodes. How the implementation is done for persisting a thread with these nodes ?
    //Refactor need to be done (methods name does not refer to what they are doing)
    //Done

    public AdjustmentThreadEntity createNewAdjustmentThread(AdjustmentThreadCreationRequest adjustmentThreadCreationRequest) throws RRException {
        AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
        adjustmentThreadEntity.setThreadType(adjustmentThreadCreationRequest.getThreadType());
        adjustmentThreadEntity.setCreatedOn(new Timestamp(new Date().getTime()));
        adjustmentThreadEntity.setCreatedBy(adjustmentThreadCreationRequest.getCreatedBy());
        adjustmentThreadEntity.setLocked(false);
        if(pltHeaderRepository.findById(adjustmentThreadCreationRequest.getPltPureId()).isPresent()) {
            PltHeaderEntity pltHeaderEntity = pltHeaderRepository.findById(adjustmentThreadCreationRequest.getPltPureId()).get();
            if(pltHeaderEntity.getPltType().equalsIgnoreCase("pure")) {
                adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadPureId(pltHeaderRepository.findById(adjustmentThreadCreationRequest.getPltPureId()).get());
                adjustmentThreadEntity = adjustmentthreadRepository.save(adjustmentThreadEntity);
                if (adjustmentThreadCreationRequest.isGenerateDefaultThread()) {
                    adjustmentThreadEntity = defaultAdjustmentService.createDefaultThread(adjustmentThreadEntity);
                }
                return adjustmentThreadEntity;
            } else {
                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_TYPE_NOT_CORRECT,1);
            }
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_NOT_FOUND,1);
        }
    }

    public AdjustmentThreadEntity getByScorPltHeader(int scorPltHeaderId){
        return adjustmentthreadRepository.getAdjustmentThreadEntityByScorPltHeaderByFkScorPltHeaderThreadId_PkScorPltHeaderId(scorPltHeaderId);
    }

    public AdjustmentThreadEntity updateAdjustmentThreadFinalPLT(AdjustmentThreadUpdateRequest adjustmentThreadCreationRequest) throws RRException {
        AdjustmentThreadEntity adjustmentThreadEntity = adjustmentthreadRepository.getOne(adjustmentThreadCreationRequest.getAdjustmentThreadId());
        if(adjustmentThreadEntity != null) {
            adjustmentThreadEntity.setThreadType(adjustmentThreadCreationRequest.getThreadType());
            adjustmentThreadEntity.setScorPltHeaderByFkScorPltHeaderThreadId(pltHeaderRepository.getOne(adjustmentThreadCreationRequest.getPltFinalId()));
            return adjustmentthreadRepository.save(adjustmentThreadEntity);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND,1);
        }
    }

    public AdjustmentThreadEntity cloneThread(Integer initialPlt,PltHeaderEntity clonedPlt) throws RRException {
       AdjustmentThreadEntity thread =  adjustmentthreadRepository.getAdjustmentThreadEntityByScorPltHeaderByFkScorPltHeaderThreadId_PkScorPltHeaderId(initialPlt);
       if(thread!=null) {
           AdjustmentThreadEntity threadClone = new AdjustmentThreadEntity();
           threadClone.setThreadType(thread.getThreadType());
           threadClone.setLocked(thread.getLocked());
           threadClone.setScorPltHeaderByFkScorPltHeaderThreadId(clonedPlt);
           threadClone.setScorPltHeaderByFkScorPltHeaderThreadPureId(cloningScorPltHeader.cloneScorPltHeader(thread.getScorPltHeaderByFkScorPltHeaderThreadPureId().getPltHeaderId()));
           return adjustmentthreadRepository.save(threadClone);

       } else {
           throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND,1);
       }
    }

    public void delete(Integer id) {
        this.adjustmentthreadRepository.delete(
                this.adjustmentthreadRepository.
                        findById(id)
                        .orElseThrow(throwException(THREAD_NOT_FOUND, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
