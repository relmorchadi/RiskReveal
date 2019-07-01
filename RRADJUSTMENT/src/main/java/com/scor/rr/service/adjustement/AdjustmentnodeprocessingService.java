package com.scor.rr.service.adjustement;

import com.scor.rr.domain.*;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentnodeprocessingService {
    private static final Logger log = LoggerFactory.getLogger(AdjustmentnodeprocessingService.class);

    @Autowired
    AdjustmentnodeprocessingRepository adjustmentnodeprocessingRepository;


    @Autowired
    private ProjectViewRepository projectViewRepository;

    @Autowired
    private ScorpltheaderRepository scorpltheaderRepository;

    @Autowired
    private RranalysisRepository rranalysisRepository;

    @Autowired
    private TargetrapRepository targetrapRepository;

   public AdjustmentNodeProcessingEntity findOne(Integer id){
       return adjustmentnodeprocessingRepository.getOne(id);
   }

    public List<AdjustmentNodeProcessingEntity> findAll(){
        return adjustmentnodeprocessingRepository.findAll();
    }

    public AdjustmentNodeProcessingEntity save(AdjustmentNodeProcessingEntity adjustmentnodeprocessingModel){
       return adjustmentnodeprocessingRepository.save(adjustmentnodeprocessingModel);
    }

    public void delete(Integer id) {
        this.adjustmentnodeprocessingRepository.delete(
                this.adjustmentnodeprocessingRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    public boolean adjustDefault(String projectId, String pltHeaderId, Long analysisId, String analysisName, Long rdmId, String rdmName, Integer targetRapId, String sourceConfigVendor) {
        log.info("DEFAULT ADJUSTMENT for projectId {} analysisId {} targetRapId = {}", projectId, analysisId, targetRapId);
        ProjectView project = projectViewRepository.findByProjectId(projectId);
        ScorPltHeaderEntity purePLTHeader = scorpltheaderRepository.findByScorPltHeaderId(pltHeaderId);
        log.info("DEFAULT ADJUSTMENT for RDM {} - {}, PLT {}, PLTHeader = {}", rdmName, rdmId, pltHeaderId, purePLTHeader);
        if (purePLTHeader == null) {
            log.error("No data found for PLT {}. Default adjustment error!", pltHeaderId);
            return false;
        }

        String engineType = null;
        if (purePLTHeader.getRrAnalysisId() != null) {
            RrAnalysisEntity rrAnalysis = rranalysisRepository.findByAnalysisId(purePLTHeader.getRrAnalysisId());
            if (rrAnalysis != null) {
                if ("ALM".equals(rrAnalysis.getModel()))
                    engineType = "AGG";
                else if ("DLM".equals(rrAnalysis.getModel()))
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
