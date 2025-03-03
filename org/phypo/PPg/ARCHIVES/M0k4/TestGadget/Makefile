all::TestGadget.class TestClass.class


jar::
	cd ../../../../..; \
	rm  com/phipo/PPg/M0k4/TestGadget/TestGadget.jar;  \
	jar cfmv    com/phipo/PPg/M0k4/TestGadget/TestGadget.jar  com/phipo/PPg/M0k4/TestGadget/manifest.mf   com/phipo/PPg/PPgUtils/*.class; \
	jar uf      com/phipo/PPg/M0k4/TestGadget/TestGadget.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf      com/phipo/PPg/M0k4/TestGadget/TestGadget.jar  com/phipo/PPg/M0k4/TestGadget/*.class com/phipo/PPg/M0k4/TestGadget/*.png;




clean::
	rm *.class *.jar *.zip

%.class: %.java
	javac   -classpath ../../../../.. -deprecation -g  $*.java


