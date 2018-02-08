package org.openfuzzy.fuzzy.set;

import java.util.Arrays;
import java.util.List;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

public class TrapezoidalShapeFuzzySet extends FuzzySetImpl implements IFuzzySet {
	private String name;
	private List<String> paramNames;
	private double x1, x2, x3, x4;

	public TrapezoidalShapeFuzzySet(String name, String axisName, double x1, double x2, double x3, double x4) {
		this.name = name;
		this.paramNames = Arrays.asList(new String[] { axisName });
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.x4 = x4;
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
			if (x <= x1 || x4 <= x)
				return FuzzyLogic.FALSE;
			else if (x2 <= x || x <= x3)
				return FuzzyLogic.TRUE;
			else if (x < x2)
				return FuzzyLogic.get((x - x1) / (x2 - x1));
			else if (x3 < x)
				return FuzzyLogic.get((x4 - x) / (x4 - x3));
			else
				throw new RuntimeException(input.toString() + " is illegal in FuzzySet : " + name);
		};
	}

}
