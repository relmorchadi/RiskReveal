package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.ContributionMatrice;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTFileCorruptedException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
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
public class FinalContributionMatrixReader {
    public List<ContributionMatrice> read(File file, int boucleSize) throws RRException {
        List<ContributionMatrice> chunkContribution = new ArrayList<>();
        if (file == null || !file.exists())
            throw new PLTFileNotFoundException();
        if (! "bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        try {
            FileChannel fc = new FileInputStream(file).getChannel();
            ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            ib.order(ByteOrder.LITTLE_ENDIAN);
            if (fc.size() % ((boucleSize*4)+18) != 0)
                throw new PLTFileCorruptedException();


            ContributionMatrice moldLine = new ContributionMatrice();
            while (ib.hasRemaining() ) {
                List<Float> contribution = new ArrayList<>();
                ContributionMatrice line = (ContributionMatrice) moldLine.clone();

                line.setSimPeriod(ib.getInt());
                line.setEventId(ib.getInt());
                line.setEventDate(ib.getLong());
                line.setSeq(ib.getShort());

                for(int i=0; i< boucleSize; i++){
                    contribution.add(ib.getFloat());
                }
                line.setContributions(contribution);

               chunkContribution.add(line);


            }
            FileUtils.closeDirectBuffer(ib);
            fc.close();

            return chunkContribution;
        } catch (IOException | CloneNotSupportedException e) {
            throw new PLTFileCorruptedException();
        }
    }
}
