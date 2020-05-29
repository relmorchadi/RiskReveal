package com.scor.rr.service.bulkImport.abstraction;

import com.scor.rr.domain.BulkImportFile;
import com.scor.rr.domain.dto.ValidationDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BulkImportService {

    BulkImportFile uploadFile(MultipartFile file);

    ValidationDto validateFile(BulkImportFile file);

    void importFile(Long id);

    List<BulkImportFile> getImportHistory(int page, int records, Long userId);
}
