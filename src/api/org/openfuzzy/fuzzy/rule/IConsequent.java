package org.openfuzzy.fuzzy.rule;


/**
 * A consequent(then-part and else-part).
 * @author Toru Ikeda
 *
 */
public interface IConsequent {
	public String getThenPartFuzzySetName();
	public String getElsePartFuzzySetName();
}
