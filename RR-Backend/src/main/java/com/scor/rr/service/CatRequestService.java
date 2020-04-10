package com.scor.rr.service;

import com.scor.rr.domain.*;
import com.scor.rr.domain.enums.CARStatus;
import com.scor.rr.domain.enums.CARType;
import com.scor.rr.repository.*;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by u004602 on 26/12/2019.
 */
@Component
@Service
public class CatRequestService {
    private static final Logger log= LoggerFactory.getLogger(CatRequestService.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRrRepository userRrRepository;

    @Autowired
    private SubsidiaryRepository subsidiaryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProjectConfigurationForeWriterRepository projectConfigurationForeWriterRepository;

    @Autowired
    private ProjectConfigurationForeWriterContractRepository projectConfigurationForeWriterContractRepository;

    @Autowired
    private ProjectConfigurationForeWriterDivisionRepository projectConfigurationForeWriterDivisionRepository;

    @Autowired
    private RefInsuredsRepository refInsuredsRepository;

    private static final long CAR_ID_OFFSET = 100000;

    public String createRequest(CatRequestData data){
        log.info("Req from FW : {}", data);
        final Date date = new Date();

        Optional<RefInsureds> client = refInsuredsRepository.findById(data.insurNumber.toString());
        String clientName;
        if(client.isPresent()) {
            clientName = client.get().getInsuredName();
        } else {
            clientName = data.insurNumber.toString();
        }
        UserRrEntity userRrEntity = userRrRepository.findByWindowsUser(data.userID);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setEntity(1);
        projectEntity.setCreationDate(date);
        projectEntity.setCreatedBy(data.userFN + " " + data.userLN);
        projectEntity.setProjectName(data.uwAnalysisName); //FIXME: check with SHAUN
        projectEntity = projectService.addNewProjectFac(data.facNumber, data.uwYear, clientName, projectEntity);

        List<ProjectConfigurationForeWriterContract> projectConfigurationForeWriterContracts = projectConfigurationForeWriterContractRepository.findByContractIdAndUwYear(data.facNumber, data.uwYear);
        for (ProjectConfigurationForeWriterContract projectConfigurationForeWriterContract : projectConfigurationForeWriterContracts) {
            Optional<ProjectConfigurationForeWriter> projectConfigurationForeWriter = projectConfigurationForeWriterRepository.findById(projectConfigurationForeWriterContract.getProjectConfigurationForeWriterId());
            if (projectConfigurationForeWriter.isPresent()) {
                if (CARStatus.CMPL.equals(projectConfigurationForeWriter.get().getCarStatus())) {
                    projectConfigurationForeWriter.get().setCarStatus(CARStatus.SUPS);
                } else {
                    projectConfigurationForeWriter.get().setCarStatus(CARStatus.CANC);
                }
//                projectConfigurationForeWriter.get().setLastUpdateDate(date);
//                projectConfigurationForeWriter.get().setLastUpdateBy(data.userFN + " " + data.userLN); // SHOULD BE USER SYSTEM
                projectConfigurationForeWriterRepository.save(projectConfigurationForeWriter.get());
            }
        }

        ProjectConfigurationForeWriter projectConfigurationForeWriter = projectConfigurationForeWriterRepository.save(new ProjectConfigurationForeWriter(1,
                projectEntity.getProjectId(),
                CARType.FAC.getCode(),
                CARStatus.NEW,
                data.uwAnalysisName,
                date,
                userRrEntity == null ? null : userRrEntity.getUserId(),
                data.userID,
                null,
                null));

        //FIXME: change CAR naming logic
        projectEntity.setProjectName("CAR-" + (CAR_ID_OFFSET + projectConfigurationForeWriter.getProjectConfigurationForeWriterId()));
        projectRepository.save(projectEntity);

        //FIXME: must define a Counter table to controll CAR ID
        projectConfigurationForeWriter.setCaRequestId("CAR-" + (CAR_ID_OFFSET + projectConfigurationForeWriter.getProjectConfigurationForeWriterId()));
        if (userRrEntity != null) {
            projectConfigurationForeWriter.setAssignedTo(userRrEntity.getUserId());
            projectConfigurationForeWriter.setCarStatus(CARStatus.ASGN);
        }
        projectConfigurationForeWriterRepository.save(projectConfigurationForeWriter);
        /*Insured or client ?*/
        //ClientEntity clientEntity = clientRepository.findByClientnumber(data.insurNumber.toString());

        SubsidiaryEntity subsidiaryEntity = subsidiaryRepository.findByCode(data.subsidiary.toString());
        ProjectConfigurationForeWriterContract projectConfigurationForeWriterContract = projectConfigurationForeWriterContractRepository.save(new ProjectConfigurationForeWriterContract(1,
                projectConfigurationForeWriter.getProjectConfigurationForeWriterId(),
                data.facNumber, //FIXME: contractId = facNum ?
                data.facNumber,
                data.uwYear,
                data.order,
                data.endorsementNumber,
                data.label,
                data.businessType.toString(), //FIXME: FK or name ? if name need a ref data for look up
                clientName,
                subsidiaryEntity != null ? subsidiaryEntity.getLabel() : data.subsidiary.toString(),
                data.lob,
                data.sector,
                data.uwAnalysisName
                ));


        data.divisions.forEach(division -> projectConfigurationForeWriterDivisionRepository.save(new ProjectConfigurationForeWriterDivision(1,
                projectConfigurationForeWriterContract.getProjectConfigurationForeWriterContractId(),
                division.divisionNumber.toString(),
                division.principalDivision,
                division.lob,
                division.coverageType,
                division.currency)));

        return projectConfigurationForeWriter.getCaRequestId();
    }

    @ToString
    public static class CatRequestData {
        final Integer businessType;
        final Integer endorsementNumber;
        final String facNumber;
        final Integer insurNumber;
        final String  label;
        final String lob;
        final Integer order;
        final String sector;
        final Integer subsidiary;
        final Integer uwYear;

        final Integer uwAnalysisId;
        final String uwAnalysisName;

        final List<CatRequestDivision> divisions;
        final String userID;
        final String userFN;
        final String userLN;

        public CatRequestData(Integer businessType, Integer endorsementNumber, String facNumber, Integer insurNumber, String label, String lob, Integer order, String sector, Integer subsidiary, Integer uwYear, Integer uwAnalysisId, String uwAnalysisName, List<CatRequestDivision> divisions, String userID, String userFN, String userLN) {
            this.businessType = businessType;
            this.endorsementNumber = endorsementNumber;
            this.facNumber = facNumber;
            this.insurNumber = insurNumber;
            this.label = label;
            this.lob = lob;
            this.order = order;
            this.sector = sector;
            this.subsidiary = subsidiary;
            this.uwYear = uwYear;
            this.uwAnalysisId = uwAnalysisId;
            this.uwAnalysisName = uwAnalysisName;
            this.divisions = divisions;
            this.userID = userID;
            this.userFN = userFN;
            this.userLN = userLN;
        }
    }

    public static class CatRequestDivision{
        final String currency;
        final String coverageType;
        final String lob;
        final Integer divisionNumber;
        final Boolean principalDivision;

        public CatRequestDivision(String currency, String coverageType, String lob, Integer divisionNumber, Integer principalDivision) {
            this.currency = currency;
            this.coverageType = coverageType;
            this.lob = lob;
            this.divisionNumber = divisionNumber;
            this.principalDivision = principalDivision > 0;
        }
    }
}
