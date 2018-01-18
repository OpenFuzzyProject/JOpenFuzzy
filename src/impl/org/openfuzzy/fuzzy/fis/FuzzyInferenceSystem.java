package org.openfuzzy.fuzzy.fis;

/**
 * An fuzzy inference system.
 * 
 * @author Toru Ikeda
 *
 */
public class FuzzyInferenceSystem implements IFuzzyInferenceSystem {
	private IKnowledgeBase kb;
	private IRuleBase rb;

	public FuzzyInferenceSystem(IKnowledgeBase kb, IRuleBase rb) {
		this.kb = kb;
		this.rb = rb;
	}

	@Override
	public IKnowledgeBase getKB() {
		return kb;
	}

	@Override
	public IRuleBase getRB() {
		return rb;
	}

}
