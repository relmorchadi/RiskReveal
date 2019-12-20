package com.scor.rr.service.Plt;

import com.scor.rr.domain.Calibration;
import com.scor.rr.repository.CalibrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PltService {

    @Autowired
    CalibrationRepository calibrationRepository;

    public List<Calibration> getPlts(String wsId, Integer uwYear) {
        return calibrationRepository.findByWorkspaceContextCodeAndUwYearAndPltType(wsId, uwYear, "PURE");
    }

}
