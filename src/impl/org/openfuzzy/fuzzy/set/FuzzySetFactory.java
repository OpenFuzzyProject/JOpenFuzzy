package org.openfuzzy.fuzzy.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

/**
 * Generator for fuzzy sets.
 * 
 * @author Toru Ikeda
 *
 */
public class FuzzySetFactory {

	/**
	 * Create any fuzzy set.
	 * 
	 * @param name
	 *            fuzzy set name
	 * @param paramNames
	 *            parameter names
	 * @param mf
	 *            membership function
	 * @return the created fuzzy set
	 */
	public static IFuzzySet createFuzzySet(String name, List<String> paramNames, IMembershipFunction mf) {
		return new FuzzySetImpl() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public IMembershipFunction getMembershipFunction() {
				return mf;
			}

			@Override
			public List<String> getParameterNames() {
				return paramNames;
			}

		};
	}

	/**
	 * Create one dimensional fuzzy set.
	 * 
	 * @param name
	 *            fuzzy set name
	 * @param paramName
	 *            parameter names
	 * @param mf
	 *            membership function
	 * @return fuzzy set
	 */
	public static IFuzzySet createFuzzySet(String name, String paramName, IMembershipFunction mf) {
		return new FuzzySetImpl() {
			private List<String> axisNames = new ArrayList<>(Arrays.asList(new String[] { paramName }));

			@Override
			public String getName() {
				return name;
			}

			@Override
			public IMembershipFunction getMembershipFunction() {
				return mf;
			}

			@Override
			public List<String> getParameterNames() {
				return axisNames;
			}

		};
	}

	public static IFuzzySet createTriangleShapeFuzzySet(String name, String axisName, double x1, double x2, double x3) {
		return new TriangleShapeFuzzySet(name, axisName, x1, x2, x3);
	}

	public static IFuzzySet createLeftUpLinearShapeFuzzySet(String name, String axisName, double x1, double x2) {
		return new LeftUpLinearShapeFuzzySet(name, axisName, x1, x2);
	}

	public static IFuzzySet createRightUpLinearShapeFuzzySet(String name, String axisName, double x1, double x2) {
		return new RightUpLinearShapeFuzzySet(name, axisName, x1, x2);
	}

	/**
	 * Create fuzzy set from sample points. For the number of sample points n, it costs O(n^3).
	 * 
	 * @param name fuzzy set name
	 * @param paramNames
	 *            parameter names that arranged in the order of parameters of sample point
	 * @param samplePoints
	 *            sample points
	 * @param km Connect the pairs of nodes that km times the average of euclidean distances of any pairs of nodes.
	 * @param kf When calculate membership value a node, consider other nodes that kf times the average of network distances of any pairs of nodes.
	 * @return fuzzy set
	 */
	public static IFuzzySet createFuzzySetBySample(String name, List<String> paramNames, List<double[]> samplePoints,
			double km, double kf) {

		return new FuzzySetImpl() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public List<String> getParameterNames() {
				return paramNames;
			}

			@Override
			public IMembershipFunction getMembershipFunction() {
				// For each point x in sample points, 
				// find point y that the distance of x and is minimum in sample points that exclude x.
				Map<double[], double[]> minDistanceLinks = samplePoints.parallelStream()
						.collect(Collectors.toMap(
								x -> x, 
								x -> samplePoints.stream()
								.filter(y -> x != y)
								.sorted((a, b) -> Double.compare(euclideanDistance(x, a), euclideanDistance(x, b)))
								.findFirst().get()));

				// Calculate neighbourhood threshold value epsilon_m 
				// that is the product of Km and the average of the distance of x and y that is closest to x. 
				double epsilon_m = minDistanceLinks.entrySet().parallelStream()
						.map(link -> euclideanDistance(link.getKey(), link.getValue()))
						.mapToDouble(Double::doubleValue)
						.average().getAsDouble() * km;

				// Find the pair of x and y in sample points that the distance of is below epsilon_m.
				Map<double[], List<double[]>> links = samplePoints.parallelStream()
						.collect(Collectors
								.toMap(x -> x,
										x -> samplePoints.stream()
										.filter(y -> euclideanDistance(x, y) <= epsilon_m)
										.collect(Collectors.toList())));

				// Calculate neighbourhood threshold value epsilon_m 
				// that is the product of Kf and the average of the network distance of x and y that is closest to x. 
				double epsilon_f = minDistanceLinks.entrySet().parallelStream()
						.filter(x -> links.get(x.getKey()).size() > 0)
						.map(link -> euclideanDistance(link.getKey(), link.getValue())).mapToDouble(Double::doubleValue)
						.average().getAsDouble() * kf;

				// For each point x in sample points,
				// collect the points that the distance of x and is below epsilon_f.
				Map<double[], List<double[]>> distanceBelowEpsilonFPoints = samplePoints.stream()
						.collect(Collectors.toMap(x -> x, x -> samplePoints.stream()
								.filter(y -> networkDistance(x, y, links) <= epsilon_f).collect(Collectors.toList())));

				// Find the maximum in the numbers of collecting points of each x in sample points.
				double nMax = distanceBelowEpsilonFPoints.values().stream().mapToDouble(x -> Double.valueOf(x.size()))
						.max().getAsDouble() + 1;

				return input -> {
					// Sort input by domain names.
					double[] p = paramNames.stream().mapToDouble(name -> input.get(name)).toArray();
					// Find same points with input.
					Optional<double[]> same_p = links.keySet().stream().filter(x -> Arrays.equals(x, p)).findFirst();

					if (same_p.isPresent()) {
						return FuzzyLogic.get((double) distanceBelowEpsilonFPoints.get(same_p.get()).size() + 1 / nMax);
					} else {
						// Create simple network.
						List<double[]> pLink = samplePoints.stream().filter(x -> x != p)
								.filter(x -> euclideanDistance(p, x) <= epsilon_m).collect(Collectors.toList());
						links.put(p, pLink);
						double n = samplePoints.stream().filter(y -> networkDistance(p, y, links) <= epsilon_f).count();
						links.remove(p, pLink);
						return FuzzyLogic.get(Math.min(n + 1 / nMax, 1.0));
					}
				};
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

			private double networkDistance(double[] x, double[] y, Map<double[], List<double[]>> links) {
				return networkDistance(x, y, links, new ArrayList<>());
			}

			private double networkDistance(double[] x, double[] y, Map<double[], List<double[]>> links,
					List<double[]> visitedNodes) {
				if (links.get(x).size() == 0)
					return Double.MAX_VALUE;
				else if (visitedNodes.contains(x))
					return Double.MAX_VALUE;
				else if (x == y) {
					double sum = 0.0;
					int lastIndex = visitedNodes.size();
					for (int i = 1; i < lastIndex; i++) {
						sum += euclideanDistance(visitedNodes.get(i - 1), visitedNodes.get(i));
					}
					return sum;
				} else {
					visitedNodes.add(x);
					double min = links.get(x).stream().map(z -> networkDistance(z, y, links, visitedNodes))
							.mapToDouble(Double::doubleValue).min().getAsDouble();
					visitedNodes.remove(x);
					return min;
				}

			}

		};
	}

}
