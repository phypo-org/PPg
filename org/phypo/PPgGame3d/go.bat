SET JOGAMP_PATH=D:\Program\Java\jogamp

SET JOGAMP_JAR=%JOGAMP_PATH%\jar\joal.jar;%JOGAMP_PATH%\jar\jogl-all-natives-windows-i586.jar;%JOGAMP_PATH%\jar\jocl.jar;%JOGAMP_PATH%\jar\jogl-all.jar;%JOGAMP_PATH%\jar\gluegen-rt.jar;%JOGAMP_PATH%\jar\gluegen-rt-natives-windows-i586


java -debug  -cp ../../../..;%JOGAMP_JAR% -Djava.library.path=%JOGAMP_PATH% com/phipo/PPg/PPgJ3d/PPgJ3d -f30  -s0.7   	

REM java -debug -cp .;%LWJGL_PATH%\res;%LWJGL_PATH%\jar\%LWJGL_PATH%.jar;%LWJGL_PATH%\jar\%LWJGL_PATH%_test.jar;%LWJGL_PATH%\jar\%LWJGL_PATH%_util.jar;%LWJGL_PATH%\jar\jinput.jar;%LWJGL_PATH%\jar\slick-util.jar.\PPgG3d.jar -Djava.library.path=%LWJGL_PATH%\native\windows com.phipo.PPg.PPgJ3d.PPgJ3d
