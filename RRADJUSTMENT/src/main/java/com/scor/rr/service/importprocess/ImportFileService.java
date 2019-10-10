package com.scor.rr.service.importprocess;

import com.scor.rr.configuration.ConverterType;
import com.scor.rr.configuration.TypeToConvert;
import com.scor.rr.configuration.file.CopyFile;
import com.scor.rr.configuration.utils.LossDataFileUtils;
import com.scor.rr.configuration.utils.Status;
import com.scor.rr.domain.importFile.*;
import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.repository.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

@Service
public class ImportFileService {

    @Autowired
    MetadataHeaderSectionRepository metadataHeader;

    @Autowired
    FileBasedImportProducerRepository headerSegment;

    @Autowired
    ImportedFileRepository importedFileRepository;

    private static final String PATH_IHUB = "RRADJUSTMENT/src/main/resources/copyfile/";

    public void copyFileToiHub(String path) throws IOException {
        File file = new File(path);
        CopyFile.copyFileFromPath(file,PATH_IHUB);
    }

    public boolean verifyFilePlt(String path, String peqtPath) {
        List<MetadataHeaderSectionEntity> metadataHeaders = metadataHeader.findAll();
        List<FileBasedImportProducer> headerSegments = headerSegment.findAll();
        return verifyFile(metadataHeaders, headerSegments, path, peqtPath);
    }

    private static final Logger log = LoggerFactory.getLogger(LossDataFileUtils.class);

    private static final String START_MARKER = "LOSSFILE\t";
    private static final String END_MARKER = "#[HEADER-END]";
    private static final String PLT_DATA_EVENT_DATE = "MM-DD(N)";
    private static final String PLT_DATA_YEAR = "YEAR";
    private static final String PLT_DATA_EVENT_ID = "EVENTID";
    private static final String PLT_DATA_VALUE = "VALUE";
    private static final String PLT_DATA_MAX_EXPOSURE = "MAXEXPOSURE";

    static Pattern pattern = Pattern.compile("(\\d+)-(\\d+)\\((\\d+)\\)");

    @Autowired
    private static PEQTFileTypeRepository peqtFileTypeRepository;
    @Autowired
    private static PEQTFileSchemaRepository peqtFileSchemaRepository;

    public List<ImportFilePLTData> getPltFromLossDataFile(String path) { // lay data : 1 list data cua 1 plt
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

    public boolean verifyFile(List<MetadataHeaderSectionEntity> metadataHeaders, List<FileBasedImportProducer> headerSegments, String path, String peqtPath) {
        log.info("start verifyFile : metadataHeaders size {}, headerSegments size {}, path {}", metadataHeaders.size(), headerSegments.size(), path );
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

        // TODO validate metadata header segment
        for (FileBasedImportProducer headerSegment : headerSegments) {
            boolean testEndMarker = false;
            boolean testStartMarker = false;
            boolean found = false;
            for (String s : stream.get().collect(Collectors.toList())) {
                if (s.equals(END_MARKER)) {
                    testEndMarker = true;
                    if (!found) {
                        importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Header not exist in file " + headerSegment.getMetadataAttribute()));
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

        // validate metadata header section
        // tim metadataHeaders tu metadata header segment ?
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

        // TODO validate loss data section
        Set<MultiKey> peqtKeySet = parsePEQTFile(new File(peqtPath));
        List<ImportFilePLTData> lines = getPltFromLossDataFile(path);
        for (ImportFilePLTData line : lines) {
            if (!peqtKeySet.contains( new MultiKey(line.getEventId(), line.getYear(), line.getEventDate()))) {
                importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Loss data is invalid"));
            }
        }
        importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.SUCCESS.name(),null));
        return true;
    }

    public Set<MultiKey> parsePEQTFile(File file) {
        if (file == null)
            return null;
        BufferedReader br = null;
        try {
            br =  new BufferedReader(new FileReader(file));
            String line; //header

            Set<MultiKey> keySet = new HashSet<>();
            PEQTFileData peqtFileData = new PEQTFileData();
            while ((line = br.readLine()) != null) {

                String modelProvider = "Default";
                String modelSystem = "Default";
                String modelSystemVersion = "Default";

                PEQTFileType peqtFileType = peqtFileTypeRepository.findPEQTFileType(modelProvider, modelSystem, modelSystemVersion);
                List<PEQTFileSchema> peqtFileSchemas = null;
                if (peqtFileType == null) {
                    peqtFileType = peqtFileTypeRepository.findByIdString("Default");
                }
                if (peqtFileType != null) {
                    peqtFileSchemas = peqtFileSchemaRepository.findByPeqtFileTypeID(peqtFileType.getId());
                    if (peqtFileSchemas == null || peqtFileSchemas.isEmpty()) {
                        importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"PEQT file schema not defined for PEQT file type " + peqtFileType.getId()));
                    }
                }

                String[] tokens = line.split(peqtFileType.getDelim());
                for (PEQTFileSchema schema : peqtFileSchemas) { // schemas
                    if (schema.getMappedPeqtField() != null) {
                        BeanUtils.setProperty(peqtFileData, schema.getMappedPeqtField(), tokens[schema.getOrder()-1].replaceAll("\"", ""));
                    }
                }
                if (! keySet.add(new MultiKey(peqtFileData.getEventId(), peqtFileData.getYear(), peqtFileData.getEventDate()))) {
                    importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"PEQT file schema not defined for PEQT file type " + "PEQT file is not valid: Duplication EventID " + peqtFileData.getEventId() + " Year " + peqtFileData.getYear() + " EventDate " + peqtFileData.getEventDate()));
                    return null;
                }
            }

            return keySet;
        } catch (Exception ex) {
            log.error("Error while parsing file {}: {}", file.getName(), ex.getMessage());
            return null;
        } finally {
            if (br != null) {
                IOUtils.closeQuietly(br);
            }
        }
    }



}
