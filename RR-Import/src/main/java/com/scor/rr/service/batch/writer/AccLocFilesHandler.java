package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.enums.XLTSubType;
import com.scor.rr.util.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@StepScope
@Slf4j
public class AccLocFilesHandler extends AbstractWriter {

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{jobParameters['division']}")
    private String division;

    @Value("#{jobParameters['carId']}")
    private String carId;

    @Value("#{jobParameters['jobType']}")
    private String jobType;

    @Value("${ihub.treaty.out.path}")
    private String iHub;

    public RepeatStatus copyFilesToIHub() {

        String targetAccFileName = this.makeFacFilename("A", new Date(), XLTSubType.ACC, "", ".txt");
        String targetLocFileName = this.makeFacFilename("A", new Date(), XLTSubType.LOC, "", ".txt");

        String sourceAccFileName = division + "_" + carId;
        String sourceLocFileName = division + "_" + carId;

        final Path iHubPath = Paths.get(iHub);

        final Path sourcePath = iHubPath.resolve("tmp");
        final Path targetPath = iHubPath.resolve(PathUtils.getPrefixDirectoryFac(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId), carId));

        try {
            this.doCopy(sourceAccFileName, targetAccFileName, "", ".acc", sourcePath, targetPath);
            this.doCopy(sourceLocFileName, targetLocFileName, "", ".loc", sourcePath, targetPath);
        } catch (IOException ex) {
            log.error("reading/writing error has occurred while copying forewriter files to the iHub");
            ex.printStackTrace();
            return RepeatStatus.valueOf("failed");
        } catch (Exception ex) {
            log.error("unknown error has occurred while copying forewriter files to the iHub");
            ex.printStackTrace();
            return RepeatStatus.valueOf("failed");
        }

        return RepeatStatus.FINISHED;
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
