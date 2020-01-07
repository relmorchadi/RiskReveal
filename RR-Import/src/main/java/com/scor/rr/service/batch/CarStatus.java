package com.scor.rr.service.batch;

import com.scor.rr.domain.ProjectConfigurationForeWriter;
import com.scor.rr.repository.ProjectConfigurationForeWriterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@StepScope
@Service
@Slf4j
public class CarStatus {


    @Autowired
    private ProjectConfigurationForeWriterRepository projectConfigurationForeWriterRepository;

    @Value("#{jobParameters['carId']}")
    private String carId;

    public RepeatStatus changeCARStatus() {

        log.info("Changing CAR status");
        if (carId != null) {
            ProjectConfigurationForeWriter car = projectConfigurationForeWriterRepository.findByCaRequestId(carId);
            car.setCarStatus("COMPLETED");
            projectConfigurationForeWriterRepository.save(car);
            log.info("Changing CAR status has been changed to COMPLETED");
        } else
            log.error("no car id has been found");
        return RepeatStatus.FINISHED;
    }
}
