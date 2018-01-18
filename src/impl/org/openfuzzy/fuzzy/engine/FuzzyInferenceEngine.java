package org.openfuzzy.fuzzy.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openfuzzy.fuzzy.fis.IFuzzyInferenceSystem;
import org.openfuzzy.fuzzy.set.IFuzzySet;

public class FuzzyInferenceEngine implements IFuzzyInferenceEngine {
	private IFuzzyInferenceSystem fis;
	private List<MamdaniFuzzyRuleEngine> ruleEngines;

	public FuzzyInferenceEngine(IFuzzyInferenceSystem fis) {
		this.fis = fis;
		ruleEngines = fis.getRB().getRules().stream().map(rule -> new MamdaniFuzzyRuleEngine(rule, fis.getKB()))
				.collect(Collectors.toList());
	}

	@Override
	public IFuzzyInferenceSystem getFIS() {
		return fis;
	}

	@Override
	public Map<String, Double> eval(Map<String, Double> input) {
		// get each rule results
		List<IFuzzySet> outputs = ruleEngines.stream().map(eng -> eng.eval(input)).filter(Optional::isPresent)
				.map(Optional::get).collect(Collectors.toList());
		
		if (outputs.size() == 0)
			return new HashMap<>();

		// join each results
		IFuzzySet output = outputs.stream().reduce((a, b) -> a.join(b)).get();

		// defuzzy output
		return fis.getRB().getDefuzzifier().eval(output);
	}

}
