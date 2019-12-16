package com.scor.rr.configuration.file;

import com.scor.rr.exceptions.RRException;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ContributionMatrixReader {
    Map<Integer,List<List<Double>>> read(File file, int divisionSize, int boucleSize) throws RRException;
}
