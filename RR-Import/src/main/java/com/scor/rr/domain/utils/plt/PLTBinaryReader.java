package com.scor.rr.domain.utils.plt;

import com.scor.rr.domain.entities.plt.ALMFFile;
import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.domain.PLTPeriod;
import com.scor.rr.importBatch.processing.domain.PLTResult;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossPeriod;
import com.scor.rr.importBatch.processing.treaty.loss.ScorPLTLossDataHeader;
import com.scor.rr.importBatch.processing.ylt.PLTReader;
import com.scor.rr.service.params.PLTFileParam;
import com.scor.rr.utils.ALMFUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * PLTBinaryReader
 *
 * @author HADDINI Zakariyae
 */
@Service
public class PLTBinaryReader implements PLTReader {

    private static Logger logger = LoggerFactory.getLogger(PLTBinaryReader.class);

    private String env;
    private String ihubPath;

    public PLTBinaryReader() {
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getIhubPath() {
        return ihubPath;
    }

    public void setIhubPath(String ihubPath) {
        this.ihubPath = ihubPath;
    }

    /**
     * {@inheritDoc}
     */
    public ScorPLTLossDataHeader readScorPLTLossDataHeader(ScorPLTHeader scorPLTHeader) {
        return instantiateScorPLTLossDataHeader(scorPLTHeader);
    }

    /**
     * {@inheritDoc}
     */
    public List<PLTLossData> readScorPLTLossData(ScorPLTHeader scorPLTHeader) {
        return instantiateScorPLTLossDataHeader(scorPLTHeader).getSortedLossData();
    }

    /**
     * {@inheritDoc}
     */
    public List<PLTLossData> readScorPLTLossData(String fileName, String filePath) {
        File file = getOrCreatePLTFile(fileName, filePath);
        List<PLTLossData> sortedPLTLossDatas = new ArrayList<>();

        if (ALMFUtils.isNotNull(file)) {
            logger.info("start reading bin file");

            int evCount = 0;
            try (FileChannel fc = new FileInputStream(file).getChannel()) {
                ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                ib.order(ByteOrder.LITTLE_ENDIAN);

                while (ib.hasRemaining()) {
                    evCount++;

                    sortedPLTLossDatas.add(instantiatePLTLossDatas(ib));
                }
            } catch (FileNotFoundException e) {
                logger.error("File Not Found Exception - filePath: {}, fileName: {}, Exception Message: {}",
                        file.getPath(), file.getName(), e.getMessage());
            } catch (IOException e) {
                logger.error("IO Exception - Exception: {}", e);
            }

            logger.info("total events: {}", evCount);
        }

        return sortedPLTLossDatas;
    }

    @Override
    public List<PLTLossData> readPLTLossDataV2(BinFile binFile) {
        List<PLTLossData> sortedList = new ArrayList<>();
        File file = new File(binFile.getPath(), binFile.getFileName());
        if (!file.exists()) {
            //fallback: a workaround for UAT/MAI after migration
            if (!"PROD".equalsIgnoreCase(env)) {
                if (binFile.getPath().contains("Pricing2")) {
                    String path = binFile.getPath().replace("Pricing2", "Pricing");
                    File tmpFile = new File(path, binFile.getFileName());
                    if (tmpFile.exists()) {
                        file = tmpFile;
                    }
                }
            }
        }
        int evCount = 0;
        try (FileChannel fc = new FileInputStream(file).getChannel()) {
            ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            //NOTE: JVM does not follow host endianness. By default is in big endian (motorola) whilst intel is little indian
            ib.order(ByteOrder.LITTLE_ENDIAN);
            while (ib.hasRemaining()) {
                evCount++;
                int period = ib.getInt();
                int eventId = ib.getInt();
                long eventDate = ib.getLong();
                int seq = ib.getShort();
                double exposure = ib.getFloat();
                double loss = ib.getFloat();
                PLTLossData lossData = new PLTLossData(eventId, eventDate, period, seq, exposure, loss);
                sortedList.add(lossData);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        //log.debug("Total events: {}", evCount);
        return sortedList;
    }

    /**
     * {@inheritDoc}
     */
    public PLTLoss readPLTFile(PLTFileParam pltFileParam) {
        return instantiatePLTLoss(pltFileParam);
    }

    /**
     * instantiate ScorPLTLossDataHeader
     *
     * @param scorPLTHeader
     * @return
     */
    private ScorPLTLossDataHeader instantiateScorPLTLossDataHeader(ScorPLTHeader scorPLTHeader) {
        ScorPLTLossDataHeader scorPLTLossDataHeader = new ScorPLTLossDataHeader();

        if (ALMFUtils.isNotNull(scorPLTHeader.getPltLossDataFilePath())
                && ALMFUtils.isNotNull(scorPLTHeader.getPltLossDataFileName())) {
            logger.info("start reading plt file");

            int evCounter = 0;
            int periodCouter = 0;

            File file = new File(scorPLTHeader.getPltLossDataFilePath(), scorPLTHeader.getPltLossDataFileName());

            try (FileChannel fc = new FileInputStream(file).getChannel()) {
                ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                ib.order(ByteOrder.LITTLE_ENDIAN);

                while (ib.hasRemaining())
                    instantiatePLTLossPeriodsAndTheirDatas(scorPLTLossDataHeader, ib, evCounter, periodCouter);
            } catch (FileNotFoundException e) {
                logger.error("File Not Found Exception - filePath: {}, fileName: {}, Exception: {}",
                        scorPLTHeader.getPltLossDataFilePath(), scorPLTHeader.getPltLossDataFileName(), e);
            } catch (IOException e) {
                logger.error("IO Exception - Exception: {}", e);
            }

            logger.info("total events {}, toal periods: {}", evCounter, periodCouter);
        }

        return scorPLTLossDataHeader;
    }

    /**
     * instantiate PLTLossPeriods and their PLTLossDatas
     *
     * @param scorPLTLossDataHeader
     * @param ib
     * @param evCounter
     * @param periodCouter
     */
    private void instantiatePLTLossPeriodsAndTheirDatas(ScorPLTLossDataHeader scorPLTLossDataHeader, ByteBuffer ib,
                                                        int evCounter, int periodCouter) {
        int period = ib.getInt();
        int nbEvents = ib.getShort();

        PLTLossPeriod pltLossPeriod = new PLTLossPeriod(period);

        while (nbEvents > 0) {
            evCounter++;
            nbEvents--;

            PLTLossData pltLossData = new PLTLossData(ib.getInt(), ib.getLong(), period, new Integer(ib.getShort()),
                    new Double(-1), new Double(ib.getFloat()));

            pltLossPeriod.addPLTLossData(pltLossData);
        }

        scorPLTLossDataHeader.addPLTLossPeriod(pltLossPeriod);

        periodCouter++;
    }

    /**
     * get file if exist otherwise create another one
     *
     * @return
     */
    private File getOrCreatePLTFile(String fileName, String filePath) {
        if (ALMFUtils.isNotNull(filePath) && ALMFUtils.isNotNull(fileName)) {
            File file = new File(filePath, fileName);

            if (!file.exists())
                if (!"PROD".equalsIgnoreCase(env))
                    if (filePath.contains("Pricing2"))
                        return new File(filePath.replace("Pricing2", "Pricing"), fileName);

            return file;
        }

        return null;
    }

    private PLTLossData instantiatePLTLossDatas(ByteBuffer ib) {
        return new PLTLossData(ib.getInt(), ib.getLong(), ib.getInt(), new Integer(ib.getShort()),
                new Double(ib.getFloat()), new Double(ib.getFloat()));
    }

    /**
     * instantiate PLTLoss
     *
     * @param pltFileParam
     * @return
     */
    private PLTLoss instantiatePLTLoss(PLTFileParam pltFileParam) {
        PLTLoss pltLoss = new PLTLoss(pltFileParam.getRegion(), pltFileParam.getPeril(), pltFileParam.getCcy(),
                pltFileParam.getFp());

        if (pltFileParam.isNotEmpty()) {
            ALMFFile dataFile = pltFileParam.getPlt().getDataFile();

            Path inFile = Paths.get(dataFile.getPath(), dataFile.getFileName());

            try (FileChannel fc = new FileInputStream(Paths.get(ihubPath).resolve(inFile).toFile()).getChannel()) {
                ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                ib.order(ByteOrder.LITTLE_ENDIAN);

                while (ib.hasRemaining())
                    instantiatePLTPeriodsAndTheirResults(pltLoss, ib);
            } catch (FileNotFoundException e) {
                logger.error("File Not Found Exception - filePath: {}, fileName: {}, Exception: {}",
                        Paths.get(ihubPath).resolve(inFile).toFile().getPath(),
                        Paths.get(ihubPath).resolve(inFile).toFile().getName(), e);
            } catch (IOException e) {
                logger.error("IO Exception - Exception: {}", e);
            }
        }

        return pltLoss;
    }

    /**
     * instantiate PLTPeriods and their PLTResults
     *
     * @param pltLoss
     * @param ib
     */
    private void instantiatePLTPeriodsAndTheirResults(PLTLoss pltLoss, ByteBuffer ib) {
        int period = ib.getInt();
        short nbEvents = ib.getShort();

        PLTPeriod pltPeriod = new PLTPeriod(period);

        while (nbEvents > 0) {
            nbEvents--;
            pltPeriod.addResult(
                    new PLTResult(ib.getInt(), new Integer(ib.getShort()), new Double(ib.getFloat()), ib.getLong()));
        }

        pltLoss.addPeriod(pltPeriod);
    }
}
