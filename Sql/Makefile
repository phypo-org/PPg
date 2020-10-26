all:: SqlConnex.class  SqlLogin.class  SqlOutImpl.class  SqlOut.class  SqlResultsExecData.class  SqlServer.class  SqlTerm.class


clean::
	rm -f *.class 

%.class: %.java
	javac   -classpath ../../../.. -deprecation -g  $*.java

