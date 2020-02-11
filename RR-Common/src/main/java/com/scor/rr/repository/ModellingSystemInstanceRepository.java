package com.scor.rr.repository;

import com.scor.rr.domain.ModellingSystemInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModellingSystemInstanceRepository extends JpaRepository<ModellingSystemInstanceEntity, String> {

    @Query("select msi from ModellingSystemInstanceEntity msi" +
            " where msi.active=true")
    List<ModellingSystemInstanceEntity> findActiveInstances();

    ModellingSystemInstanceEntity findByName(String sourceModellingSystemInstance);
}
