import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import org.openfuzzy.fuzzy.util.ImportSamplePoints;

public class AirConditioner2 {
	public static void main(String[] args) {
		// Domain for the fuzzy inference system
		IDomain domain = new Domain() {
			{
				addParam("temperature", 10, 40);
				addParam("humidity", 0, 100);
				addParam("volume", 0, 10);
			}
		};

		List<double[]> comfortableSample = ImportSamplePoints.fromTSV(new File("sample/comfortableSamplePoints.tsv"));
		List<double[]> uncomfortableSample = ImportSamplePoints.fromTSV(new File("sample/uncomfortableSamplePoints.tsv"));

		// Fuzzy sets for input
		IFuzzySet comfortable = FuzzySetFactory.createFuzzySetBySample("comfortable", Arrays.asList(new String[] { "temperature", "humidity" }),
				comfortableSample, 1.0, 1.0);
		IFuzzySet uncomfortable = FuzzySetFactory.createFuzzySetBySample("uncomfortable",
				Arrays.asList(new String[] { "temperature", "humidity" }), uncomfortableSample, 1.0, 1.0);

		// Fuzzy sets for output
		IFuzzySet strong = FuzzySetFactory.createRightUpLinearShapeFuzzySet("強い", "volume", 6, 10);
		IFuzzySet week = FuzzySetFactory.createLeftUpLinearShapeFuzzySet("弱い", "volume", 0, 4);

		IKnowledgeBase kb = new KnowledgeBase(domain, comfortable, uncomfortable, strong, week);

		IFuzzyRule rule1 = new FuzzyRule(new Antecedent("comfortable"), new Consequent("弱い"));
		IFuzzyRule rule2 = new FuzzyRule(new Antecedent("uncomfortable"), new Consequent("強い"));

		IRuleBase rb = new MamdaniRuleBase(Defuzzifiers.DefuzzyByCOG(domain, 100), rule1, rule2);

		IFuzzyInferenceSystem airConditioner = new FuzzyInferenceSystem(kb, rb);

		FuzzyInferenceEngine engine = new FuzzyInferenceEngine(airConditioner);

		// input values
		Map<String, Double> input = new HashMap<>();
		input.put("temperature", 36.0);
		input.put("humidity", 75.0);

		// display inference result
		System.out.println(engine.eval(input));

	}
}
