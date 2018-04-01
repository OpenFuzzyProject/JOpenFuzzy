package org.openfuzzy.fuzzy.set;

import java.util.List;
import java.util.Map;

public interface IDomain {
	public List<IParameter> getParameters();
	
	public IParameter getParam(String name);
	
	/**
	 * 
	 * @param point
	 * @return true if input in the range
	 */
	public boolean contains(Map<String, Double> point);
}
