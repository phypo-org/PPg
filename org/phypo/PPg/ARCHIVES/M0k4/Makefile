all:: MyTrayIcon.class M0k4.class DialogNewGadget.class M0k4_Gadget.class


jar::
	cd ../../../..; \
	rm  com/phipo/PPg/M0k4/M0k4.jar;  \
	jar cfmv    com/phipo/PPg/M0k4/M0k4.jar  com/phipo/PPg/M0k4/manifest.mf   com/phipo/PPg/PPgUtils/*.class; \
	jar uf      com/phipo/PPg/M0k4/M0k4.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf      com/phipo/PPg/M0k4/M0k4.jar  com/phipo/PPg/PPgSound/*.class; \
	jar uf      com/phipo/PPg/M0k4/M0k4.jar  com/phipo/PPg/M0k4/*.class com/phipo/PPg/M0k4/*.png; 



zip::
	rm -rf M0k4
	mkdir M0k4  ; \
	cp M0k4.jar *.ico *.ini *.png *.mid go go.bat *.lnk M0k4; \
	mkdir M0k4/TimAlarm ; cp TimAlarm/TimAlarm.jar M0k4/TimAlarm; \
	\
	mkdir M0k4/DesktopAnim M0k4/DesktopAnim/Anim ;
	cp DesktopAnim/DesktopAnim.jar M0k4/DesktopAnim; \
	cp  DesktopAnim/Anim/* M0k4/DesktopAnim/Anim; \
	zip -9 -r M0k4 M0k4; \


clean::
	rm *.class *.jar *.zip

%.class: %.java
	javac   -classpath ../../../.. -deprecation -g  $*.java

