package org.openfuzzy.fuzzy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImportSamplePoints {
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
