package org.openfuzzy.fuzzy.fis;

import java.util.Arrays;
import java.util.List;

import org.openfuzzy.fuzzy.lang.IDefuzzifier;
import org.openfuzzy.fuzzy.rule.IFuzzyRule;

public class MamdaniRuleBase implements IRuleBase {
	private IDefuzzifier defuzzifier;
	private List<IFuzzyRule> rules;
	
	public MamdaniRuleBase(IDefuzzifier defuzzifier, List<IFuzzyRule> rules) {
		this.defuzzifier = defuzzifier;
		this.rules = rules;
	}
	
	public MamdaniRuleBase(IDefuzzifier defuzzifier, IFuzzyRule... rules) {
		this.defuzzifier = defuzzifier;
		this.rules = Arrays.asList(rules);
	}
	
	@Override
	public List<IFuzzyRule> getRules() {
		return rules;
	}

	@Override
	public IDefuzzifier getDefuzzifier() {
		return defuzzifier;
	}
	
}
