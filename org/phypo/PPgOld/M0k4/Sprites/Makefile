all::Sprites.class  SimplePanel.class MyGamer.class MyWorld.class GamerEvent.class


jar::
	cd ../../../../..; \
	rm  com/phipo/PPg/M0k4/Sprites/Sprites.jar;  \
	jar cfmv    com/phipo/PPg/M0k4/Sprites/Sprites.jar  com/phipo/PPg/M0k4/Sprites/manifest.mf   com/phipo/PPg/PPgUtils/*.class; \
	jar uf      com/phipo/PPg/M0k4/Sprites/Sprites.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf      com/phipo/PPg/M0k4/Sprites/Sprites.jar  com/phipo/PPg/PPgSound/*.class; \
	jar uf      com/phipo/PPg/M0k4/Sprites/Sprites.jar  com/phipo/PPg/M0k4/Sprites/*.class com/phipo/PPg/M0k4/Sprites/*.png com/phipo/PPg/M0k4/Sprites/*.mid; 


clean::
	rm *.class *.jar *.zip

%.class: %.java
	javac   -classpath ../../../../.. -deprecation -g  $*.java


