JAVA_FILES=$(wildcard *.java)
JAVA_CLASS=$(notdir  $(patsubst %.java, %.class,  $(JAVA_FILES) ) )


all:: ${JAVA_CLASS}


space := $(null) #


LWGL_ALL_JAR=$(wildcard ${LWJGL_PATH}/*.jar)
LWGL_JAR=$(subst $(space),:,$(LWGL_ALL_JAR) )


# LWGL_JAR=${LWJGL_PATH}/lwjgl.jar:${LWJGL_PATH}/lwjgl_util.jar:${LWJGL_PATH}/input.jar:${LWJGL_PATH}/lwjgl-debug.jar:${LWJGL_PATH}/slick-util.jar


jar::
	cd ../../../..; \
	rm  org/phypo/PPg/PPgG3d/PPgG3d.jar;  \
  jar cfmv    org/phypo/PPg/PPgG3d/PPgG3d.jar org/phypo/PPg/PPgG3d/manifest.mf \
	jar uf      org/phypo/PPg/PPgG3d/*.class  org/phypo/PPg/PPgUtils/*.class  org/phypo/PPg/PPgMath/*.class;


#  jar uf ${LWJGL_PATH}/jar/lwjgl.jar ${LWJGL_PATH}/jar/lwjgl_util.jar ${LWJGL_PATH}/jar/jinput.jar ${LWJGL_PATH}/jar/lwjgl-debug.jar/


zip::
	mkdir PPgG3d; \
	cp PPgG3d.jar *.ico *.png  go go.bat *.lnk PPgG3d; \
	zip -9 -r PPgG3d PPgG3d ; \


clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../../..:${LWGL_JAR}  -deprecation -g  $*.java

