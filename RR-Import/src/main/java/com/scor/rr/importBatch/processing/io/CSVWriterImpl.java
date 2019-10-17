package com.scor.rr.importBatch.processing.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * CSV Writer
 * 
 * @author HADDINI Zakariyae
 *
 */
public class CSVWriterImpl implements CSVWriter {

	private static final Logger logger = LoggerFactory.getLogger(CSVWriterImpl.class);

	/**
	 * {@inheritDoc}
	 */
	public void writeCSV(List<String> rows, String path, String filename) {
		// @formatter:off
        File file = makeFile(path, filename);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file));) {
        	rows.forEach(row -> {
        		try {
					bw.write(row);
					bw.newLine();
				} catch (IOException e) {
					logger.error("Exception : {}", e);
					
					return;
				}                
        	});

            bw.flush();
        } catch (IOException e) {
            logger.error("Exception : {}", e);
            
            throw new RuntimeException(e);
        }
        // @formatter:on
	}

	/**
	 * make file
	 * 
	 * @param path
	 * @param filename
	 * @return
	 */
	private File makeFile(String path, String filename) {
		// @formatter:off
		final Path fullPath = Paths.get(path);

		try {
			Files.createDirectories(fullPath);
		} catch (IOException e) {
			logger.error("Exception : {}", e);

			throw new RuntimeException("error creating paths " + fullPath, e);
		}
		
		return new File(fullPath.toFile(), filename);
		// @formatter:on
	}
}
