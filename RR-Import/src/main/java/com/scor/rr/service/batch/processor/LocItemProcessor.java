package com.scor.rr.service.batch.processor;

import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.dto.CARDivisionDto;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.repository.TaskRepository;
import com.scor.rr.service.abstraction.ConfigurationService;
import com.scor.rr.service.abstraction.JobManager;
import com.scor.rr.service.batch.processor.rows.RLLocRow;
import com.scor.rr.service.state.TransformationPackage;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@StepScope
public class LocItemProcessor implements ItemProcessor<RLLocRow, RLLocRow> {

    private boolean forceCarId = true;

    @Autowired
    @Qualifier("jobManagerImpl")
    private JobManager jobManager;

    @Autowired
    private TaskRepository taskRepository;

    @Value("#{jobParameters['taskId']}")
    private String taskId;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ConfigurationService configurationService;

    @Value("#{jobParameters['carId']}")
    private String carId;

    private List<CARDivisionDto> divisions;

    private boolean isDone = false;

    @Override
    public RLLocRow process(RLLocRow item) throws Exception {
        try {
            if (forceCarId)
                item.setCarID(carId);

            Integer division = transformationPackage.getModelPortfolios().get(0).getDivision();
            if (transformationPackage.getModelPortfolios() != null && !transformationPackage.getModelPortfolios().isEmpty())
                item.setDivision(String.valueOf(division));
            // TODO : review later & currency
//        item.setAccuracyLevel(mappingHandler.getGeoResForCode(Integer.toString(item.getGeoResultionCode())));
            divisions = configurationService.getDivisions(carId);
            item.setCurrencyCode(
                    divisions.stream().filter(div -> div.getDivisionNumber().equals(division))
                            .map(CARDivisionDto::getCurrency)
                            .findFirst().orElse("USD")
            );

            if (!isDone) {
                TaskEntity task = taskRepository.findById(Long.valueOf(taskId)).orElse(null);
                if (task != null) {
                    StepEntity step = task.getSteps().stream().filter(s -> s.getStepName().equalsIgnoreCase("ExtractLOC")).findFirst().orElse(null);
                    if (step != null)
                        jobManager.logStep(step.getStepId(), StepStatus.SUCCEEDED);
                }
                isDone = true;
            }
            return item;
        } catch (Exception ex) {
            jobManager.onTaskError(Long.valueOf(taskId));
            TaskEntity task = taskRepository.findById(Long.valueOf(taskId)).orElse(null);
            if (task != null) {
                StepEntity step = task.getSteps().stream().filter(s -> s.getStepName().equalsIgnoreCase("ExtractLOC")).findFirst().orElse(null);
                if (step != null)
                    jobManager.logStep(step.getStepId(), StepStatus.FAILED);
            }
            ex.printStackTrace();
            return null;
        }
    }
}
