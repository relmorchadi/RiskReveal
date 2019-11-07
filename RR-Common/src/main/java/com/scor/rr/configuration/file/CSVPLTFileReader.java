package com.scor.rr.configuration.file;

import com.scor.rr.configuration.utils.Constant;
import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;

import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.EventDateFormatException;
import com.scor.rr.exceptions.pltfile.PLTFileCorruptedException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
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
                int periodIdx = Integer.parseInt(sc.next()); //Simulated Period ID
                int eventId = Integer.parseInt(sc.next()); //Event ID
                Date eventDate = Constant.getEventDateFormat().parse(sc.next());
                int seq = Integer.parseInt(sc.next().replace(" ",""));
                double exp = Double.parseDouble(sc.next().replace(" ","")); //Loss
                double loss = Double.parseDouble(sc.next().replace(" ","")); //Loss

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

    public List<PEATData> readPeatData(File file) throws RRException {
        if (file == null || !file.exists())
            throw new PLTFileNotFoundException();
        if (! "csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();

        try {
            List<PEATData> peatData = new ArrayList<>();
            Scanner sc = new Scanner(new FileReader(file));
            sc.useDelimiter("\\r\\n|;");
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNext()) {
                int eventId = Integer.parseInt(sc.next()); //Simulated Period ID
                int simPeriod = Integer.parseInt(sc.next()); //Event ID
                int seq = Integer.parseInt(sc.next());
                double lmf = Double.parseDouble(sc.next()); //Lmf

                peatData.add(new PEATData(eventId,
                        simPeriod,
                        seq,
                        lmf));
            }
            return peatData;
        } catch (IOException | NoSuchElementException e) {
            throw new PLTFileCorruptedException();
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

    public List<AdjustmentReturnPeriodBandingParameterEntity> readAdjustmentReturnPeriodBanding(File file) throws RRException {
        if (file == null || !file.exists())
            throw new PLTFileNotFoundException();
        if (! "csv".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();

        try {
            List<AdjustmentReturnPeriodBandingParameterEntity> returnPeriodBendings = new ArrayList<>();
            Scanner sc = new Scanner(new FileReader(file));
            sc.useDelimiter("\\r\\n|;");
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNext()) {
                Double returnPeriod = Double.parseDouble(sc.next()); //Simulated Period ID
                Double lmf = Double.parseDouble(sc.next()); //Lmf

                returnPeriodBendings.add(new AdjustmentReturnPeriodBandingParameterEntity(returnPeriod,
                        lmf));
            }
            return returnPeriodBendings;
        } catch (IOException | NoSuchElementException e) {
            throw new PLTFileCorruptedException();
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }


}
