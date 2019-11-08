package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.BinaryPLTFileWriter;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import com.scor.rr.service.cloning.CloningScorPltHeader;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
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
    BinfileRepository binfileRepository;

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
    AdjustmentScalingParameterRepository adjustmentScalingParameterRepository;

    @Autowired
    AdjustmentReturnPeriodBandingParameterRepository adjustmentReturnPeriodBandingParameterRepository;

    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @Autowired
    CloningScorPltHeader cloningScorPltHeader;

    private final static String pathbin = "src/main/resources/file/plt.bin";

    public AdjustmentNodeProcessingEntity findOne(Integer id) {
        return adjustmentNodeProcessingRepository.getOne(id);
    }

    public List<AdjustmentNodeProcessingEntity> findAll() {
        return adjustmentNodeProcessingRepository.findAll();
    }

    public AdjustmentNodeProcessingEntity findByNode(int nodeId) {
        return adjustmentNodeProcessingRepository.findByAdjustmentNodeId(nodeId);
    }

    //NOTE: please add the comments to explain what will be done by these methods saveBy... and how they could be called.
    //Perhaps a refactor need to be done

//    public AdjustmentNodeProcessingEntity saveByInputPlt(AdjustmentNodeProcessingRequest adjustmentNodeProcessingRequest) {
//        log.info("------ input PLT processing ------");
//        log.info(" getting the input PLT");
//        if (pltHeaderRepository.findById(adjustmentNodeProcessingRequest.getScorPltHeaderIdPure()).isPresent()) {
//            log.info("success getting the input PLT");
//            PltHeaderEntity scorPltHeader = pltHeaderRepository.findById(adjustmentNodeProcessingRequest.getScorPltHeaderIdPure()).get(); // tim plt pure
//            log.info(" getting Node");
//            if (adjustmentnodeRepository.findById(adjustmentNodeProcessingRequest.getAdjustmentNodeId()).isPresent()) { // tim node
//                log.info("success getting Node");
//                AdjustmentNodeEntity adjustmentNode = adjustmentnodeRepository.findById(adjustmentNodeProcessingRequest.getAdjustmentNodeId()).get();
//                AdjustmentNodeProcessingEntity nodeProcessing = new AdjustmentNodeProcessingEntity();
//                if (adjustmentNodeProcessingRequest.getAdjustmentNodeProcessingId() != 0) {
//                    log.info("updating node processing");
//                    nodeProcessing.setAdjustmentNodeProcessingId(adjustmentNodeProcessingRequest.getAdjustmentNodeProcessingId()); // node id ?
//                } else {
//                    log.info("Creating node processing");
//                }
//                nodeProcessing.setInputPlt(scorPltHeader);
//                nodeProcessing.setAdjustmentNodeByFkAdjustmentNode(adjustmentNode);
//                log.info("------End saving input PLT processing ------");
//                return adjustmentnodeprocessingRepository.save(nodeProcessing);
//            } else {
//                throwException(NODE_NOT_FOUND, NOT_FOUND);
//                return null;
//            }
//        } else {
//            throwException(PLT_NOT_FOUND, NOT_FOUND);
//            return null;
//        }
//    }

    //NOTE: should have the separated functions:
    //  - Save Adjustment Node, adjustment parameters and adjustment processing into DB
    //  - Trigger adjustment processing (i.e call CalculAdjustement methods), return PLT Loss Data list and status
    //  - Persist PLT to DB

