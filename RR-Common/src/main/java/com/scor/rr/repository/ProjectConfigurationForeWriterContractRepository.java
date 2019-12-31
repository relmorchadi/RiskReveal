package com.scor.rr.repository;

import com.scor.rr.domain.ProjectConfigurationForeWriterContract;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by u004602 on 27/12/2019.
 */
public interface ProjectConfigurationForeWriterContractRepository extends JpaRepository<ProjectConfigurationForeWriterContract, Long> {
    List<ProjectConfigurationForeWriterContract> findByContractIdAndUwYear(String contractId, int uwYear);
}
