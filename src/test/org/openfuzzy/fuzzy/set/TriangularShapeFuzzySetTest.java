package org.openfuzzy.fuzzy.set;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openfuzzy.fuzzy.set.TriangularShapeFuzzySet;

public class TriangularShapeFuzzySetTest {

	@Test
	public void test() {
		TriangularShapeFuzzySet set = new TriangularShapeFuzzySet("test", "x", 0, 50, 100);
		Map<String, Double> input = new HashMap<>();
		input.put("x", 50.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 1.0, 0.0);
		input.put("x", 12.5);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.25, 0.0);
		input.put("x", 25.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.5, 0.0);
		input.put("x", 75.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.5, 0.0);
		input.put("x", 0.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.0, 0.0);
		input.put("x", 87.5);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.25, 0.0);
		input.put("x", 100.0);
		assertEquals(set.mv(input).toDouble().doubleValue(), 0.0, 0.0);
	}

}
