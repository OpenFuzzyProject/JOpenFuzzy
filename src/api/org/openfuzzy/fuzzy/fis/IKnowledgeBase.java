package org.openfuzzy.fuzzy.fis;

import java.util.List;
import java.util.Optional;

import org.openfuzzy.fuzzy.set.IDomain;
import org.openfuzzy.fuzzy.set.IFuzzySet;

/**
 * A konwledge base that includes domain and fuzzy sets. 
 * @author Toru Ikeda
 *
 */
public interface IKnowledgeBase {
	/**
	 * Returns domain of all fuzzy sets.
	 * @return domain
	 */
	public IDomain getDomain();
	
	/**
	 * Returns all fuzzy sets.
	 * @return array of fuzzy sets
	 */
	public List<IFuzzySet> getFuzzySets();
	
	/**
	 * Returns the fuzzy set that named the specified name.
	 * @param name fuzzy set name
	 * @return the fuzzy set if it was found 
	 */
	public Optional<IFuzzySet> getFuzzySet(String name);
}
