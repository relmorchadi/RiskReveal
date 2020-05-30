package com.scor.rr.repository;

import com.scor.rr.domain.importfile.RefFileBasedImportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RefFileBasedImportRepository extends JpaRepository<RefFileBasedImportEntity, String> {


    @Query("from RefFileBasedImportEntity e where e.modellingVendor=:modellingVendor and e.modellingSystem=:modellingSystem and e.peril=:peril and e.eventSetId=:eventSetId")
    RefFileBasedImportEntity findByModellingVendorAndModellingSystemAndPerilAndEventSetId(
            @Param("modellingVendor") String modellingVendor,
            @Param("modellingSystem") String modellingSystem,
            @Param("peril") String peril,
            //Integer eventSetId  //commented
           @Param("eventSetId") Integer eventSetId  //commented
    );


    @Query(value = "SELECT c.targetRapCode\n" +
            "FROM dbo.RefFileBasedImport a\n" +
            "    LEFT JOIN dbo.RefPET b\n" +
            "        ON b.rmsSimulationSetId = a.eventSetId\n" +
            "    LEFT JOIN dbo.RefTargetRap c\n" +
            "        ON c.petId = b.petId\n" +
            "WHERE a.id=:referenceId", nativeQuery = true)
    List<String> findTargetRapsCodesByRefFileBasedId(
            @Param("referenceId") String referenceId
    );



}
