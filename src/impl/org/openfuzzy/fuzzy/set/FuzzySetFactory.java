package org.openfuzzy.fuzzy.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
			private List<String> paramNames = new ArrayList<>(Arrays.asList(new String[] { paramName }));

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

	public static IFuzzySet createTriangularShapeFuzzySet(String name, String paramName, double x1, double x2, double x3) {
		return new TriangularShapeFuzzySet(name, paramName, x1, x2, x3);
	}

	public static IFuzzySet createLeftUpLinearShapeFuzzySet(String name, String paramName, double x1, double x2) {
		return new LeftUpLinearShapeFuzzySet(name, paramName, x1, x2);
	}

	public static IFuzzySet createRightUpLinearShapeFuzzySet(String name, String paramName, double x1, double x2) {
		return new RightUpLinearShapeFuzzySet(name, paramName, x1, x2);
	}
	
	public static IFuzzySet createTrapezoidalShapeFuzzySet(String name, String paramName, double x1, double x2, double x3, double x4) {
		return new TrapezoidalShapeFuzzySet(name, paramName, x1, x2, x3, x4);
	}

	public static IFuzzySet createSingletonShapeFuzzySet(String name, String paramName, double x){
		return new SingletonShapeFuzzySet(name, paramName, x);
	}
	
	public static IFuzzySet createRectangularShapeFuzzySet(String name, String paramName, double x1, double x2){
		return new RectangularShapeFuzzySet(name, paramName, x1, x2);
	}
	
	/**
	 * Create fuzzy set from sample points. For the number of sample points n, it costs O(n^3).
	 * 
	 * @param name fuzzy set name
	 * @param paramNames
	 *            parameter names that arranged in the order of parameters of sample
	 *            point
	 * @param samplePoints
	 *            sample points
	 * @param km
	 *            Connect the pairs of nodes that km times the average of euclidean
	 *            distances of any pairs of nodes.
	 * @param kf
	 *            When calculate membership value a node, consider other nodes that
	 *            kf times the average of network distances of any pairs of nodes.
	 * @return fuzzy set
	 */
	public static IFuzzySet createFuzzySetBySample(String name, List<String> paramNames, List<double[]> samplePoints,
			double km, double kf) {
		return new FuzzySetBySample(name, paramNames, samplePoints, km, kf);
	}

}
