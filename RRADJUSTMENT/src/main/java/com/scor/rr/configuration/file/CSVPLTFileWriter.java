package com.scor.rr.configuration.file;

import com.scor.rr.configuration.utils.Constant;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.fileExceptionPlt.PLTDataNullException;
import com.scor.rr.exceptions.fileExceptionPlt.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.fileExceptionPlt.PLTFileWriteException;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Date;
import java.util.List;

/**
 * Created by u004602 on 28/06/2019.
 */
public class CSVPLTFileWriter implements PLTFileWriter {
    public void write(List<PLTLossData> pltLossDataList, File file) throws RRException {
        if (! "csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        if (pltLossDataList == null) {
            throw new PLTDataNullException();
        }
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Period;EventID;EventDate;Seq;MaxExp;Loss");
            bw.newLine();
            for (PLTLossData lossData : pltLossDataList) {
                StringBuilder sb = new StringBuilder();
                sb.append(lossData.getSimPeriod()).append(";").
                        append(lossData.getEventId()).append(";").
                        append(Constant.getEventDateFormat().format(lossData.getEventDate())).append(";").
                        append(lossData.getSeq()).append(";").
                        append(lossData.getMaxExposure()).append(";").
                        append(lossData.getLoss());
                bw.write(sb.toString());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new PLTFileWriteException(file.getPath());
        }
    }
}
