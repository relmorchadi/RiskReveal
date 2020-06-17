package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.ProjectConfigurationForeWriterFiles;
import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.domain.enums.XLTSubType;
import com.scor.rr.repository.ProjectConfigurationForeWriterFilesRepository;
import com.scor.rr.repository.ProjectConfigurationForeWriterRepository;
import com.scor.rr.service.abstraction.JobManager;
import com.scor.rr.service.state.TransformationPackage;
import com.scor.rr.util.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@StepScope
@Slf4j
@Service
public class AccLocFilesHandler extends AbstractWriter {

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ProjectConfigurationForeWriterFilesRepository projectConfigurationForeWriterFilesRepository;

    @Autowired
    private ProjectConfigurationForeWriterRepository projectConfigurationForeWriterRepository;

    @Autowired
    @Qualifier("jobManagerImpl")
    private JobManager jobManager;

    @Value("#{jobParameters['taskId']}")
    private String taskId;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{jobParameters['carId']}")
    private String carId;

    @Value("#{jobParameters['division']}")
    private String division;

    @Value("#{jobParameters['jobType']}")
    private String jobType;

    @Value("#{stepExecution.jobExecution.jobId}")
    private Long jobId;

    @Value("${ihub.treaty.out.path}")
    private String iHub;

    public RepeatStatus copyFilesToIHub() {

        StepEntity step = jobManager.createStep(Long.valueOf(taskId), "Extract_LOC_ACC_FW", 17);
        try {

            if (transformationPackage.getModelPortfolios() != null && !transformationPackage.getModelPortfolios().isEmpty()) {

                Integer division = transformationPackage.getModelPortfolios().get(0).getDivision();

                String targetAccFileName = this.makeFacFilename("A", new Date(), XLTSubType.ACC, "", ".txt", division);
                String targetLocFileName = this.makeFacFilename("A", new Date(), XLTSubType.LOC, "", ".txt", division);

                // TODO: Review this later
                String sourceAccFileName = carId + "_" + this.division + "_" + jobId;
                String sourceLocFileName = carId + "_" + this.division + "_" + jobId;

                final Path iHubPath = Paths.get(iHub);

                final Path sourcePath = iHubPath.resolve("tmp");
                final Path targetPath = iHubPath.resolve(PathUtils.getPrefixDirectoryFac(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), division, carId, importSequence));

                ProjectConfigurationForeWriterFiles accLocFile = new ProjectConfigurationForeWriterFiles();
                accLocFile.setProjectConfigurationForeWriterId(
                        projectConfigurationForeWriterRepository.findByProjectId(
                                transformationPackage.getModelPortfolios().get(0).getProjectId()
                        ).getProjectConfigurationForeWriterId()
                );
                accLocFile.setEntity(1);

                try {
                    accLocFile.setAccFileName(targetPath + "/" + this.doCopy(sourceAccFileName, targetAccFileName, "", ".acc", sourcePath, targetPath));
                    accLocFile.setLocFileName(targetPath + "/" + this.doCopy(sourceLocFileName, targetLocFileName, "", ".loc", sourcePath, targetPath));
                    projectConfigurationForeWriterFilesRepository.saveAndFlush(accLocFile);
                    jobManager.logStep(step.getStepId(), StepStatus.SUCCEEDED);
                } catch (IOException ex) {
                    log.error("reading/writing error has occurred while copying forewriter files to the iHub");
                    jobManager.onTaskError(Long.valueOf(taskId));
                    jobManager.logStep(step.getStepId(), StepStatus.FAILED);
                    ex.printStackTrace();
                    return RepeatStatus.FINISHED;
                } catch (Exception ex) {
                    log.error("unknown error has occurred while copying forewriter files to the iHub");
                    jobManager.onTaskError(Long.valueOf(taskId));
                    jobManager.logStep(step.getStepId(), StepStatus.FAILED);
                    ex.printStackTrace();
                    return RepeatStatus.FINISHED;
                }
            }
            return RepeatStatus.FINISHED;
        } catch (Exception ex) {
            jobManager.onTaskError(Long.valueOf(taskId));
            jobManager.logStep(step.getStepId(), StepStatus.FAILED);
            ex.printStackTrace();
            return RepeatStatus.FINISHED;
        }
    }

    private String doCopy(String baseSourceName, String baseTargetName, String complement, String sourceExtension, Path sourcePath, Path targetPath) throws IOException {
        String targetFileName = baseTargetName + complement;
        String finalTargetFileName = targetFileName.trim();//.replaceAll(" +", " ").replaceAll("[^-a-zA-Z0-9\\s]", "").replaceAll(" ", "-").replaceAll("\\.", "");

        final Path tmpSourcePath = sourcePath.resolve(baseSourceName + sourceExtension);
        final Path targetFilePath = targetPath.resolve(finalTargetFileName);
        log.info("writing " + finalTargetFileName);
        Files.createDirectories(targetPath);
        Files.copy(tmpSourcePath, targetFilePath);

        return finalTargetFileName;
    }
}
