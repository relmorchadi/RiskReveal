package com.scor.rr.importBatch.processing.statistics;

import com.scor.rr.importBatch.processing.domain.MessageData;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;

import java.util.List;

/**
 * Created by U002629 on 11/01/2016.
 */
public class ExtractStepListener<T,S> extends StepListenerSupport<T,S> {
    protected MessageData messages;

    private final String processName;

    public ExtractStepListener(String processName) {
        this.processName = processName;
    }

    public void setMessages(MessageData messages) {
        this.messages = messages;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getReadCount();
        stepExecution.getWriteCount();
        stepExecution.getSkipCount();
        stepExecution.getExitStatus();
        messages.addMessage(processName, "Extraction completed with status "+
                stepExecution.getExitStatus().getExitCode()+
                ". items read: "+stepExecution.getReadCount()+
                ". items skipped: "+stepExecution.getSkipCount()+
                ". items written: "+stepExecution.getWriteCount());
        return null;
    }

    @Override
    public void onReadError(Exception ex) {
        messages.addError(processName, "error in item read. cause: "+ ExceptionUtils.getMessage(ex)+" ; root cause: "+ ExceptionUtils.getRootCauseMessage(ex));
    }

    @Override
    public void onWriteError(Exception ex, List<? extends S> items) {
        messages.addError(processName, "error in item write. cause: "+ ExceptionUtils.getMessage(ex)+" ; root cause: "+ ExceptionUtils.getRootCauseMessage(ex));
    }

    @Override
    public void onProcessError(T item, Exception ex) {
        messages.addError(processName, "error in item processing. cause: "+ ExceptionUtils.getMessage(ex)+" ; root cause: "+ ExceptionUtils.getRootCauseMessage(ex));
    }
}
