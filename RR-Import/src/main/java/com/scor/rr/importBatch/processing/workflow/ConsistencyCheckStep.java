package com.scor.rr.importBatch.processing.workflow;

import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 30/03/2015.
 */
public class ConsistencyCheckStep extends BaseBatchBeanImpl implements CheckStep {

    private static final Logger log = LoggerFactory.getLogger(ConsistencyCheckStep.class);

    private List<ConsistencyCheck> checksToDo;

    public void setChecksToDo(List<ConsistencyCheck> checksToDo) {
        this.checksToDo = checksToDo;
    }

    @Override
    public ExitStatus runChecks(){
        if (true) {
            log.info("Thai");
            return ExitStatus.COMPLETED;
        }
        log.debug("Starting ConsistencyCheckStep");
        Boolean hasErrors = false;
        List<ConsistencyStatus> statuses = new LinkedList<>();
        Map<String, Object> activeChecks = fireRules("activeChecks");
        for (ConsistencyCheck check : checksToDo) {
            if((Boolean)activeChecks.get(check.getCheckName())) {
                log.info("running check {}", check.getCheckName());
                ConsistencyStatus status = check.performCheck();
                if (status.getStatus().equals(ConsistencyStatus.Status.KO))
                    hasErrors = true;
                statuses.add(status);
            }
        }

        StringBuilder message = new StringBuilder();
        if(hasErrors) {
            message.append("Status KO\n");
            log.warn("errors found in checks");
        }
        else
            message.append("status OK\n");

        for (ConsistencyStatus status : statuses) {
            message.append(status.getCheckName()).append(" : ").append(status.getStatus()).append(" : ").append(status.getMessage()).append("\n");
        }

        String messageStr = message.toString();
        if(hasErrors) {
            log.warn(messageStr);
            addError("CONSISTENCY", messageStr);
        }
        else if(log.isDebugEnabled()) {
            log.info(messageStr);
            addMessage("CONSISTENCY", messageStr);
        }

        log.debug("ConsistencyCheckStep completed");
        return hasErrors?ExitStatus.FAILED:ExitStatus.COMPLETED;
    }
}
