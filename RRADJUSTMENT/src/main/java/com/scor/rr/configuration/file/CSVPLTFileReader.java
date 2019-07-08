package com.scor.rr.configuration.file;

import com.scor.rr.configuration.utils.Constant;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;

import com.scor.rr.exceptions.fileExceptionPlt.*;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by u004602 on 27/06/2019.
 */
public class CSVPLTFileReader implements PLTFileReader {
    public List<PLTLossData> read(File file) throws RRException {
        if (file == null || !file.exists())
            throw new PLTFileNotFoundException();
        if (! "csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();

        try {
            List<PLTLossData> pltLossDatas = new ArrayList<PLTLossData>();
            Scanner sc = new Scanner(new FileReader(file));
            sc.useDelimiter("\\r\\n|;");
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNext()) {
                Integer periodIdx = Integer.valueOf(sc.next()); //Simulated Period ID
                Integer eventId = Integer.valueOf(sc.next()); //Event ID
                Date eventDate = Constant.getEventDateFormat().parse(sc.next());
                int seq = Integer.valueOf(sc.next());
                Double exp = Double.valueOf(sc.next()); //Loss
                Double loss = Double.valueOf(sc.next()); //Loss

                pltLossDatas.add(new PLTLossData(eventId,
                        eventDate.getTime(),
                        periodIdx,
                        seq,
                        exp,
                        loss));
            }
            return pltLossDatas;
        } catch (IOException | NoSuchElementException e) {
            throw new PLTFileCorruptedException();
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        } catch (ParseException e) {
            throw new EventDateFormatException();
        }
    }
}
