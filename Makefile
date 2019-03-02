all::
	cd PPgUtils;	make ;cd ..;\
	cd PPgImg; 	  make ;cd ..;\
	cd PPgMath;  make ;cd ..;\
	cd PPgData;  make ;cd ..;\
	cd PPgWin;    make ;cd ..;\
	cd PPgFileImgChooser; make ;cd ..;\
	cd PPgEdImg;  make ;cd ..;\
	cd PPgProjEd; make ;cd ..;\
	cd PPgGame;   make ;cd ..;\
	cd PPgRts;    make ;cd ..;\
	cd PPgSFX;    make ;cd ..;\
	cd PPgSwarm;  make ;cd ..;\
	cd PPgTestSwarm;   make ;cd ..;\
	cd PPgTest;  make ;cd ..;\




jar::
	cd ../../.. \
	rm  com/phipo/PPg/PPg.jar;  \
	jar cf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgUtils/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgImg/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgMath/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgData/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgFileImgChooser/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgGame/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgRts/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgSFX/*.class; \
	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgSwarm/*.class; \

T1m3::
	cd ../../.. \
	rm  com/phipo/PPg/T1m3/T1m3.jar;  \
	jar cf       com/phipo/PPg/T1m3/T1m3.jar  com/phipo/PPg/PPgUtils/*.class; \
	jar uf       com/phipo/PPg/T1m3/T1m3.jar  com/phipo/PPg/PPgWin/*.class; \
	jar uf       com/phipo/PPg/T1m3/T1m3.jar  com/phipo/PPg/PPgSound/*.class; \
	jar uf       com/phipo/PPg/T1m3/T1m3.jar  com/phipo/PPg/T1m3/*.class; \

#	jar uf       com/phipo/PPg/PPgLib.jar  com/phipo/PPg/PPgSwarm/Ressources/*.png; \


install::
	cp PPgLib.jar  ~/DEV/JavaUtils/JGame/src


clean::
	rm *.jar *.class
	cd PPgUtils; 	make clean;cd ..;\
	cd PPgImg; 	  make clean;cd ..;\
	cd PPgMath;   make clean;cd ..;\
	cd PPgData;   make clean;cd ..;\
	cd PPgWin;    make clean;cd ..;\
	cd PPgFileImgChooser;   make clean;cd ..;\
	cd PPgEdImg;  make clean;cd ..;\
	cd PPgProjEd; make clean;cd ..;\
	cd PPgGame;   make clean;cd ..;\
	cd PPgRts;    make clean;cd ..;\
	cd PPgSFX;    make clean;cd ..;\
	cd PPgSwarm;  make clean;cd ..;\
	cd PPgTestSwarm;   make clean;cd ..;\
	cd PPgTest;   make clean;cd ..;\
	cd Boid;      make clean;cd ..;\
	cd Towers;    make clean;cd ..;\

