package org.openfuzzy.fuzzy.lang;

import java.util.Map;

import org.openfuzzy.fuzzy.set.IFuzzySet;

/**
 * An Defuzzifier.
 * @author toru
 */
@FunctionalInterface
public interface IDefuzzifier {
	/**
	 * Defuzzy the specified fuzzy set.
	 * @param set fuzzy set
	 * @return values that result of defuzzification.
	 */
	public Map<String ,Double> eval(IFuzzySet set);
}
