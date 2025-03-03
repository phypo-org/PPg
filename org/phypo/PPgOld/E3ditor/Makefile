all::  E3ditor.class FrameE3d.class WorldEd.class


JOGAMP_JAR\
=${JOGAMP_PATH}/jar/joal.jar:${JOGAMP_PATH}/jar/joal-natives-linux-i586.jar:${JOGAMP_PATH}/jar/jocl.jar:${JOGAMP_PATH}/jar/jocl-natives-solaris-i586.jar\:${JOGAMP_PATH}/jar/jogl-all.jar:${JOGAMP_PATH}/jar/jogl-all-natives-linux-i586.jar:${JOGAMP_PATH}/jar/gluegen-rt.jar:${JOGAMP_PATH}/jar/gluegen-rt-natives-linux-i586.jar

jar::
	cd ../../../..; \
	rm  com/phipo/PPg/E3ditor/E3ditor.jar ; \
	jar cfmv com/phipo/PPg/E3ditor/E3ditor.jar com/phipo/PPg/E3ditor/manifest.mf; \
	jar uf  com/phipo/PPg/E3ditor/E3ditor.jar  com/phipo/PPg/PPgJ3d/*.class com/phipo/PPg/E3ditor/*.class  com/phipo/PPg/PPgUtils/*.class  com/phipo/PPg/PPgMath/*.class;




zip::
	rm  -rf  E3ditor;\
	mkdir E3ditor; \
	mkdir E3ditor/jogamp; \
	mkdir E3ditor/jogamp/jar;\
  cp com/phipo/PPg/E3ditor/E3ditor.jar E3ditor; \
  cp ${JOGAMP_PATH}/jar/*.jar E3ditor/jogamp/jar ;\
	cp E3ditor.jar *.ico *.png  go go.bat gojar *.lnk E3ditor; \
	zip -9 -r E3ditor E3ditor ; \


clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../../..:${JOGAMP_JAR}  -deprecation -g  $*.java

