package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.UtilsMethod;
import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.BinaryPLTFileWriter;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import com.scor.rr.service.cloning.CloningScorPltHeaderService;
import com.scor.rr.utils.RRDateUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;
import static com.scor.rr.exceptions.ExceptionCodename.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentNodeProcessingService {
    private static final Logger log = LoggerFactory.getLogger(AdjustmentNodeProcessingService.class);

    @Autowired
    AdjustmentNodeProcessingRepository adjustmentNodeProcessingRepository;

    @Autowired
    PltHeaderRepository pltHeaderRepository;

    @Autowired
    AdjustmentNodeRepository adjustmentNodeRepository;

    @Autowired
    AdjustmentNodeOrderRepository adjustmentNodeOrderRepository;

    @Autowired
    AdjustmentThreadRepository adjustmentThreadRepository;

    @Autowired
    AdjustmentScalingParameterService adjustmentScalingParameterService;

    @Autowired
    AdjustmentReturnPeriodBandingParameterService periodBandingParameterService;

    @Autowired
    AdjustmentEventBasedParameterService eventBasedParameterService;

    @Autowired
    ScalingAdjustmentParameterRepository scalingAdjustmentParameterRepository;

    @Autowired
    ReturnPeriodBandingAdjustmentParameterRepository returnPeriodBandingAdjustmentParameterRepository;

    @Autowired
    EventBasedAdjustmentParameterRepository eventBasedAdjustmentParameterRepository;

    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @Autowired
    CloningScorPltHeaderService cloningScorPltHeaderService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    private final static String pathbin = "src/main/resources/file/plt.bin";

    public AdjustmentNodeProcessingEntity findOne(Integer id) {
        return adjustmentNodeProcessingRepository.getOne(id);
    }

    public List<AdjustmentNodeProcessingEntity> findAll() {
        return adjustmentNodeProcessingRepository.findAll();
    }

    public AdjustmentNodeProcessingEntity findByNode(Integer nodeId) {
        return adjustmentNodeProcessingRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
    }

    //NOTE: please add the comments to explain what will be done by these methods saveBy... and how they could be called.
    //Perhaps a refactor need to be done

    //NOTE: should have the separated functions:
    //  - Save Adjustment Node, adjustment parameters and adjustment processing into DB
    //  - Trigger adjustment processing (i.e call CalculAdjustement methods), return PLT Loss Data list and status
    //  - Persist PLT to DB

    public PltHeaderEntity adjustPLTsInThread(Integer threadId) throws RRException {
        log.info("------ begin thread processing ------");
        AdjustmentThreadEntity thread = adjustmentThreadRepository.findById(threadId).get();
        if (thread == null) {
            log.info("------ thread null, wrong ------");
            return null;
        }
        List<AdjustmentNode> adjustmentNodes = adjustmentNodeRepository.findByAdjustmentThread(thread);
        AdjustmentNodeProcessingEntity processing = null;
        if (adjustmentNodes != null && !adjustmentNodes.isEmpty()) {
            // sort adjustmentNodes by node order from 1 to n then processing
            adjustmentNodes.sort(
                    Comparator.comparing(this::findOrderOfNode));
            for (AdjustmentNode node : adjustmentNodes) {
                processing = adjustPLTPassingByNode(node.getAdjustmentNodeId());
            }
        }

        PltHeaderEntity finalPLT = thread.getFinalPLT();

        if (finalPLT == null) {
            finalPLT = new PltHeaderEntity(thread.getInitialPLT());
            finalPLT.setPltType("THREAD");
        }

        File sourceFile = null;
        if (processing == null) {
            sourceFile = new File(thread.getInitialPLT().getLossDataFilePath(), thread.getInitialPLT().getLossDataFileName());
        } else {
            sourceFile = new File(processing.getAdjustedPLT().getLossDataFilePath(), processing.getAdjustedPLT().getLossDataFileName());
        }

        File dstFile = new File(sourceFile.getParent(), "ThreadPLT_Thread_" + thread.getAdjustmentThreadId() + "_" + sdf.format(new Date()) + ".bin");
        try {
            UtilsMethod.copyFile(sourceFile, dstFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finalPLT.setLossDataFilePath(dstFile.getParent());
        finalPLT.setLossDataFileName(dstFile.getName());

        finalPLT.setIsLocked(false);
        finalPLT.setCreatedDate(RRDateUtils.getDateNow());
        pltHeaderRepository.save(finalPLT);
        thread.setFinalPLT(finalPLT);
        adjustmentThreadRepository.save(thread);
        return finalPLT; // return final PLT
    }

    public Integer findOrderOfNode(AdjustmentNode node) {
        log.info("------ findOrderOfNode ------");
        AdjustmentNodeOrder nodeOrder = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
        return nodeOrder.getAdjustmentOrder();
    }

    public AdjustmentNodeProcessingEntity adjustPLTPassingByNode(Integer nodeId) throws RRException {
        log.info("------ begin adjusting PLT processing ------");

        AdjustmentNode adjustmentNode = adjustmentNodeRepository.findById(nodeId).get();
        if (adjustmentNode == null) {
            log.info("------ node not found ------");
            return null;
        }

        if (adjustmentNode.getAdjustmentCategoryCode() == null) {
            log.info("------ adjustmentNode.getAdjustmentCategory() null, node is final or pure, no adjustment ------");
            return null;
        }

        AdjustmentThreadEntity adjustmentThread = adjustmentThreadRepository.findById(adjustmentNode.getAdjustmentThread().getAdjustmentThreadId()).get();
        if (adjustmentThread == null) {
            log.info("------ no adjustmentThread, wrong ------");
            return null;
        }

        // kiem tra xem node co PLT input ?
        AdjustmentNodeOrder adjustmentNodeOrder = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
        if (adjustmentNodeOrder == null) {
            log.info("------ adjustmentNodeOrder not found ------");
            return null;
        }

        if (adjustmentNodeOrder.getAdjustmentOrder() == null) {
            log.info("------ node order = 0, no adjustment for pure node ------");
            return null;
        }

        PltHeaderEntity pltPure = adjustmentThread.getInitialPLT();
        if (pltPure == null) {
            log.info("------ node PLT pure, wrong ------");
            return null;
        }

        PltHeaderEntity inputPLT = null;
        if (adjustmentNodeOrder.getAdjustmentOrder() == 1) {
            inputPLT = pltPure;
        }

        if (adjustmentNodeOrder.getAdjustmentOrder() > 1) {
            AdjustmentNodeOrder adjustmentNodeOrderParent = adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentOrder(adjustmentThread.getAdjustmentThreadId(), adjustmentNodeOrder.getAdjustmentOrder() - 1);
            if (adjustmentNodeOrderParent == null) {
                log.info("------ adjustmentNodeOrderParent null, no input PLT for adjustment ------");
                return null;
            }
            AdjustmentNodeProcessingEntity nodeProcessingParent = adjustmentNodeProcessingRepository.findByAdjustmentNodeAdjustmentNodeId(adjustmentNodeOrderParent.getAdjustmentNode().getAdjustmentNodeId());
            inputPLT = nodeProcessingParent.getAdjustedPLT();
        }

        AdjustmentNodeProcessingEntity nodeProcessing = adjustmentNodeProcessingRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
        if (nodeProcessing == null) {
            log.info("create node processing");
            nodeProcessing = new AdjustmentNodeProcessingEntity();
        }

        nodeProcessing.setInputPLT(inputPLT);

        // tinh adjusted plt
        List<PLTLossData> pltLossData = getLossFromPltInputAdjustment(inputPLT); // input lay ra List<PLTLossData>
        pltLossData = calculateProcessing(adjustmentNode, pltLossData); // tinh toan
        log.info("saving loss file for adjusted PLT");
        String filename = "InterimPLT_Node" + adjustmentNode.getAdjustmentNodeId() + "_" +  sdf.format(new Date()) + ".bin";
        File binFile = savePLTFile(pltLossData, inputPLT.getLossDataFilePath(),  filename); // luu file
        if (binFile != null) {
            log.info("success saving loss file for adjusted PLT");
            PltHeaderEntity adjustedPLT = new PltHeaderEntity(inputPLT); // tao plt moi
            adjustedPLT.setLossDataFilePath(binFile.getParent());
            adjustedPLT.setLossDataFileName(binFile.getName());
            adjustedPLT.setCreatedDate(new java.sql.Date(new java.util.Date().getTime()));
            adjustedPLT.setPltType("Interim");
            adjustedPLT = pltHeaderRepository.save(adjustedPLT);
            log.info("success saving adjustedPLT");
            nodeProcessing.setAdjustedPLT(adjustedPLT);
            nodeProcessing.setAdjustmentNode(adjustmentNode);
            log.info("------END adjust PLT processing ------");
            return adjustmentNodeProcessingRepository.save(nodeProcessing);
        } else {
            log.info("fail saving loss file for adjusted PLT");
            throw new com.scor.rr.exceptions.RRException(BIN_FILE_EXCEPTION, 1);
        }
    }

    public AdjustmentNodeProcessingEntity findByNodeId(Integer nodeId) {
        return adjustmentNodeProcessingRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
    }

    public void deleteProcessingByNode(Integer nodeId){
        AdjustmentNodeProcessingEntity processing = adjustmentNodeProcessingRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
        if (processing != null) {
            adjustmentNodeProcessingRepository.delete(processing);
        }
    }

    private File savePLTFile(List<PLTLossData> pltLossData, String filePath, String fileName) {
        File file = new File(filePath, fileName);
        if ("csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
            try {
                csvpltFileWriter.write(pltLossData, file);
            } catch (RRException e) {
                e.printStackTrace();
            }
        } else {
            BinaryPLTFileWriter binpltFileWriter = new BinaryPLTFileWriter();
            try {
                binpltFileWriter.write(pltLossData, file);

            } catch (RRException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private List<PLTLossData> getLossFromPltInputAdjustment(PltHeaderEntity pltHeaderEntity) throws RRException {
        if(pltHeaderEntity != null) {
            if(pltHeaderEntity.getLossDataFilePath() != null && pltHeaderEntity.getLossDataFileName() != null) {
                File file = new File(pltHeaderEntity.getLossDataFilePath(), pltHeaderEntity.getLossDataFileName());
                if ("csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
                    CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
                    try {
                        return csvpltFileReader.read(file);
                    } catch (RRException e) {
                        e.printStackTrace();
                    }
                } else {
                    BinaryPLTFileReader binpltFileReader = new BinaryPLTFileReader();
                    try {
                        return binpltFileReader.read(file);
                    } catch (RRException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                throw new com.scor.rr.exceptions.RRException(BIN_FILE_EXCEPTION, 1);
            }
        } else {
            throw new com.scor.rr.exceptions.RRException(PLT_NOT_FOUND, 1);
        }
        return null;
    }

    public void delete(Integer id) {
        this.adjustmentNodeProcessingRepository.delete(
                this.adjustmentNodeProcessingRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    private List<PLTLossData> calculateProcessing(AdjustmentNode node, List<PLTLossData> pltLossData) throws RRException {
        if (LINEAR.getValue().equals(node.getAdjustmentTypeCode())) {
            ScalingAdjustmentParameter adjustmentScalingParameter = scalingAdjustmentParameterRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
            return CalculAdjustement.linearAdjustement(pltLossData, adjustmentScalingParameter.getAdjustmentFactor(), node.getCapped());
        } else if (EEF_FREQUENCY.getValue().equals(node.getAdjustmentTypeCode())) {
            ScalingAdjustmentParameter adjustmentScalingParameter = scalingAdjustmentParameterRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
            return CalculAdjustement.eefFrequency(pltLossData, node.getCapped(), adjustmentScalingParameter.getAdjustmentFactor()); // getRpmf() la gi
        } else if (NONLINEAR_OEP_RPB.getValue().equals(node.getAdjustmentTypeCode())) {
            List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandingParameters = returnPeriodBandingAdjustmentParameterRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
            return CalculAdjustement.oepReturnPeriodBanding(pltLossData, node.getCapped(), adjustmentReturnPeriodBandingParameters); // revise
//            return CalculAdjustement.OEPReturnBanding(pltLossData, node.getCapped(), adjustmentReturnPeriodBandingParameters); // revise

        } else if (NONLINEAR_EEF_RPB.getValue().equals(node.getAdjustmentTypeCode())) {
            List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBandingParameters = returnPeriodBandingAdjustmentParameterRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
            return CalculAdjustement.eefReturnPeriodBanding(pltLossData, node.getCapped(), adjustmentReturnPeriodBandingParameters);
        } else if (NONLINEAR_EVENT_DRIVEN.getValue().equals(node.getAdjustmentTypeCode())) {
            EventBasedAdjustmentParameter parameter = eventBasedAdjustmentParameterRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
            File peatDataFile = new File(parameter.getInputFilePath(), parameter.getInputFileName());
            List<PEATData> peatData = UtilsMethod.getEvenDrivenPeatDataFromFile(peatDataFile.getPath());
            return CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossData, node.getCapped(), peatData);
        } else if (NONLINEAR_EVENT_PERIOD_DRIVEN.getValue().equals(node.getAdjustmentTypeCode())) {
            EventBasedAdjustmentParameter parameter = eventBasedAdjustmentParameterRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
            File peatDataFile = new File(parameter.getInputFilePath(), parameter.getInputFileName());
            List<PEATData> peatData = UtilsMethod.getEvenPeriodDrivenPeatDataFromFile(peatDataFile.getPath());
            return CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossData, node.getCapped(), peatData);
        } else {
            throw new com.scor.rr.exceptions.RRException(TYPE_NOT_FOUND, 1);
        }
    }

    public List<AdjustmentNodeProcessingEntity> cloneAdjustmentNodeProcessing(List<AdjustmentNode> nodeClones, AdjustmentThreadEntity threadInitial, AdjustmentThreadEntity threadCloned) throws RRException {
        if(nodeClones != null) {
            PltHeaderEntity inputPlt = threadCloned.getFinalPLT();
            List<AdjustmentNodeProcessingEntity> processingEntities = new ArrayList<>();
            for (AdjustmentNode nodeCloned : nodeClones) {
                AdjustmentNodeProcessingEntity processingEntityPure = adjustmentNodeProcessingRepository.findByAdjustmentNode(nodeCloned.getCloningSource());
                AdjustmentNodeProcessingEntity processingEntityCloned = new AdjustmentNodeProcessingEntity();
                processingEntityCloned.setAdjustmentNode(nodeCloned);
                processingEntityCloned.setInputPLT(inputPlt);
                processingEntityCloned.setAdjustedPLT(processingEntityPure.getAdjustedPLT().getPltHeaderId() == threadInitial.getInitialPLT().getPltHeaderId() ? threadCloned.getInitialPLT() : cloningScorPltHeaderService.cloneScorPltHeader(processingEntityPure.getAdjustedPLT().getPltHeaderId()));
                inputPlt = processingEntityCloned.getAdjustedPLT();
                processingEntities.add(processingEntityCloned);
            }
            return processingEntities;
        }
        return null;
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
