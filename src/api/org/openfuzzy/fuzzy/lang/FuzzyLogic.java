package org.openfuzzy.fuzzy.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * A Fuzzy logic variable and operations.
 * 
 * @author Toru Ikeda
 *
 */
public final class FuzzyLogic {
	private double val;
	private static Map<Double, FuzzyLogic> instances = new HashMap<>();

	public static final FuzzyLogic TRUE = FuzzyLogic.get(1.0);
	public static final FuzzyLogic FALSE = FuzzyLogic.get(0.0);

	public static final FuzzyBinaryOperation MAX = (x, y) -> FuzzyLogic.get(Math.max(x.val, y.val));
	public static final FuzzyBinaryOperation MIN = (x, y) -> FuzzyLogic.get(Math.min(x.val, y.val));
	public static final FuzzyBinaryOperation ADD = (x, y) -> FuzzyLogic.get(x.val + y.val - x.val * y.val);
	public static final FuzzyBinaryOperation MULTI = (x, y) -> FuzzyLogic.get(x.val * y.val);
	public static final FuzzyUnaryOperation ZADEH_NOT = x -> FuzzyLogic.get(1.0 - x.val);
	private static FuzzyBinaryOperation or = MAX;
	private static FuzzyBinaryOperation and = MIN;
	private static FuzzyUnaryOperation not = ZADEH_NOT;

	public static void setAnd(FuzzyBinaryOperation eval) {
		and = eval;
	}

	public static void setOr(FuzzyBinaryOperation eval) {
		or = eval;
	}

	public static void setNot(FuzzyUnaryOperation eval) {
		not = eval;
	}

	/*
	 * singleton
	 */
	private FuzzyLogic(double val) {
		this.val = val;
	}

	public FuzzyLogic and(FuzzyLogic other) {
		return and.eval(this, other);
	}

	public FuzzyLogic or(FuzzyLogic other) {
		return or.eval(this, other);
	}

	public FuzzyLogic not() {
		return not.eval(this);
	}

	public static FuzzyLogic get(double val) {
		if (val < 0.0 || 1.0 < val)
			throw new RuntimeException("Out of Range : " + val + " is not in [0, 1]");
		if (instances.containsKey(val))
			return instances.get(val);
		instances.put(val, new FuzzyLogic(val));
		return instances.get(val);
	}

	public static FuzzyLogic get(boolean val) {
		return val ? TRUE : FALSE;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FuzzyLogic)
			return ((FuzzyLogic) obj).val == this.val;
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return String.valueOf(val);
	}

	public Double toDouble() {
		return val;
	}

}
