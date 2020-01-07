package com.scor.rr.service.batch.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

@StepScope
public class RLAccAndLocItemWriter implements FlatFileHeaderCallback {

    private String header;

    public RLAccAndLocItemWriter(String header) {
        this.header = header;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }
}
