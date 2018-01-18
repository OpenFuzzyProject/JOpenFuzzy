package org.openfuzzy.fuzzy.rule;

import java.util.List;

/**
 * An antecedent(if-part).
 * @author Toru Ikeda
 *
 */
public interface IAntecedent {
	public List<String> getFuzzySetNames();
}
