package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeProcessingRequest;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentNodeProcessingService {
    private static final Logger log = LoggerFactory.getLogger(AdjustmentNodeProcessingService.class);

    @Autowired
    AdjustmentnodeprocessingRepository adjustmentnodeprocessingRepository;


    @Autowired
    private ProjectViewRepository projectViewRepository;

    @Autowired
    private RranalysisRepository rranalysisRepository;

    @Autowired
    private TargetrapRepository targetrapRepository;


    @Autowired
    ScorpltheaderRepository scorpltheaderRepository;

    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

   public AdjustmentNodeProcessingEntity findOne(Integer id){
       return adjustmentnodeprocessingRepository.getOne(id);
   }

    public List<AdjustmentNodeProcessingEntity> findAll(){
        return adjustmentnodeprocessingRepository.findAll();
    }

    public AdjustmentNodeProcessingEntity save(AdjustmentNodeProcessingRequest adjustmentNodeProcessingRequest){
       if(scorpltheaderRepository.findById(adjustmentNodeProcessingRequest.getScorPltHeaderIdPure()).isPresent()) {
           ScorPltHeaderEntity scorPltHeader = scorpltheaderRepository.findById(adjustmentNodeProcessingRequest.getScorPltHeaderIdPure()).get();
           if(adjustmentnodeRepository.findById(adjustmentNodeProcessingRequest.getAdjustmentNodeId()).isPresent()) {
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

    public List<PLTLossData> getLossFromPltInputAdjustment(ScorPltHeaderEntity scorPltHeaderEntity) {
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
        return null;
    }

    public void delete(Integer id) {
        this.adjustmentnodeprocessingRepository.delete(
                this.adjustmentnodeprocessingRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    public boolean adjustDefault(String projectId, Integer pltHeaderId, Long analysisId, String analysisName, Long rdmId, String rdmName, Integer targetRapId, String sourceConfigVendor) {
        log.info("DEFAULT ADJUSTMENT for projectId {} analysisId {} targetRapId = {}", projectId, analysisId, targetRapId);
        ProjectView project = projectViewRepository.findByProjectId(projectId);
        ScorPltHeaderEntity purePLTHeader = scorpltheaderRepository.getOne(pltHeaderId);
        log.info("DEFAULT ADJUSTMENT for RDM {} - {}, PLT {}, PLTHeader = {}", rdmName, rdmId, pltHeaderId, purePLTHeader);
        if (purePLTHeader == null) {
            log.error("No data found for PLT {}. Default adjustment error!", pltHeaderId);
            return false;
        }

        String engineType = null;
        if (purePLTHeader.getRrAnalysis() != null) {
            RrAnalysisNewEntity rrAnalysis = purePLTHeader.getRrAnalysis();
            if (rrAnalysis != null) {
                if ("ALM".equals(rrAnalysis.getEntityModule()))
                    engineType = "AGG";
                else if ("DLM".equals(rrAnalysis.getEntityModule()))
                    engineType = "DET";
            }
        }

        TargetRapEntity targetRap = targetrapRepository.findBytargetRapId(targetRapId.toString());
        boolean hasDefaultAdjustment = false;

        return hasDefaultAdjustment;
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }


}
