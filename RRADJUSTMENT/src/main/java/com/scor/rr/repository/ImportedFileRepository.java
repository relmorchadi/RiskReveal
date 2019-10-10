package com.scor.rr.repository;

import com.scor.rr.domain.importFile.ImportedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportedFileRepository extends JpaRepository<ImportedFileEntity,Integer> {
}
