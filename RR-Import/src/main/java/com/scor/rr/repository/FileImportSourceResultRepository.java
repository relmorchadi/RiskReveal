package com.scor.rr.repository;

import com.scor.rr.domain.importfile.FileImportSourceResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileImportSourceResultRepository extends JpaRepository<FileImportSourceResult, Integer>, JpaSpecificationExecutor<FileImportSourceResult> {
    @Query("select c from FileImportSourceResult c where c.projectId = :projectId")
    List<FileImportSourceResult> findByProjectId(Integer projectId);
}