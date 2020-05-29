package com.scor.rr.service.fileBasedImport;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.domain.importfile.*;
import com.scor.rr.domain.model.PathNode;
import com.scor.rr.domain.model.TreeNode;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImportFileService {

    @Autowired
    MetadataHeaderSectionRepository metadataHeaderSectionRepository;

    @Autowired
    FileBasedImportProducerRepository fileBasedImportProducerRepository;

    @Autowired
    ImportedFileRepository importedFileRepository;

    @Autowired
    RegionPerilRepository regionperilRepository;

    @Autowired
    FileBasedImportConfigRepository fileBasedImportConfigRepository;

    @Autowired
    FileImportSourceResultRepository fileImportSourceResultRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WorkspaceEntityRepository workspaceRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    ModellingVendorRepository modellingVendorRepository;

    @Autowired
    ModellingSystemRepository modellingSystemRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Value("${nonrms.plt.root.path}")
    private String rootFilePath;
    //private String rootFilePath="C:\\ManualBasedImportFile";

    @Value("${nonrms.peqt.path}")
    private String peqtFilePath;

    private Map<String, Object> extractNamingNonRMSProperties(Long projectId, Long nonrmspicId, String instanceId) {
        ProjectEntity project = projectRepository.findById(projectId).get();
        if (project == null) {
            log.error("Non RMS : No project found for projectId={}", projectId);
            return null;
        }
        WorkspaceEntity myWorkspace = workspaceRepository.findById(project.getWorkspaceId()).get();
        if (myWorkspace == null) {
            log.error("Non RMS : Error. No workspace found");
            return null;
        }

        String workspaceCode = myWorkspace.getWorkspaceContextCode();
        String contractId = myWorkspace.getContractId();
        if ((contractId == null) || contractId.isEmpty()) {
            log.error("Non RMS : Error. contractId is null or empty");
            return null;
        }

        FileBasedImportConfig nonrmspic = fileBasedImportConfigRepository.findById(nonrmspicId).get();
        ModellingSystemInstanceEntity modellingSystemInstance = null;
        //SectionEntity section = sectionRepository.findById(contractId).get();
        //if (section == null) {
         //   log.error("Non RMS : Error. No section found");
         //   return null;
        //}


        String reinsuranceType = "T"; // fixed for TT
        String division = "01"; // fixed for TT
        String sourceVendor = "Non RMS";
        String periodBasis = "FT"; // fixed for TT
        String prefix = myWorkspace.getWorkspaceContextCode();
        //String uwYear = myWorkspace.getWorkspaceUwYear() + "-01";
        String uwYear = String.valueOf(myWorkspace.getWorkspaceUwYear());
        String modelSystemVersion = modellingSystemInstance != null ? modellingSystemInstance.getModellingSystemVersion().getId() : null;

        Long imSeq = null;
        if (nonrmspic == null) {
            log.error("Non RMS : No NonRmsProjectImportConfig for nonrmspicId = {}", nonrmspicId);
            return null;
        }

        ProjectImportRunEntity lastProjectImportRun = null;
        if (nonrmspic.getLastProjectImportRunId() != null) {
            lastProjectImportRun = projectImportRunRepository.findById(nonrmspic.getLastProjectImportRunId()).get();
        }

        // distinguish Non RMS et RMS
        List<ProjectImportRunEntity> projectImportRuns = projectImportRunRepository.findByProjectId(projectId);
        if (projectImportRuns == null) {
            imSeq = 1L;
        } else {
            imSeq = projectImportRuns.size() + 1L;
        }

        log.info("Non RMS BatchRest: rmspicId {}, lastProjectImportRunId {}, runId {}", nonrmspic.getFileBasedImportConfigId(), lastProjectImportRun == null ? null : lastProjectImportRun.getProjectImportRunId(), lastProjectImportRun == null ? null : lastProjectImportRun.getRunId());

        String clientName = myWorkspace.getClientName();
        String clientId = myWorkspace.getClientId();

        log.info("Non RMS : reinsuranceType {}, prefix {}, clientName {}, clientId {}, contractId {}, division {}, uwYear {}, sourceVendor {}, modelSystemVersion {}, periodBasis {}, importSequence {}",
                reinsuranceType, prefix, clientName, clientId, contractId, division, uwYear, sourceVendor, modelSystemVersion, periodBasis, imSeq);

        Map<String, Object> map = new HashMap<>();
        map.put("reinsuranceType", reinsuranceType);
        map.put("prefix", prefix);
        map.put("clientName", clientName);
        map.put("clientId", clientId);
        map.put("contractId", workspaceCode); // use code instead of contract Id
        map.put("division", division);
        map.put("uwYear", uwYear);
        map.put("sourceVendor", sourceVendor);
        map.put("modelSystemVersion", modelSystemVersion);
        map.put("periodBasis", periodBasis);
        map.put("importSequence", imSeq);
        map.put("projectId", projectId);
        map.put("nonrmspicId", nonrmspicId);
        map.put("instanceId", instanceId);
        return map;
    }

    @Autowired
    @Qualifier(value = "fileBasedImport")
    private Job fileBasedImport;

    public Long launchFileBasedImport(String instanceId,
                                            Long nonrmspicId,
                                            Long fileBasedImportConfigId,
                                            String userId,
                                            Long projectId,
                                            String fileImportSourceResultIds) {
        log.info("Starting launchFileBasedImport at backend");
        try {
            Map<String, Object> properties = extractNamingNonRMSProperties(projectId, nonrmspicId, instanceId);
            if (properties == null) {
                log.error("Error. No workspace found");
                return null;
            }

            JobParametersBuilder builder = new JobParametersBuilder()
                    .addString("instanceId", instanceId)
                    .addString("reinsuranceType", (String) properties.get("reinsuranceType"))
                    .addString("prefix", (String) properties.get("prefix"))
                    .addString("clientName", (String) properties.get("clientName"))
                    .addString("clientId", (String) properties.get("clientId"))
                    .addString("contractId", (String) properties.get("contractId"))
                    .addString("division", (String) properties.get("division"))
                    .addString("uwYear", (String) properties.get("uwYear"))
                    .addString("sourceVendor", (String) properties.get("sourceVendor"))
                    .addString("modelSystemVersion", (String) properties.get("modelSystemVersion"))
                    .addString("periodBasis", (String) properties.get("periodBasis"))
                    .addLong("importSequence", (Long) properties.get("importSequence"))
                    .addLong("nonrmspicId", nonrmspicId)
                    .addLong("fileBasedImportConfigId", fileBasedImportConfigId)
                    .addString("userId", userId)
                    .addLong("projectId", projectId)
                    .addString("fileImportSourceResultIds", fileImportSourceResultIds);
            log.info("Starting Non RMS import batch: nonrmspicId {}, userId {}, projectId {}, fileIds {}", nonrmspicId, userId, projectId, fileImportSourceResultIds);
            JobExecution execution = null;
            execution = jobLauncher.run(fileBasedImport, builder.toJobParameters());
            return execution.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static final String PATH_IHUB = "RRADJUSTMENT/src/main/resources/copyfile/";

    @Autowired
    RefFileBasedImportRepository refFileBasedImportRepository;

    public Map<String, String> readMetadata(String path) {
        return readMetadata(path, metadataHeaderSectionRepository.findAll());
    }

    private Map<String, String> readMetadata(String path, List<MetadataHeaderSectionEntity> metadataHeaderSectionEntities) {
        File file = new File(path);
        SourceFileImport sourceFileImport = buildSourceFileImport(file);
        Map<String, String> combinatedKey= getCombinedKeys(metadataHeaderSectionEntities, sourceFileImport.getImportFileHeader());
        ModellingVendorEntity modellingVendor = modellingVendorRepository.findByName(combinatedKey.get(RefFileBasedImportEntity.MODEL_PROVIDER_COLUMN));
        ModellingSystemEntity modellingSystem = modellingSystemRepository.findByVendorIdAndName(modellingVendor.getId(), combinatedKey.get(RefFileBasedImportEntity.MODEL_SYSTEM_COLUMN));
        ImportFileLossDataHeader importFileLossDataHeader = parseLossDataTableHeader(file, metadataHeaderSectionEntities);
        Map<String, String> metadata= new HashMap<>(importFileLossDataHeader.getMetadata());

        RefFileBasedImportEntity reference = refFileBasedImportRepository.findByModellingVendorAndModellingSystemAndPerilAndEventSetId(
                modellingVendor.getId(),
                modellingSystem.getId(),
                combinatedKey.get(RefFileBasedImportEntity.PERIL_COLUMN),
                Integer.parseInt(combinatedKey.get(RefFileBasedImportEntity.EVENT_SET_ID_COLUMN)));

        String regionPerilCode= getRegionPeril(metadata.get("Geo_Code"), metadata.get("Peril"));
        metadata.put("ModellingVersionYear", String.valueOf(reference.getModelVersionYear()));
        metadata.put("TargetRapCode", getTargetRap(reference, regionPerilCode));
        return metadata;
    }

    @Autowired
    SourceRapMappingRepository sourceRapMappingRepository;

    @Autowired
    TargetRapRepository targetRapRepository;
    @Autowired
    PETRepository petRepository;
    @Autowired
    RegionPerilMappingRepository regionPerilMappingRepository;

    private String getRegionPeril(String geoCode, String perilCode){
        Long rpId= regionPerilMappingRepository.findByCountryCodeAndPerilCode(geoCode, perilCode)
                .orElseThrow(() -> new RuntimeException("Cannot find Region Peril with GeoCode and Peril : " + geoCode+ "/" + perilCode))
                .getRegionPerilID();
        return regionperilRepository.findById(rpId)
                .orElseThrow(() -> new RuntimeException("Cannot find Region Peril with ID : " + rpId))
                .getRegionPerilCode();

    }

    private String getTargetRap(RefFileBasedImportEntity reference, String rpCode){
        // Should be ModellingSystem _ RPCode _ Mv{Year}
        // String profileKey = reference.getModellingVendor() + "-" + reference.getModellingSystem() + "-" + reference.getModelVersionYear();
        String profileKey = reference.getModellingSystem() + "_" + rpCode + "_Mv" + reference.getModelVersionYear();
        List<TargetRapEntity> targetRapEntities = targetRapRepository.findByProfileKey(profileKey);
        for (TargetRapEntity targetRap : targetRapEntities) {
            Optional<PetEntity> petOpt = petRepository.findById(targetRap.getPetId());
            if (petOpt.isPresent() && reference.getPeril().equals(petOpt.get().getPeril()) &&
                    String.valueOf(reference.getEventSetId()).equals(petOpt.get().getSimulationPeriodBasisCode()) ) {
                //sourceFileImport.setTargetRapCode(targetRap.getTargetRapCode());
                //break;
                return targetRap.getSourceRAPCode();
                //TODO: if more than one match
            }
        }
        return null;
    }


    public List<FileImportSourceResult> persisteFileBasedImportConfig(FileBasedImportConfigRequest request) {
        List<FileImportSourceResult> fileImportSourceResults=new ArrayList<>();
        FileBasedImportConfig fileBasedImportConfig=new FileBasedImportConfig();
        fileBasedImportConfig.setProjectId(Long.parseLong(request.getProjectId()));
        //fileBasedImportConfig.setSelectedFolderSourcePath(folderPath);
        fileBasedImportConfigRepository.save(fileBasedImportConfig);
        System.out.println("taille"+request.getSelectedFileSourcePath().size());
        List<MetadataHeaderSectionEntity> metadataHeaderSectionEntities= metadataHeaderSectionRepository.findAll();

        for(String filePath : request.getSelectedFileSourcePath()) {
            System.out.println("filePath" + filePath);
            Map<String, String> maps = readMetadata(filePath, metadataHeaderSectionEntities);
            FileImportSourceResult fileImportSourceResult = new FileImportSourceResult();
            fileImportSourceResult.setResultName(maps.get("ResultsName"));
            fileImportSourceResult.setFinancialPerspective(maps.get("FinPerspectiveDesc"));
            //fileImportSourceResult.setModelVersion(maps.get("Model_Version"));
            fileImportSourceResult.setModelVersion(maps.get("ModellingVersionYear"));
            fileImportSourceResult.setFilePath(filePath);
            fileImportSourceResult.setProjectId(Integer.parseInt(request.getProjectId()));
            //fileImportSourceResult.setFileName(fileName);
            fileImportSourceResult.setFileBasedImportConfigId(fileBasedImportConfig.getFileBasedImportConfigId().intValue());
            fileImportSourceResultRepository.save(fileImportSourceResult);
            fileImportSourceResults.add(fileImportSourceResult);
        }
        return fileImportSourceResults;
    }

    private Map<String, String> getCombinedKeys(List<MetadataHeaderSectionEntity> definitions, ImportFileLossDataHeader header){
        Map<String, String> combinatedKey = new HashMap<>();
        definitions.forEach(d -> {
            if(d.getDataType() != null && d.getDataType().contains("reference:")){
                String column = d.getDataType().replace("reference:", "");
                combinatedKey.put(column, header.getMetadata().get(d.getId()));
            }
        });
        return combinatedKey;
    }

    public Map<String, String> readPLTdata(String path) {
        Map<String, String> results = new HashMap<>();
        List<ImportFilePLTData> lines = parsePLTLossDataFile(new File(path));
        if (lines != null) {
            int i = 0;
            for (ImportFilePLTData line : lines) {
                i++;
                results.put("Line" + i + " : ", line.toString());
                if (i == 10) {
                    break;
                }
            }
        }
        return results;
    }

    public boolean validateMetadata(String path) {
        File file = new File(path);
        SourceFileImport sourceFileImport = buildSourceFileImport(file);  // ham nay chua ham validate(importFileLossDataHeader, mandatoryMetadataList, defaultMetadataList) nho hon
        sourceFileImport.setValidatedHeader(false);

        if (sourceFileImport.getImportFileHeader() == null) {
            sourceFileImport.getErrorMessages().add("Import File Header not found");
            //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Import File Header not found"));
            return false;
        }

        if (! sourceFileImport.getImportFileHeader().getScanErrors().isEmpty()) {
            sourceFileImport.getErrorMessages().addAll(sourceFileImport.getImportFileHeader().getScanErrors());
            //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),sourceFileImport.getErrorMessages().toString()));
            return false;
        }

        List<String> metadataValidationErrMsgs = validateMetadata(sourceFileImport); // validate cac thong tin chinh cua meta data (numeric ...)
        if (!metadataValidationErrMsgs.isEmpty()) {
            sourceFileImport.getErrorMessages().addAll(metadataValidationErrMsgs);
            //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),sourceFileImport.getErrorMessages().toString()));
            return false;
        }

        if (sourceFileImport.getErrorMessages().isEmpty()) {
            sourceFileImport.setValidatedHeader(true);
        }

        //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.SUCCESS.name(),null));
        return true;
    }

    public boolean validatePLTdata(String path, String peqtPath) {
        Set<MultiKey> peqtKeySet = parsePEQTFile(new File(peqtPath));
        List<ImportFilePLTData> lines = parsePLTLossDataFile(new File(path));
        MultiKey key = null;
        for (ImportFilePLTData line : lines) {
            key = new MultiKey((Object) line.getEventId(), (Object) line.getYear(), (Object) line.getEventDate());
            if (!peqtKeySet.contains(key)) {
                //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Loss data is invalid"));
                return false;
            }
        }
        //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.SUCCESS.name(),null));
        return true;
    }

    public SourceFileImport buildSourceFileImport(File file) {
        ImportFileLossDataHeader importFileLossDataHeader = parseLossDataTableHeader(file, metadataHeaderSectionRepository.findAll()); // ham nay chua ham validate(importFileLossDataHeader, mandatoryMetadataList, defaultMetadataList) nho hon
        SourceFileImport sourceFileImport = new SourceFileImport();
        sourceFileImport.setProjectId(null);
        sourceFileImport.setFilePath(file.getParent().replace(getRootFilePath(), ""));
        sourceFileImport.setFileName(file.getName());
        sourceFileImport.setImportFileHeader(importFileLossDataHeader);
        return sourceFileImport;
    }

    public List<ImportFilePLTData> parsePLTLossDataFile(File file) {
        if (file == null)
            return null;
        BufferedReader br = null;
        try {
            br =  new BufferedReader(new FileReader(file));
            String line = null;
            boolean startOfDataSection = false;
            boolean fieldHeadingsFound = false;
            List<ImportFilePLTData> lossDataList = new ArrayList<>();
            Map<String, Integer> fieldOrder = new HashMap<>();
            while ((line = br.readLine()) != null) {
                line.trim(); //ignore leading and trailing whitespaces
                if (StringUtils.isEmpty(line)) {
                    continue;
                }
                if (END_MARKER.equals(line)) {
                    startOfDataSection = true;
                    continue;
                }

                if (!startOfDataSection) {
                    continue;
                }

                if (!fieldHeadingsFound) {
                    String[] headings = line.split("\t");

                    if (!retrieveFieldOrder(headings, fieldOrder)) {
                        log.error("The headings line contains unknown field");
                        return null;
                    }

                    fieldHeadingsFound = true;
                    continue;
                }

                String[] values = line.split("\t");
                if (values.length != fieldOrder.size()) {
                    log.error("Data line {} is not conformed with the headings - ignore it", line);
                    continue;
                }

                try {
                    ImportFilePLTData importFilePLTData = new ImportFilePLTData();
                    if (fieldOrder.get(PLT_DATA_EVENT_ID) != null) {
                        importFilePLTData.setEventId(Integer.parseInt(values[fieldOrder.get(PLT_DATA_EVENT_ID)]));
                    }
                    if (fieldOrder.get(PLT_DATA_YEAR) != null) {
                        importFilePLTData.setYear(Integer.parseInt(values[fieldOrder.get(PLT_DATA_YEAR)]));
                    }
                    if (values[fieldOrder.get(PLT_DATA_VALUE)] != null) {
                        importFilePLTData.setValue(Float.parseFloat(values[fieldOrder.get(PLT_DATA_VALUE)]));
                    }
                    if (fieldOrder.get(PLT_DATA_MAX_EXPOSURE) != null ) {
                        importFilePLTData.setMaxExposure(Float.parseFloat(values[fieldOrder.get(PLT_DATA_MAX_EXPOSURE)]));
                    } else {
                        importFilePLTData.setMaxExposure(importFilePLTData.getValue());
                    }
                    if (fieldOrder.get(PLT_DATA_EVENT_DATE) != null) {
                        String eventDate = values[fieldOrder.get(PLT_DATA_EVENT_DATE)];
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

                    lossDataList.add(importFilePLTData);
                } catch (Exception ex) {
                    log.warn("Error while reading line {}: {}", line, ex.getMessage());
                    continue;
                }
            }
            if (!startOfDataSection) {
                log.error("File {} does not contain data section", file.getName());
            }
            return lossDataList;
        } catch (IOException ex) {
            log.error("Error while parsing file {}: {}", file.getName(), ex.getMessage());
            return null;
        } finally {
            if (br != null) {
                IOUtils.closeQuietly(br);
            }
        }
    }

    private static final Logger log = LoggerFactory.getLogger(ImportFileService.class);

    private static final String START_MARKER = "LOSSFILE";
    private static final String END_MARKER = "#[HEADER-END]";
    private static final String PLT_DATA_EVENT_DATE = "MM-DD(N)";
    private static final String PLT_DATA_YEAR = "YEAR";
    private static final String PLT_DATA_EVENT_ID = "EVENTID";
    private static final String PLT_DATA_VALUE = "VALUE";
    private static final String PLT_DATA_MAX_EXPOSURE = "MAXEXPOSURE";

    static Pattern pattern = Pattern.compile("(\\d+)-(\\d+)\\((\\d+)\\)");

    @Autowired
    PEQTFileTypeRepository peqtFileTypeRepository;

    @Autowired
    PEQTFileSchemaRepository peqtFileSchemaRepository;

    private boolean retrieveFieldOrder(String[] headings, Map<String, Integer> fieldOrder) {
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
                    return false;
            }
        }
        return true;
    }

    private String trim(String line) {
        line = line.trim();
        int len = line.length();
        int st = 0;
        char[] val = line.toCharArray();

        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return ((st > 0) || (len < line.length())) ? line.substring(st, len) : line;
    }

//    @Value("${nonrms.plt.root.path}")
//    private String rootFilePath = "C:\\\\scor\\\\data\\\\ihub\\\\nonRMS\\\\plt\\\\";

    private String rootDirectoryPath = "/scor/data/ihub/nonRMS/";

//    @Value("${nonrms.peqt.path}")
//    private String peqtFilePath = "/scor/data/ihub/nonRMS/peqt/";

    public String getRootFilePath() {
        return rootFilePath;
    }

    public String getRootDirectoryPath() {
        return rootDirectoryPath;
    }


    public ImportFileLossDataHeader parseLossDataTableHeader(File file) {
        return parseLossDataTableHeader(file, metadataHeaderSectionRepository.findAll());
    }

    // scan file de lay du lieu + validate 1 phan thong tin co ban, validate cac thong tin khac cua header sau (numeric, ...)
    private ImportFileLossDataHeader parseLossDataTableHeader(File file, List<MetadataHeaderSectionEntity> metadataDefinitions) {
        if (file == null)
            return null;
        log.debug("Parsing file {}", file.getName());
        ImportFileLossDataHeader importFileLossDataHeader = new ImportFileLossDataHeader();
        // TODO la String hay Integer : id sql
        Map<String, MetadataHeaderSectionEntity> metadataDefinitionMap = new HashMap<>();
        List<MetadataHeaderSectionEntity> mandatoryMetadataList = new ArrayList<>();
        List<MetadataHeaderSectionEntity> defaultMetadataList = new ArrayList<>();

        if (metadataDefinitions != null && !metadataDefinitions.isEmpty()) {
            for (MetadataHeaderSectionEntity metadataDefinition : metadataDefinitions) {
                // TODO
//                metadataDefinitionMap.put(metadataDefinition.getId().toUpperCase(), metadataDefinition);
                metadataDefinitionMap.put(metadataDefinition.getId().toUpperCase(), metadataDefinition);
                if (MetadataHeaderSectionEntity.MANDATORY_Y.equals(metadataDefinition.getMandatory())) {
                    mandatoryMetadataList.add(metadataDefinition);
                } else if (MetadataHeaderSectionEntity.MANDATORY_D.equals(metadataDefinition.getMandatory())) {
                    defaultMetadataList.add(metadataDefinition);
                }
            }
        }
        BufferedReader br = null;
        importFileLossDataHeader.getMetadata().put("File_Name", file.getName());
        importFileLossDataHeader.getMetadata().put("File_Path", file.getParent().replace(getRootFilePath(), ""));
        Date lastUpdated = new Date(file.lastModified());
        //importFileLossDataHeader.getMetadata().put("File_Last_Update_Date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastUpdated));
        importFileLossDataHeader.getMetadata().put("File_Last_Update_Date", new SimpleDateFormat("yyyy-MM-dd").format(lastUpdated));
        try {
            importFileLossDataHeader.setLastScanDate(new Date());
            br =  new BufferedReader(new FileReader(file));
            String line = br.readLine(); // read start marker
            line = trim(line);
            if (!START_MARKER.equals(line)) {
                importFileLossDataHeader.getScanErrors().add("The start marker metadata must be present in the first line of the file");
                return importFileLossDataHeader;
            }
            int i = 1;
            while ((line = br.readLine()) != null) {
                i++;
                line = trim(line); //ignore leading and trailing whitespaces/tabs
                if (StringUtils.isEmpty(line))
                    continue;
                if (END_MARKER.equals(line)) {
                    return validate(importFileLossDataHeader, mandatoryMetadataList, defaultMetadataList);
                }

                int idx = line.indexOf("\t");
                String name = "";
                String value = "";
                if (idx == - 1) {
                    log.warn("Line {} is not conformed: metadata name and metadata value shall be separated by a horizontal tab", line);
                    name = line;
                } else {
                    name = line.substring(0, idx);
                    value = line.substring(idx+1);
                }

                /*if (i == 4) {
                    if (!name.equals("LossTableHeaderProducer")) {
                        importFileLossDataHeader.getScanErrors().add("LossTableHeaderProducer must be present in the fourth line of the file");
                        return importFileLossDataHeader;
                    }
                }
                
                if (i == 3) {
                    if (!name.equals("LossTableHeaderFormat")) {
                        importFileLossDataHeader.getScanErrors().add("LossTableHeaderFormat must be present in the third of the file");
                        return importFileLossDataHeader;
                    }
                }

                if (i == 2) {
                    if (!name.equals("File_Version")) {
                        importFileLossDataHeader.getScanErrors().add("FileVersion must be present in the second line of the file");
                        return importFileLossDataHeader;
                    }
                }*/
                
                MetadataHeaderSectionEntity metadataDefinition = metadataDefinitionMap.get(name.toUpperCase()); // name la metadataDefinition.getMetadataAttribute()
                if (metadataDefinition == null) {
                    log.warn("Metadata {} not recognised", name);
                    importFileLossDataHeader.getMetadata().put(name, value);
                } else {
                    importFileLossDataHeader.getMetadata().put(String.valueOf(metadataDefinition.getId()), value);
                }
            }

            importFileLossDataHeader.getScanErrors().add("The end marker metadata must be present");
            return importFileLossDataHeader;
        } catch (IOException ex) {
            importFileLossDataHeader.getScanErrors().add("Error while parsing file " + file.getName() + ": " + ex.getMessage());
            return importFileLossDataHeader;
        } finally {
            if (br != null) {
                IOUtils.closeQuietly(br);
            }
        }
    }

    public ImportFileLossDataHeader validate(ImportFileLossDataHeader importFileLossDataHeader,
                                              List<MetadataHeaderSectionEntity> mandatoryMetadataList,
                                              List<MetadataHeaderSectionEntity> defaultMetadataList) {
        List<String> missings = new ArrayList<>();
        for (MetadataHeaderSectionEntity mandatoryMetadata : mandatoryMetadataList) {
            if (importFileLossDataHeader.getMetadata().get(mandatoryMetadata.getMetadataAttribute()) == null) {
                // TODO chu y id sql
                missings.add(String.valueOf(mandatoryMetadata.getMetadataAttribute()));
            }
        }
        if (!missings.isEmpty()) {
            importFileLossDataHeader.getScanErrors().add("Invalid header data : The following mandatory field(s) are not found : " + StringUtils.join(missings, ","));
        }

        for (MetadataHeaderSectionEntity defaultMetadata : defaultMetadataList) {
            if (importFileLossDataHeader.getMetadata().get(defaultMetadata.getMetadataAttribute()) == null) {
                String value = null;
                if (defaultMetadata.getDefaultValue() != null && ! defaultMetadata.getDefaultValue().contains("<")) {
                    value = defaultMetadata.getDefaultValue();
                } else {
                    //TODO: could be more generic ?
                    if ("Grain".equalsIgnoreCase(defaultMetadata.getMetadataAttribute())) {
                        // } else if ("RegionPeril".equalsIgnoreCase(defaultMetadata.getId())) {
                        value = importFileLossDataHeader.getMetadata().get("ResultsName");
                    } else if ("RegionPeril".equalsIgnoreCase(defaultMetadata.getMetadataAttribute())) {
//                    } else if ("RegionPeril".equalsIgnoreCase(defaultMetadata.getId())) {
                        String countryCode = importFileLossDataHeader.getMetadata().get("Geo_Code");
                        String perilCode = importFileLossDataHeader.getMetadata().get("Peril");
//                        if (countryCode != null && perilCode != null) {
//                            RegionPerilMapping regionPerilMapping = regionPerilMappingRepository.findByCountryCodeAndPerilCode(countryCode, perilCode);
//                            if (regionPerilMapping != null) {
//                                RegionPerilEntity regionPeril = regionperilRepository.findOne(regionPerilMapping.getRegionPerilID());
//                                if (regionPeril != null) {
//                                    value = regionPeril.getRegionPerilCode();
//                                }
//                            }
//                        }
                    } else if ("UserName".equalsIgnoreCase(defaultMetadata.getMetadataAttribute())) {
                        if (importFileLossDataHeader.getMetadata().get("User") != null) {
//                            UserRrEntity user = userRrRepository.findByUserName(importFileLossDataHeader.getMetadata().get("User"));
//                            if (user != null) {
//                                value = user.getUserFirstName() + user.getUserLastName();
//                            }
                        }
                    }
                }
                log.debug("Header section does not contain metadata {} - use default value {}", defaultMetadata.getMetadataAttribute(), value);
                importFileLossDataHeader.getMetadata().put(defaultMetadata.getMetadataAttribute(), value);
            }
        }
        return importFileLossDataHeader;
    }

    private List<String> validateMetadata(SourceFileImport sourceFileImport) {
        List<String> errorMessages = new ArrayList<>();
        ImportFileLossDataHeader header = sourceFileImport.getImportFileHeader();
        List<MetadataHeaderSectionEntity> definitions = metadataHeaderSectionRepository.findAll();
        Map<String, String> combinatedKey = new HashMap<>(); // TODO used after
        for (MetadataHeaderSectionEntity definition : definitions) {
            if (!StringUtils.isEmpty(definition.getDataType())) {
                if (header.getMetadata().get(definition.getMetadataAttribute()) != null) {
                    if ("numeric".equals(definition.getDataType())) {
                        try {
                            Double.valueOf(header.getMetadata().get(definition.getMetadataAttribute()));
                        } catch (NumberFormatException ex) {
                            errorMessages.add(definition.getId() + " " + definition.getMetadataAttribute() + " must be a numeric. Current value is " + header.getMetadata().get(definition.getMetadataAttribute()));
                        }
                    } else if ("datetime".equals(definition.getDataType())) {
                        if (!StringUtils.isEmpty(definition.getFormat())) {
                            try {
                                Date date = new SimpleDateFormat(definition.getFormat()).parse(header.getMetadata().get(definition.getMetadataAttribute()));
                            } catch (ParseException ex) {
                                errorMessages.add(definition.getId() + " " + definition.getMetadataAttribute() + " must be in format " + definition.getFormat() +
                                        ". Current value is " + header.getMetadata().get(definition.getMetadataAttribute()));
                            }
                        }
                    } else if (definition.getDataType().contains("reference:")) {
                        String column = definition.getDataType().replace("reference:", "");
                        combinatedKey.put(column, header.getMetadata().get(definition.getMetadataAttribute()));
                    }
                }
            }

            if (!StringUtils.isEmpty(definition.getAssertValue())) {
                if (! definition.getAssertValue().equals(header.getMetadata().get(definition.getMetadataAttribute()))) {
                    errorMessages.add(definition.getId() + " " + definition.getMetadataAttribute() + " must be " + definition.getAssertValue() + ". Current value is " + header.getMetadata().get(definition.getMetadataAttribute()));
                }
            }
        }

//        NonRmsImportReference reference = null;
//        ModellingVendor modellingVendor = modellingVendorRepository.findByName(combinatedKey.get(NonRmsImportReference.MODEL_PROVIDER_COLUMN));
//        ModellingSystem modellingSystem = modellingSystemRepository.findByVendorIdAndName(modellingVendor.getId(), combinatedKey.get(NonRmsImportReference.MODEL_SYSTEM_COLUMN));
//
//        try {
//            reference = nonRmsImportReferenceRepository.findByModellingVendorAndModellingSystemAndPerilAndEventSetId(
//                    modellingVendor,
//                    modellingSystem,
//                    combinatedKey.get(NonRmsImportReference.PERIL_COLUMN),
//                    Integer.parseInt(combinatedKey.get(NonRmsImportReference.EVENT_SET_ID_COLUMN)));
//            if (reference != null) {
//                if (! StringUtils.isEmpty(combinatedKey.get(NonRmsImportReference.COUNTRY_COLUMN))) {
//                    String[] countries = combinatedKey.get(NonRmsImportReference.COUNTRY_COLUMN).split(",");
//                    for (int i = 0; i < countries.length; i++) {
//                        if (! reference.getCountries().contains(countries[i])) {
//                            errorMessages.add("Country " + countries[i] +
//                                    " not found for the Model Provider " + combinatedKey.get(NonRmsImportReference.MODEL_PROVIDER_COLUMN) +
//                                    " Model System " + combinatedKey.get(NonRmsImportReference.MODEL_SYSTEM_COLUMN) +
//                                    " Peril " + combinatedKey.get(NonRmsImportReference.PERIL_COLUMN) +
//                                    " EventSetID " + combinatedKey.get(NonRmsImportReference.EVENT_SET_ID_COLUMN) +
//                                    ". Current country list are " + StringUtils.join(reference.getCountries(), ",")
//                            );
//                        } else {
//                            //FIXME: change when we have ref data
////                            RegionPerilMapping regionPerilMapping = ttRegionPerilMappingRepository.findByCountryCodeAndPerilCode(countries[i], reference.getPeril());
////                            if (regionPerilMapping != null) {
////                                RegionPeril regionPeril = ttRegionPerilRepository.findByRegionPerilID(regionPerilMapping.getRegionPerilID());
////                                if (regionPeril != null) {
////                                    sourceFileImport.getRegionPerilCodes().add(regionPeril.getRegionPerilCode());
////                                }
////                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            reference = null;
//        }
//        if (reference == null) {
//            errorMessages.add("Reference not found for the Model Provider " + combinatedKey.get(NonRmsImportReference.MODEL_PROVIDER_COLUMN) +
//                    " Model System " + combinatedKey.get(NonRmsImportReference.MODEL_SYSTEM_COLUMN) +
//                    " Peril " + combinatedKey.get(NonRmsImportReference.PERIL_COLUMN) +
//                    " EventSetID " + combinatedKey.get(NonRmsImportReference.EVENT_SET_ID_COLUMN));
//        } else {
//            sourceFileImport.setModelVersionYear(reference.getModelVersionYear());
//
//            if (sourceFileImport.getRegionPerilCodes().isEmpty()) {
//                errorMessages.add("Region Perils not found for the Model Provider " + combinatedKey.get(NonRmsImportReference.MODEL_PROVIDER_COLUMN) +
//                        " Model System " + combinatedKey.get(NonRmsImportReference.MODEL_SYSTEM_COLUMN) +
//                        " Peril " + combinatedKey.get(NonRmsImportReference.PERIL_COLUMN) +
//                        " Countries " + combinatedKey.get(NonRmsImportReference.COUNTRY_COLUMN));
//            }
/*
            String profileKey = reference.getModellingVendor().getId() + "-" +
                    reference.getModellingSystem().getId() + "-" +
                    reference.getModelVersionYear();
            List<SourceRapMapping> sourceRapMappings = sourceRapMappingRepository.findByProfileKey(profileKey);
            for (SourceRapMapping sourceRapMapping : sourceRapMappings) {
                List<SourceRap> sourceRaps = sourceRapRepository.findBySourceRapCode(sourceRapMapping.getSourceRapCode());
                for (SourceRap sourceRap : sourceRaps) {
                    List<TargetRap> targetRaps = targetRapRepository.findByModellingVendorAndModellingSystem(modellingVendor.getId(), modellingSystem.getId());
                    for (TargetRap targetRap : targetRaps) {
                        PET pet = petRepository.findOne(String.valueOf(targetRap.getPetId()));
                        if (pet != null && reference.getPeril().equals(pet.getPeril()) &&
                                reference.getEventSetId() == pet.getRmsSimulationSetId()) {
                            sourceFileImport.setTargetRapCode(targetRap.getTargetRapCode());
                            break;
                            //TODO: if more than one match
                        }
                    }
                    break;
                }
                break;
            }
*/

//            List<TargetRap> targetRaps = targetRapRepository.findByModellingVendorAndModellingSystem(modellingVendor.getId(), modellingSystem.getId());
//            for (TargetRap targetRap : targetRaps) {
//                PET pet = petRepository.findOne(String.valueOf(targetRap.getPetId()));
//                if (pet != null && reference.getPeril().equals(pet.getPeril()) &&
//                        reference.getEventSetId() == pet.getRmsSimulationSetId()) {
//                    sourceFileImport.setTargetRapCode(targetRap.getTargetRapCode());
//                    break;
//                    //TODO: if more than one match
//                }
//            }
//
//            if (sourceFileImport.getTargetRapCode() == null) {
//                errorMessages.add("Target Rap not found for the Model Provider " + combinatedKey.get(NonRmsImportReference.MODEL_PROVIDER_COLUMN) +
//                        " Model System " + combinatedKey.get(NonRmsImportReference.MODEL_SYSTEM_COLUMN) +
//                        " Peril " + combinatedKey.get(NonRmsImportReference.PERIL_COLUMN) +
//                        " Countries " + combinatedKey.get(NonRmsImportReference.COUNTRY_COLUMN));
//            }
//        }
//
        return errorMessages;
    }

//    public boolean verifyFile(List<MetadataHeaderSectionEntity> metadataHeaders, List<FileBasedImportProducer> headerSegments, String path, String peqtPath) {
//        log.info("start verifyFile : metadataHeaders size {}, headerSegments size {}, path {}", metadataHeaders.size(), headerSegments.size(), path );
//        File file = new File(path);
//        ArrayList<String> key = new ArrayList<>();
//        TypeToConvert type = new TypeToConvert();
//        List<Field> fs = Arrays.asList(type.getClass().getFields());
//        Supplier<Stream<String>> stream = () -> {
//            try {
//                return Files.lines(Paths.get(path));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        };
//        boolean testHeaderExists = false;
//
//        // TODO validate metadata header segment
//
//        for (FileBasedImportProducer headerSegment : headerSegments) {
//            ArrayList<String> keySegmentHeaders = new ArrayList<>();
//            boolean testEndMarker = false;
//            boolean testStartMarker = false;
//            boolean found = false;
//            for (String s : stream.get().collect(Collectors.toList())) {
////                if (s.equals(SEGMENT_END_MARKER)) {
////                    testEndMarker = true;
////                    if (!found) {
////                        //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Header segment not exist in file "));
////                        log.info("element not found in file");
////                        return false;
////                    }
////                    testHeaderExists = true;
////                }
////                if (!testEndMarker && testStartMarker) {
//                    String[] tokens = s.split("\t");
//                    if (pltline.length <= 2) {
//                        if (!keySegmentHeaders.contains(pltline[0]) && pltline[0].equals(headerSegment.getLossTableHeaderProducer())) {
//                            keySegmentHeaders.add(headerSegment.getLossTableHeaderProducer());
//                            found = true;
//                        }
//                        if (!keySegmentHeaders.contains(pltline[0]) && pltline[0].equals(headerSegment.getLossTableHeaderFormat())) {
//                            keySegmentHeaders.add(headerSegment.getLossTableHeaderFormat());
//                            found = true;
//                        }
//                        if (!keySegmentHeaders.contains(pltline[0]) && pltline[0].equals(headerSegment.getFileFormatVersion())) {
//                            keySegmentHeaders.add(headerSegment.getFileFormatVersion());
//                            found = true;
//                        }
//                    }
////                }
//                if (s.equals(START_MARKER)) {
//                    testStartMarker = true;
//                }
//            }
//
//            if (found) { // size of keySegmentHeaders > 0
//                if (!keySegmentHeaders.get(0).equals(headerSegment.getLossTableHeaderProducer())) {
//                    //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Header segment is invalid, LossTableHeaderProducer is wrong"));
//                    log.info("Header segment is invalid, LossTableHeaderProducer is wrong");
//                    return false;
//                }
//
//                if (keySegmentHeaders.get(1) == null || (keySegmentHeaders.get(1) != null && !keySegmentHeaders.get(1).equals(headerSegment.getLossTableHeaderFormat()))) {
//                    if (!keySegmentHeaders.get(1).equals(headerSegment.getLossTableHeaderFormat())) {
//                        //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Header segment is invalid, LossTableHeaderFormat is wrong"));
//                        log.info("Header segment is invalid, LossTableHeaderFormat is wrong");
//                        return false;
//                    }
//                }
//
//                if (keySegmentHeaders.get(2)  == null || (keySegmentHeaders.get(2) != null && !keySegmentHeaders.get(2).equals(headerSegment.getFileFormatVersion()))) {
//                    if (!keySegmentHeaders.get(1).equals(headerSegment.getFileFormatVersion())) {
//                        //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Header segment is invalid, FileFormatVersion is wrong"));
//                        log.info("Header segment is invalid, FileFormatVersion is wrong");
//                        return false;
//                    }
//                }
//            }
//        }
//
//        // validate metadata header section
//        // tim metadataHeaders tu metadata header segment ?
//        if (testHeaderExists) {
//            for (MetadataHeaderSectionEntity metadataHeaderSectionEntity : metadataHeaders) {
//                if (metadataHeaderSectionEntity.getMandatory().equalsIgnoreCase("Y")) {
//                    boolean testEndMarker = false;
//                    boolean testStartMarker = false;
//                    boolean found = false;
//                    String firstLigne = stream.get().findFirst().orElse(null);
//                    if (firstLigne != null && firstLigne.equals(START_MARKER)) {
//                        for (String s : stream.get().collect(Collectors.toList())) {
//                            if (s.equals(END_MARKER)) {
//                                testEndMarker = true;
//                                if (!found) {
//                                    log.info("element not found in file {}", metadataHeaderSectionEntity.getMetadataAttribute());
//                                    //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"element not found in file : "+metadataHeaderSectionEntity.getMetadataAttribute()));
//                                    return false;
//                                }
//                            }
//                            if (!testEndMarker && testStartMarker) {
//                                String[] pltline = s.split("\t");
//                                if (pltline.length == 2) {
//                                    if (pltline[0].equals(metadataHeaderSectionEntity.getMetadataAttribute())) {
//                                        if (!key.contains(pltline[0])) {
//                                            Field fieldTemp = fs.stream().filter(field -> field.getType().getSimpleName().equals(metadataHeaderSectionEntity.getDataType()))
//                                                    .findFirst()
//                                                    .orElse(null);
//                                            ConverterType converterType = new ConverterType(fieldTemp.getType());
//                                            converterType.convert(pltline[1], metadataHeaderSectionEntity);
//                                            if (converterType.value != null) {
//                                                if (!converterType.value.getClass().equals(fieldTemp.getType())) {
//                                                    //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Data Type not conform require "+metadataHeaderSectionEntity.getMetadataAttribute()));
//                                                    return false;
//                                                } else {
//                                                    found = true;
//                                                    key.add(metadataHeaderSectionEntity.getMetadataAttribute());
//                                                }
//                                            } else {
//                                                //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Data Type not conform require "+metadataHeaderSectionEntity.getMetadataAttribute()));
//                                                return false;
//                                            }
//                                        } else {
//                                            log.info("duplicate metadata {}", metadataHeaderSectionEntity.getMetadataAttribute());
//                                            //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"duplicate metadata "+metadataHeaderSectionEntity.getMetadataAttribute()));
//                                            return false;
//                                        }
//                                    }
//                                } else {
//                                    //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"File not right splitted"));
//                                    return false;
//                                }
//                            }
//                            if (s.equals(START_MARKER)) {
//                                testStartMarker = true;
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Header file missing"));
//            return false;
//        }
//
//        // TODO validate loss data section
//        Set<MultiKey> peqtKeySet = parsePEQTFile(new File(peqtPath));
//        List<ImportFilePLTData> lines = parsePLTLossDataFile(path);
//        for (ImportFilePLTData line : lines) {
//            if (!peqtKeySet.contains( new MultiKey(line.getEventId(), line.getYear(), line.getEventDate()))) {
//                //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"Loss data is invalid"));
//            }
//        }
//        //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.SUCCESS.name(),null));
//        return true;
//    }

    public Set<MultiKey> parsePEQTFile(File file) {
        if (file == null)
            return null;
        BufferedReader br = null;

        final PEQTFileType defaultPEQTFileType = peqtFileTypeRepository.findByIdString("Default");
        final String modelProvider = "Default";
        final String modelSystem = "Default";
        final String modelSystemVersion = "Default";

        try {
            br =  new BufferedReader(new FileReader(file));
            String line; //header

            Set<MultiKey> keySet = new HashSet<>();
            PEQTFileData peqtFileData = new PEQTFileData();

            PEQTFileType peqtFileType = peqtFileTypeRepository.findPEQTFileType(modelProvider, modelSystem, modelSystemVersion);
            if (peqtFileType == null) {
                peqtFileType = defaultPEQTFileType;
            }
            List<PEQTFileSchema> peqtFileSchemas = null;
            if (peqtFileType != null) {
                peqtFileSchemas = peqtFileSchemaRepository.findByPeqtFileTypeID(peqtFileType.getId());
            }

            if (peqtFileSchemas == null || peqtFileSchemas.isEmpty()) {
                //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"PEQT file schema not defined for PEQT file type " + peqtFileType.getId()));
                return keySet;
            }

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(peqtFileType.getDelim());
                for (PEQTFileSchema schema : peqtFileSchemas) { // schemas
                    if (schema.getMappedPeqtField() != null) {
                        BeanUtils.setProperty(peqtFileData, schema.getMappedPeqtField(), tokens[schema.getOrder()-1].replaceAll("\"", ""));
                    }
                }
                if (! keySet.add(new MultiKey(peqtFileData.getEventId(), peqtFileData.getYear(), peqtFileData.getEventDate()))) {
                    //importedFileRepository.save(new ImportedFileEntity(file.getName(),file.getPath(), Status.FAILED.name(),"PEQT file schema not defined for PEQT file type " + "PEQT file is not valid: Duplication EventID " + peqtFileData.getEventId() + " Year " + peqtFileData.getYear() + " EventDate " + peqtFileData.getEventDate()));
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

    public String directoryListing() {
        PathNode rootData = new PathNode(new File(getRootFilePath()), null);
        com.scor.rr.domain.model.TreeNode<PathNode> root = new com.scor.rr.domain.model.TreeNode<>(rootData, null);
        getPathList(root, new File(getRootFilePath()));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootJsonNode = objectMapper.valueToTree(root);
        return printJsonString(rootJsonNode);
    }

    public String printJsonString(JsonNode jsonNode) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(jsonNode.toString(), Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (Exception e) {
            log.error("Sorry, print Json String didn't work");
            e.printStackTrace();
            return " ";
        }
    }

    public void getPathList(com.scor.rr.domain.model.TreeNode node, File file) {
        if (file.isDirectory()) {
            log.info("DIRECTORY  -  " + file.getAbsolutePath());

            File fList[] = file.listFiles();
            if (fList != null) {
                for (int i = 0; i < fList.length; i++) {
                    if (fList[i].isDirectory()) {
                        PathNode childData;
                        if (node.getParent() == null) {
                            childData = new PathNode(fList[i], fList[i].getName());
                        } else {
                            childData = new PathNode(fList[i], null);
                        }

                        TreeNode child = new com.scor.rr.domain.model.TreeNode<>(childData, node);

                        node.getChildren().add(child);
                        getPathList(child, fList[i]);
                    }
                }
            } else {
                log.info("DIRECTORY NULL -  " + file.getAbsolutePath());
            }
        }
    }

    /*public List<String> retrieveTextFiles(String path) {
        List<String> textFiles = new ArrayList<>();
        File repo = new File(path);
        if (repo.isDirectory()) {
            File fList[] = repo.listFiles();
            if (fList != null) {
                for (int i = 0; i < fList.length; i++) {
                    if ("txt".equalsIgnoreCase(FilenameUtils.getExtension(fList[i].getName()))) {
                        textFiles.add(fList[i].getPath());
                    }
                }
            }
        }
        return textFiles;
    }*/

    public List<Map<String, String>> retrieveTextFiles(String path){
        List<Map<String,String>> maps=new ArrayList<>();
        File repo = new File(path);
        if (repo.isDirectory()) {
            File fList[] = repo.listFiles();
            if (fList != null) {
                for (int i = 0; i < fList.length; i++) {
                    if ("txt".equalsIgnoreCase(FilenameUtils.getExtension(fList[i].getName()))) {
                        Map<String, String> map=new HashMap<>();
                        Map<String,String> mapTemp=readMetadata(fList[i].getPath(), metadataHeaderSectionRepository.findAll());
                        map.put("label",fList[i].getPath());
                        map.put("createdAt",mapTemp.get("CreateDate").split(" ")[0]);
                        // new SimpleDateFormat("yyyy-MM-dd").format(lastUpdated)
                        map.put("updatedAt",mapTemp.get("File_Last_Update_Date"));
                        maps.add(map);
                    }
                }
            }
        }
        return maps;
    }

    public void updateFileBasedConfig(FileBasedImportConfigRequest request) throws RRException {
        FileBasedImportConfig fileBasedImportConfigDB = fileBasedImportConfigRepository.findByProjectId(Integer.valueOf(request.getProjectId()));

        if (fileBasedImportConfigDB == null) {
            fileBasedImportConfigDB = createFileBasedImportConfigIfNotExist(request);
        }

        if (request.getSelectedFileSourcePath() == null || request.getSelectedFileSourcePath().size() == 0) {
            fileBasedImportConfigDB.setSelectedFileSourcePath(null);
        } else {
            fileBasedImportConfigDB.setSelectedFileSourcePath(request.getSelectedFileSourcePath().toString());
        }

        fileBasedImportConfigDB.setImportLocked(request.isImportLocked());

        fileBasedImportConfigRepository.save(fileBasedImportConfigDB);

        // create/update FileImportSourceResult if isImportLocked = false
        if (!request.isImportLocked()) {
            //remove old FileImportSourceResult
            List<FileImportSourceResult> oldFileImportSourceResults = fileImportSourceResultRepository.findByProjectId(Integer.valueOf(request.getProjectId()));
            for (FileImportSourceResult fileImportSourceResult : oldFileImportSourceResults) {
                fileImportSourceResultRepository.deleteById(fileImportSourceResult.getFileImportSourceResultId());
            }

            //update new FileImportSourceResult
            if (request.getSelectedFileSourcePath() != null && request.getSelectedFileSourcePath().size() > 0) {
                Set<File> selectedTextFiles = new HashSet<>();
                for (String path : request.getSelectedFileSourcePath()) {
                    File file = new File(path);
                    selectedTextFiles.add(file);
                }

                for (File file : selectedTextFiles) {
                    if (file.isDirectory() || !"txt".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
                        continue;
                    }

                    SourceFileImport sourceFileImport = buildSourceFileImport(file);

                    fileBasedImportConfigDB = fileBasedImportConfigRepository.findByProjectId(Integer.valueOf(request.getProjectId()));
                    if (sourceFileImport.getErrorMessages() == null || sourceFileImport.getErrorMessages().size() == 0) {
                        FileImportSourceResult sourceResult = buildFileImportSourceResult(fileBasedImportConfigDB, sourceFileImport);
                        fileImportSourceResultRepository.save(sourceResult);
                    }
                }
            }
        }
    }

    private FileBasedImportConfig createFileBasedImportConfigIfNotExist(FileBasedImportConfigRequest request) {
        FileBasedImportConfig fileBasedImportConfig = new FileBasedImportConfig();
        fileBasedImportConfig.setProjectId(Long.valueOf(request.getProjectId()));
        fileBasedImportConfig.setImportLocked(false);
        fileBasedImportConfig.setLastUnlockDateForImport(new Date());

        return fileBasedImportConfig;
    }

    public String retrieveFileBasedConfig(String projectId) {
        String fileBasedConfigStr = "";

        FileBasedImportConfig fileBasedImportConfigDB = fileBasedImportConfigRepository.findByProjectId(Integer.valueOf(projectId));
        if (fileBasedImportConfigDB != null) {
            fileBasedConfigStr = fileBasedImportConfigDB.toString();
        }

        return fileBasedConfigStr;
    }

    private FileImportSourceResult buildFileImportSourceResult(FileBasedImportConfig fileBasedImportConfigDB, SourceFileImport sourceFileImport) {
        FileImportSourceResult fileImportSourceResult = new FileImportSourceResult();
        ImportFileLossDataHeader importFileLossDataHeader = sourceFileImport.getImportFileHeader();
        if (importFileLossDataHeader != null) {
            Map<String, String> metadata = importFileLossDataHeader.getMetadata();

            //TODO
            fileImportSourceResult.setFileBasedImportConfigId(fileBasedImportConfigDB.getFileBasedImportConfigId().intValue());
            fileImportSourceResult.setResultName(metadata.get("ResultsName"));
            fileImportSourceResult.setTargetRAPCode(sourceFileImport.getTargetRapCode());
            fileImportSourceResult.setProjectId(fileBasedImportConfigDB.getProjectId().intValue());
            if (metadata.get("Years") != null) {
                fileImportSourceResult.setPLTSimulationPeriods(Integer.valueOf(metadata.get("Years")));
            }
            fileImportSourceResult.setSourceCurrency(metadata.get("Currency"));
            fileImportSourceResult.setTargetCurrency(null);
            fileImportSourceResult.setFinancialPerspective(metadata.get("FinPerspective"));
            fileImportSourceResult.setUnitMultiplier(new BigDecimal(1.0));
            fileImportSourceResult.setProportion(new BigDecimal(100.0));
            fileImportSourceResult.setDataSource(metadata.get("ResultsDatabaseName"));
            fileImportSourceResult.setFilePath(sourceFileImport.getFilePath());
            fileImportSourceResult.setFileName(sourceFileImport.getFileName());
            if (metadata.get("User") != null) {
                fileImportSourceResult.setImportUser(Integer.valueOf(metadata.get("User").replace("U", "0")));
            }
            fileImportSourceResult.setGrain(metadata.get("Grain"));

        }
        return fileImportSourceResult;
    }
}
