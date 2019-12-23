package com.scor.rr.configuration.file;


import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTDataNullException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileWriteException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;

/**
 * Created by u004602 on 28/06/2019.
 */
@Component
public class BinaryPLTFileWriter implements PLTFileWriter {
    public void write(List<PLTLossData> pltLossDataList, File file) throws RRException {
        if (! "bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        if (pltLossDataList == null) {
            throw new PLTDataNullException();
        }
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            int size = pltLossDataList.size() * 26;
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (PLTLossData lossData : pltLossDataList) {
                buffer.putInt(lossData.getSimPeriod());
                buffer.putInt(lossData.getEventId());
                buffer.putLong(lossData.getEventDate());
                buffer.putShort((short) lossData.getSeq());
                buffer.putFloat((float) lossData.getMaxExposure());
                buffer.putFloat((float) lossData.getLoss());
            }
        } catch (IOException e) {
            throw new PLTFileWriteException(file.getPath());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (buffer != null) {
                FileUtils.closeDirectBuffer(buffer);
            }
    }

    }

}
