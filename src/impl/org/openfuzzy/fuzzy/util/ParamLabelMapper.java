package org.openfuzzy.fuzzy.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final public class ParamLabelMapper {
	// Singleton
	private ParamLabelMapper() {}
	
	public static Map<String, Double> toMap(List<String> paramNames, double[] data) {
		if (paramNames.size() != data.length)
			throw new RuntimeException("The number of names or the length of data is wrong. : " + paramNames.toString()
					+ " : " + Arrays.toString(data));
		int s = paramNames.size();
		Map<String, Double> map = new HashMap<>(s);
		for (int i = 0; i < s; i++)
			map.put(paramNames.get(i), data[i]);
		return map;
	}
}
