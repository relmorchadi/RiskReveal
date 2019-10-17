package com.scor.rr.importBatch.processing.batch;

import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;

/**
 * Created by U002629 on 09/04/2015.
 */
public class  BasicBeanioWriter implements BeanioWriter {

    private static final NumberFormat format;

    static {
        format = NumberFormat.getInstance();
        format.setMinimumIntegerDigits(2);
        format.setGroupingUsed(false);
    }

    private StreamFactory streamFactory;
    private Resource streamMapping;
    private String streamName;
    private String filePath;
    private final boolean append = false;
    private boolean shouldDeleteIfExists = true;
    private boolean shouldDeleteIfEmpty = false;
    private BeanWriter beanWriter;

    public BasicBeanioWriter() {
    }

    public void setStreamFactory(StreamFactory streamFactory) {
        this.streamFactory = streamFactory;
    }

    public void setStreamMapping(Resource streamMapping) {
        this.streamMapping = streamMapping;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public void setShouldDeleteIfExists(boolean shouldDeleteIfExists) {
        this.shouldDeleteIfExists = shouldDeleteIfExists;
    }

    public void setShouldDeleteIfEmpty(boolean shouldDeleteIfEmpty) {
        this.shouldDeleteIfEmpty = shouldDeleteIfEmpty;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void createWriter(String fileName) {
        File file;
        try {
            final Path fullPath = Paths.get(filePath);
            Files.createDirectories(fullPath);
            file = new File(fullPath.toFile(), fileName);
            if (!append) {
                if (file.exists()) {
                    if (!shouldDeleteIfExists) {
                        throw new ItemStreamException("File already exists: " + file.getAbsolutePath());
                    }
                    if (!file.delete()) {
                        throw new IOException("Could not delete file: " + file.getAbsolutePath());
                    }
                }
                if (file.getParent() != null) {
                    new File(file.getParent()).mkdirs();
                }
                if (!createNewFile(file)) {
                    throw new ItemStreamException("Output file was not created: " + file.getAbsolutePath());
                }
            }
            else {
                if (!file.exists()) {
                    if (!createNewFile(file)) {
                        throw new ItemStreamException("Output file was not created: " + file.getAbsolutePath());
                    }
                }
            }
        }
        catch (IOException ioe) {
            throw new ItemStreamException("Unable to create file: " + fileName, ioe);
        }

        // checkParams the file is writable if it exists
        if (!file.canWrite()) {
            throw new ItemStreamException("File is not writable: " + file.getAbsolutePath());
        }

        beanWriter = streamFactory.createWriter(streamName, file);
    }

    private boolean createNewFile(File file) throws IOException {
        try {
            return file.createNewFile() && file.exists();
        }
        catch (IOException e) {
            // Per spring-batch code, on some filesystems you can get an exception
            // here even though the file was successfully created
            if (file.exists()) {
                return true;
            }
            else {
                throw e;
            }
        }
    }

    @Override
    public void write(Object o){
        beanWriter.write(o);
    }

    @Override
    public void writeHeader(){
        beanWriter.write("header",null);
    }

    @Override
    public void end(){
        beanWriter.flush();
        beanWriter.close();
    }
}
