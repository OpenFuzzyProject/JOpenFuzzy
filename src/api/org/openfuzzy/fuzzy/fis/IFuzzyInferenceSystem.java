package org.openfuzzy.fuzzy.fis;

/**
 * A fuzzy inference system.
 * @author Toru Ikeda
 *
 */
public interface IFuzzyInferenceSystem {
	/**
	 * Returns knowledge base.
	 * @return knowledge base
	 */
	public IKnowledgeBase getKB();
	
	/**
	 * Returns rule base.
	 * @return rule base.
	 */
	public IRuleBase getRB();
}
