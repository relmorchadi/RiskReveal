package com.scor.rr.importBatch.processing.treaty.services;

import com.google.common.collect.Ordering;
import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.references.plt.TTFinancialPerspective;
import com.scor.rr.domain.enums.AdjustmentType;
import com.scor.rr.domain.enums.PLTSimulationPeriod;
import com.scor.rr.domain.enums.StatisticMetric;
import com.scor.rr.domain.utils.plt.PLTEPCurve;
import com.scor.rr.domain.utils.plt.PLTSummaryStatistic;
import com.scor.rr.importBatch.processing.io.CSVWriter;
import com.scor.rr.importBatch.processing.io.CSVWriterImpl;
import com.scor.rr.importBatch.processing.treaty.loss.ELTLossnBetaFunction;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossPeriod;
import com.scor.rr.importBatch.processing.treaty.loss.ScorPLTLossDataHeader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

//import com.scor.almf.treaty.cdm.domain.dss.ImportDecision;
//import com.scor.almf.treaty.cdm.repository.dss.ImportDecisionRepository;

public class TransformationUtils {

    private static final int PERIOD_THRESHOLD = 100000;

    private static final Logger logger = LoggerFactory.getLogger(TransformationUtils.class);

    /**
     * 100k-period sampling of 800k-period ScorPLTHeader
     *
     * @param scorPLTHeader
     * @return
     */
    public static ScorPLTHeader subScorPLTHeader(ScorPLTHeader scorPLTHeader) {
        ScorPLTHeader clone = new ScorPLTHeader();

        clone.setPeqtFile(scorPLTHeader.getPeqtFile());
        clone.setPltLossDataFile(null);
//        clone.setPLTEPHeaders(null);
        clone.setPltStatisticList(null);

        //clone.setPltGrouping(scorPLTHeader.getPltGrouping());
        //clone.setPltInuring(scorPLTHeader.getPltInuring());
        clone.setPltStatus(scorPLTHeader.getPltStatus());
        //clone.setInuringPackageDefinition(scorPLTHeader.getInuringPackageDefinition());
        clone.setPltSimulationPeriods(PLTSimulationPeriod.SIM100K.getCode());
        clone.setPltType(scorPLTHeader.getPltType());
        clone.setThreadName(scorPLTHeader.getThreadName());
        clone.setUdName(scorPLTHeader.getUdName());

        clone.setProject(scorPLTHeader.getProject());
//        clone.setEltHeader(scorPLTHeader.getEltHeader());
        clone.setRrLossTableId(scorPLTHeader.getRrLossTableId());

        clone.setCurrency(scorPLTHeader.getCurrency());
//        clone.setRepresentationDataset(scorPLTHeader.getRepresentationDataset());
        clone.setRrRepresentationDatasetId(scorPLTHeader.getRrRepresentationDatasetId());

        clone.setTargetRAP(scorPLTHeader.getTargetRAP());
        clone.setRegionPeril(scorPLTHeader.getRegionPeril());
//        clone.setSourceResult(scorPLTHeader.getSourceResult());
        clone.setRrAnalysisId(scorPLTHeader.getRrAnalysisId());
        clone.setRrRepresentationDatasetId(scorPLTHeader.getRrRepresentationDatasetId());

        clone.setFinancialPerspective(scorPLTHeader.getFinancialPerspective());

        //clone.setAdjustmentStructure(scorPLTHeader.getAdjustmentStructure());
        clone.setCatAnalysisDefinition(scorPLTHeader.getCatAnalysisDefinition());

        //clone.setSourcePLTHeader(Arrays.asList(scorPLTHeader));
        clone.setSystemShortName(scorPLTHeader.getSystemShortName());
        clone.setUserShortName(scorPLTHeader.getUserShortName());
        clone.setTags(scorPLTHeader.getTags());

        clone.setCreationDate(scorPLTHeader.getCreationDate());
        clone.setXActPublicationDate(null);
        clone.setXActAvailable(scorPLTHeader.getXActAvailable());
        clone.setXActUsed(scorPLTHeader.getXActUsed());
        clone.setXActModelVersion(scorPLTHeader.getXActModelVersion());

        clone.setGeneratedFromDefaultAdjustement(scorPLTHeader.getGeneratedFromDefaultAdjustement());
        clone.setUserSelectedGrain(scorPLTHeader.getUserSelectedGrain());
        clone.setExportedDPM(false);
        clone.setRmsSimulationSet(scorPLTHeader.getRmsSimulationSet());

        clone.setGeoCode(scorPLTHeader.getGeoCode());
        clone.setGeoDescription(scorPLTHeader.getGeoDescription());
        clone.setPerilCode(scorPLTHeader.getPerilCode());

        clone.setEngineType(scorPLTHeader.getEngineType());
        clone.setInstanceId(scorPLTHeader.getInstanceId());
        clone.setImportSequence(scorPLTHeader.getImportSequence());

        clone.setTruncationCurrency(scorPLTHeader.getTruncationCurrency());
        clone.setTruncationThreshold(scorPLTHeader.getTruncationThreshold());
        clone.setTruncationThresholdEur(scorPLTHeader.getTruncationThresholdEur());

        return clone;
    }

