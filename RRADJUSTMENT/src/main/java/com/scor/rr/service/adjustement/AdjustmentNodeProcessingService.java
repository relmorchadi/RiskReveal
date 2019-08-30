package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.BinaryPLTFileWriter;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeProcessingRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentnodeRepository;
import com.scor.rr.repository.AdjustmentnodeprocessingRepository;
import com.scor.rr.repository.BinfileRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import com.scor.rr.service.cloning.CloningScorPltHeader;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    AdjustmentnodeprocessingRepository adjustmentnodeprocessingRepository;

    @Autowired
    ScorpltheaderRepository scorpltheaderRepository;

    @Autowired
    BinfileRepository binfileRepository;

    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

    @Autowired
    AdjustmentScalingParameterService adjustmentScalingParameterService;

    @Autowired
    AdjustmentReturnPeriodBandingParameterService periodBandingParameterService;

    @Autowired
    AdjustmentEventBasedParameterService eventBasedParameterService;

    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @Autowired
    CloningScorPltHeader cloningScorPltHeader;

    private final static String pathbin = "src/main/resources/file/plt.bin";

    public AdjustmentNodeProcessingEntity findOne(Integer id) {
        return adjustmentnodeprocessingRepository.getOne(id);
    }

    public List<AdjustmentNodeProcessingEntity> findAll() {
        return adjustmentnodeprocessingRepository.findAll();
    }

    //NOTE: please add the comments to explain what will be done by these methods saveBy... and how they could be called.
    //Perhaps a refactor need to be done

    public AdjustmentNodeProcessingEntity saveByInputPlt(AdjustmentNodeProcessingRequest adjustmentNodeProcessingRequest) {
        log.info("------ input PLT processing ------");
        log.info(" getting the input PLT");
        if (scorpltheaderRepository.findById(adjustmentNodeProcessingRequest.getScorPltHeaderIdPure()).isPresent()) {
            log.info("success getting the input PLT");
            ScorPltHeaderEntity scorPltHeader = scorpltheaderRepository.findById(adjustmentNodeProcessingRequest.getScorPltHeaderIdPure()).get();
            log.info(" getting Node");
            if (adjustmentnodeRepository.findById(adjustmentNodeProcessingRequest.getAdjustmentNodeId()).isPresent()) {
                log.info("success getting Node");
                AdjustmentNodeEntity adjustmentNode = adjustmentnodeRepository.findById(adjustmentNodeProcessingRequest.getAdjustmentNodeId()).get();
                AdjustmentNodeProcessingEntity nodeProcessing = new AdjustmentNodeProcessingEntity();
                if(adjustmentNodeProcessingRequest.getAdjustmentNodeProcessingId()!= 0) {
                    log.info("updating node processing");
                    nodeProcessing.setAdjustmentNodeProcessingId(adjustmentNodeProcessingRequest.getAdjustmentNodeProcessingId());
                } else {
                    log.info("Creating node processing");
                }
                nodeProcessing.setScorPltHeaderByFkInputPlt(scorPltHeader);
                nodeProcessing.setAdjustmentNodeByFkAdjustmentNode(adjustmentNode);
                log.info("------End saving input PLT processing ------");
                return adjustmentnodeprocessingRepository.save(nodeProcessing);
            } else {
                throwException(NODENOTFOUND, NOT_FOUND);
                return null;
            }
        } else {
            throwException(PLTNOTFOUNT, NOT_FOUND);
            return null;
        }
    }

    //NOTE: should have the separated functions:
    //  - Save Adjustment Node, adjustment parameters and adjustment processing into DB
    //  - Trigger adjustment processing (i.e call CalculAdjustement methods), return PLT Loss Data list and status
    //  - Persist PLT to DB

    public AdjustmentNodeProcessingEntity saveByAdjustedPlt(AdjustmentParameterRequest parameterRequest) {
        log.info("------Begin adjusted PLT processing ------");
        log.info(" getting the input PLT");
        if (scorpltheaderRepository.findById(parameterRequest.getScorPltHeaderInput()).isPresent()) {
            log.info("success getting the input PLT");
            ScorPltHeaderEntity scorPltHeader = scorpltheaderRepository.findById(parameterRequest.getScorPltHeaderInput()).get();
            log.info(" getting Node");
            if (adjustmentnodeRepository.findById(parameterRequest.getNodeId()).isPresent()) {
                log.info("success getting Node");
                AdjustmentNodeEntity adjustmentNode = adjustmentnodeRepository.findById(parameterRequest.getNodeId()).get();
                log.info("getting Node Processing");
                AdjustmentNodeProcessingEntity nodeProcessing = adjustmentnodeprocessingRepository.getAdjustmentNodeProcessingEntity(parameterRequest.getNodeId());
                log.info("success getting Node Processing");
                if(nodeProcessing != null) {
                    log.info("saving Parametre Node Processing");
                    saveParameterNode(adjustmentNode, parameterRequest);
                    log.info("success saving Parametre Node Processing");
                    List<PLTLossData> pltLossData = getLossFromPltInputAdjustment(scorPltHeader);
                    pltLossData = calculateProcessing(adjustmentNode, parameterRequest, pltLossData);
                    log.info("saving file LOSS for adjusted PLT");
                    BinFileEntity binFileEntity = savePLTFile(pltLossData);
                    if (binFileEntity != null) {
                        log.info("success saving file LOSS for adjusted PLT");
                        log.info("saving PLT");
                        ScorPltHeaderEntity scorPltHeaderAdjusted = new ScorPltHeaderEntity(scorPltHeader);
                        scorPltHeaderAdjusted.setBinFileEntity(binFileEntity);
                        scorPltHeaderAdjusted.setCreatedOn(new Timestamp(new Date().getTime()));
                        scorPltHeaderAdjusted.setCreatedBy("HAMZA");
                        scorPltHeaderAdjusted = scorpltheaderRepository.save(scorPltHeaderAdjusted);
                        log.info("success saving PLT");
                        nodeProcessing.setScorPltHeaderByFkAdjustedPlt(scorPltHeaderAdjusted);
                        nodeProcessing.setAdjustmentNodeByFkAdjustmentNode(adjustmentNode);
                        log.info("------END adjusted PLT processing ------");
                        return adjustmentnodeprocessingRepository.save(nodeProcessing);
                    } else {
                        throwException(BINFILEEXCEPTION, NOT_FOUND);
                        return null;
                    }
                } else {
                    throwException(NODEPROCESSINGNOTFOUND, NOT_FOUND);
                    return null;
                }
            } else {
                throwException(NODENOTFOUND, NOT_FOUND);
                return null;
            }
        } else {
            throwException(PLTNOTFOUNT, NOT_FOUND);
            return null;
        }
    }

    public AdjustmentNodeProcessingEntity getProcessingByNode(Integer nodeId) {
        return adjustmentnodeprocessingRepository.findAll()
                .stream()
                .filter(ape -> ape.getScorPltHeaderByFkInputPlt()
                        .getPkScorPltHeaderId() == nodeId)
                .findAny()
                .orElseThrow(throwException(PLTNOTFOUNT, NOT_FOUND));
    }

    public void deleteProcessingByNode(Integer nodeId){
        adjustmentnodeprocessingRepository.delete(getProcessingByNode(nodeId));
    }

    private BinFileEntity savePLTFile(List<PLTLossData> pltLossData) {
        File file = new File("C:\\Users\\u008208\\Desktop\\plt.csv");
        File fileWrite = null;
        if ("csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
            try {
                fileWrite = new File("C:\\Users\\u008208\\Desktop\\plt.csv");
                csvpltFileWriter.write(pltLossData,fileWrite);
            } catch (com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
                e.printStackTrace();
            }
        } else {
            BinaryPLTFileWriter binpltFileWriter = new BinaryPLTFileWriter();
            try {
                fileWrite = new File(pathbin);
                binpltFileWriter.write(pltLossData,fileWrite);

            } catch (com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
                e.printStackTrace();
            }
        }
        BinFileEntity binFileEntity = new BinFileEntity();
        binFileEntity.setFileName(fileWrite.getName());
        binFileEntity.setPath(fileWrite.getPath());
        binFileEntity.setFqn(fileWrite.getAbsolutePath());
        return binfileRepository.save(binFileEntity);
    }


    private List<PLTLossData> getLossFromPltInputAdjustment(ScorPltHeaderEntity scorPltHeaderEntity) {
        if(scorPltHeaderEntity != null) {
            if(scorPltHeaderEntity.getBinFileEntity() != null) {
                File file = new File("C:\\Users\\u008208\\Desktop\\plt.csv");
                if ("csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
                    CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
                    try {
                        return csvpltFileReader.read(file);
                    } catch (com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
                        e.printStackTrace();
                    }
                } else {
                    BinaryPLTFileReader binpltFileReader = new BinaryPLTFileReader();
                    try {
                        return binpltFileReader.read(file);
                    } catch (com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                throwException(BINFILEEXCEPTION,NOT_FOUND);
            }
        } else {
            throwException(PLTNOTFOUNT,NOT_FOUND);
            return null;
        }
        return null;
    }

    public void delete(Integer id) {
        this.adjustmentnodeprocessingRepository.delete(
                this.adjustmentnodeprocessingRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }

    //NOTE: should separate two functions: save parameter and processing
    //Done

    private List<PLTLossData> calculateProcessing(AdjustmentNodeEntity node, AdjustmentParameterRequest parameterRequest, List<PLTLossData> pltLossData){
        List<AdjustmentReturnPeriodBandingParameterEntity> parameterEntities = new ArrayList<>();
        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
            return CalculAdjustement.linearAdjustement(pltLossData, parameterRequest.getLmf(), node.getCapped());
        }
        else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
            return CalculAdjustement.eefFrequency(pltLossData, node.getCapped(), parameterRequest.getRpmf());
        }
        else if (NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
            for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
                parameterEntities.add(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(),periodBanding.getLmf(),node));
            }
            return CalculAdjustement.oepReturnPeriodBanding(pltLossData, node.getCapped(), parameterEntities);
        }
        else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
            return CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossData,node.getCapped(),parameterRequest.getPeatData());
         }
        else if (NONLINEARRETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            return CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossData,node.getCapped(),parameterRequest.getPeatData());
        }
        else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
                parameterEntities.add(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(),periodBanding.getLmf(),node));
            }
            return CalculAdjustement.eefReturnPeriodBanding(pltLossData,node.getCapped(),parameterEntities);
        }
        else throwException(TYPENOTFOUND, NOT_FOUND);
        return null;
    }

    private void saveParameterNode(AdjustmentNodeEntity node, AdjustmentParameterRequest parameterRequest) {
        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
            adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getLmf(),node));
        }
        else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
            adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getRpmf(),node));
        }
        else if (NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
            for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
                periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(),periodBanding.getLmf(),node));
            }
        }
        else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
            adjustmentNodeService.savePeatDataFile(node, parameterRequest);
        }
        else if (NONLINEARRETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            adjustmentNodeService.savePeatDataFile(node, parameterRequest);
        }
        else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
                periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(), periodBanding.getLmf(), node));
            }
        }
        else throwException(TYPENOTFOUND, NOT_FOUND);
    }

    public AdjustmentNodeProcessingEntity cloneAdjustmentNodeProcessing(AdjustmentNodeEntity nodePure,AdjustmentNodeEntity nodeCloned,ScorPltHeaderEntity pltHeaderPure) {
        AdjustmentNodeProcessingEntity processingEntitypure =  adjustmentnodeprocessingRepository.getAdjustmentNodeProcessingEntitiesByAdjustmentNodeByFkAdjustmentNode(nodePure);
        AdjustmentNodeProcessingEntity processingEntityCloned = new AdjustmentNodeProcessingEntity();
        processingEntityCloned.setAdjustmentNodeByFkAdjustmentNode(nodeCloned);
        processingEntityCloned.setScorPltHeaderByFkInputPlt(pltHeaderPure);
        processingEntityCloned.setScorPltHeaderByFkAdjustedPlt(cloningScorPltHeader.cloneScorPltHeader(processingEntitypure.getScorPltHeaderByFkAdjustedPlt().getPkScorPltHeaderId()));
        return processingEntityCloned;
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
