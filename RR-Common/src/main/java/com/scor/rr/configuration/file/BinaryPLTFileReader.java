package com.scor.rr.configuration.file;


import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTFileCorruptedException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u004602 on 24/06/2019.
 */
@Component
public class BinaryPLTFileReader implements PLTFileReader {
    public List<PLTLossData> read(File file) throws RRException {
        if (file == null || !file.exists())
            throw new PLTFileNotFoundException();
        if (! "bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        try {
            FileChannel fc = new FileInputStream(file).getChannel();
            ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            ib.order(ByteOrder.LITTLE_ENDIAN);
            if (fc.size() % 26 != 0)
                throw new PLTFileCorruptedException();
            List<PLTLossData> pltLossDatas = new ArrayList<>((int) fc.size() / 26);
            while (ib.hasRemaining()) {
                int period = ib.getInt();
                int eventId = ib.getInt();
                long eventDate = ib.getLong();
                short seq = ib.getShort();
                float exposure = ib.getFloat();
                float loss = ib.getFloat();
                PLTLossData lossData = new PLTLossData(period, eventId, eventDate, seq, exposure, loss);
                pltLossDatas.add(lossData);
            }


            FileUtils.closeDirectBuffer(ib);
            fc.close();

            return pltLossDatas;
        } catch (IOException e) {
            throw new PLTFileCorruptedException();
        }
    }
}