    /**
     * 100k-period sampling of 800k-period ScorPLTHeader
     *
     * @param pltLossDataHeader
     * @return
     */
    public static ScorPLTLossDataHeader subScorPLTLossDataHeader(ScorPLTLossDataHeader pltLossDataHeader) {
        List<PLTLossPeriod> pltLossPeriods = pltLossDataHeader.getPltLossPeriods();

        // TODO - clone available common properties
        if (pltLossPeriods == null || pltLossPeriods.size() == 0) {
            throw new IllegalStateException();
        }
        Collections.sort(pltLossPeriods);

        List<PLTLossPeriod> excerpt = new ArrayList<>();
        for (PLTLossPeriod pltLossPeriod : pltLossPeriods) {
            if (pltLossPeriod.getSimPeriod() > PERIOD_THRESHOLD) {
                break;
            }
            excerpt.add(pltLossPeriod);
        }
        logger.info("Extracting 100k PLT (actually {} periods) of 800k PLT (actually {} periods)", excerpt.size(), pltLossPeriods.size());
        return new ScorPLTLossDataHeader(excerpt);
//        if (pltLossPeriods.size() <= PERIOD_THRESHOLD) {
//            logger.info("Referencing to the same PLT data having <100k periods (actual, {} periods)", pltLossPeriods.size());
//            return pltLossDataHeader;
//        }
//        ScorPLTLossDataHeader copyCat = new ScorPLTLossDataHeader();
//
//        List<PLTLossPeriod> excerpt = pltLossPeriods.subList(0, PERIOD_THRESHOLD);
//        copyCat.addPLTLossPeriods(excerpt);
//        logger.info("Referencing to 100k periods (actually, {} periods) of 800k PLT data (actual, {} periods)", excerpt.size(), pltLossPeriods.size());
//        return copyCat;
    }

//    public static ScorPLTLossDataHeader copy(ScorPLTLossDataHeader scorPLTLossDataHeader) {
//        logger.info("Cloning PLT data");
//        List<PLTLossPeriod> pltLossPeriods = scorPLTLossDataHeader.getPltLossPeriods();
//
//        // TODO - clone available common properties
//
//        if (pltLossPeriods == null || pltLossPeriods.size() == 0) {
//            throw new IllegalStateException();
//        }
//        ScorPLTLossDataHeader copyCat = new ScorPLTLossDataHeader();
//        for (PLTLossPeriod pltLossPeriod : pltLossPeriods) {
//            copyCat.addPLTLossPeriod(TransformationUtils.copy(pltLossPeriod));
//        }
//        return copyCat;
//    }
//
//    public static PLTLossPeriod copy(PLTLossPeriod pltLossPeriod) {
//        PLTLossPeriod copyCat = new PLTLossPeriod(pltLossPeriod.getSimPeriod());
//        for (PLTLossData pltLossData : pltLossPeriod.getPltLossDataByPeriods()) {
//            copyCat.addPLTLossData(TransformationUtils.copy(pltLossData));
//        }
//        return copyCat;
//    }

//    @Deprecated
//    public static PLTLossData copy(PLTLossData pltLossData) {
//        PLTLossData copyCat = new PLTLossData(pltLossData.getRank(), pltLossData.getEventId(),
//                pltLossData.getEventDate(),
//                pltLossData.getSimPeriod(),
//                pltLossData.getSeq(),
//                pltLossData.getMaxExposure(),
//                pltLossData.getLoss());
//        return copyCat;
//    }


