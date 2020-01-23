package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.CARDivisionDto;

import java.util.List;

public interface DivisionService {
    List<CARDivisionDto> getDivisions(String carId);
}
