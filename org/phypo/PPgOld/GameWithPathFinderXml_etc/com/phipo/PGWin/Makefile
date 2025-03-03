all:: FrameGamer2D.class  InterfaceDisplayGamer.class  PanelInfoSelect.class  PanelMiniMap.class GamePanel.class	 PanelBox.class  PanelMain2D.class  PanelOrder.class

clean::
	rm *.class  PGWin.jar

jar::
	cd ../../../..; \
	rm  com/caic/ors/OrsMonitor/OrsMonitor.jar; \
	jar cfm      com/phipo/NEW/PGWin/PGWin.jar  com/phipo/NEW/PGWin/PGWin.mf com/phipo/NEW/PGWin/PGWin/*.class ; \
	cd  com/phipo/NEW/PGWin/PGWin;
	jar -uf PGWin.jar


%.class: %.java
	javac   -classpath .:../../../.. -deprecation -g  $*.java
 
