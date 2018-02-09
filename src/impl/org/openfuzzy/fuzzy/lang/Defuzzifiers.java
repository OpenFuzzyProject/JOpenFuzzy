package org.openfuzzy.fuzzy.lang;

import org.openfuzzy.fuzzy.set.IDomain;

/**
 * Defuzzifiers that is used commonly.
 * @author Toru Ikeda
 *
 */
final public class Defuzzifiers {

	/**
	 * Defuzzy By Center of Gravity.
	 */
	public static IDefuzzifier DefuzzyByCOG(IDomain domain, double n) {
		return new COGDefuzzifier(domain, n);
	}
	
	/**
	 * Defuzzy By Left Most Maximum.
	 */
	public static IDefuzzifier DefuzzyByLeftmostMax(IDomain domain, double n) {
		return new LMDefuzzifier(domain, n);
	}

}
