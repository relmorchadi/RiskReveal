package com.scor.rr.repository;

import com.scor.rr.entity.InuringPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by u004602 on 11/09/2019.
 */
@Repository
public interface InuringPackageRepository extends JpaRepository<InuringPackage, Integer> {
    InuringPackage findByInuringPackageId(int inuringPackageId);
}
