package com.scor.rr.repository;

import com.scor.rr.domain.BulkImportFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulkImportFileRepository extends JpaRepository<BulkImportFile, Long> {
    List<BulkImportFile> findByUserId(Long userId, Pageable pageable);
}
