# JOpenFuzzy
JOpenFuzzy is java frameworks for development of fuzzy inference systems.

# Before You Start
1. Install Open JDK or Oracle JDK
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
