package com.scor.rr.importBatch.processing.domain;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by U002629 on 23/04/2015.
 */
public class MessageDataImpl implements MessageData {

    private final Set<Message> messages;
    private final Set<Message> errors;
    private int index;

    public MessageDataImpl() {
        messages = Collections.synchronizedSortedSet(new TreeSet<Message>());
        errors = Collections.synchronizedSortedSet(new TreeSet<Message>());
        index = 0;
    }

    @Override
    public void addError(String process, String error) {
        errors.add(new Message(++index, process, error));
    }

    @Override
    public Set<Message> getErrors() {
        return errors;
    }

    @Override
    public void addMessage(String process, String message) {
        messages.add(new Message(++index, process, message));
    }

    @Override
    public Set<Message> getMessages() {
        return messages;
    }
}
