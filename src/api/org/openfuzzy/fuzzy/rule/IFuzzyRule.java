package org.openfuzzy.fuzzy.rule;


/**
 * A fuzzy rule.
 * @author toru
 *
 */
public interface IFuzzyRule {
	/**
	 * Returns an antecedent(if-part).
	 * @return antecedent(if-part)
	 */
	public IAntecedent getAntecedent();
	
	/**
	 * Returns an consequent(then-part and else-part).
	 * @return consequent(then-part and else-part)
	 */
	public IConsequent getConsequent();
}
