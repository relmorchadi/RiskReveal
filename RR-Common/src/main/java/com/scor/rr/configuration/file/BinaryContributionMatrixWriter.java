package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTDataNullException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileWriteException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
@Component
public class BinaryContributionMatrixWriter implements ContributionMatrixWriter {


    @Autowired
    private BinaryPLTFileWriter binaryPLTFileWriter;

    public void write(Map<Integer, List<List<Double>>> contributionPerPhase, File file,int size, int lineSize) throws RRException {
        if (!"bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        if (contributionPerPhase == null) {
            throw new PLTDataNullException();
        }
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size*((lineSize*8)+4));
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (Map.Entry<Integer, List<List<Double>>> entry : contributionPerPhase.entrySet()) {
                for (List<Double> contribution : entry.getValue()) {
                    buffer.putInt(entry.getKey());
                    for (int i = 0; i < contribution.size(); i++) {
                        buffer.putDouble(contribution.get(i));
                    }

                }
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
                binaryPLTFileWriter.closeDirectBuffer(buffer);
            }
        }

    }

}
