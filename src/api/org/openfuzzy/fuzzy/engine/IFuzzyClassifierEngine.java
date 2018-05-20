package org.openfuzzy.fuzzy.engine;

import java.util.Map;

import org.openfuzzy.fuzzy.classifier.IFuzzyClassifier;

/**
 * An engine for fuzzy classifier.
 * @author Toru Ikeda
 *
 */
public interface IFuzzyClassifierEngine {
	public IFuzzyClassifier getFuzzyClassifier();
	public String eval(Map<String, Double> input);
}
