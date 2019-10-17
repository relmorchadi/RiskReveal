package com.scor.rr.importBatch.processing.statistics;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by U002629 on 11/01/2016.
 */
public class StringHeaderCallback implements FlatFileHeaderCallback {

    private final String headerText;

    public StringHeaderCallback(String headerText) {
        this.headerText = headerText;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(headerText);
    }
}
