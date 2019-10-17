package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.plt.ALMFFile;
import com.scor.rr.domain.entities.plt.ModelRAP;
import com.scor.rr.domain.entities.plt.ModelRAPSource;
import com.scor.rr.domain.utils.plt.PLT;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import com.scor.rr.importBatch.processing.domain.PLTData;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by U002629 on 14/04/2015.
 */
public class PLTSaver extends BaseFileWriter implements PLTHandler {
    private static final Logger log = LoggerFactory.getLogger(PLTSaver.class);

    private PLTData pltData;

    private TransformationPackage transformationPackage;

    public PLTSaver() {
    }

    public PLTSaver(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    public PLTData getPltData() {
        return pltData;
    }

    public void setPltData(PLTData pltData) {
        this.pltData = pltData;
    }

    public TransformationPackage getTransformationPackage() {
        return transformationPackage;
    }

    public void setTransformationPackage(TransformationPackage transformationPackage) {
        this.transformationPackage = transformationPackage;
    }

    @Override
    public Boolean handle() {
        log.info("No plt save implementation");
        // No implementation
        return true;
    }

    public Boolean handleFAC() {
        String pureIdentifier = "";
        Path pltOutDir = getDir();
        for (String regionPeril : pltData.getRegionPerils()) {
            log.info("begin saving PLT for " + regionPeril);

            // TREATY - dont care about ModellingResult
            // final Map<String, ModellingResult> resultsByRegionPeril = getModellingResultsByRegionPeril();
            PLTLoss lossData = pltData.getLossDataForRP(regionPeril);
            // final ModellingResult result = resultsByRegionPeril.get(regionPeril);
            // result.setPurePLT(buildPLT(pltOutDir, regionPeril, lossData, result.getModelRAPSource(), result.getModelRAP(), pureIdentifier));
            log.info("finished saving PLT " + regionPeril);
            log.info("finished saving PLT for " + regionPeril);
            addMessage("PLT SAVE", "PLT saved OK for " + regionPeril);
        }
        return true;
    }

    public PLT buildPLT(String regionPeril, PLTLoss lossData, ModelRAPSource rapSource, ModelRAP rapTarget, String identifier) {
        return buildPLT(getDir(), regionPeril, lossData, rapSource, rapTarget, identifier);
    }

    public PLT buildPLT(String regionPeril, PLTLoss lossData, ModelRAPSource rapSource, ModelRAP rapTarget, String model, String identifier) {
        return buildPLT(getDir(), regionPeril, lossData, rapSource, rapTarget, model, identifier);
    }

    public PLT buildPLT(Path pltOutDir, String regionPeril, PLTLoss lossData, ModelRAPSource rapSource, ModelRAP rapTarget, String model, String identifier) {
        PLT plt = new PLT();
        plt.setModelRAPSource(rapSource);
        plt.setModelRAP(rapTarget);
        String fileName = getFileName("PLT", regionPeril, lossData.getFinancialPerspective(), lossData.getCurrency(), model, "DAT", getFileExtension());
        fileName = String.format(fileName, identifier);
        final Path fullPath = getIhubPath().resolve(pltOutDir);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths " + fullPath, e);
        }

        plt.setDataFile(new ALMFFile(fileName, pltOutDir.toString(), fullPath.resolve(fileName).toString()));
        return plt;
    }

    public PLT buildPLT(Path pltOutDir, String regionPeril, PLTLoss lossData, ModelRAPSource rapSource, ModelRAP rapTarget, String identifier) {
        return buildPLT(pltOutDir, regionPeril, lossData, rapSource, rapTarget, "INTERNAL", identifier);
    }

}
