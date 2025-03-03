all::DesktopAnim.class AgentAlarm.class DialogOptions.class Sprite.class


jar::
	cd ../../../../..; \
	rm  com/phipo/PPg/M0k4/DesktopAnim/DesktopAnim.jar;  \
	jar cfmv    com/phipo/PPg/M0k4/DesktopAnim/DesktopAnim.jar  com/phipo/PPg/M0k4/DesktopAnim/manifest.mf   com/phipo/PPg/PPgUtils/*.class; \
	jar uf      com/phipo/PPg/M0k4/DesktopAnim/DesktopAnim.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf      com/phipo/PPg/M0k4/DesktopAnim/DesktopAnim.jar  com/phipo/PPg/PPgMath/*.class; \
	jar uf      com/phipo/PPg/M0k4/DesktopAnim/DesktopAnim.jar  com/phipo/PPg/PPgImg/*.class; \
	jar uf      com/phipo/PPg/M0k4/DesktopAnim/DesktopAnim.jar  com/phipo/PPg/M0k4/DesktopAnim/*.class com/phipo/PPg/M0k4/DesktopAnim/*.png com/phipo/PPg/M0k4/DesktopAnim/*.png; 


clean::
	rm *.class *.jar *.zip

%.class: %.java
	javac   -classpath ../../../../.. -deprecation -g  $*.java


