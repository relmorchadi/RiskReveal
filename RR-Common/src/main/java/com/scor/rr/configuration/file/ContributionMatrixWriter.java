package com.scor.rr.configuration.file;

import com.scor.rr.exceptions.RRException;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ContributionMatrixWriter {

    void write(Map<Integer, List<List<Double>>> contributionPerPeriod, File file, int size, int lineSize) throws RRException;
}
