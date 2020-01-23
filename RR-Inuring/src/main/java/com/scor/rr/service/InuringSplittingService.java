package com.scor.rr.service;

import com.scor.rr.configuration.file.BinaryPLTFileWriter;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.GroupedPLTSplitter;
import com.scor.rr.configuration.file.PLTFileWriter;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import com.scor.rr.request.InuringSplittingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InuringSplittingService {

    @Autowired
    private GroupedPLTSplitter groupedPLTSplitter;
    @Autowired
    private BinaryPLTFileWriter pltFileWriter;

    @Value(value = "${application.inuring.path}")
    private String path;
    @Value(value = "${application.inuring.separator}")
    private String separator;

    public void SplitPLTintoIndividual(InuringSplittingRequest request) throws RRException, CloneNotSupportedException {
        File groupedPlt = new File(request.getPltGroupedPath());
        if (!groupedPlt.exists()) throw new PLTFileNotFoundException();
        int index = 0;

        Map<Integer, List<PLTLossData>> returnMap = groupedPLTSplitter.contributionCorrecter(groupedPlt,
                request.getContributionMatriceLossPath(),
                request.getContributionMatriceMaxExpoPath(),
                request.getPltIds().size());

        String targetFile = path + "SplittedPLTFolder" + separator;
        boolean created1 = new File(targetFile).mkdir();

        File file = new File(targetFile + "plt"+request.getPltIds().get(index) + ".bin");
        pltFileWriter.write(returnMap.get(2), file);

        index++;

        while (index < request.getPltIds().size()) {
            File filePlt = new File(targetFile +"plt"+ request.getPltIds().get(index) + ".bin");
            List<PLTLossData> pltToWrite = groupedPLTSplitter.groupedPLTSplitter(returnMap.get(1),
                    request.getContributionMatriceLossPath(),
                    request.getContributionMatriceMaxExpoPath(),
                    request.getPltIds().size(),
                    index);


            pltFileWriter.write(pltToWrite, filePlt);
            index++;
        }


    }
}