    public static BinFile makeScorPLTLossDataFile(BinFile clonedPLTLossFile, String newPltId) {
        if (clonedPLTLossFile == null || !clonedPLTLossFile.isValid()) {
            return null;
        }
        String pattern = "(.+)_ID-(.+)_Job-(.+)";
        String fileName = clonedPLTLossFile.getFileName();
        String scorPLTLossDataFileName = fileName.replaceAll(pattern, "$1" + "_ID-" + newPltId + "_Job-" + "$3");
        return new BinFile(scorPLTLossDataFileName, clonedPLTLossFile.getPath(), null);
    }

    public static BinFile makeScorPLTLossDataFile(BinFile clonedPLTLossFile, String newPltId, String newNodeId) {
        if (clonedPLTLossFile == null || !clonedPLTLossFile.isValid()) {
            return null;
        }
        String patternId = "(.+)_ID-(.+)_Job-(.+)";
        String fileName = clonedPLTLossFile.getFileName();
        String scorPLTLossDataFileName = fileName.replaceAll(patternId, "$1" + "_ID-" + newPltId + "_Job-" + "$3");
        String patternNode = "(.+)_Job-(.+)_(.+).bin";
        scorPLTLossDataFileName = scorPLTLossDataFileName.replaceAll(patternNode, "$1" + "_Job-" + "$2" + "_" + newNodeId + ".bin");
        return new BinFile(scorPLTLossDataFileName, clonedPLTLossFile.getPath(), null);
    }

