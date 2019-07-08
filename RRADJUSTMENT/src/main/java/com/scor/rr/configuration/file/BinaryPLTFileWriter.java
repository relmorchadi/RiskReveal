package com.scor.rr.configuration.file;


import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.fileExceptionPlt.PLTDataNullException;
import com.scor.rr.exceptions.fileExceptionPlt.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.fileExceptionPlt.PLTFileWriteException;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import org.apache.commons.io.FilenameUtils;
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
                buffer.putDouble(lossData.getEventDate());
                buffer.putShort((short) lossData.getSeq());
                buffer.putFloat((float) lossData.getMaxExposure());
                buffer.putFloat((float) lossData.getLoss());
            }
        } catch (IOException e) {
            throw new PLTFileWriteException(file.getPath());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
    }

    }

    private boolean closeDirectBuffer(final ByteBuffer buffer){
        if(!buffer.isDirect())
            return false;
        final DirectBuffer dbb = (DirectBuffer)buffer;
        return AccessController.doPrivileged(
                (PrivilegedAction<Object>) () -> {
                    try {
                        Cleaner cleaner = dbb.cleaner();
                        if (cleaner != null) cleaner.clean();
                        return null;
                    } catch (Exception e) {
                        return dbb;
                    }
                }
        ) == null;
    }
}
