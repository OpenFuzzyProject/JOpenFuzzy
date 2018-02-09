import java.util.HashMap;
import java.util.Map;

import org.openfuzzy.fuzzy.engine.FuzzyInferenceEngine;
import org.openfuzzy.fuzzy.fis.FuzzyInferenceSystem;
import org.openfuzzy.fuzzy.fis.IFuzzyInferenceSystem;
import org.openfuzzy.fuzzy.fis.IKnowledgeBase;
import org.openfuzzy.fuzzy.fis.IRuleBase;
import org.openfuzzy.fuzzy.fis.KnowledgeBase;
import org.openfuzzy.fuzzy.fis.MamdaniRuleBase;
import org.openfuzzy.fuzzy.lang.Defuzzifiers;
import org.openfuzzy.fuzzy.rule.Antecedent;
import org.openfuzzy.fuzzy.rule.Consequent;
import org.openfuzzy.fuzzy.rule.FuzzyRule;
import org.openfuzzy.fuzzy.rule.IFuzzyRule;
import org.openfuzzy.fuzzy.set.Domain;
import org.openfuzzy.fuzzy.set.FuzzySetFactory;
import org.openfuzzy.fuzzy.set.IDomain;
import org.openfuzzy.fuzzy.set.IFuzzySet;

public class AirConditioner {
	// Domain for the fuzzy inference system
	private static IDomain domain = new Domain() {
		{
			addParam("temperature", 10, 40);
			addParam("humidity", 0, 100);
			addParam("volume", 0, 10);
		}
	};

	// Fuzzy sets for input
	private static IFuzzySet hot = FuzzySetFactory.createRightUpLinearShapeFuzzySet("hot", "temperature", 20.0, 35.0);
	private static IFuzzySet cool = FuzzySetFactory.createLeftUpLinearShapeFuzzySet("cool", "temperature", 15.0, 25.0);
	private static IFuzzySet dry = FuzzySetFactory.createRightUpLinearShapeFuzzySet("dry", "humidity", 30.0, 65.0);
	private static IFuzzySet wet = FuzzySetFactory.createLeftUpLinearShapeFuzzySet("wet", "humidity", 60.0, 100.0);

	// Fuzzy sets for output
	private static IFuzzySet strong = FuzzySetFactory.createRightUpLinearShapeFuzzySet("strong", "volume", 6.0, 10.0);
	private static IFuzzySet middle = FuzzySetFactory.createTriangularShapeFuzzySet("middle", "volume", 3.0, 5.0, 7.0);
	private static IFuzzySet week = FuzzySetFactory.createLeftUpLinearShapeFuzzySet("week", "volume", 0.0, 4.0);

	private static IKnowledgeBase kb = new KnowledgeBase(domain, hot, cool, dry, wet, strong, middle, week);

	private static IFuzzyRule rule1 = new FuzzyRule(new Antecedent("hot", "dry"), new Consequent("middle"));
	private static IFuzzyRule rule2 = new FuzzyRule(new Antecedent("hot", "wet"), new Consequent("strong"));
	private static IFuzzyRule rule3 = new FuzzyRule(new Antecedent("cool", "dry"), new Consequent("week"));
	private static IFuzzyRule rule4 = new FuzzyRule(new Antecedent("cool", "wet"), new Consequent("middle"));

	private static IRuleBase rb = new MamdaniRuleBase(Defuzzifiers.DefuzzyByCOG(domain, 500.0), rule1, rule2, rule3,
			rule4);

	private static IFuzzyInferenceSystem airConditioner = new FuzzyInferenceSystem(kb, rb);

	private static FuzzyInferenceEngine engine = new FuzzyInferenceEngine(airConditioner);

	public static void main(String[] args) {

		// input values
		Map<String, Double> input = new HashMap<>();
		input.put("temperature", 35.0);
		input.put("humidity", 80.0);

		// display inference result
		System.out.println(engine.eval(input));

	}
}