    public static long copyFile(File sourceFile, File destFile) throws IOException {
        FileChannel source = null;
        FileChannel destination = null;
        long size = 0;
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            logger.debug("source file {} dest file {}", sourceFile, destFile);
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            size = source.size();
        } catch (Exception e) {
            logger.debug("Error in copyFile {}", e);
            throw e;
        } finally {
            try {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
                logger.debug("Channel closed with size {}", size);
            } catch (Exception ex) {
                logger.debug("Closed channel exception {}", ex.getMessage());
            }
        }
        return size;
    }

    public static <K, V> SortedMap<K, V> newReverseMap() {
        return new TreeMap<K, V>(Collections.reverseOrder());
    }

    @Deprecated
    public static <T> SortedSet<T> newReverseSet() {
        return new TreeSet<T>(Collections.reverseOrder());
    }

    public static <T> void sortReverse(List<T> list) {
        Collections.sort(list, Collections.<T>reverseOrder());
    }

    public static <T extends Comparable> Boolean isSortedReversely(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return Ordering.natural().reverse().isOrdered(list);
    }

    public static <T extends Comparable> Boolean isSorted(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        return Ordering.natural().isOrdered(list);
    }


    public static List<Double> lerp(List<Double> inputs, List<Double> ascKeys, List<Double> values) {
        if (!isSorted(ascKeys)) {
            throw new IllegalStateException("Input ascKeys not sorted ascendingly");
        }
        if (ascKeys.size() != values.size()) {
            throw new IllegalStateException("Input ascKeys and values not having same size");
        }
        List<Double> out = new ArrayList<>();
        for (Double input : inputs) {
            int idx = Collections.binarySearch(ascKeys, input);
            double interped;
            if (idx == -1) { // under the referenced range
                interped = values.get(0);
            } else if (idx == -1 - ascKeys.size()) { // beyond the referenced range
                interped = values.get(ascKeys.size() - 1);
            } else if (idx >= 0) { // it matches one endpoint in RP input list
                interped = values.get(idx);
            } else { // it falls into an interval
                int lowIdx = Math.abs(idx + 2);
                int highIdx = Math.abs(idx + 1);
                Double loKey = ascKeys.get(lowIdx);
                Double hiKey = ascKeys.get(highIdx);
                Double loValue = values.get(lowIdx);
                Double hiValue = values.get(highIdx);

                interped = (input - loKey) * (hiValue - loValue) / (hiKey - loKey) + loValue;
            }
            out.add(interped);
        }
        return out;
    }

    // File generators

    public static void logForVerificationELTPLT(String peqtFile, ELTLossnBetaFunction eltLossnBetaFunction, PLTLossData lossData, double quantile, Double eventLoss) {
        logger.debug("{}\n{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}",
                peqtFile,
                lossData.getSimPeriod(),
                lossData.getEventId(),
                eltLossnBetaFunction.getRate(),
                eltLossnBetaFunction.getLoss(),
                eltLossnBetaFunction.getStdDevC(),
                eltLossnBetaFunction.getStdDevI(),
                eltLossnBetaFunction.getStdDevUSq(),
                eltLossnBetaFunction.getStdDevUSq(),
                eltLossnBetaFunction.getExposureValue(),
                quantile,
                eventLoss);
    }

    // TEST VALIDATION
    public static void writeEPCurve(Map<StatisticMetric, List<PLTEPCurve>> metricToCurve, String path, String adjustmentType, String adjPLTFileName) {
        final String DELIMITER = ";";
        List<String> rows = new ArrayList<>();
        StringBuilder firstBuilder = new StringBuilder();
        firstBuilder.append("returnPeriod").append(DELIMITER)
                .append("probability").append(DELIMITER)
                .append("loss").append(DELIMITER)
                .append("(d) probability").append(DELIMITER)
                .append("(d) loss");
        rows.add(firstBuilder.toString());

        CSVWriter csvWriter = new CSVWriterImpl();

        for (Map.Entry<StatisticMetric, List<PLTEPCurve>> entry : metricToCurve.entrySet()) {
            for (PLTEPCurve pltepCurve : entry.getValue()) {
                StringBuilder builder = new StringBuilder();
                builder.append(pltepCurve.getReturnPeriod()).append(DELIMITER)
                        .append(pltepCurve.getExceedanceProbability().floatValue()).append(DELIMITER)
                        .append(pltepCurve.getLossAmount().floatValue()).append(DELIMITER)
                        .append(pltepCurve.getExceedanceProbability()).append(DELIMITER)
                        .append(pltepCurve.getLossAmount());
                rows.add(builder.toString());
            }
            csvWriter.writeCSV(rows, path, makeEPCurveFileName(adjustmentType, entry.getKey(), adjPLTFileName));
            rows.clear();
            rows.add(firstBuilder.toString());
        }
    }

    // TEST VALIDATION
    public static void writeSummaryStats(PLTSummaryStatistic summaryStatistics, String path, String adjPLTFileName) {
        final String DELIMITER = ";";
        List<String> rows = new ArrayList<>();
        StringBuilder firstBuilder = new StringBuilder();
        firstBuilder.append("getPurePremium").append(DELIMITER)
                .append("getStandardDeviation").append(DELIMITER)
                .append("getCov").append(DELIMITER)
                .append("(d) getPurePremium").append(DELIMITER)
                .append("(d) getStandardDeviation").append(DELIMITER)
                .append("(d) getCov");
        rows.add(firstBuilder.toString());

        CSVWriter csvWriter = new CSVWriterImpl();

        StringBuilder builder = new StringBuilder();
        builder.append(summaryStatistics.getPurePremium().floatValue()).append(DELIMITER)
                .append(summaryStatistics.getStandardDeviation().floatValue()).append(DELIMITER)
                .append(summaryStatistics.getCov().floatValue()).append(DELIMITER)
                .append(summaryStatistics.getPurePremium()).append(DELIMITER)
                .append(summaryStatistics.getStandardDeviation()).append(DELIMITER)
                .append(summaryStatistics.getCov());
        rows.add(builder.toString());
        csvWriter.writeCSV(rows, path, makeStatFileName(adjPLTFileName));
        rows.clear();
    }

    public static void writePLT(List<PLTLossData> sortedLossData, String path, String filename) {
        SortedMap<Integer, PLTLossPeriod> periodToLossPeriod = TransformationUtils.newReverseMap();
        for (PLTLossData pltLossData : sortedLossData) {
            int period = pltLossData.getSimPeriod();
            if (!periodToLossPeriod.containsKey(pltLossData.getSimPeriod())) {
                periodToLossPeriod.put(period, new PLTLossPeriod(period));
            }
            periodToLossPeriod.get(period).addPLTLossData(pltLossData);
        }

        ScorPLTLossDataHeader scorPLTLossDataHeader = new ScorPLTLossDataHeader();
        scorPLTLossDataHeader.setPltLossPeriods(new ArrayList<PLTLossPeriod>(periodToLossPeriod.values()));

        final String DELIMITER = ";";
        List<String> rows = new ArrayList<>();
        StringBuilder firstBuilder = new StringBuilder();
        firstBuilder.append("simPeriod").append(DELIMITER)
                .append("nEvents").append(DELIMITER)
                .append("eventId").append(DELIMITER)
                .append("seq").append(DELIMITER)
                .append("eventDate").append(DELIMITER)
                .append("loss").append(DELIMITER)
                .append("(d) loss");
        rows.add(firstBuilder.toString());

        for (PLTLossPeriod pltLossPeriod : scorPLTLossDataHeader.getPltLossPeriods()) {
            short nEvents = (short) pltLossPeriod.getPltLossDataByPeriods().size();
            for (PLTLossData pltLossData : pltLossPeriod.getPltLossDataByPeriods()) {
                StringBuilder builder = new StringBuilder();
                builder.append(pltLossData.getSimPeriod()).append(DELIMITER)
                        .append(nEvents).append(DELIMITER)
                        .append(pltLossData.getEventId()).append(DELIMITER)
                        .append((short) pltLossData.getSeq()).append(DELIMITER)
                        .append(pltLossData.getEventDate()).append(DELIMITER)
                        .append((float) pltLossData.getLoss()).append(DELIMITER)
                        .append(pltLossData.getLoss());
                rows.add(builder.toString());
            }
        }

        CSVWriter csvWriter = new CSVWriterImpl();
        csvWriter.writeCSV(rows, path, filename + ".csv");
    }

    public static void writeELT(List<ELTLossnBetaFunction> eltData, String path, String filename) {
        final String DELIMITER = ";";

        List<String> rows = new ArrayList<>();
        StringBuilder firstBuilder = new StringBuilder();
        firstBuilder.append("getEventId").append(DELIMITER)
                .append("getLoss").append(DELIMITER)
                .append("getExposureValue").append(DELIMITER)
                .append("getStdDevC").append(DELIMITER)
                .append("getStdDevUSq").append(DELIMITER)
                .append("getRate").append(DELIMITER)
                .append("(d) getEventId").append(DELIMITER)
                .append("(d) getLoss").append(DELIMITER)
                .append("(d) getExposureValue").append(DELIMITER)
                .append("(d) getStdDevC").append(DELIMITER)
                .append("(d) getStdDevUSq").append(DELIMITER)
                .append("(d) getRate");
        rows.add(firstBuilder.toString());

        for (ELTLossnBetaFunction eltLossnBetaFunction : eltData) {
            StringBuilder builder = new StringBuilder();
            builder.append(eltLossnBetaFunction.getEventId()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getLoss().floatValue()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getExposureValue().floatValue()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getStdDevC().floatValue()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getStdDevI().floatValue()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getRate().floatValue()).append(DELIMITER)

                    .append(eltLossnBetaFunction.getEventId()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getLoss()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getExposureValue()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getStdDevC()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getStdDevI()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getRate());
            rows.add(builder.toString());

        }
        CSVWriter csvWriter = new CSVWriterImpl();
        csvWriter.writeCSV(rows, path, filename);
    }


    public static String makeEPCurveFileName(String adjustmentType, StatisticMetric statisticMetric, String adjPLTFileName) {
        final String SEPARATOR = "_";
        String type = null;
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(adjustmentType, AdjustmentType.NONE.toString())) {
            type = "None";
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(adjustmentType, AdjustmentType.RP_BANDING_EEF.toString())) {
            type = "RP_Banding_EEF";
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(adjustmentType, AdjustmentType.LINEAR.toString())) {
            type = "Linear";
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(adjustmentType, AdjustmentType.RP_BANDING_OEP.toString())) {
            type = "RP_Banding_OEP";
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(adjustmentType, AdjustmentType.EV_SPEC.toString())) {
            type = "Ev_Spec";
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(adjustmentType, AdjustmentType.FREQUENCY_EEF.toString())) {
            type = "Frequency_EEF";
        }
        return type + SEPARATOR + "epCurve" + SEPARATOR + statisticMetric.toString() + SEPARATOR + adjPLTFileName + ".csv";
    }

    public static String makeStatFileName(String adjPLTFileName) {
        final String SEPARATOR = "_";
        return "sumstat" + SEPARATOR + SEPARATOR + adjPLTFileName + ".csv";
    }

    public static TTFinancialPerspective makeFinancialPerspectives(String fpCode, Integer ttId) {
        TTFinancialPerspective fp = new TTFinancialPerspective();
        fp.setTtFinancialPerspectiveId("FP_RL__" + fpCode);
        fp.setCode(fpCode);
        fp.setModellingVendor(null);
        fp.setModellingSystemVersion(null);
        fp.setModellingSystem(null);
        fp.setUserSelectableForElt(false);
        fp.setDescription(null);
        fp.setTreatyLabel(null);
        fp.setTreatyId(ttId);
        return fp;
    }

    public static List<String> parseXMLForModelingOptions(String xml) throws IOException, DocumentException {
        List<String> options = new ArrayList<>();
        SAXReader reader = new SAXReader();
        InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        Document document = reader.read(is);

        Element root = document.getRootElement();

        List nodes = document.selectNodes("//RmsDlmProfile/ModellingOptions/*");
        for (Iterator iter = nodes.iterator(); iter.hasNext(); ) {
            Node node = (Node) iter.next();
            Element element = (Element) node;
            String option = element.attributeValue("Code");
            options.add(option);
            logger.info("path {}, name {}, text {}, value {}, option", node.getPath(), node.getName(), node.getText(), node.getStringValue(), option);
        }
        return options;
    }

    /**
     * Remark: if sourceResult is LazyLoading object, sourceResult.setXXX(xxx) then sourceResultRepo.save(sourceResult) fails
     */
//    public static void setImportDecisionError(ImportDecisionRepository importDecisionRepository, ImportDecision srImport, String errorMsg) {
//        srImport.setImportStatus(ImportDecision.ImportStatus.Error);
//        srImport.addErrorMessage(errorMsg);
//        importDecisionRepository.save(srImport);
//    }

}
