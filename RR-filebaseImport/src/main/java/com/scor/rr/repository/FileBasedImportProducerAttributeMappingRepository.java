package com.scor.rr.repository;

import com.scor.rr.domain.importfile.FileBasedImportProducerAttributeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileBasedImportProducerAttributeMappingRepository extends JpaRepository<FileBasedImportProducerAttributeMapping,Integer> {
}
