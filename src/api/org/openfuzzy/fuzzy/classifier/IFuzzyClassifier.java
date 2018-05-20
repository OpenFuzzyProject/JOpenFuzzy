package org.openfuzzy.fuzzy.classifier;

import java.util.List;

import org.openfuzzy.fuzzy.set.IFuzzySet;

/**
 * A fuzzy classifier.
 * @author Toru Ikeda
 *
 */
public interface IFuzzyClassifier {
	/**
	 * Returns list of fuzzy sets that use for classify.
	 * @return List of fuzzy sets.
	 */
	public List<IFuzzySet> getClassFuzzySets();
}
