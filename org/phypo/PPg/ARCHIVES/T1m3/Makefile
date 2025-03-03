all:: MyTrayIcon.class T1m3.class DialogOptions.class AgentAlarm.class


jar::
	cd ../../../..; \
	rm  com/phipo/PPg/T1m3/T1m3.jar;  \
	jar cfmv    com/phipo/PPg/T1m3/T1m3.jar  com/phipo/PPg/T1m3/manifest.mf   com/phipo/PPg/PPgUtils/*.class; \
	jar uf      com/phipo/PPg/T1m3/T1m3.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf      com/phipo/PPg/T1m3/T1m3.jar  com/phipo/PPg/PPgSound/*.class; \
	jar uf      com/phipo/PPg/T1m3/T1m3.jar  com/phipo/PPg/T1m3/*.class com/phipo/PPg/T1m3/*.png; 



zip::
	mkdir T1m3; \
	cp T1m3.jar *.ico *.png *.mid go go.bat T1m3.lnk T1m3; \
	zip -9 -r T1m3 T1m3; \


clean::
	rm *.class *.jar *.zip

%.class: %.java
	javac   -classpath ../../../.. -deprecation -g  $*.java


