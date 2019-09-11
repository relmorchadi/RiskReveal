package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;

public class MultiExtentionReadPltFile implements PLTFileReader{

    @Override
    public List<PLTLossData> read(File file) throws RRException {
        if (file == null || !file.exists())
            throw new PLTFileNotFoundException();
        if ("csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))){
            return new CSVPLTFileReader().read(file);
        } else  if ( "bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
            return new BinaryPLTFileReader().read(file);
        } else {
            throw new PLTFileExtNotSupportedException();
        }
    }
}
