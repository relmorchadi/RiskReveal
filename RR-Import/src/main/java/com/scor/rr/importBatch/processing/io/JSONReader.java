package com.scor.rr.importBatch.processing.io;

import com.google.gson.JsonObject;
import com.scor.rr.domain.entities.references.omega.BinFile;

/**
 * Created by u004119 on 25/07/2016.
 */
public interface JSONReader {
    JsonObject readJson(BinFile jsonFile);
}
