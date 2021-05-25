all:: EcoMath.class PPgVector.class PPgPoint.class PPgRect.class PPgRandom.class
 

clean::
	rm  -f *.class 

%.class: %.java
	javac   -classpath ../../../.. -deprecation -g  $*.java
 
