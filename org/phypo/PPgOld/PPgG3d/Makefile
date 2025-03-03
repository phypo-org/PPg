all::   V3f.class Float3.class Float4.class Double3.class Facet.class Object3d.class CompilObj.class DimFloat3.class RectFloat3.class DimFloat3.class EnumFaction.class  TransformBase.class Transform3d.class Aspect3d.java ActorBase.class ActorLocation.class ActorMobil.class Behavior.class BehaviorSimpleMove.class NodeBase.class Node3d.class Leaf3d.class Kamera3d.class Primitiv3d.class GlutPrimitiv.class ModelBase.class ModelPrim.class Gamer.class GamerHuman.class  Engine.class Calcul3d.class World.class PPgG3d.class

LWGL_JAR=${LWJGL_PATH}/lwjgl.jar:${LWJGL_PATH}/lwjgl_util.jar:${LWJGL_PATH}/input.jar:${LWJGL_PATH}/lwjgl-debug.jar:${LWJGL_PATH}/slick-util.jar


jar::
	cd ../../../..; \
	rm  com/phipo/PPg/PPgG3d/PPgG3d.jar;  \
  jar cfmv    com/phipo/PPg/PPgG3d/PPgG3d.jar  com/phipo/PPg/PPgG3d/manifest.mf \
	jar uf      com/phipo/PPg/PPgG3d/*.class  com/phipo/PPg/PPgUtils/*.class  com/phipo/PPg/PPgMath/*.class;


#  jar uf ${LWJGL_PATH}/jar/lwjgl.jar ${LWJGL_PATH}/jar/lwjgl_util.jar ${LWJGL_PATH}/jar/jinput.jar ${LWJGL_PATH}/jar/lwjgl-debug.jar/


zip::
	mkdir PPgG3d; \
	cp PPgG3d.jar *.ico *.png  go go.bat *.lnk PPgG3d; \
	zip -9 -r PPgG3d PPgG3d ; \


clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../../..:${LWGL_JAR}  -deprecation -g  $*.java

