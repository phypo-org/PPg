

JAVA_FILES=$(wildcard *.java)
JAVA_CLASS=$(notdir  $(patsubst %.java, %.class,  $(JAVA_FILES) ) )




NAME=PPgTestGame3d
PACK=org/phypo/PPgTestGame3d
ROOT=../../..
JAR_PATH=${ROOT}/JAR

JAVA_EXE=${ROOT}/EXE
JAVA_INSTALL=${JAVA_EXE}/${NAME}
PPG_JAR=${JAR_PATH}/PPgLib.jar



all:: ${JAVA_CLASS}



JOGAMP_JAR\
=${JOGAMP_PATH}/jar/joal.jar:${JOGAMP_PATH}/jar/joal-natives-linux-i586.jar:${JOGAMP_PATH}/jar/jocl.jar:${JOGAMP_PATH}/jar/jocl-natives-solaris-i586.jar\:${JOGAMP_PATH}/jar/jogl-all.jar:${JOGAMP_PATH}/jar/jogl-all-natives-linux-i586.jar:${JOGAMP_PATH}/jar/gluegen-rt.jar:${JOGAMP_PATH}/jar/gluegen-rt-natives-linux-i586.jar





clean::
	rm *.class *.jar


%.class: %.java
	javac   -classpath ../../..:${JOGAMP_JAR}  -deprecation -g  $*.java

