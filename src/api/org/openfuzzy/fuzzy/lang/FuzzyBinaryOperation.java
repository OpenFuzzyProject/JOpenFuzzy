package org.openfuzzy.fuzzy.lang;

/**
 * An binary operator for fuzzy logic.
 * @author Toru Ikeda
 *
 */
public interface FuzzyBinaryOperation {
	public FuzzyLogic eval(FuzzyLogic x, FuzzyLogic y);
}
