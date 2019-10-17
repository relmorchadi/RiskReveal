package com.scor.rr.importBatch.processing.io;

import com.google.gson.JsonObject;
import com.scor.rr.domain.entities.references.omega.BinFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by u004119 on 03/06/2016.
 */
public class JSONWriterImpl implements JSONWriter {

    private static final Logger log = LoggerFactory.getLogger(JSONWriterImpl.class);

    private String jsonPath;

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public BinFile write(String filename, JsonObject jsonObject) {
        log.info("Write json structure {}", filename);
        File file = makeFile(filename);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(jsonObject.toString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            log.info("Error in writing json structure {}", e.getMessage());
        }
        return new BinFile(file.getName(), file.getParent(), null);
    }

    private File makeFile(String filename) {
        final Path fullPath = Paths.get(jsonPath);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths "+fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }

}
