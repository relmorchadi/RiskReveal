package com.scor.rr.service.batch.processor;

import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.repository.TaskRepository;
import com.scor.rr.service.abstraction.JobManager;
import com.scor.rr.service.batch.processor.rows.RLAccRow;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@StepScope
@Service
public class AccItemProcessor implements ItemProcessor<RLAccRow, RLAccRow> {

    // TODO : Review
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

    @Value("#{jobParameters['carId']}")
    private String carId;

    @Value("#{jobParameters['lob']}")
    private String lob;

    public void setForceCarId(boolean forceCarId) {
        this.forceCarId = forceCarId;
    }

    @Override
    public RLAccRow process(RLAccRow item) throws Exception {

        try {
            if (!this.isConstruction()) {
                item.setInceptionDate(null);
                item.setPracticalCompletionDate(null);
            }
            if (forceCarId)
                item.setCarID(carId);
            TaskEntity task = taskRepository.findById(Long.valueOf(taskId)).orElse(null);
            if (task != null) {
                StepEntity step = task.getSteps().stream().filter(s -> s.getStepName().equalsIgnoreCase("ExtractACC")).findFirst().orElse(null);
                if (step != null)
                    jobManager.logStep(step.getStepId(), StepStatus.SUCCEEDED);
            }
            return item;
        } catch (Exception ex) {
            jobManager.onTaskError(Long.valueOf(taskId));
            TaskEntity task = taskRepository.findById(Long.valueOf(taskId)).orElse(null);
            if (task != null) {
                StepEntity step = task.getSteps().stream().filter(s -> s.getStepName().equalsIgnoreCase("ExtractACC")).findFirst().orElse(null);
                if (step != null)
                    jobManager.logStep(step.getStepId(), StepStatus.FAILED);
            }
            ex.printStackTrace();
            return null;
        }
    }

    private boolean isConstruction() {
        return "02".equals(lob);
    }
}