package com.scor.rr.importBatch.processing.ylt;

/**
 * Created by U002629 on 24/03/2015.
 */
public interface PEQTSelector {
    String getPEQTFile(String region, String peril);

    String getPEQTFile(String regionPeril);

    String getPEQTFunction(String dlmProfile);
    String getPETType(String dlmProfile);
    PET getPETInfo(String dlmProfile);

    class PET {
        final String fileName;
        final String functionType;
        final String petType;

        public PET(String fileName, String functionType, String petType) {
            this.fileName = fileName;
            this.functionType = functionType;
            this.petType = petType;
        }

        public String getFileName() {
            return fileName;
        }

        public String getFunctionType() {
            return functionType;
        }

        public String getPetType() {
            return petType;
        }
    }
}
