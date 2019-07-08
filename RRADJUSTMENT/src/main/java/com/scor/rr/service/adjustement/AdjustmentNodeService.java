package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentNodeService {
    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

    @Autowired
    AdjustmentnodeprocessingRepository adjustmentnodeprocessingRepository;

    @Autowired
    AdjustmentBasisRepository adjustmentBasisRepository;

    @Autowired
    AdjustmentThreadRepository adjustmentThread;

    @Autowired
    AdjustmentStateRepository adjustmentStateRepository;

    @Autowired
    AdjustmentCategoryRepository adjustmentCategoryRepository;

    @Autowired
    AdjustmentTypeRepository adjustmentTypeRepository;


    public AdjustmentNodeEntity findOne(Long id){
        return adjustmentnodeRepository.getOne(id);
    }

    public List<AdjustmentNodeEntity> findAll(){
        return adjustmentnodeRepository.findAll();
    }

    public AdjustmentNodeEntity save(AdjustmentNodeEntity adjustmentnodeModel, Integer adjustmentBasis, Integer adjustmentType,Integer adjustmentCategory,Integer adjustmentState,Integer adjustmentThreadId){
        adjustmentnodeModel.setAdjustmentType(adjustmentTypeRepository.getOne(adjustmentType));
        adjustmentnodeModel.setAdjustmentBasis(adjustmentBasisRepository.getOne(adjustmentBasis));
        adjustmentnodeModel.setAdjustmentCategory(adjustmentCategoryRepository.getOne(adjustmentCategory));
        adjustmentnodeModel.setAdjustmentState(adjustmentStateRepository.getOne(adjustmentState));
        adjustmentnodeModel.setAdjustmentThread(adjustmentThread.getOne(adjustmentThreadId));
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
