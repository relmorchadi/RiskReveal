package com.scor.rr.configuration.file;

import com.scor.rr.configuration.utils.Constant;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTDataNullException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileWriteException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@Component
public class ContributionMatrixCSVWriter implements ContributionMatrixCSVWriterRepository{
    public void write(List<PLTLossData> pltLossDataList, Map<Integer, List<List<Double>>> contributionPerPhase, File file,List<Long> listOfPLTId) throws RRException {
        if (! "csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        if (pltLossDataList == null) {
            throw new PLTDataNullException();
        }
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            StringBuilder header = new StringBuilder();
            header.append("Period;EventID;EventDate;Seq;");
            for (Long pltId: listOfPLTId
                 ) {
                header.append(pltId).append(";");
            }
            bw.write(header.toString());
            bw.newLine();
            int index = 0;
            for (Map.Entry<Integer, List<List<Double>>> entry : contributionPerPhase.entrySet()) {
                for (List<Double> contribution : entry.getValue()) {
                    PLTLossData lossData = pltLossDataList.get(index);
                    StringBuilder sb = new StringBuilder();
                    sb.append(lossData.getSimPeriod()).append(";").
                            append(lossData.getEventId()).append(";").
                            append(Constant.getEventDateFormat().format(lossData.getEventDate())).append(";").
                            append(lossData.getSeq()).append(";");
                    for (int i = 0; i < contribution.size(); i++) {
                        sb.append(contribution.get(i).floatValue()).append(";");
                    }
                    index++;
                    bw.write(sb.toString());
                    bw.newLine();
                }
            }
            bw.flush();
        } catch (IOException e) {
            throw new PLTFileWriteException(file.getPath());
        }
    }
}