//    public AdjustmentNodeProcessingEntity saveByAdjustedPlt(AdjustmentParameterRequest parameterRequest) throws RRException {
//        log.info("------Begin adjusted PLT processing ------");
//        log.info(" getting the input PLT");
//        if (pltHeaderRepository.findById(parameterRequest.getPltHeaderInput()).isPresent()) {
//            log.info("success getting the input PLT");
//            PltHeaderEntity scorPltHeader = pltHeaderRepository.findById(parameterRequest.getPltHeaderInput()).get(); // plt input
//            log.info(" getting Node");
//            if (adjustmentnodeRepository.findById(parameterRequest.getNodeId()).isPresent()) {
//                log.info("success getting Node");
//                AdjustmentNodeEntity adjustmentNode = adjustmentnodeRepository.findById(parameterRequest.getNodeId()).get(); // node
//                log.info("getting Node Processing");
//                AdjustmentNodeProcessingEntity nodeProcessing = adjustmentnodeprocessingRepository.getAdjustmentNodeProcessingEntity(parameterRequest.getNodeId()); // processing luu tu ham saveByInputPLT
//                log.info("success getting Node Processing");
//                if(nodeProcessing != null) {
//                    log.info("saving Parametre Node Processing");
//                    saveParameterNode(adjustmentNode, parameterRequest); // luu cac kieu adjustment : linear, ...
//                    log.info("success saving Parametre Node Processing");
//                    List<PLTLossData> pltLossData = getLossFromPltInputAdjustment(scorPltHeader); // input lay ra List<PLTLossData>
//                    pltLossData = calculateProcessing(adjustmentNode, parameterRequest, pltLossData); // tinh toan
//                    log.info("saving file LOSS for adjusted PLT");
//                    BinFileEntity binFileEntity = savePLTFile(pltLossData); // luu file
//                    if (binFileEntity != null) {
//                        log.info("success saving file LOSS for adjusted PLT");
//                        log.info("saving PLT");
//                        PltHeaderEntity scorPltHeaderAdjusted = new PltHeaderEntity(scorPltHeader); // tao plt moi
//                        scorPltHeaderAdjusted.setBinFileEntity(binFileEntity);
//                        scorPltHeaderAdjusted.setCreatedDate(new java.sql.Date(new java.util.Date().getTime()));
////                        scorPltHeaderAdjusted.setCreatedBy("HAMZA");
//                        scorPltHeaderAdjusted.setPltType("interm");
//                        scorPltHeaderAdjusted = pltHeaderRepository.save(scorPltHeaderAdjusted);
//                        log.info("success saving PLT");
//                        nodeProcessing.setAdjustedPlt(scorPltHeaderAdjusted);
//                        nodeProcessing.setAdjustmentNodeByFkAdjustmentNode(adjustmentNode);
//                        log.info("------END adjusted PLT processing ------");
//                        return adjustmentnodeprocessingRepository.save(nodeProcessing);
//                    } else {
//                        throw new com.scor.rr.exceptions.RRException(BIN_FILE_EXCEPTION,1);
//                    }
//                } else {
//                    throw new com.scor.rr.exceptions.RRException(NODE_PROCESSING_NOT_FOUND,1);
//                }
//            } else {
//                throw new com.scor.rr.exceptions.RRException(NODE_NOT_FOUND,1);
//            }
//        } else {
//            throw new com.scor.rr.exceptions.RRException(PLT_NOT_FOUND,1);
//        }
//    }
    public PltHeaderEntity adjustPLTsInThread(Integer threadId) throws RRException {
        log.info("------ begin thread processing ------");
        AdjustmentThreadEntity thread = adjustmentThreadRepository.findById(threadId).get();
        if (thread == null) {
            log.info("------ thread null, wrong ------");
            return null;
        }
        List<AdjustmentNodeEntity> adjustmentNodes = adjustmentNodeRepository.findByAdjustmentThread(thread);
        if (adjustmentNodes == null || adjustmentNodes.isEmpty()) {
            log.info("------ adjustmentNodes null or empty, wrong ------");
            return null;
        }

        // sort adjustmentNodes by node order from 0 to n
        adjustmentNodes.sort(
                Comparator.comparing(this::findOrderOfNode));
        AdjustmentNodeProcessingEntity processing = null;
        for (AdjustmentNodeEntity node : adjustmentNodes) {
            processing = adjustPLTPassingByNode(node.getAdjustmentNodeId());
        }
        return processing != null ? processing.getAdjustedPLT() : null;
    }

    public Integer findOrderOfNode(AdjustmentNodeEntity node) {
        log.info("------ findOrderOfNode ------");
        AdjustmentNodeOrderEntity nodeOrder = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
        return nodeOrder.getAdjustmentOrder();
    }

    public AdjustmentNodeProcessingEntity adjustPLTPassingByNode(Integer nodeId) throws RRException {
        log.info("------ begin adjusting PLT processing ------");

        AdjustmentNodeEntity adjustmentNode = adjustmentNodeRepository.findById(nodeId).get();
        if (adjustmentNode == null) {
            log.info("------ node not found ------");
            return null;
        }

//        if (adjustmentNode.getAdjustmentCategory() == null) {
//            log.info("------ adjustmentNode.getAdjustmentCategory() null, node is final or pure, no adjustment ------");
//            return null;
//        }

        AdjustmentThreadEntity adjustmentThread = adjustmentThreadRepository.findById(adjustmentNode.getAdjustmentThread().getAdjustmentThreadId()).get();
        if (adjustmentThread == null) {
            log.info("------ no adjustmentThread, wrong ------");
            return null;
        }

        // kiem tra xem node co PLT input ?
        AdjustmentNodeOrderEntity adjustmentNodeOrder = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
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
            AdjustmentNodeOrderEntity adjustmentNodeOrderParent = adjustmentNodeOrderRepository.findByAdjustmentOrderAndAdjustmentThreadAdjustmentThreadId(adjustmentThread.getAdjustmentThreadId(), adjustmentNodeOrder.getAdjustmentOrder() - 1);
            if (adjustmentNodeOrderParent == null) {
                log.info("------ adjustmentNodeOrderParent null, no input PLT for adjustment ------");
                return null;
            }
            AdjustmentNodeProcessingEntity nodeProcessingParent = adjustmentNodeProcessingRepository.findByAdjustmentNodeId(adjustmentNodeOrderParent.getAdjustmentNode().getAdjustmentNodeId());
            inputPLT = nodeProcessingParent.getAdjustedPLT();
        }

        AdjustmentNodeProcessingEntity nodeProcessing = adjustmentNodeProcessingRepository.findByAdjustmentNodeId(nodeId);
        if (nodeProcessing == null) {
            log.info("create node processing");
            nodeProcessing = new AdjustmentNodeProcessingEntity();
        }

        nodeProcessing.setInputPLT(inputPLT);

        // tinh adjusted plt
        List<PLTLossData> pltLossData = getLossFromPltInputAdjustment(inputPLT); // input lay ra List<PLTLossData>
        pltLossData = calculateProcessing(adjustmentNode, pltLossData); // tinh toan
        log.info("saving loss file for adjusted PLT");
        BinFileEntity binFileEntity = savePLTFile(pltLossData); // luu file
        if (binFileEntity != null) {
            log.info("success saving loss file for adjusted PLT");
            PltHeaderEntity adjustedPLT = new PltHeaderEntity(inputPLT); // tao plt moi
            adjustedPLT.setBinFileEntity(binFileEntity);
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

    public AdjustmentNodeProcessingEntity getProcessingByNode(Integer nodeId) {
        return adjustmentNodeProcessingRepository.findAll()
                .stream()
                .filter(ape -> ape.getAdjustmentNode().getAdjustmentNodeId()== nodeId)
                .findAny()
                .orElseThrow(throwException(PLT_NOT_FOUND, NOT_FOUND));
    }

    public void deleteProcessingByNode(Integer nodeId){
        adjustmentNodeProcessingRepository.delete(getProcessingByNode(nodeId));
    }

    private BinFileEntity savePLTFile(List<PLTLossData> pltLossData) {
        File file = new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv");
        File fileWrite = null;
        if ("csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
            try {
                fileWrite = new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv");
                csvpltFileWriter.write(pltLossData,fileWrite);
            } catch (RRException e) {
                e.printStackTrace();
            }
        } else {
            BinaryPLTFileWriter binpltFileWriter = new BinaryPLTFileWriter();
            try {
                fileWrite = new File(pathbin);
                binpltFileWriter.write(pltLossData,fileWrite);

            } catch (RRException e) {
                e.printStackTrace();
            }
        }
        BinFileEntity binFileEntity = new BinFileEntity();
        binFileEntity.setFileName(fileWrite.getName());
        binFileEntity.setPath(fileWrite.getPath());
        binFileEntity.setFqn(fileWrite.getAbsolutePath());
        return binfileRepository.save(binFileEntity);
    }


    private List<PLTLossData> getLossFromPltInputAdjustment(PltHeaderEntity pltHeaderEntity) throws RRException {
        if(pltHeaderEntity != null) {
            if(pltHeaderEntity.getBinFileEntity() != null) {
                File file = new File(pltHeaderEntity.getBinFileEntity().getPath(), pltHeaderEntity.getBinFileEntity().getFileName());
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
                throw new com.scor.rr.exceptions.RRException(BIN_FILE_EXCEPTION,1);
            }
        } else {
            throw new com.scor.rr.exceptions.RRException(PLT_NOT_FOUND,1);
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

    //NOTE: should separate two functions: save parameter and processing
    //Done

//else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
//        adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getRpmf(),node));
//        } else if (NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
//        for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
//        periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(),periodBanding.getLmf(),node));
//        }
//        } else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
//        adjustmentNodeService.savePeatDataFile(node, parameterRequest);
//        } else if (NONLINEARRETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
//        adjustmentNodeService.savePeatDataFile(node, parameterRequest);
//        } else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
//        for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
//        periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(), periodBanding.getLmf(), node));
//        }
//

    private List<PLTLossData> calculateProcessing(AdjustmentNodeEntity node, List<PLTLossData> pltLossData) throws RRException {
        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
            AdjustmentScalingParameterEntity adjustmentScalingParameter = adjustmentScalingParameterRepository.findByNodeId(node.getAdjustmentNodeId());
            return CalculAdjustement.linearAdjustement(pltLossData, adjustmentScalingParameter.getFactor(), node.getCapped());
        } else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
            AdjustmentScalingParameterEntity adjustmentScalingParameter = adjustmentScalingParameterRepository.findByNodeId(node.getAdjustmentNodeId());
            return CalculAdjustement.eefFrequency(pltLossData, node.getCapped(), adjustmentScalingParameter.getFactor()); // getRpmf() la gi
        } else if (NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
            List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBandingParameters = adjustmentReturnPeriodBandingParameterRepository.findByNodeId(node.getAdjustmentNodeId());
            return CalculAdjustement.oepReturnPeriodBanding(pltLossData, node.getCapped(), adjustmentReturnPeriodBandingParameters);
            // TODO getPeatData
//        } else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
//            return CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossData, node.getCapped(), parameterRequest.getPeatData());
//         } else if (NONLINEARRETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
//            return CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossData, node.getCapped(), parameterRequest.getPeatData());
        } else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBandingParameters = adjustmentReturnPeriodBandingParameterRepository.findByNodeId(node.getAdjustmentNodeId());
            return CalculAdjustement.eefReturnPeriodBanding(pltLossData, node.getCapped(), adjustmentReturnPeriodBandingParameters);
        } else {
            throw new com.scor.rr.exceptions.RRException(TYPE_NOT_FOUND, 1);
        }
    }

