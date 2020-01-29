package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.ContributionMatrice;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTDataNullException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileWriteException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

@Component
public class FinalContributionMatrixWriter {

    public void write(List<PLTLossData> pltLossDataList, Map<Integer, List<List<Double>>> contributionPerPhase, File file, int size, int lineSize) throws RRException {
        if (!"bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        if (contributionPerPhase == null) {
            throw new PLTDataNullException();
        }
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size*((lineSize*4)+18));
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            int index = 0;
            for (Map.Entry<Integer, List<List<Double>>> entry : contributionPerPhase.entrySet()) {
                for (List<Double> contribution : entry.getValue()) {
                    PLTLossData lossData = pltLossDataList.get(index);
                    buffer.putInt(lossData.getSimPeriod());
                    buffer.putInt(lossData.getEventId());
                    buffer.putLong(lossData.getEventDate());
                    buffer.putShort((short) lossData.getSeq());
                    for (int i = 0; i < contribution.size(); i++) {
                        buffer.putFloat(contribution.get(i).floatValue());
                    }
                    index++;

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
                FileUtils.closeDirectBuffer(buffer);
            }
        }

    }

    public void writeCorrectMatrice(List<ContributionMatrice> contMat, File file, int size, int lineSize) throws RRException {
        if (!"bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        if (contMat == null) {
            throw new PLTDataNullException();
        }
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size*((lineSize*4)+18));
            buffer.order(ByteOrder.LITTLE_ENDIAN);

                for (ContributionMatrice line : contMat) {
                    buffer.putInt(line.getSimPeriod());
                    buffer.putInt(line.getEventId());
                    buffer.putLong(line.getEventDate());
                    buffer.putShort((short) line.getSeq());
                    for (int i = 0; i < line.getContributions().size(); i++) {
                        buffer.putFloat(line.getContributions().get(i));
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
                FileUtils.closeDirectBuffer(buffer);
            }
        }
    }
}
