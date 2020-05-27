package com.scor.rr.repository;

import com.scor.rr.domain.BulkImportFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkImportFileRepository extends JpaRepository<BulkImportFile, Long> {
}
