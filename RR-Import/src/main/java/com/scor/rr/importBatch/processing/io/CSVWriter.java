package com.scor.rr.importBatch.processing.io;

import java.util.List;

/**
 * CSV Writer
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface CSVWriter {

	/**
	 * write CSV
	 * 
	 * @param rows
	 * @param path
	 * @param filename
	 */
	public void writeCSV(List<String> rows, String path, String filename);

}
