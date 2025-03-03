

JAVA_FILES=$(wildcard *.java)
JAVA_CLASS=$(notdir  $(patsubst %.java, %.class,  $(JAVA_FILES) ) )





NAME=PPgGame3d
PACK=org/phypo/PPgGame3d
ROOT=../../..
JAR_PATH=${ROOT}/JAR

JAVA_EXE=${ROOT}/EXE
JAVA_INSTALL=${JAVA_EXE}/${NAME}
PPG_JAR=${JAR_PATH}/PPgLib.jar



all:: ${JAVA_CLASS}


# JOGAMP_JAR=${JOGAMP_PATH}/jar/joal-natives-linux-i586.jar:${JOGAMP_PATH}/jar/gluegen-rt-natives-linux-i586.jar:${JOGAMP_PATH}/jar/jocl-natives-solaris-i586.jar:${JOGAMP_PATH}/jar/lwjgl-debug.jar:${JOGAMP_PATH}/jar/jogl-all-natives-linux-i586.jar

JOGAMP_JAR\
=${JOGAMP_PATH}/jar/joal.jar:${JOGAMP_PATH}/jar/joal-natives-linux-i586.jar:${JOGAMP_PATH}/jar/jocl.jar:${JOGAMP_PATH}/jar/jocl-natives-solaris-i586.jar\:${JOGAMP_PATH}/jar/jogl-all.jar:${JOGAMP_PATH}/jar/jogl-all-natives-linux-i586.jar:${JOGAMP_PATH}/jar/gluegen-rt.jar:${JOGAMP_PATH}/jar/gluegen-rt-natives-linux-i586.jar


jar::
	cd ${ROOT}; \
	rm  -f   ${PACK}/${NAME}.jar;  \
	jar cf   ${PACK}/${NAME}.jar  ${PACK}/*.class; \


install::
	mkdir -p ${JAVA_EXE}; \
	mkdir -p ${JAVA_INSTALL}; \
	mkdir -p ${JAVA_INSTALL}\jogamp; \
	cp ${JAR_PATH}/PPgLib.jar    ${JAVA_INSTALL};\
	cp -p  ${NAME}.jar           ${JAVA_INSTALL}; \
	cp -rp go* *.ico *.png *.lnk ${JAVA_INSTALL}; \



zip::
	rm  -rf PPgGame3d ;\
	mkdir PPgGame3d; \
	mkdir PPgGame3d/jogamp; \
	mkdir PPgGame3d/jogamp/jar;\
  cp org/phypo/PPgGame3d/PPgGame3d.jar PPgGame3d; \
  cp ${JOGAMP_PATH}/jar/*.jar PPgGame3d/jogamp/jar ;\
	cp PPgGame3d.jar *.ico *.png  go go.bat gojar gojar.bat *.lnk PPgGame3d; \
	zip -9 -r PPgGame3d PPgGame3d ; \


clean::
	rm *.class *.jar

%.class: %.java
	javac   -classpath ../../..:${JOGAMP_JAR}  -deprecation -g  $*.java

