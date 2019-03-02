SET JOGAMP_PATH=D:\Program\Java\jogamp

SET JOGAMP_JAR=%JOGAMP_PATH%\jar\joal.jar;%JOGAMP_PATH%\jar\jogl-all-natives-windows-i586.jar;%JOGAMP_PATH%\jar\jocl.jar;%JOGAMP_PATH%\jar\jogl-all.jar;%JOGAMP_PATH%\jar\gluegen-rt.jar;%JOGAMP_PATH%\jar\gluegen-rt-natives-windows-i586

javac   -classpath ..\..\..\..;%JOGAMP_JAR%  -deprecation -g  *.java
