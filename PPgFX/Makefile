
JAVA_FILES=$(wildcard *.java)
JAVA_CLASS=$(notdir  $(patsubst %.java, %.class,  $(JAVA_FILES) ) )


all:: ${JAVA_CLASS}


clean::
	rm  -f *.class 

%.class: %.java
	javac  --module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml -classpath ../../../.. -deprecation -g  $*.java


