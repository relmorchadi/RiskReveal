package com.scor.rr.service.cloning;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.BinFileEntity;
import com.scor.rr.domain.ScorPltHeaderEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeOrderRequest;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import com.scor.rr.repository.BinfileRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import com.scor.rr.service.adjustement.AdjustmentNodeOrderService;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class CloningScorPltHeader {

    @Autowired
    ScorpltheaderRepository scorpltheaderRepository;

    @Autowired
    BinfileRepository binfileRepository;

    @Autowired
    AdjustmentThreadService threadService;

    //TODO: we need to clone ALL properties from source PLT EXCEPT the following ones:
    // - createdDate, inuringPackageId, pltLossDataFileName, pltLossDataFilePath, projectByFkProjectId, binFileEntity: refer to target context
    // - publishToPricing: reset to FALSE
    @Autowired
    AdjustmentNodeService nodeService;

    @Autowired
    AdjustmentNodeOrderService adjustmentNodeOrderService;

    @Autowired
    AdjustmentNodeProcessingService processingService;

    public ScorPltHeaderEntity cloneScorPltHeader(int scorPltHeaderEntityInitialId) throws com.scor.rr.exceptions.RRException {
        ScorPltHeaderEntity scorPltHeaderEntityInitial = scorpltheaderRepository.findByPkScorPltHeaderId(scorPltHeaderEntityInitialId);
        if(scorPltHeaderEntityInitial != null) {
            ScorPltHeaderEntity scorPltHeaderEntityClone = new ScorPltHeaderEntity(scorPltHeaderEntityInitial);
            scorPltHeaderEntityClone.setCreatedDate(new Date(new java.util.Date().getTime()));
            scorPltHeaderEntityClone.setPublishToPricing(false);
            scorPltHeaderEntityClone.setBinFileEntity(cloneBinFile(scorPltHeaderEntityInitial.getBinFileEntity()));
            scorPltHeaderEntityClone.setWorkspaceEntity(scorPltHeaderEntityInitial.getWorkspaceEntity());
            scorPltHeaderEntityClone.setScorPltHeader(scorPltHeaderEntityInitial);
            return scorpltheaderRepository.save(scorPltHeaderEntityClone);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLTNOTFOUNT,1);
        }
    }

    private BinFileEntity cloneBinFile(BinFileEntity binFileEntity) {
        try {
            File file = new File(binFileEntity.getPath());
            MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
            List<PLTLossData> pltFileReaders = readPltFile.read(file);
            File fileClone = new File("/src/main/resources/file/pltnew.csv");
            FileUtils.touch(fileClone);
            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
            csvpltFileWriter.write(pltFileReaders,fileClone);
            BinFileEntity fileBinClone = new BinFileEntity();
            fileBinClone.setFileName(fileClone.getName());
            fileBinClone.setFqn(fileClone.getAbsolutePath());
            fileBinClone.setPath(fileClone.getPath());
            return binfileRepository.save(fileBinClone);
        } catch (RRException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ScorPltHeaderEntity clonePltWithAdjustment(int pltHeaderEntityInitialId) throws com.scor.rr.exceptions.RRException {
        ScorPltHeaderEntity scorPltHeaderCloned = cloneScorPltHeader(pltHeaderEntityInitialId);
        if (scorPltHeaderCloned.getPltType().equalsIgnoreCase("pure")) {
            AdjustmentThreadEntity threadCloned = threadService.cloneThread(pltHeaderEntityInitialId, scorPltHeaderCloned);
            if (threadCloned != null) {
                AdjustmentThreadEntity threadParent = threadService.getByScorPltHeader(435);
                List<AdjustmentNodeEntity> nodeEntities = nodeService.cloneNode(threadCloned, threadParent);
                if (nodeEntities != null) {
                    for (AdjustmentNodeEntity adjustmentNodeCloned : nodeEntities) {
                        adjustmentNodeOrderService.saveNodeOrder(new AdjustmentNodeOrderRequest(adjustmentNodeCloned.getAdjustmentNodeId(), threadCloned.getAdjustmentThreadId(), adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadParent.getAdjustmentThreadId(), adjustmentNodeCloned.getAdjustmentNodeByFkAdjustmentNodeIdCloning().getAdjustmentNodeId()).getOrderNode()));
                    }
                    processingService.cloneAdjustmentNodeProcessing(nodeEntities, threadParent, threadCloned);
                    return scorPltHeaderCloned;
                } else {
                    throw new com.scor.rr.exceptions.RRException(ExceptionCodename.NODENOTFOUND, 1);
                }
            } else {
                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREADNOTFOUND, 1);
            }
        } else {
            return scorPltHeaderCloned;
        }
    }

}
