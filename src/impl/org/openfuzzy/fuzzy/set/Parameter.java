package org.openfuzzy.fuzzy.set;

public class Parameter implements IParameter {
	private String name;
	private double max;
	private double min;
	
	public Parameter(String name, double min, double max) {
		this.name = name;
		this.min = min;
		this.max = max;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getMax() {
		return max;
	}

	@Override
	public double getMin() {
		return min;
	}

	@Override
	public boolean contains(double x) {
		return min <= x && x <= max;
	}

}
