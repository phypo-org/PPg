all:: DialogGraf4Request.class FrameSqlDataTable.class  FrameSqlServer.class FrameSqlTerm.class GrafViewer2D.class GrafViewerControler.class GrafViewer.class IniParam.class MyTableRenderer.class SqlDataResult.class SqlDataTableAction.class SqlOutListener.class SqlRowLine.class SqlThreadRequest.class SqlTools.class


FREECHART_JAR=${JFREECHART_PATH}/jfreechart.jar:${JFREECHART_PATH}/jcommonjar
MARIADB_JAR=${MARIADB_PATH}/mariadb-java-client.jar

clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../..:${FREECHART_JAR}:${MARIADB_JAR} -deprecation -g  $*.java

