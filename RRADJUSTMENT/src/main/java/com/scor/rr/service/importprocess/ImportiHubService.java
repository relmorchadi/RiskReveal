package com.scor.rr.service.importprocess;

import com.scor.rr.configuration.file.CopyFile;
import com.scor.rr.configuration.utils.LossDataFileUtils;
import com.scor.rr.domain.MetadataHeaderSectionEntity;
import com.scor.rr.domain.MetadataHeaderSegmentEntity;
import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.repository.ImportedFileRepository;
import com.scor.rr.repository.MetadataHeaderSectionRepository;
import com.scor.rr.repository.MetadataHeaderSegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ImportiHubService {

    @Autowired
    MetadataHeaderSectionRepository metadataHeader;

    @Autowired
    MetadataHeaderSegmentRepository headerSegment;

    @Autowired
    ImportedFileRepository importedFileRepository;

    private static final String PATH_IHUB = "RRADJUSTMENT/src/main/resources/copyfile/";

    public void copyFileToiHub(String path) throws IOException {
        File file = new File(path);
        CopyFile.copyFileFromPath(file,PATH_IHUB);
    }

    public List<ImportFilePLTData> getPltFromLossDataFile(String path) {
        return LossDataFileUtils.getPltFromLossDataFile(path);
    }

    public boolean verifyFilePlt(String path) {
        List<MetadataHeaderSectionEntity> metadataHeaders = metadataHeader.findAll();
        List<MetadataHeaderSegmentEntity> headerSegments = headerSegment.findAll();
        return LossDataFileUtils.verifyFile(metadataHeaders,headerSegments,path,importedFileRepository);

    }
}
