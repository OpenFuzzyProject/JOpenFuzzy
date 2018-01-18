package org.openfuzzy.fuzzy.set;

import java.util.List;

public interface IDomain {
	public List<IParameter> getParameters();
	public IParameter getParam(String name);
}
