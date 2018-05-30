package org.openfuzzy.fuzzy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

final public class Measure {
	// Singleton
	private Measure() {}
	
	public static double euclideanDistance(double[] x, double[] y) {
		return norm2(x, y);
	}

	public static double norm2(double[] x, double[] y) {
		if (x.length != y.length)
			throw new RuntimeException("The number of dimensions is different. : ");
		double sum = 0.0;
		for (int i = 0; i < x.length; i++) {
			sum += Math.pow(x[i] - y[i], 2.0);
		}
		return Math.sqrt(sum);
	}

	public static double norm(double p, double[] x, double[] y) {
		if (x.length != y.length)
			throw new RuntimeException("The number of dimensions is different. : ");
		double sum = 0.0;
		for (int i = 0; i < x.length; i++) {
			sum += Math.pow(x[i] - y[i], p);
		}
		return Math.pow(sum, 1 / p);
	}
	
	public static double networkDistance(double[] s, double[] e, Map<double[], List<double[]>> links) {
		return networkDistance(s, links).get(e);
	}

	public static Map<double[], Double> networkDistance(double[] s, Map<double[], List<double[]>> links) {
		Set<double[]> vertexes = links.keySet();
		Map<double[], Double> d = vertexes.stream().collect(Collectors.toMap(v -> v, v -> Double.MAX_VALUE));
		d.put(s, 0.0);
		Map<double[], List<double[]>> prev = vertexes.stream()
				.collect(Collectors.toMap(v -> v, v -> new ArrayList<>()));
		Set<double[]> q = vertexes.stream().collect(Collectors.toSet());

		while (!q.isEmpty()) {
			double[] u = q.stream().min((a, b) -> Double.compare(d.get(a), d.get(b))).get();
			q.remove(u);
			links.get(u).forEach(v -> {
				double ed = d.get(u) + euclideanDistance(u, v);
				if (d.get(v) > ed) {
					d.put(v, ed);
					prev.get(v).add(u);
				}
			});
		}
		return d;
	}
}
