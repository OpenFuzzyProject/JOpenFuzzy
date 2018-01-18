package org.openfuzzy.fuzzy.rule;


public class FuzzyRule implements IFuzzyRule {

	private IAntecedent antecedent;
	private IConsequent consequent;
	
	public FuzzyRule(IAntecedent antecedent, IConsequent consequent) {
		this.antecedent = antecedent;
		this.consequent = consequent;
	}

	@Override
	public IAntecedent getAntecedent() {
		return antecedent;
	}

	@Override
	public IConsequent getConsequent() {
		return consequent;
	}

}
