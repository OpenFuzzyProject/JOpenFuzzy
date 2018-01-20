package org.openfuzzy.fuzzy.engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openfuzzy.fuzzy.fis.IKnowledgeBase;
import org.openfuzzy.fuzzy.lang.FuzzyLogic;
import org.openfuzzy.fuzzy.rule.IFuzzyRule;
import org.openfuzzy.fuzzy.set.IFuzzySet;

/**
 * A mamdami fuzzy rule engine.
 * 
 * @author Toru Ikeda
 *
 */
class MamdaniFuzzyRuleEngine {
	private List<IFuzzySet> ifSets;
	private IFuzzySet thenSet;

	protected MamdaniFuzzyRuleEngine(IFuzzyRule rule, IKnowledgeBase kb) {
		ifSets = rule.getAntecedent().getFuzzySetNames().stream()
				.map(kb::getFuzzySet)
				.map(Optional::get)
				.collect(Collectors.toList());
		thenSet = kb.getFuzzySet(rule.getConsequent().getThenPartFuzzySetName()).get();
	}

	protected Optional<IFuzzySet> eval(Map<String, Double> input) {
		// calculate goodness-of-fit at if-part  
		FuzzyLogic fit = ifSets.stream()
				.map(set -> set.mv(input))
				.reduce((a, b) -> a.and(b)).get();

		if (fit == FuzzyLogic.FALSE)
			return Optional.empty();
		else if (fit == FuzzyLogic.TRUE)
			return Optional.of(thenSet);

		return Optional.of(thenSet.cutoff(fit));
	}
}
