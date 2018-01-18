package org.openfuzzy.fuzzy.rule;

import java.util.Arrays;
import java.util.List;

public class Antecedent implements IAntecedent {
	private List<String> fuzzySetNames;

	public Antecedent(List<String> fuzzySetNames) {
		this.fuzzySetNames = fuzzySetNames;
	}
	
	public Antecedent(String... fuzzySetNames) {
		this.fuzzySetNames = Arrays.asList(fuzzySetNames);
	}

	@Override
	public List<String> getFuzzySetNames() {
		return fuzzySetNames;
	}
}
