package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Map;
@Repository
public interface ContributionMatrixCSVWriterRepository {
    void write(List<PLTLossData> pltLossDataList, Map<Integer, List<List<Double>>> contributionPerPhase, File file,List<Long> listOfPLTId) throws RRException;
}
