all::
	cd PPgUtils;	make ;cd ..;\
	cd PPgImg; 	make ;cd ..;\
	cd PPgMath;     make ;cd ..;\
	cd PPgWin;      make ;cd ..;\
	cd PPgFileImgChooser; make ;cd ..;\
	cd Sql; 	make ;cd ..;\
	cd PPgFX; 	make ;cd ..;\


NAME=PPgLib
PACK=org/phypo/PPg
ROOT=../../..
JAR_PATH=../../../../../Bin


jar::
	cd ../../.. \
	rm  -f ${PACK}/${Name}.jar;  \
	jar cf       ${PACK}/${NAME}.jar  ${PACK}/PPgUtils/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgImg/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgMath/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgWin/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgFileImgChooser/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/Sql/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgFX/*.class; \



install::
	cp -p ${NAME}.jar  ${JAR_PATH}


clean::
	cd PPgUtils; 	make clean;cd ..;\
	cd PPgImg; 	make clean;cd ..;\
	cd PPgMath;     make clean;cd ..;\
	cd PPgWin;      make clean;cd ..;\
	cd PPgFileImgChooser;   make clean;cd ..;\
	cd Sql;         make clean;cd ..;\
	cd PPgFx;         make clean;cd ..;\
	rm -f *.jar *.class

