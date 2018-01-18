package org.openfuzzy.fuzzy.set;

import java.util.Arrays;
import java.util.List;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

/**
 * An triangular shape fuzzy set
 * 
 * @author Toru Ikeda
 *
 */
public class TriangleShapeFuzzySet extends FuzzySetImpl implements IFuzzySet {
	private String name;
	private List<String> axisNames;
	private double x1, x2, x3;

	public TriangleShapeFuzzySet(String name, String axisName, double x1, double x2, double x3) {
		this.name = name;
		this.axisNames = Arrays.asList(new String[] {axisName});
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
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
			if (x <= x1 || x3 <= x)
				return FuzzyLogic.FALSE;
			else if (x == x2)
				return FuzzyLogic.TRUE;
			else if (x < x2)
				return FuzzyLogic.get((x - x1) / (x2 - x1));
			else if (x2 < x)
				return FuzzyLogic.get((x3 - x) / (x3 - x2));
			else
				throw new RuntimeException(input.toString() + " is illegal in FuzzySet : " + name);
		};
	}

}
