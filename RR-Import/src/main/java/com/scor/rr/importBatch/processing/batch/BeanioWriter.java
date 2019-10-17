package com.scor.rr.importBatch.processing.batch;

/**
 * Created by U002629 on 14/04/2015.
 */
public interface BeanioWriter {
    void createWriter(String fileName);

    void write(Object o);

    void writeHeader();

    void end();
}
