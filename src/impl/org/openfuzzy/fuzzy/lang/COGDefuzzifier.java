package org.openfuzzy.fuzzy.lang;

import java.util.HashMap;
import java.util.Map;

import org.openfuzzy.fuzzy.set.IDomain;
import org.openfuzzy.fuzzy.set.IFuzzySet;

final class COGDefuzzifier implements IDefuzzifier {
	private IDomain domain;
	private double n;

	protected COGDefuzzifier(IDomain domain, double n) {
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
		double sum_mx = 0.0;
		double sum_m = 0.0;
		Map<String, Double> input = new HashMap<>();
		for (double i = 0.0; i <= n; i++) {
			double x = st + pitch * i;
			input.put(name, x);
			double m = set.mv(input).toDouble();
			sum_mx += m * x;
			sum_m += m;
		}
		Map<String, Double> cog = new HashMap<>();
		cog.put(name, sum_mx / sum_m);
		return cog;
	}

}
