
SRCS=$(wildcard  *.java)
SRCS_CLASS=$(patsubst %.java, %.class, $(SRCS))


all:: $(SRCS_CLASS)



FREECHART_JAR=${JFREECHART_PATH}/jfreechart-1.0.19.jar:${JFREECHART_PATH}/jcommon-1.0.23.jar
MARIADB_JAR=~/bin/mariadb-java-client-1.1.10.jar

clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../..:${FREECHART_JAR}:${MARIADB_JAR} -deprecation -g  $*.java

