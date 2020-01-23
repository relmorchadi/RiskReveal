package com.scor.rr.repository;

import com.scor.rr.domain.importfile.FileBasedImportConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface FileBasedImportConfigRepository extends JpaRepository<FileBasedImportConfig, Integer>, JpaSpecificationExecutor<FileBasedImportConfig> {
    @Query("select c from FileBasedImportConfig c where c.projectId = :projectId")
    FileBasedImportConfig findByProjectId(Integer projectId);
}
