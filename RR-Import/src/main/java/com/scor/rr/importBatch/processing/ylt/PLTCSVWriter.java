package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import com.scor.rr.importBatch.processing.domain.PLTData;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.domain.PLTPeriod;
import com.scor.rr.importBatch.processing.domain.PLTResult;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.processing.treaty.loss.ScorPLTLossDataHeader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by U002629 on 19/02/2015.
 */
public class PLTCSVWriter extends BaseFileWriter implements PLTWriter {
    private static final Logger log= LoggerFactory.getLogger(PLTCSVWriter.class);
    private PLTData pltData;

    public PLTData getPltData() {
        return pltData;
    }

    public void setPltData(PLTData pltData) {
        this.pltData = pltData;
    }


    public PLTCSVWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    @Override
    public Boolean writeHeader() {
        return true;
    }

    @Override
    public Boolean batchWrite(){
        String identifier = null;
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier cannot be null");
        }
        for (String regionPeril : pltData.getRegionPerils()) {
            log.info("writing PLT csv file for "+regionPeril);
            Path pltOutDir = getDir();
            final Path fullPath = getIhubPath().resolve(pltOutDir);
            try {
                Files.createDirectories(fullPath);
            } catch (IOException e) {
                log.error("Exception: ", e);
                throw new RuntimeException("error creating paths "+fullPath, e);
            }
            PLTLoss lossData = pltData.getLossDataForRP(regionPeril);
            String pltFile = getFileName("PLT", regionPeril, lossData.getFinancialPerspective(), lossData.getCurrency(), "INTERNAL", "DAT", getFileExtension());
            pltFile = String.format(pltFile, identifier);
            BufferedWriter bw = null;
            try {
                bw = Files.newBufferedWriter(fullPath.resolve(pltFile), Charset.defaultCharset());//bw = new BufferedWriter(new FileWriter(yltFile));
                int cpt = 0;
                for (PLTPeriod period : pltData.getLossDataForRP(regionPeril).getPeriods()) {
                    for (PLTResult result : period.getResults()) {
                        cpt++;
                        bw.write(Integer.toString(period.getPeriod()));
                        bw.write(";");
                        bw.write(result.toString());
                        bw.newLine();

                    }
                    if(cpt==100) {
                        bw.flush();
                        cpt=0;
                    }
                }
                bw.flush();
            } catch (IOException e) {
                log.error("Exception: ",e);
            }finally {
                IOUtils.closeQuietly(bw);
            }
        }

        return true;
    }

    @Override
    public void writeLossData(PLTLoss lossData, Path pltOutDir, String regionPeril, String model, String identifier) {

    }

    @Override
    public void writeLossData(PLTLoss lossData, String regionPeril, String model, String identifier) {

    }

    @Override
    public BinFile writeScorPLTLossData(ScorPLTLossDataHeader scorPLTLossDataHeader, String filename) {
        return null;
    }

    @Override
    public BinFile writeScorPLTLossData(List<PLTLossData> sortedList, String filename) {
        return null;
    }

    @Override
    public void writePLTLossDataV2(List<PLTLossData> list, File file) {

    }

    @Override
    public void writePLTLossDataNonRMS(List<PLTLossData> list, File file) {

    }
}
