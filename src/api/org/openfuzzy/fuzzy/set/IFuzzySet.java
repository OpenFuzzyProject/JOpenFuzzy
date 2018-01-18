package org.openfuzzy.fuzzy.set;

import java.util.List;
import java.util.Map;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;


/**
 * An fuzzy set.
 * @author toru
 *
 */
public interface IFuzzySet {

	/**
	 * Returns this fuzzy set name.
	 * @return fuzzy set name
	 */
	public String getName() ;

	/**
	 * Returns this fuzzy set domain.
	 * @return parameter names
	 */
	public List<String> getParameterNames();
	
	/**
	 * Joins this set and other set.
	 * @return the union of this and other
	 */
	public IFuzzySet join(IFuzzySet other);
	
	/**
	 * Products this set and other set.
	 * @return  the product set of this and other
	 */
	public IFuzzySet product(IFuzzySet other);
	
	/**
	 * Returns complement set of this
	 * @return the complementary set of this
	 */
	public IFuzzySet complement();
	
	/**
	 * Cutoff membership value at the specified value.
	 * @param val cutoff value
	 * @return the fuzzy set
	 */
	public IFuzzySet cutoff(FuzzyLogic val);
	
	/**
	 * Returns membership value of the specified input
	 * @return membership value as fuzzy logic
	 */
	public default FuzzyLogic mv(Map<String, Double> input) {
		return getMembershipFunction().mv(input);
	}
	
	/**
	 * Returns membership function of this set.
	 */
	public IMembershipFunction getMembershipFunction();
	
}
