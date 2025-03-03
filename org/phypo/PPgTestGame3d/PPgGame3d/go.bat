set JOGAMP_PATH=jogamp

#java -debug  -Djava.library.path=%JOGAMP_PATH -jar PPgGame3d

java -debug -cp .;%LWJGL_PATH%\res;%LWJGL_PATH%\jar\%LWJGL_PATH%.jar;%LWJGL_PATH%\jar\%LWJGL_PATH%_test.jar;%LWJGL_PATH%\jar\%LWJGL_PATH%_util.jar;%LWJGL_PATH%\jar\jinput.jar;%LWJGL_PATH%\jar\slick-util.jar.\PPgG3d.jar -Djava.library.path=%LWJGL_PATH%\native\windows com.phipo.PPg.PPgJ3d.PPgGame3d
