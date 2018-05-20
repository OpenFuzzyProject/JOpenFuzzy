package org.openfuzzy.fuzzy.engine;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.openfuzzy.fuzzy.classifier.IFuzzyClassifier;
import org.openfuzzy.fuzzy.lang.FuzzyLogic;
import org.openfuzzy.fuzzy.set.IFuzzySet;

public class FuzzyClassifierEngine implements IFuzzyClassifierEngine {
	private IFuzzyClassifier classifier;
	
	public FuzzyClassifierEngine(IFuzzyClassifier classifier) {
		this.classifier = classifier;
	}
	
	@Override
	public IFuzzyClassifier getFuzzyClassifier() {
		return classifier;
	}

	@Override
	public String eval(Map<String, Double> input) {
		Map<String, FuzzyLogic> mvs = classifier.getClassFuzzySets().stream().collect(Collectors.toMap(IFuzzySet::getName, s -> s.mv(input)));
		Entry<String, FuzzyLogic> max = mvs.entrySet().stream().max((a, b) -> Double.compare(a.getValue().toDouble(), b.getValue().toDouble())).get();
		return max.getKey();
	}

}
