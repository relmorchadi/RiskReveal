package com.scor.rr.importBatch.processing.batch;

import java.io.File;

/**
 * Created by U002629 on 20/04/2015.
 */
public interface XMLWriter {
    boolean write(Object toMarshall, String type, String rp, String fp, String ccy, String model, String sfx, String fileExtension);
    public boolean write(String xmlSource, File output);
}
