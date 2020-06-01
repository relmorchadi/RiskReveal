package com.scor.rr.repository;

import com.scor.rr.domain.ModellingVendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModellingVendorRepository extends JpaRepository<ModellingVendorEntity, String> {

    ModellingVendorEntity findByName(String name);

}
