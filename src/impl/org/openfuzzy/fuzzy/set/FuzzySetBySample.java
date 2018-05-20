package org.openfuzzy.fuzzy.set;

import java.util.List;

class FuzzySetBySample extends FuzzySetImpl {
	private String name;
	private List<String> paramNames;
	private IMembershipFunction mf;

	public FuzzySetBySample(String name, List<String> paramNames, List<double[]> samplePoints, double km, double kf) {
		this.name = name;
		this.paramNames = paramNames;
		this.mf = new MembershipFunctionBySample(paramNames, samplePoints, km, kf);
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
		return mf;
	}

}
