package com.scor.rr.importBatch.processing.domain;

import java.util.Set;

/**
 * Created by U002629 on 23/04/2015.
 */
public interface MessageData {
    void addError(String process, String error);
    Set<Message> getErrors();

    void addMessage(String process, String message);
    Set<Message> getMessages();
}
