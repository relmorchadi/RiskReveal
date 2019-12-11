package com.scor.rr.service.cloning;

import com.scor.rr.configuration.UtilsMethod;
import com.scor.rr.domain.*;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.WorkspaceRepository;
import com.scor.rr.service.adjustement.AdjustmentNodeOrderService;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
import com.scor.rr.utils.RRDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CloningScorPltHeaderService {

    @Autowired
    PltHeaderRepository pltHeaderRepository;

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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public PltHeaderEntity cloneScorPltHeader(Long pltId) throws com.scor.rr.exceptions.RRException {
        PltHeaderEntity plt = pltHeaderRepository.findByPltHeaderId(pltId);
        if (plt != null) {
            PltHeaderEntity newPLT = new PltHeaderEntity(plt);
            newPLT.setCreatedDate(RRDateUtils.getDateNow());
            newPLT.setIsLocked(false);
            pltHeaderRepository.save(newPLT); // to take PLT id
            // copy file
            File sourceFile = new File(plt.getLossDataFilePath(), plt.getLossDataFileName());
            File dstFile = new File(sourceFile.getParent(), "PLT_" + newPLT.getPltHeaderId() + "_" + newPLT.getPltType() + "_" + sdf.format(new Date()) + ".bin");
            try {
                UtilsMethod.copyFile(sourceFile, dstFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            newPLT.setLossDataFilePath(dstFile.getParent());
            newPLT.setLossDataFileName(dstFile.getName());
            newPLT.setCloningSourceId(plt.getPltHeaderId());
            return pltHeaderRepository.save(newPLT);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_NOT_FOUND, 1);
        }
    }

    public PltHeaderEntity clonePltWithAdjustment(Long pltHeaderEntityInitialId, String workspaceId) throws com.scor.rr.exceptions.RRException {
        PltHeaderEntity scorPltHeaderCloned = cloneScorPltHeader(pltHeaderEntityInitialId);
        if (scorPltHeaderCloned.getPltType().equalsIgnoreCase("pure")) {
            AdjustmentThreadEntity threadCloned = threadService.cloneThread(pltHeaderEntityInitialId, scorPltHeaderCloned);
            if (threadCloned != null) {
                AdjustmentThreadEntity threadParent = threadService.getByPltHeader(435L); // what ???
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
