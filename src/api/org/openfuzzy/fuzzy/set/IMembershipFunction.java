package org.openfuzzy.fuzzy.set;

import java.util.Map;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

/**
 * A membership function from double to FuzzyLogic values.
 * This is function interface. 
 * You can create the instance by lambda expression.
 * <pre>
 * input -> FuzzyLogic.get([expr]);
 * </pre>
 * 
 * @see FuzzyLogic
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
