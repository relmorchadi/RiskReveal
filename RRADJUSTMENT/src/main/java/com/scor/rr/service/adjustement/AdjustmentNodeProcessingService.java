package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.BinaryPLTFileWriter;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeProcessingRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;
import static com.scor.rr.exceptions.ExceptionCodename.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentNodeProcessingService {

    @Autowired
    AdjustmentnodeprocessingRepository adjustmentnodeprocessingRepository;

    @Autowired
    ScorpltheaderRepository scorpltheaderRepository;

    @Autowired
    BinfileRepository binfileRepository;

    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

    final static String pathcsv = "C:\\Users\\u008208\\Desktop\\plt.csv";

    final static String pathbin = "C:\\Users\\u008208\\Desktop\\plt.bin";

    public AdjustmentNodeProcessingEntity findOne(Integer id) {
        return adjustmentnodeprocessingRepository.getOne(id);
    }

    public List<AdjustmentNodeProcessingEntity> findAll() {
        return adjustmentnodeprocessingRepository.findAll();
    }

    public AdjustmentNodeProcessingEntity saveByInputPlt(AdjustmentNodeProcessingRequest adjustmentNodeProcessingRequest) {
        if (scorpltheaderRepository.findById(adjustmentNodeProcessingRequest.getScorPltHeaderIdPure()).isPresent()) {
            ScorPltHeaderEntity scorPltHeader = scorpltheaderRepository.findById(adjustmentNodeProcessingRequest.getScorPltHeaderIdPure()).get();
            if (adjustmentnodeRepository.findById(adjustmentNodeProcessingRequest.getAdjustmentNodeId()).isPresent()) {
                AdjustmentNodeEntity adjustmentNode = adjustmentnodeRepository.findById(adjustmentNodeProcessingRequest.getAdjustmentNodeId()).get();
                AdjustmentNodeProcessingEntity nodeProcessing = new AdjustmentNodeProcessingEntity();
                nodeProcessing.setScorPltHeaderByInputPltId(scorPltHeader);
                nodeProcessing.setAdjustmentNodeByAdjustmentNodeId(adjustmentNode);
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

    public AdjustmentNodeProcessingEntity saveByAdjustedPlt(AdjustmentParameterRequest parameterRequest) {
        if (scorpltheaderRepository.findById(parameterRequest.getScorPltHeaderInput()).isPresent()) {
            ScorPltHeaderEntity scorPltHeader = scorpltheaderRepository.findById(parameterRequest.getScorPltHeaderInput()).get();
            if (adjustmentnodeRepository.findById(parameterRequest.getNodeId()).isPresent()) {
                AdjustmentNodeEntity adjustmentNode = adjustmentnodeRepository.findById(parameterRequest.getNodeId()).get();
                AdjustmentNodeProcessingEntity nodeProcessing = new AdjustmentNodeProcessingEntity();
                List<PLTLossData> pltLossData = getLossFromPltInputAdjustment(scorPltHeader);
                pltLossData = calculateAdjustment(adjustmentNode,parameterRequest,pltLossData);
                BinFileEntity binFileEntity = savePLTFile(pltLossData,scorPltHeader);
                if(binFileEntity != null) {
                    ScorPltHeaderEntity scorPltHeaderAdjusted = new ScorPltHeaderEntity();
                    scorPltHeaderAdjusted.setBinFile(binFileEntity);
                    scorpltheaderRepository.save(scorPltHeaderAdjusted);
                    nodeProcessing.setScorPltHeaderByAdjustedPltId(scorPltHeaderAdjusted);
                    nodeProcessing.setScorPltHeaderByInputPltId(scorPltHeader);
                    nodeProcessing.setAdjustmentNodeByAdjustmentNodeId(adjustmentNode);
                    return adjustmentnodeprocessingRepository.save(nodeProcessing);
                } else {
                    throwException(BINFILEEXCEPTION, NOT_FOUND);
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
                .filter(ape -> ape.getScorPltHeaderByInputPltId()
                        .getScorPltHeaderId() == nodeId)
                .findAny()
                .orElseThrow(throwException(PLTNOTFOUNT, NOT_FOUND));
    }

    private BinFileEntity savePLTFile(List<PLTLossData> pltLossData,ScorPltHeaderEntity scorPltHeaderEntity) {
        File file = new File(scorPltHeaderEntity.getBinFile().getFileName());
        File fileWrite = null;
        if ("csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
            try {
                fileWrite = new File(pathcsv);
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
            if(scorPltHeaderEntity.getBinFile() != null) {
                File file = new File(scorPltHeaderEntity.getBinFile().getFileName());
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

    private List<PLTLossData> calculateAdjustment(AdjustmentNodeEntity node, AdjustmentParameterRequest parameterRequest, List<PLTLossData> pltLossData) {
        CalculAdjustement calculAdjustement = new CalculAdjustement();
        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
            return calculAdjustement.lineaireAdjustement(pltLossData, parameterRequest.getLmf(), node.getCapped());
        }
        if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
            return calculAdjustement.eefFrequency(pltLossData, node.getCapped(), parameterRequest.getRpmf());
        }
        if (NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
            return calculAdjustement.oepReturnPeriodBanding(pltLossData, node.getCapped(), parameterRequest.getAdjustmentReturnPeriodBendings());
        }
        if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
            return calculAdjustement.nonLineaireEventDrivenAdjustment(pltLossData, node.getCapped(), parameterRequest.getPeatData());
        }
        if (NONLINEAIRERETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            return calculAdjustement.nonLineaireEventPeriodDrivenAdjustment(pltLossData, node.getCapped(), parameterRequest.getPeatData());
        }
        if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            return calculAdjustement.eefReturnPeriodBanding(pltLossData, node.getCapped(), parameterRequest.getAdjustmentReturnPeriodBendings());
        }
        throwException(TYPENOTFOUND, NOT_FOUND);
        return null;

    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }


}
