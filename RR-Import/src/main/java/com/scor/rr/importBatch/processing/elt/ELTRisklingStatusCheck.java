package com.scor.rr.importBatch.processing.elt;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Created by U002629 on 05/03/2015.
 */
class ELTRisklingStatusCheck implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
          return RepeatStatus.FINISHED;

//        return RepeatStatus.CONTINUABLE;
    }
}
