[![Build Status](https://travis-ci.org/OpenFuzzyProject/JOpenFuzzy.svg?branch=master)](https://travis-ci.org/OpenFuzzyProject/JOpenFuzzy)

# JOpenFuzzy
JOpenFuzzy is java frameworks for development of fuzzy inference systems.
JOpenFuzzy is under development and is beta version.

## Features
* Multi-dimentional fuzzy set from sample data.

```java
IFuzzySet comfortable = FuzzySetFactory
    .createFuzzySetBySample(
        "comfortable", // fuzzy set name
        Arrays.asList(new String[] { "temperature", "humidity" }), // input parameter difinision
        comfortableSample, // sample data
        1.0, 1.0 // meta parameters
    );
```

* Fuzzy logic calculation.

```java
FuzzyLogic a = FuzzyLogic.get(0.2);
FuzzyLogic b = FuzzyLogic.get(0.8);
a.and(b); // 0.2
a.or(b);  // 0.8
```

* Fuzzy set calculation.

```java
// Create Fuzzy Sets
IFuzzySet hot = FuzzySetFactory.createRightUpLinearShapeFuzzySet("hot", "temperature", 20.0, 35.0);
IFuzzySet cool = FuzzySetFactory.createLeftUpLinearShapeFuzzySet("cool", "temperature", 15.0, 25.0);

// Fuzzy Set Operations
IFuzzySet hotOrCool = hot.join(cool);
IFuzzySet hotAndCool = hot.product(cool);

// Element Operations
Map<String, Double> input = new HashMap();
input.put("temperature", 36.1);
hot.mv(input);
```

* Fuzzy Inference System (FIS).

```java
IKnowledgeBase kb = new KnowledgeBase(domain, hot, cool, dry, wet, strong, middle, week);

IFuzzyRule rule1 = new FuzzyRule(new Antecedent("hot", "dry"), new Consequent("middle"));
IFuzzyRule rule2 = new FuzzyRule(new Antecedent("hot", "wet"), new Consequent("strong"));
IFuzzyRule rule3 = new FuzzyRule(new Antecedent("cool", "dry"), new Consequent("week"));
IFuzzyRule rule4 = new FuzzyRule(new Antecedent("cool", "wet"), new Consequent("middle"));

IRuleBase rb = new MamdaniRuleBase(Defuzzifiers.DefuzzyByCOG(domain, 500.0), rule1, rule2, rule3, rule4);

IFuzzyInferenceSystem airConditioner = new FuzzyInferenceSystem(kb, rb);
FuzzyInferenceEngine engine = new FuzzyInferenceEngine(airConditioner);
System.out.println(engine.eval(input));
```
* Fuzzy Classifier (Experimental)

```java
Map<String, Double> input = new HashMap();
input.put("x", 25.0);
IFuzzyClassifier classifier = new FuzzyClassifier(cool, hot);
IFuzzyClassifierEngine engine = new FuzzyClassifierEngine(classifier);
System.out.println(engine.eval(input)); // cool or hot
```

# Before You Start
1. Install Open JDK or Oracle JDK 8 or later
2. Install Apache Ant

# Clone Repository
```
$ git clone https://github.com/OpenFuzzyProject/JOpenFuzzy.git
Cloning into 'JOpenFuzzy'...
remote: Counting objects: 70, done.
remote: Compressing objects: 100% (56/56), done.
remote: Total 70 (delta 4), reused 66 (delta 3), pack-reused 0
Unpacking objects: 100% (70/70), done.
Checking connectivity... done.
$ cd JOpenFuzzy
$ ls JOpenFuzzy
LICENSE  build.xml  sample  src
```

# Build Repository
```
$ cd JOpenFuzzy
$ ls
LICENSE  build.xml  sample  srcls
$ ant all
Buildfile: /home/toru/JOpenFuzzy/build.xml
...
BUILD SUCCESSFUL
Total time: 4 seconds
$ ls
LICENSE  build  build.xml  doc  jopenfuzzy.jar  sample  src
```

# Run Sample Code
```
$ cd JOpenFuzzy/sample
$ ls
AirConditioner.java  AirConditioner2.java  comfortableSamplePoints.tsv  uncomfortableSamplePoints.tsv
$ javac -classpath ../jopenfuzzy.jar AirConditioner.java
$ ls
AirConditioner$1.class  AirConditioner.java   comfortableSamplePoints.tsv
AirConditioner.class    AirConditioner2.java  uncomfortableSamplePoints.tsv
$ java -classpath .:../jopenfuzzy.jar AirConditioner 
{volume=6.479418417745566}
```
