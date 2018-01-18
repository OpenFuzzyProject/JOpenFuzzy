package org.openfuzzy.fuzzy.lang;

import java.util.HashMap;
import java.util.Map;

import org.openfuzzy.fuzzy.set.IDomain;
import org.openfuzzy.fuzzy.set.IFuzzySet;

public class Defuzzifiers {

	/**
	 * Defuzzy By Center of Gravity.
	 */
	public static IDefuzzifier DefuzzyByCOG(IDomain domain, double n) {
		return new IDefuzzifier() {
			@Override
			public Map<String, Double> eval(IFuzzySet set) {
				return goc(set, n);
			}

			private Map<String, Double> goc(IFuzzySet set, double n) {
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

		};
	}
	
	/**
	 * Defuzzy By Left Most Maximum.
	 */
	public static IDefuzzifier DefuzzyByLeftmostMax(IDomain domain, double n) {
		return new IDefuzzifier() {
			@Override
			public Map<String, Double> eval(IFuzzySet set) {
				return lm(set, n);
			}

			private Map<String, Double> lm(IFuzzySet set, double n) {
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
					if(max <= m){
						max = m;
						output = x;
					}
				}
				
				Map<String, Double> lm = new HashMap<>();
				lm.put(name, output);
				return lm;
			}

		};
	}

}
