package org.openfuzzy.fuzzy.util.preprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

final public class Normalizer {

	// singleton
	private Normalizer() {
	}
	
	public static List<double[]> normalize(List<double[]> data) {
		return normalize(0.0, 1.0, data);
	}

	public static List<double[]> noNormalize(List<double[]> data) {
		return data;
	}
	
	public static List<double[]> normalize(double min, double max, List<double[]> data) {
		if(data.isEmpty())
			new RuntimeException("data is empty.");
		
		int dimnum = data.get(0).length;
		List<double[]> normalizedData = new ArrayList<>(data.size());
		IntStream.range(0, data.size()).forEach(n -> normalizedData.add(n, new double[dimnum]));
		
		IntStream.range(0, dimnum).forEach(i -> {
			double dmax = data.stream().mapToDouble(d -> d[i]).max().getAsDouble();
			double dmin = data.stream().mapToDouble(d -> d[i]).min().getAsDouble();
			IntStream.range(0, data.size()).forEach(n -> normalizedData.get(n)[i] = ((data.get(n)[i] - dmin) / (dmax - dmin)) * (max - min) + min);
		});
		
		return normalizedData;
	}
	
}
