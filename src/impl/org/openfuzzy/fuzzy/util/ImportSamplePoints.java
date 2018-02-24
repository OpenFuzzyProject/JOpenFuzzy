package org.openfuzzy.fuzzy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Importer of the files written sample points for multi-dimensional fuzzy
 * set.
 * 
 * @author Toru Ikeda
 *
 */
public class ImportSamplePoints {

	// singleton class
	private ImportSamplePoints() {
	}

	/**
	 * <p>
	 * Read TSV file written sample points without header.
	 * 
	 * <p>
	 * It is need to put 1 sample point into 1 line like the follow.<br>
	 * 
	 * <pre>
	 * 36\t70\n
	 * 39\t60\n
	 * 39\t80\n
	 * </pre>
	 * 
	 * The each number strings  read from file are cast to Double number.
	 * So the string format have to be able to cast by Double::valueOf.
	 * 
	 * @see java.lang.Double
	 * 
	 * @param file
	 *            TSV file written sample points
	 * 
	 * @return sample points list
	 */
	public static List<double[]> fromTSV(File file) {
		List<double[]> samplePoints = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			samplePoints = reader.lines()
					.map(line -> Arrays.asList(line.split("\t")).stream().mapToDouble(Double::valueOf).toArray())
					.collect(Collectors.toList());

			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return samplePoints;
	}
}
