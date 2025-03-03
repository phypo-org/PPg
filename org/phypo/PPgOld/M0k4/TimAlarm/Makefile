all::TimAlarm.class AgentAlarm.class DialogOptions.class


jar::
	cd ../../../../..; \
	rm  com/phipo/PPg/M0k4/TimAlarm/TimAlarm.jar;  \
	jar cfmv    com/phipo/PPg/M0k4/TimAlarm/TimAlarm.jar  com/phipo/PPg/M0k4/TimAlarm/manifest.mf   com/phipo/PPg/PPgUtils/*.class; \
	jar uf      com/phipo/PPg/M0k4/TimAlarm/TimAlarm.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf      com/phipo/PPg/M0k4/TimAlarm/TimAlarm.jar  com/phipo/PPg/PPgSound/*.class; \
	jar uf      com/phipo/PPg/M0k4/TimAlarm/TimAlarm.jar  com/phipo/PPg/M0k4/TimAlarm/*.class com/phipo/PPg/M0k4/TimAlarm/*.png com/phipo/PPg/M0k4/TimAlarm/*.mid; 


clean::
	rm *.class *.jar *.zip

%.class: %.java
	javac   -classpath ../../../../.. -deprecation -g  $*.java


