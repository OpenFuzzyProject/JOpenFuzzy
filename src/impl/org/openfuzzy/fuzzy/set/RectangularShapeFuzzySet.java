package org.openfuzzy.fuzzy.set;

import java.util.Arrays;
import java.util.List;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

public class RectangularShapeFuzzySet extends FuzzySetImpl implements IFuzzySet {
	private String name;
	private List<String> paramNames;
	private double x1, x2;

	public RectangularShapeFuzzySet(String name, String axisName, double x1, double x2) {
		this.name = name;
		this.paramNames = Arrays.asList(new String[] { axisName });
		this.x1 = x1;
		this.x2 = x2;
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
			return FuzzyLogic.get(x1 <= x && x <= x2);
		};
	}

}
