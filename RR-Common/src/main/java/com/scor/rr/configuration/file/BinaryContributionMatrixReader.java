package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTFileCorruptedException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class BinaryContributionMatrixReader implements ContributionMatrixReader {
    @Autowired
    private BinaryPLTFileWriter binaryPLTFileWriter;
    @Override
    public Map<Integer,List<List<Double>>> read(File file, int divisionSize,int boucleSize) throws RRException {
        Map<Integer,List<List<Double>>> map = new TreeMap<>();
        if (file == null || !file.exists())
            throw new PLTFileNotFoundException();
        if (! "bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        try {
            FileChannel fc = new FileInputStream(file).getChannel();
            ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            ib.order(ByteOrder.LITTLE_ENDIAN);
            if (fc.size() % divisionSize != 0)
                throw new PLTFileCorruptedException();

            while (ib.hasRemaining() ) {
                int period = ib.getInt();
                List<Double> contributionLine = new ArrayList<>();
                for(int i=0; i< boucleSize; i++){
                    double value = ib.getFloat();
                    contributionLine.add(value);
                }
                if(map.containsKey(period)){
                    List<List<Double>> exists = map.get(period);
                    exists.add(contributionLine);
                    map.put(period,exists);
                } else{
                    List<List<Double>> newCase = new ArrayList<>();
                    newCase.add(contributionLine);
                    map.put(period,newCase);
                }


            }
            binaryPLTFileWriter.closeDirectBuffer(ib);
            fc.close();

            return map;
        } catch (IOException e) {
            throw new PLTFileCorruptedException();
        }
    }
}
