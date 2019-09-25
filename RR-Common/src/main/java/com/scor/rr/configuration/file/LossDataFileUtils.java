package com.scor.rr.configuration.file;

import com.scor.rr.configuration.ConverterType;
import com.scor.rr.configuration.TypeToConvert;
import com.scor.rr.configuration.utils.Status;
import com.scor.rr.domain.ImportedFileEntity;
import com.scor.rr.domain.MetadataHeaderSectionEntity;
import com.scor.rr.domain.MetadataHeaderSegmentEntity;
import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.repository.ImportedFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LossDataFileUtils {

    private static final Logger log = LoggerFactory.getLogger(LossDataFileUtils.class);

    private static final String START_MARKER = "LOSSFILE\t";
    private static final String END_MARKER = "#[HEADER-END]";
    private static final String PLT_DATA_EVENT_DATE = "MM-DD(N)";
    private static final String PLT_DATA_YEAR = "YEAR";
    private static final String PLT_DATA_EVENT_ID = "EVENTID";
    private static final String PLT_DATA_VALUE = "VALUE";
    private static final String PLT_DATA_MAX_EXPOSURE = "MAXEXPOSURE";

    static Pattern pattern = Pattern.compile("(\\d+)-(\\d+)\\((\\d+)\\)");

    public static List<ImportFilePLTData> getPltFromLossDataFile(String path) {
        List<ImportFilePLTData> pltDataList = null;
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            AtomicBoolean testEndMarker = new AtomicBoolean(false);
            AtomicBoolean testHeaderPlt = new AtomicBoolean(false);
            AtomicReference<Map<String, Integer>> pltHeaderOrder = new AtomicReference<>();
            pltDataList = new ArrayList<>();
            List<ImportFilePLTData> finalPltDataList = pltDataList;
            stream.forEach(s -> {
                        if (!StringUtils.isEmpty(s)) {
                            if (testHeaderPlt.get()) {
                                if (pltHeaderOrder.get() != null) {
                                    finalPltDataList.add(getImportFilePLTData(pltHeaderOrder.get(), s.split("\\s+")));
                                }
                            }
                            if (testEndMarker.get()) {
                                String[] pltline = s.split("\\s+");
                                pltHeaderOrder.set(retrieveFieldOrder(pltline));
                                testHeaderPlt.set(true);
                                testEndMarker.set(false);
                            }
                            if (s.equals(END_MARKER)) {
                                testEndMarker.set(true);
                            }
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pltDataList;
    }

    private static ImportFilePLTData getImportFilePLTData(Map<String, Integer> pltHeaderOrder, String[] pltline) {
        ImportFilePLTData importFilePLTData = new ImportFilePLTData();
        if (pltHeaderOrder.get(PLT_DATA_EVENT_ID) != null) {
            importFilePLTData.setEventId(Integer.parseInt(pltline[pltHeaderOrder.get(PLT_DATA_EVENT_ID)]));
        }
        if (pltHeaderOrder.get(PLT_DATA_YEAR) != null) {
            importFilePLTData.setYear(Integer.parseInt(pltline[pltHeaderOrder.get(PLT_DATA_YEAR)]));
        }
        if (pltline[pltHeaderOrder.get(PLT_DATA_VALUE)] != null) {
            importFilePLTData.setValue(Float.parseFloat(pltline[pltHeaderOrder.get(PLT_DATA_VALUE)]));
        }
        if (pltHeaderOrder.get(PLT_DATA_MAX_EXPOSURE) != null) {
            importFilePLTData.setMaxExposure(Float.parseFloat(pltline[pltHeaderOrder.get(PLT_DATA_MAX_EXPOSURE)]));
        } else {
            importFilePLTData.setMaxExposure(importFilePLTData.getValue());
        }
        if (pltHeaderOrder.get(PLT_DATA_EVENT_DATE) != null) {
            String eventDate = pltline[pltHeaderOrder.get(PLT_DATA_EVENT_DATE)];
            importFilePLTData.setEventDate(eventDate);
            Matcher matcher = pattern.matcher(eventDate);
            if (matcher.find()) {
                if (matcher.group(1) != null) {
                    importFilePLTData.setMonth(Integer.parseInt(matcher.group(1)));
                }
                if (matcher.group(2) != null) {
                    importFilePLTData.setDay(Integer.parseInt(matcher.group(2)));
                }
                if (matcher.group(3) != null) {
                    importFilePLTData.setRepetition(Integer.parseInt(matcher.group(3)));
                }
            }
        }
        return importFilePLTData;
    }

    private static Map<String, Integer> retrieveFieldOrder(String[] headings) {
        Map<String, Integer> fieldOrder = new HashMap<>();
        for (int i = 0; i < headings.length; i++) {
            switch (headings[i].toUpperCase()) {
                case PLT_DATA_EVENT_DATE:
                    fieldOrder.put(PLT_DATA_EVENT_DATE, i);
                    break;
                case PLT_DATA_YEAR:
                    fieldOrder.put(PLT_DATA_YEAR, i);
                    break;
                case PLT_DATA_EVENT_ID:
                    fieldOrder.put(PLT_DATA_EVENT_ID, i);
                    break;
                case PLT_DATA_VALUE:
                    fieldOrder.put(PLT_DATA_VALUE, i);
                    break;
                case PLT_DATA_MAX_EXPOSURE:
                    fieldOrder.put(PLT_DATA_MAX_EXPOSURE, i);
                    break;
                default:
                    log.debug("Field {} unknown", headings[i]);
                    return null;
            }
        }
        return fieldOrder;
    }

    public static boolean verifyFile(List<MetadataHeaderSectionEntity> metadataHeaders, List<MetadataHeaderSegmentEntity> headerSegments, String path, ImportedFileRepository importedFileRepository) {
        File file = new File(path);
        ArrayList<String> key = new ArrayList<>();
        ArrayList<String> keyHeader = new ArrayList<>();
        TypeToConvert type = new TypeToConvert();
        List<Field> fs = Arrays.asList(type.getClass().getFields());
        Supplier<Stream<String>> stream = () -> {
            try {
                return Files.lines(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
        boolean testHeaderExists = false;
        for (MetadataHeaderSegmentEntity headerSegment : headerSegments) {
            boolean testEndMarker = false;
            boolean testStartMarker = false;
            boolean found = false;
            for (String s : stream.get().collect(Collectors.toList())) {
                if (s.equals(END_MARKER)) {
                    testEndMarker = true;
                    if (!found) {
                        importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Header not exist in file "+headerSegment.getMetadataAttribute()));
                        log.info("element not found in file {}", headerSegment.getMetadataAttribute());
                        return false;
                    }
                    testHeaderExists = true;
                }
                if (!testEndMarker && testStartMarker) {
                    String[] pltline = s.split("\t");
                    if (pltline.length <= 2) {
                        if (!keyHeader.contains(pltline[0]) && pltline[0].equals(headerSegment.getMetadataAttribute())) {
                            keyHeader.add(headerSegment.getMetadataAttribute());
                                found = true;
                        }
                    }
                }
                if (s.equals(START_MARKER)) {
                    testStartMarker = true;
                }
            }
        }
        if (testHeaderExists) {
            for (MetadataHeaderSectionEntity metadataHeaderSectionEntity : metadataHeaders) {
                if (metadataHeaderSectionEntity.getMandatory().equalsIgnoreCase("Y")) {
                    boolean testEndMarker = false;
                    boolean testStartMarker = false;
                    boolean found = false;
                    String firstLigne = stream.get().findFirst().orElse(null);
                    if (firstLigne != null && firstLigne.equals(START_MARKER)) {
                        for (String s : stream.get().collect(Collectors.toList())) {
                            if (s.equals(END_MARKER)) {
                                testEndMarker = true;
                                if (!found) {
                                    log.info("element not found in file {}", metadataHeaderSectionEntity.getMetadataAttribute());
                                    importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"element not found in file : "+metadataHeaderSectionEntity.getMetadataAttribute()));
                                    return false;
                                }
                            }
                            if (!testEndMarker && testStartMarker) {
                                String[] pltline = s.split("\t");
                                if (pltline.length == 2) {
                                    if (pltline[0].equals(metadataHeaderSectionEntity.getMetadataAttribute())) {
                                        if (!key.contains(pltline[0])) {
                                            Field fieldTemp = fs.stream().filter(field -> field.getType().getSimpleName().equals(metadataHeaderSectionEntity.getDataType()))
                                                    .findFirst()
                                                    .orElse(null);
                                            ConverterType converterType = new ConverterType(fieldTemp.getType());
                                            converterType.convert(pltline[1], metadataHeaderSectionEntity);
                                            if (converterType.value != null) {
                                                if (!converterType.value.getClass().equals(fieldTemp.getType())) {
                                                    importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Data Type not conform require "+metadataHeaderSectionEntity.getMetadataAttribute()));
                                                    return false;
                                                } else {
                                                    found = true;
                                                    key.add(metadataHeaderSectionEntity.getMetadataAttribute());
                                                }
                                            } else {
                                                importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Data Type not conform require "+metadataHeaderSectionEntity.getMetadataAttribute()));
                                                return false;
                                            }
                                        } else {
                                            log.info("duplicate metadata {}", metadataHeaderSectionEntity.getMetadataAttribute());
                                            importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"duplicate metadata "+metadataHeaderSectionEntity.getMetadataAttribute()));
                                            return false;
                                        }
                                    }
                                } else {
                                    importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"File not right splitted"));
                                    return false;
                                }
                            }
                            if (s.equals(START_MARKER)) {
                                testStartMarker = true;
                            }
                        }
                    }
                }
            }
        } else {
            importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Header file missing"));
            return false;
        }
        importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.SUCCESS.name(),null));
        return true;
    }
}
