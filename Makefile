all::
	cd PPgUtils;	make ;cd ..;\
	cd PPgImg; 	make ;cd ..;\
	cd PPgMath;     make ;cd ..;\
	cd PPgWin;      make ;cd ..;\
	cd PPgFileImgChooser; make ;cd ..;\
	cd Sql; 	make ;cd ..;\




jar::
	cd ../../.. \
	rm  com/phipo/PPg/PPg.jar;  \
	jar cf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgUtils/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgImg/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgMath/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgFileImgChooser/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/Sql/*.class; \



install::
	cp PPgLib.jar  ../../../../..


clean::
	cd PPgUtils; 	make clean;cd ..;\
	cd PPgImg; 	  make clean;cd ..;\
	cd PPgMath;   make clean;cd ..;\
	cd PPgWin;    make clean;cd ..;\
	cd PPgFileImgChooser;   make clean;cd ..;\
	cd Sql;    make clean;cd ..;\
	rm *.jar *.class

