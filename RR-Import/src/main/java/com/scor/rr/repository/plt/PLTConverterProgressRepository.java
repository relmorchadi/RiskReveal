package com.scor.rr.repository.plt;

import com.scor.rr.domain.entities.plt.PLTConverterProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hamiani Mohammed
 * creation date  17/09/2019 at 17:57
 **/
public interface PLTConverterProgressRepository extends JpaRepository<PLTConverterProgress, Long> {
    PLTConverterProgress findByPltId(String pltId);

    List<PLTConverterProgress> findByPeqtFilename(String peqtFilename);
}
