package org.openfuzzy.fuzzy.set;

import java.util.Arrays;
import java.util.List;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

public class SingletonShapeFuzzySet extends FuzzySetImpl implements IFuzzySet {

	private String name;
	private List<String> paramNames;
	private double x1;

	public SingletonShapeFuzzySet(String name, String paramName, double x) {
		this.name = name;
		this.paramNames = Arrays.asList(new String[] { paramName });
		this.x1 = x;
	}

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
		return input -> {
			double x = input.get(paramNames.get(0));
			return FuzzyLogic.get(x == x1);
		};
	}

}
