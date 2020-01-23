package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.CarContractDto;

public interface ContractService {
    CarContractDto getContractInfo(Long projectId);
}
