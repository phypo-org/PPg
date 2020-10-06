all:: PPgRef.class PPgUtils.class PPgToken.class PPgIniFile.class PPgParam.class PPgXml.class PPgInterfaceXmlNodeHandler.class PPgXmlTag.class PPgLog.class PPgTrace.class PPgPair.class PPgObjectDestroy.class PPgDualOutputStream.class  PPgTrace.class SimpleNotifier.class SimpleNotifierSubject.class SimpleNotifierObserver.class

clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../../.. -deprecation -g  $*.java

