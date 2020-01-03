package com.scor.rr.repository;

import com.scor.rr.domain.ProjectConfigurationForeWriterDivision;
import com.scor.rr.domain.dto.CARDivisionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by u004602 on 27/12/2019.
 */
public interface ProjectConfigurationForeWriterDivisionRepository extends JpaRepository<ProjectConfigurationForeWriterDivision, Long> {

    @Query(value ="exec dbonew.usp_DivisionListForCarID @CARID =:CARID", nativeQuery = true)
    List<Map<String, Object>> findByCARId(@Param("CARID") String carId);
}
