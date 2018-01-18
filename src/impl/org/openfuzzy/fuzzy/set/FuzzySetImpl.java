package org.openfuzzy.fuzzy.set;

import java.util.List;

import org.openfuzzy.fuzzy.lang.FuzzyLogic;

abstract class FuzzySetImpl implements IFuzzySet {
	@Override
	public IFuzzySet join(IFuzzySet other) {
		IFuzzySet parent = this;
		return new FuzzySetImpl() {
			private String name = parent.getName() + " join " + other.getName();
			private IMembershipFunction mf = in -> parent.mv(in)
					.or(other.mv(in));

			@Override
			public String getName() {
				return name;
			}

			@Override
			public IMembershipFunction getMembershipFunction() {
				return mf;
			}

			@Override
			public List<String> getParameterNames() {
				return parent.getParameterNames();
			}
		};
	}

	@Override
	public IFuzzySet product(IFuzzySet other) {
		IFuzzySet parent = this;
		return new FuzzySetImpl() {
			private String name = parent.getName() + " product " + other.getName();
			private IMembershipFunction mf = in -> parent.getMembershipFunction().mv(in)
					.and(other.getMembershipFunction().mv(in));

			@Override
			public String getName() {
				return name;
			}

			@Override
			public IMembershipFunction getMembershipFunction() {
				return mf;
			}

			@Override
			public List<String> getParameterNames() {
				return parent.getParameterNames();
			}
		};
	}

	@Override
	public IFuzzySet complement() {
		IFuzzySet parent = this;
		return new FuzzySetImpl() {
			private String name = parent.getName() + "(complement)";
			private IMembershipFunction mf = in -> parent.getMembershipFunction().mv(in).not();

			@Override
			public String getName() {
				return name;
			}

			@Override
			public IMembershipFunction getMembershipFunction() {
				return mf;
			}

			@Override
			public List<String> getParameterNames() {
				return parent.getParameterNames();
			}
		};
	}

	@Override
	public IFuzzySet cutoff(FuzzyLogic val) {
		IFuzzySet parent = this;
		return new FuzzySetImpl() {
			private IMembershipFunction mf = in -> parent.getMembershipFunction().mv(in).and(val);
			private String name = parent.getName().concat("[cutoff = ").concat(val.toString()).concat("]");

			@Override
			public String getName() {
				return name;
			}

			@Override
			public IMembershipFunction getMembershipFunction() {
				return mf;
			}

			@Override
			public List<String> getParameterNames() {
				return parent.getParameterNames();
			}
		};
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
