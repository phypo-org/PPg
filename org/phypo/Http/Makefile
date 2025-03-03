
#  ${PATH_TO_FX} must be define in shell



JAVA_FILES=$(wildcard *.java)
JAVA_CLASS=$(notdir  $(patsubst %.java, %.class,  $(JAVA_FILES) ) )




NAME=Http
PACK=org/phypo/Http
ROOT=../../..
JAR_PATH=${ROOT}/JAR

JAVA_EXE=${ROOT}/EXE
JAVA_INSTALL=${JAVA_EXE}/${NAME}
PPG_JAR=${JAR_PATH}/PPgLib.jar



all:: ${JAVA_CLASS}


jar::
	cd ${ROOT}; \
	rm  -f   ${PACK}/${NAME}.jar;  \
	jar cf   ${PACK}/${NAME}.jar  ${PACK}/*.class; \


install::
	mkdir -p ${JAVA_EXE}; \
	mkdir -p ${JAVA_INSTALL}; \
	cp ${JAR_PATH}/PPgLib.jar    ${JAVA_INSTALL};\
	cp -p  ${NAME}.jar           ${JAVA_INSTALL}; \


clean::
	rm -f *.class *.jar



%.class: %.java
	javac     -classpath ${ROOT}:${PPG_JAR}  -deprecation -g  $*.java


