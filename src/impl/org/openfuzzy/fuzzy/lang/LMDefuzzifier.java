package org.openfuzzy.fuzzy.lang;

import java.util.HashMap;
import java.util.Map;

import org.openfuzzy.fuzzy.set.IDomain;
import org.openfuzzy.fuzzy.set.IFuzzySet;

final class LMDefuzzifier implements IDefuzzifier {
	private IDomain domain;
	private double n;

	protected LMDefuzzifier(IDomain domain, double n) {
		this.domain = domain;
		this.n = n;
	}

	@Override
	public Map<String, Double> eval(IFuzzySet set) {
		final int K = set.getParameterNames().size();

		if (K != 1)
			throw new RuntimeException("no or multi dimentional fuzzy set defuzzy is undefine.");

		String name = set.getParameterNames().get(0);

		double st = domain.getParam(name).getMin();
		double ed = domain.getParam(name).getMax();
		double pitch = (ed - st) / n;
		Map<String, Double> input = new HashMap<>();
		double max = Double.MIN_VALUE;
		double output = 0.0;
		for (double x = st; x <= ed; x += pitch) {
			input.put(name, x);
			double m = set.getMembershipFunction().mv(input).toDouble();
			if (max <= m) {
				max = m;
				output = x;
			}
		}

		Map<String, Double> lm = new HashMap<>();
		lm.put(name, output);
		return lm;
	}

}
