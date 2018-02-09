package org.openfuzzy.fuzzy.set;

import java.util.Arrays;
import java.util.List;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

public class LeftUpLinearShapeFuzzySet extends FuzzySetImpl implements IFuzzySet {
	private String name;
	private List<String> axisNames;
	private double x1, x2;

	public LeftUpLinearShapeFuzzySet(String name, String paramName, double x1, double x2) {
		this.name = name;
		this.axisNames = Arrays.asList(new String[] { paramName });
		this.x1 = x1;
		this.x2 = x2;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getParameterNames() {
		return axisNames;
	}

	@Override
	public IMembershipFunction getMembershipFunction() {
		return input -> {
			double x = input.get(axisNames.get(0));
			if (x <= x1)
				return FuzzyLogic.TRUE;
			else if (x2 <= x)
				return FuzzyLogic.FALSE;
			else if (x1 < x || x < x2)
				return FuzzyLogic.get(1.0 - (x - x1) / (x2 - x1));
			else
				throw new RuntimeException(input.toString() + " is illegal in FuzzySet : " + name);
		};
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

}
