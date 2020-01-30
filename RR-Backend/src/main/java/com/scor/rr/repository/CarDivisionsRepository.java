package com.scor.rr.repository;


import com.scor.rr.domain.entities.CarDivisions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarDivisionsRepository extends JpaRepository<CarDivisions, Long> {

    @Query("SELECT cd.divisionNumber FROM CarDivisions cd WHERE cd.carRequestId = :carRequestId")
    List<String> findAllDivisions(@Param("carRequestId") String carRequestId);
}