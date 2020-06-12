package com.scor.rr.service.bulkImport.abstraction;

import com.scor.rr.domain.BulkImportFile;
import com.scor.rr.domain.dto.ValidationDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BulkImportService {

    BulkImportFile uploadFile(MultipartFile file);

    ValidationDto validateFile(BulkImportFile file);

    void importFile(Long id, String projectName, String projectDescription, Boolean createWorkspace);

    Page<BulkImportFile> getImportHistory(int page, int records);
}
