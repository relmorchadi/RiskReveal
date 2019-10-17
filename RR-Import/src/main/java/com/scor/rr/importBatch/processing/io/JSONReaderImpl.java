package com.scor.rr.importBatch.processing.io;

//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
//import com.scor.almf.treaty.cdm.domain.reference.meta.BinFile;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;

/**
 * Created by u004119 on 03/06/2016.
 */
//@Service
//public class JSONReaderImpl extends BaseFileWriter implements JSONReader {
//
//
//    private String jsonPath;
//
//    public String getJsonPath() {
//        return jsonPath;
//    }
//
//    public void setJsonPath(String jsonPath) {
//        this.jsonPath = jsonPath;
//    }
//
//    public JsonObject readJson(BinFile jsonFile) {
//        try {
//            System.out.println("File path: " + jsonFile.getPath());
//            System.out.println("File name: " + jsonFile.getPath());
//            FileReader fr = new FileReader(new File(jsonFile.getPath(), jsonFile.getFileName()));
//            JsonObject jsonObject = (JsonObject) new JsonParser().parse(fr);
//
//            return jsonObject;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
