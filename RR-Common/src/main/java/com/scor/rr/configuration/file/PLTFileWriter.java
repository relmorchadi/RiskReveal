package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;

import java.io.File;
import java.util.List;

/**
 * Created by u004602 on 24/06/2019.
 */
public interface PLTFileWriter {
    void write(List<PLTLossData> pltLossDataList, File file) throws RRException;
}
