package org.openfuzzy.fuzzy.set;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Domain implements IDomain {
	private Map<String, IParameter> params;

	public Domain() {
		params = new HashMap<>();
	}

	public void addParam(String name, double min, double max) {
		params.put(name, new Parameter(name, min, max));
	}

	@Override
	public List<IParameter> getParameters() {
		return params.values().stream().collect(Collectors.toList());
	}

	@Override
	public IParameter getParam(String name) {
		return params.get(name);
	}

	@Override
	public boolean contains(Map<String, Double> point) {
		return getParameters().stream().map(param -> param.contains(point.get(param.getName()))).filter(b -> !b)
				.findFirst().isPresent();
	}

}
