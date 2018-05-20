package org.openfuzzy.fuzzy.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

class MembershipFunctionBySample implements IMembershipFunction {
	private List<double[]> samplePoints;
	private List<String> paramNames;
	private Map<double[], double[]> minDistanceLinks;
	double epsilon_m;
	Map<double[], List<double[]>> links;
	double epsilon_f;
	Map<double[], List<double[]>> distanceBelowEpsilonFPoints;
	double nMax;

	public MembershipFunctionBySample(List<String> paramNames, List<double[]> samplePoints, double km, double kf) {
		this.samplePoints = samplePoints;
		this.paramNames = paramNames;
		
		// For each point x in sample points,
		// find point y that the distance of x and is minimum in sample points that
		// exclude x.
		// O(n^2)
		minDistanceLinks = samplePoints.parallelStream()
				.collect(Collectors.toMap(x -> x,
						x -> samplePoints.stream().filter(y -> x != y)
								.sorted((a, b) -> Double.compare(euclideanDistance(x, a), euclideanDistance(x, b)))
								.findFirst().get()));
		System.out.println("minDistanceLinks = " + minDistanceLinks);

		// Calculate neighbourhood threshold value epsilon_m
		// that is the product of Km and the average of the distance of x and y that is
		// closest to x.
		// O(n)
		epsilon_m = minDistanceLinks.entrySet().parallelStream()
				.map(link -> euclideanDistance(link.getKey(), link.getValue())).mapToDouble(Double::doubleValue)
				.average().getAsDouble() * km;
		System.out.println("epsilon_m = " + epsilon_m);

		// Find the pair of x and y in sample points that the distance of is below
		// epsilon_m.
		// O(n^2)
		links = samplePoints.parallelStream().collect(Collectors.toMap(x -> x, x -> samplePoints.stream()
				.filter(y -> euclideanDistance(x, y) <= epsilon_m).collect(Collectors.toList())));
		System.out.println("links = " + links);
		
		// Calculate neighbourhood threshold value epsilon_m
		// that is the product of Kf and the average of the network distance of x and y
		// that is closest to x.
		// O(n)
		epsilon_f = minDistanceLinks.entrySet().parallelStream().filter(x -> links.get(x.getKey()).size() > 0)
				.map(link -> euclideanDistance(link.getKey(), link.getValue())).mapToDouble(Double::doubleValue)
				.average().getAsDouble() * kf;
		System.out.println("epsilon_f = " + epsilon_f);

		// For each point x in sample points,
		// collect the points that the network distance of x and is below epsilon_f.
		distanceBelowEpsilonFPoints = samplePoints.stream().collect(Collectors.toMap(x -> x, x -> samplePoints
				.stream().filter(y -> networkDistance(x, y, links) <= epsilon_f).collect(Collectors.toList())));
		System.out.println("distanceBelowEpsilonFPoints = " + distanceBelowEpsilonFPoints);

		// Find the maximum in the numbers of collecting points of each x in sample
		// points.
		nMax = distanceBelowEpsilonFPoints.values().stream().mapToDouble(x -> Double.valueOf(x.size())).max()
				.getAsDouble() + 1;
	}

	@Override
	public FuzzyLogic mv(Map<String, Double> input) {
		// Sort input by domain names.
		double[] p = paramNames.stream().mapToDouble(name -> input.get(name)).toArray();
		// Find same point in sample points with input.
		Optional<double[]> same_p = links.keySet().stream().filter(x -> Arrays.equals(x, p)).findFirst();

		if (same_p.isPresent()) {
			return FuzzyLogic.get(((double) distanceBelowEpsilonFPoints.get(same_p.get()).size() + 1) / nMax);
		} else {
			// collect K points from sample points. K is half the number of sample points.
			List<double[]> nearPoints = samplePoints.stream()
					.sorted((x, y) -> Double.compare(euclideanDistance(x, p), euclideanDistance(y, p)))
					.collect(Collectors.toList()).subList(0, samplePoints.size() / 2);

			double weightedMVSum = nearPoints.stream().mapToDouble(x -> (1.0 / euclideanDistance(x, p))
					* (((double) distanceBelowEpsilonFPoints.get(x).size() + 1) / nMax)).sum();

			double weighteSum = nearPoints.stream().mapToDouble(x -> (1.0 / euclideanDistance(x, p))).sum();

			return FuzzyLogic.get(weightedMVSum / weighteSum);
		}
	}
	
	private double euclideanDistance(double[] x, double[] y) {
		if (x.length != y.length)
			throw new RuntimeException("The number of dimensions is different. : ");
		double sum = 0.0;
		for (int i = 0; i < x.length; i++) {
			sum += Math.pow(x[i] - y[i], 2.0);
		}
		return Math.sqrt(sum);
	}

	private Map<double[], Map<double[], Double>> minNetWorkDistances = new HashMap<>();
	private double networkDistance(double[] s, double[] e, Map<double[], List<double[]>> links) {
		if(minNetWorkDistances.containsKey(s))
			return minNetWorkDistances.get(s).get(e);
		Set<double[]> vertexes = links.keySet();
		Map<double[], Double> d = vertexes.stream().collect(Collectors.toMap(v -> v, v -> Double.MAX_VALUE));
		d.put(s, 0.0);
		Map<double[], List<double[]>> prev = vertexes.stream().collect(Collectors.toMap(v -> v, v -> new ArrayList<>()));
		Set<double[]> q = vertexes.stream().collect(Collectors.toSet());
		
		while(!q.isEmpty()) {
			double[] u = q.stream().min((a, b) -> Double.compare(d.get(a), d.get(b))).get();
			q.remove(u);
			links.get(u).forEach(v -> {
				double ed = d.get(u) + euclideanDistance(u, v);
				if(d.get(v) > ed) {
					d.put(v, ed);
					prev.get(v).add(u);
				}
			});
		}
		minNetWorkDistances.put(s, d);
		return d.get(e);
//		return networkDistance(x, y, links, new ArrayList<>());
	}
}