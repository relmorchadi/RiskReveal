package com.scor.rr.repository;

import com.scor.rr.domain.ModellingSystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModellingSystemRepository extends JpaRepository<ModellingSystemEntity, String> {


    @Query("from ModellingSystemEntity mse where mse.vendor.id=:vendorId and mse.name=:name")
    ModellingSystemEntity findByVendorIdAndName(@Param("vendorId") String vendorId, @Param("name") String name);

}
