package com.scor.rr.repository;

import com.scor.rr.entity.InuringPackageProcessingExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Repository
public interface InuringPackageProcessingExchangeRateRepository extends JpaRepository<InuringPackageProcessingExchangeRate, Integer> {
}
