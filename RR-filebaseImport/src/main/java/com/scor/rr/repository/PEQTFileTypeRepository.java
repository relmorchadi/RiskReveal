package com.scor.rr.repository;

import com.scor.rr.domain.importfile.PEQTFileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PEQTFileTypeRepository extends JpaRepository<PEQTFileType, String>, JpaSpecificationExecutor<PEQTFileType> {

//    @Query("select c from PEQTFileType c where c.modelProvider= :modelProvider and concat(c.uwYear,'')= :uwy order by c.modelProvider asc, c.modelSystem asc")
    @Query("select c from PEQTFileType c where c.modelProvider= :modelProvider and c.modelSystem= :modelSystem and c.modelSystemVersion= :modelSystemVersion")
    PEQTFileType findPEQTFileType(String modelProvider,
                                  String modelSystem,
                                  String modelSystemVersion);

    @Query("select c from PEQTFileType c where c.id= :id")
    PEQTFileType findByIdString(String id);
}
