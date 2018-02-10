package org.openfuzzy.fuzzy.set.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openfuzzy.fuzzy.set.IFuzzySet;
import org.openfuzzy.fuzzy.set.LeftUpLinearShapeFuzzySet;

public class LeftUpLinearShapeFuzzySetTest {

	@Test
	public void test() {
		IFuzzySet set = new LeftUpLinearShapeFuzzySet("test", "x", 0, 100);
		Map<String, Double> input = new HashMap<>();
		input.put("x", 50.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.5, 0.0);
		input.put("x", 25.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.75, 0.0);
		input.put("x", 75.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.25, 0.0);
		input.put("x", 0.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 1.0, 0.0);
		input.put("x", 100.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.0, 0.0);
	}

}
