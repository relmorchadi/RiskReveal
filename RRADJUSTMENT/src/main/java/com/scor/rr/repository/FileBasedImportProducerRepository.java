package com.scor.rr.repository;

import com.scor.rr.domain.importFile.FileBasedImportProducer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileBasedImportProducerRepository extends JpaRepository<FileBasedImportProducer,Integer> {
}
