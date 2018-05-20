package org.openfuzzy.fuzzy.classifier;

import java.util.Arrays;
import java.util.List;

import org.openfuzzy.fuzzy.set.IFuzzySet;

public class FuzzyClassifier implements IFuzzyClassifier {
	private List<IFuzzySet> sets = null;
	
	public FuzzyClassifier(List<IFuzzySet> sets) {
		this.sets = sets;
	}
	
	public FuzzyClassifier(IFuzzySet... sets) {
		this.sets = Arrays.asList(sets);
	}
	
	@Override
	public List<IFuzzySet> getClassFuzzySets() {
		return sets;
	}

}
