all:: SqlConnex.class  SqlLogin.class  SqlOut.class  SqlOutImpl.class SqlServer.class  SqlTerm.class


clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../../.. -deprecation -g  $*.java

