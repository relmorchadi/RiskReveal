package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.ExposureManagerRefDto;

public interface ExposureManagerService {

    ExposureManagerRefDto getRefForExposureManager(Long projectId);
}
