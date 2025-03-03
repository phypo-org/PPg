all::
	cd PPgFileImgChooser;	make ;cd ..;\
	cd PPgData;  	make ;cd ..;\
	cd PPgFX;  	make ;cd ..;\
	cd PPgImg;      make ;cd ..;\
	cd PPgMath;     make ;cd ..;\
	cd PPgUtils;    make ;cd ..;\
	cd PPgWin; 	make ;cd ..;\
	cd Sql; 	make ;cd ..;\
	cd PPgMidi; 	make ;cd ..;\
	cd PPgSound;    make ;cd ..;\



NAME=PPgLib
PACK=org/phypo/PPg
ROOT=../../..
JAR_PATH=${ROOT}/JAR


jar::
	cd ${ROOT} \
	rm  -f ${PACK}/${Name}.jar;  \
	jar cf       ${PACK}/${NAME}.jar  ${PACK}/PPgUtils/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgImg/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgMath/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgWin/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgFileImgChooser/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/Sql/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgData/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgFX/*.class; \
	jar uf       ${PACK}/${NAME}.jar  ${PACK}/PPgMidi/*.class; \



install::
	mkdir -p ${JAR_PATH}; \
	cp -p ${NAME}.jar  ${JAR_PATH}


clean:
	cd PPgFileImgChooser;   make clean;cd ..;\
	cd PPgWin;      make clean;cd ..;\
	cd Sql;         make clean;cd ..;\
	cd PPgImg; 	make clean;cd ..;\
	cd PPgMath;     make clean;cd ..;\
	cd PPgUtils; 	make clean;cd ..;\
	cd PPgData;     make clean;cd ..;\
	cd PPgFX;       make clean;cd ..;\
	cd PPgMidi;     make clean;cd ..;\
	cd PPgSound;     make clean;cd ..;\
	rm -f *.jar *.class \

.PHONY: clean
