package com.scor.rr.service.cloning;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeOrderRequest;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.BinfileRepository;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.WorkspaceRepository;
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
    PltHeaderRepository pltHeaderRepository;

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

    @Autowired
    WorkspaceRepository workspaceRepository;

    public PltHeaderEntity cloneScorPltHeader(Long scorPltHeaderEntityInitialId) throws com.scor.rr.exceptions.RRException {
        PltHeaderEntity pltHeaderEntityInitial = pltHeaderRepository.findByPltHeaderId(scorPltHeaderEntityInitialId);
        if (pltHeaderEntityInitial != null) {
            PltHeaderEntity pltHeaderEntityClone = new PltHeaderEntity(pltHeaderEntityInitial);
            pltHeaderEntityClone.setCreatedDate(new Date(new java.util.Date().getTime()));
            pltHeaderEntityClone.setLocked(false);
            //@Todo Review
//            pltHeaderEntityClone.setBinFileEntity(cloneBinFile(pltHeaderEntityInitial.getBinFileEntity()));
//            pltHeaderEntityClone.setWorkspaceEntity(pltHeaderEntityInitial.getWorkspaceEntity());
//            pltHeaderEntityClone.setCloningSource(pltHeaderEntityInitial);
            return pltHeaderRepository.save(pltHeaderEntityClone);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_NOT_FOUND, 1);
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
            csvpltFileWriter.write(pltFileReaders, fileClone);
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

    public PltHeaderEntity clonePltWithAdjustment(Long pltHeaderEntityInitialId, String workspaceId) throws com.scor.rr.exceptions.RRException {
        WorkspaceEntity workspaceEntity = workspaceRepository.findById(workspaceId).orElse(null);
        PltHeaderEntity scorPltHeaderCloned = cloneScorPltHeader(pltHeaderEntityInitialId);
//        scorPltHeaderCloned.setWorkspaceEntity(workspaceEntity);
        if (scorPltHeaderCloned.getPltType().equalsIgnoreCase("pure")) {
            AdjustmentThreadEntity threadCloned = threadService.cloneThread(pltHeaderEntityInitialId, scorPltHeaderCloned);
            if (threadCloned != null) {
                AdjustmentThreadEntity threadParent = threadService.getByPltHeader(435); // what ???
                List<AdjustmentNode> nodeEntities = nodeService.cloneNode(threadCloned, threadParent);
                if (nodeEntities != null) {
                    for (AdjustmentNode adjustmentNodeCloned : nodeEntities) {
                        AdjustmentNodeOrder order = adjustmentNodeOrderService.findByAdjustmentNode(adjustmentNodeCloned);
                        adjustmentNodeOrderService.createNodeOrder(adjustmentNodeCloned, order.getAdjustmentOrder());
                    }
                    processingService.cloneAdjustmentNodeProcessing(nodeEntities, threadParent, threadCloned);
                    return scorPltHeaderCloned;
                } else {
                    throw new com.scor.rr.exceptions.RRException(ExceptionCodename.NODE_NOT_FOUND, 1);
                }
            } else {
                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND, 1);
            }
        } else {
            return scorPltHeaderCloned;
        }
    }

}
