package com.scor.rr.repository;

import com.scor.rr.domain.ModellingSystemInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModellingSystemInstanceRepository extends JpaRepository<ModellingSystemInstanceEntity, String> {

    @Query("select msi.modellingSystemInstanceId from ModellingSystemInstanceEntity msi")
    List<String> findInstanceCodes();

}
