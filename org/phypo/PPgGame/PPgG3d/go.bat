
set LWJGL_PATH=lwjgl

java -debug  -Djava.library.path=%LWJGL_PATH%\native\windows -jar PPgG3d

REM java -debug -cp .;%LWJGL_PATH%\res;%LWJGL_PATH%\jar\%LWJGL_PATH%.jar;%LWJGL_PATH%\jar\%LWJGL_PATH%_test.jar;%LWJGL_PATH%\jar\%LWJGL_PATH%_util.jar;%LWJGL_PATH%\jar\jinput.jar;%LWJGL_PATH%\jar\slick-util.jar.\PPgG3d.jar -Djava.library.path=%LWJGL_PATH%\native\windows com.phipo.PPg.PPgG3d.PPgG3d
