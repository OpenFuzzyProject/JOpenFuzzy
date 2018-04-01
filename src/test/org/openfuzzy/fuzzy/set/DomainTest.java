package org.openfuzzy.fuzzy.set;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DomainTest {

	@Test
	public void containsTest() {
		IDomain domain = new Domain() {
			{
				addParam("a", 0, 1);
				addParam("b", 0, 10);
				addParam("c", 0, 100);
			}
		};
		
		assertEquals(domain.getParam("a").contains(1.0), true);
		assertEquals(domain.getParam("a").getName(), "a");
		
		
		Map<String, Double> point = new HashMap<String, Double>();
		point.put("a", 1.0);
		point.put("b", 1.0);
		point.put("c", 1.0);

		assertEquals(domain.contains(point), true);

		point.put("a", 2.0);
		point.put("b", 1.0);
		point.put("c", 1.0);
		
		assertEquals(domain.contains(point), false);
		
		point.put("a", 2.0);
		point.put("b", 20.0);
		point.put("c", 1.0);
		
		assertEquals(domain.contains(point), false);
		
		point.put("a", 2.0);
		point.put("b", 20.0);
		point.put("c", 200.0);
		
		assertEquals(domain.contains(point), false);

	}

}
