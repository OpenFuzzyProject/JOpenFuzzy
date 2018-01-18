package org.openfuzzy.fuzzy.set;

import java.util.Map;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

/**
 * A membership function. 
 * @author Toru Ikeda
 *
 */
public interface IMembershipFunction {
	/**
	 * Returns membership value of the specified input
	 * @return membership value as fuzzy logic
	 */
	public FuzzyLogic mv(Map<String, Double> input);
	
}
