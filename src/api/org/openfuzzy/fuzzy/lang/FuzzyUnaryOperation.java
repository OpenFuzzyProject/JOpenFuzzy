package org.openfuzzy.fuzzy.lang;

/**
 * An unary operator for fuzzy logic.
 * @author Toru Ikeda
 *
 */
public interface FuzzyUnaryOperation {
	public FuzzyLogic eval(FuzzyLogic x);
}
