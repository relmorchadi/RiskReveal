package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

/**
 * Created by u004602 on 24/06/2019.
 */
public interface PLTFileReader  {
    List<PLTLossData> read(File file) throws RRException;
}
