package org.openfuzzy.fuzzy.fis;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openfuzzy.fuzzy.set.IDomain;
import org.openfuzzy.fuzzy.set.IFuzzySet;

public class KnowledgeBase implements IKnowledgeBase {
	private IDomain domain;
	private List<IFuzzySet> sets;
	
	public KnowledgeBase(IDomain domain, List<IFuzzySet> sets) {
		this.domain = domain;
		this.sets = sets;
	}

	public KnowledgeBase(IDomain domain, IFuzzySet... sets) {
		this.domain = domain;
		this.sets = Arrays.asList(sets);
	}

	@Override
	public IDomain getDomain() {
		return domain;
	}

	@Override
	public List<IFuzzySet> getFuzzySets() {
		return sets;
	}

	@Override
	public Optional<IFuzzySet> getFuzzySet(String name) {
		return sets.stream().filter(set -> set.getName().equals(name)).findFirst();
	}

}
