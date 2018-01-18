package org.openfuzzy.fuzzy.engine;

import java.util.Map;

import org.openfuzzy.fuzzy.fis.IFuzzyInferenceSystem;

/**
 * An engine for fuzzy inference system.
 * @author Toru Ikeda
 *
 */
public interface IFuzzyInferenceEngine {
	/**
	 * Returns fuzzy inference system that executed by this engine. 
	 * @return a fuzzy inference system
	 */
	public IFuzzyInferenceSystem getFIS();
	
	/**
	 * Infer from the specified input.
	 * @param input input values for inference 
	 * @return inference result values
	 */
	public Map<String, Double> eval(Map<String, Double> input);
}
