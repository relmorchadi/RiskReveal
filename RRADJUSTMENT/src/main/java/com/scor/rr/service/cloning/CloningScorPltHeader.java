package com.scor.rr.service.cloning;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.BinFileEntity;
import com.scor.rr.domain.ScorPltHeaderEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeOrderRequest;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
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

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Service
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

    public ScorPltHeaderEntity cloneScorPltHeader(int pltHeaderId){
        ScorPltHeaderEntity scorPltHeaderEntityInitial = scorpltheaderRepository.getOne(pltHeaderId);
        ScorPltHeaderEntity scorPltHeaderEntityClone = new ScorPltHeaderEntity();
        scorPltHeaderEntityClone.setCreatedDate(new Date(new java.util.Date().getTime()));
        scorPltHeaderEntityClone.setTargetRap(scorPltHeaderEntityInitial.getTargetRap());
        scorPltHeaderEntityClone.setMarketChannel(scorPltHeaderEntityInitial.getMarketChannel());
        scorPltHeaderEntityClone.setBinFileEntity(cloneBinFile(scorPltHeaderEntityInitial.getBinFileEntity()));
        scorPltHeaderEntityClone.setScorPltHeader(scorPltHeaderEntityInitial);
        return scorpltheaderRepository.save(scorPltHeaderEntityClone);
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
            fileBinClone.setFqn(fileBinClone.getFqn());
            fileBinClone.setPath(fileBinClone.getPath());
            return binfileRepository.save(fileBinClone);
        } catch (RRException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ScorPltHeaderEntity clonePltWithAdjustment(ScorPltHeaderEntity pltHeaderEntityInitial) {
        ScorPltHeaderEntity scorPltHeaderCloned = cloneScorPltHeader(pltHeaderEntityInitial.getPkScorPltHeaderId());
        AdjustmentThreadEntity threadCloned = threadService.cloneThread(pltHeaderEntityInitial,scorPltHeaderCloned);
        if(threadCloned!=null) {
            AdjustmentThreadEntity threadParent = threadService.getByScorPltHeader(435);
            List<AdjustmentNodeEntity> nodeEntities = nodeService.cloneNode(threadCloned, threadParent);
            if(nodeEntities != null) {
                for (AdjustmentNodeEntity adjustmentNodeCloned : nodeEntities) {
                    adjustmentNodeOrderService.saveNodeOrder(new AdjustmentNodeOrderRequest(adjustmentNodeCloned.getAdjustmentNodeId(), threadCloned.getAdjustmentThreadId(), adjustmentNodeOrderService.getAdjustmentOrderByThreadIdAndNodeId(threadParent.getAdjustmentThreadId(), adjustmentNodeCloned.getAdjustmentNodeByFkAdjustmentNodeIdCloning().getAdjustmentNodeId()).getOrderNode()));
                }
                processingService.cloneAdjustmentNodeProcessing(nodeEntities, threadParent, threadCloned);
            }
        }
        return scorPltHeaderCloned;
    }

}
