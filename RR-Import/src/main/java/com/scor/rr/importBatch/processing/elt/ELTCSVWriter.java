package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import com.scor.rr.importBatch.processing.domain.ELTData;
import com.scor.rr.importBatch.processing.domain.ELTLoss;
import com.scor.rr.importBatch.processing.domain.loss.EventLoss;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by U002629 on 19/02/2015.
 */
public class ELTCSVWriter extends BaseFileWriter implements ELTWriter {
    private static final Logger log= LoggerFactory.getLogger(ELTCSVWriter.class);
    private ELTData eltData;

    public ELTCSVWriter() {
    }

    public ELTCSVWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    public ELTData getEltData() {
        return eltData;
    }

    public void setEltData(ELTData eltData) {
        this.eltData = eltData;
    }


    @Override
    public Boolean writeHeader() {
        return true;
    }

    @Override
    public Boolean batchWrite(){
        Path eltOutDir = getDir();
        for (String regionPeril : eltData.getRegionPerils()) {
            log.info("writing ELT CSV file for"+regionPeril);
            final Path fullPath = getIhubPath().resolve(eltOutDir);
            try {
                Files.createDirectories(fullPath);
            } catch (IOException e) {
                log.error("Exception: ", e);
                throw new RuntimeException("error creating paths "+fullPath, e);
            }
            ELTLoss lossData = eltData.getLossDataForRp(regionPeril);
            String eltFileName = getFileName("ELT", lossData.getDlmProfileName(), lossData.getFinancialPerspective(), lossData.getCurrency(), "MODEL", "DAT", getFileExtension());
            log.info("writing "+eltOutDir+"/"+eltFileName);
            BufferedWriter bw = null;
            try {
                bw = Files.newBufferedWriter(fullPath.resolve(eltFileName), Charset.defaultCharset());

                int cpt=0;
                for (EventLoss loss : lossData.getEventLosses()) {
                    cpt++;
                    StringBuilder sb = new StringBuilder();
                    sb.append(loss.getEventId()).append(";")
                    .append(loss.getMeanLoss()).append(";")
                    .append(loss.getExposureValue()).append(";")
                    .append(loss.getStdDevC()).append(";")
                    .append(loss.getStdDevU()).append(";")
                    .append(loss.getFreq());
                    bw.write(sb.toString());
                    bw.newLine();
                    if(cpt==100){
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

}
