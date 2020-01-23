package com.scor.rr.service;

import com.scor.rr.domain.dto.CarContractDto;
import com.scor.rr.repository.CarContractViewRepository;
import com.scor.rr.service.abstraction.ContractService;
import com.scor.rr.service.abstraction.DivisionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private CarContractViewRepository carContractViewRepository;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CarContractDto getContractInfo(Long projectId) {

        CarContractDto carContractDto = modelMapper.map(carContractViewRepository.findByProjectId(projectId), CarContractDto.class);

        if (carContractDto != null)
            carContractDto.setDivisions(divisionService.getDivisions(carContractDto.getCarId()));

        return carContractDto;
    }
}
