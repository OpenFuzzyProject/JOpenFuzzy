package org.openfuzzy.fuzzy.fis;

import java.util.List;

import org.openfuzzy.fuzzy.lang.IDefuzzifier;
import org.openfuzzy.fuzzy.rule.IFuzzyRule;

public interface IRuleBase {
	public List<IFuzzyRule> getRules();
	public IDefuzzifier getDefuzzifier();
}
