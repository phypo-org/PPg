
#  ${PATH_TO_FX} must be define in shell



JAVA_FILES=$(wildcard *.java)
JAVA_CLASS=$(notdir  $(patsubst %.java, %.class,  $(JAVA_FILES) ) )




NAME=Jixmu
PACK=org/phypo/Jixmu
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
	cp -rp Resources  ${JAVA_INSTALL}; \
	cp -rp Scripts/*  ${JAVA_INSTALL}; \
	cp -rp Config.ini  ${JAVA_INSTALL}; \


clean::
	rm -f *.class *.jar



%.class: %.java
	javac     --module-path ${PATH_TO_FX}  --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAME --add-reads javafx.graphics=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.charts=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio.common=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED   -classpath ${ROOT}:${PPG_JAR}  -deprecation -g  $*.java