//    private void saveParameterNode(AdjustmentNodeEntity node, AdjustmentParameterRequest parameterRequest) throws RRException { // ham nay tai sao lai save nhieu kieu entity ?
//        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
//            adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getLmf(),node));
//        } else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
//            adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getRpmf(),node));
//        } else if (NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
//            for(AdjustmentReturnPeriodBandingParameterEntity periodBanding : parameterRequest.getAdjustmentReturnPeriodBendings()) {
//                periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(),periodBanding.getLmf(),node));
//            }
//        } else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
//            adjustmentNodeService.savePeatDataFile(node, parameterRequest);
//        } else if (NONLINEARRETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
//            adjustmentNodeService.savePeatDataFile(node, parameterRequest);
//        } else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
//            for(AdjustmentReturnPeriodBandingParameterEntity periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
//                periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(), periodBanding.getLmf(), node));
//            }
//        } else {
//            throw new com.scor.rr.exceptions.RRException(TYPE_NOT_FOUND,1);
//        }
//    }

    public List<AdjustmentNodeProcessingEntity> cloneAdjustmentNodeProcessing(List<AdjustmentNodeEntity> nodeClones,AdjustmentThreadEntity threadInitial,AdjustmentThreadEntity threadCloned) throws RRException {
        if(nodeClones != null) {
            PltHeaderEntity inputPlt = threadCloned.getFinalPLT();
            List<AdjustmentNodeProcessingEntity> processingEntities = new ArrayList<>();
            for (AdjustmentNodeEntity nodeCloned : nodeClones) {
                AdjustmentNodeProcessingEntity processingEntityPure = adjustmentNodeProcessingRepository.findByAdjustmentNode(nodeCloned.getAdjustmentNodeCloning());
                AdjustmentNodeProcessingEntity processingEntityCloned = new AdjustmentNodeProcessingEntity();
                processingEntityCloned.setAdjustmentNode(nodeCloned);
                processingEntityCloned.setInputPLT(inputPlt);
                processingEntityCloned.setAdjustedPLT(processingEntityPure.getAdjustedPLT().getPltHeaderId() == threadInitial.getInitialPLT().getPltHeaderId() ? threadCloned.getInitialPLT() : cloningScorPltHeader.cloneScorPltHeader(processingEntityPure.getAdjustedPLT().getPltHeaderId()));
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
