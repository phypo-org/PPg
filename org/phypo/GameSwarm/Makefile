JAVA_FILES=$(wildcard *.java)
JAVA_CLASS=$(notdir  $(patsubst %.java, %.class,  $(JAVA_FILES) ) )


all:: ${JAVA_CLASS}


clean::
	rm -f *.class 

%.class: %.java
	javac   -classpath ../../.. -deprecation -g  $*.java

