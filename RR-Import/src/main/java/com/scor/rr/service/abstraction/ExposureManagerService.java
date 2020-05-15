package com.scor.rr.service.abstraction;

import com.scor.rr.domain.dto.ExposureManagerDto;
import com.scor.rr.domain.dto.ExposureManagerParamsDto;
import com.scor.rr.domain.dto.ExposureManagerRefDto;

public interface ExposureManagerService {

    ExposureManagerRefDto getRefForExposureManager(Long projectId);

    ExposureManagerDto getExposureManagerData(ExposureManagerParamsDto params);

    byte[] exportExposureManagerData(ExposureManagerParamsDto params);

    String exposureManagerDataExport(ExposureManagerParamsDto params);
}
