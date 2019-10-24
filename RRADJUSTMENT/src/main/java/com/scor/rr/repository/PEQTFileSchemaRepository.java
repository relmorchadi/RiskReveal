package com.scor.rr.repository;

import com.scor.rr.domain.importFile.PEQTFileSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PEQTFileSchemaRepository extends JpaRepository<PEQTFileSchema, String>, JpaSpecificationExecutor<PEQTFileSchema> {

    @Query("select c from PEQTFileSchema c where c.peqtFileTypeID= :peqtFileTypeID")
    List<PEQTFileSchema> findByPeqtFileTypeID(String peqtFileTypeID);
}
