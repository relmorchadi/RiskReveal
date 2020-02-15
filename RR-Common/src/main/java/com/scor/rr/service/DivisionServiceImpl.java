package com.scor.rr.service;

import com.scor.rr.domain.dto.CARDivisionDto;
import com.scor.rr.repository.ProjectConfigurationForeWriterDivisionRepository;
import com.scor.rr.service.abstraction.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class DivisionServiceImpl implements DivisionService {

    @Autowired
    private ProjectConfigurationForeWriterDivisionRepository projectConfigurationForeWriterDivisionRepository;

    @Override
    public List<CARDivisionDto> getDivisions(String carId) {
        List<Map<String, Object>> divisions = projectConfigurationForeWriterDivisionRepository.findByCARId(carId);
        List<CARDivisionDto> carDivisions = new ArrayList<>();
        for (Map<String, Object> division : divisions) {
            CARDivisionDto carDivisionDto = new CARDivisionDto();
            carDivisionDto.setCaRequestId((String) division.get("caRequestId"));
            carDivisionDto.setCarStatus((String) division.get("carStatus"));
            carDivisionDto.setContractId((String) division.get("contractId"));
            carDivisionDto.setCurrency((String) division.get("currency"));
            carDivisionDto.setDivisionNumber(Integer.valueOf((String) division.get("divisionNumber")));
            carDivisionDto.setIsPrincipalDivision(Boolean.parseBoolean(String.valueOf(division.get("IsPrincipalDivision"))));
            carDivisionDto.setProjectId(((BigInteger) division.get("projectId")).longValue());
            carDivisionDto.setUwYear((Integer) division.get("uwYear"));
            carDivisionDto.setWorkspaceId(((BigInteger) division.get("workspaceId")).longValue());
            carDivisionDto.setCoverage((String) division.get("coverage"));
            carDivisionDto.setLob((String) division.get("lob"));
            carDivisions.add(carDivisionDto);
        }
        carDivisions.sort(Comparator.comparing(CARDivisionDto::getDivisionNumber));
        return carDivisions;
    }
}
