package org.openfuzzy.fuzzy.rule;


public class Consequent implements IConsequent {
private String thenSet;
private String elseSet = null;
	
	public Consequent(String thenSet, String elseSet) {
		this.thenSet = thenSet;
		this.elseSet = elseSet;
	}
	
	public Consequent(String thenSet) {
		this.thenSet = thenSet;
	}
	
	@Override
	public String getThenPartFuzzySetName() {
		return thenSet;
	}

	@Override
	public String getElsePartFuzzySetName() {
		return elseSet;
	}

}
