all::  ActorCursor.class  Float2.class Float3.class Color4.class Float4.class Double3.class Double3.class  RectFloat3.class DimFloat3.class Calcul3d.class Aspect3d.class  Behavior.class BehaviorSimpleMove.class TransformBase.class Transform3d.class EnumFaction.class Kamera3d.class ActorBase.class ActorLocation.class ActorMobil.class Facet.class CompilObj.class Object3d.class Primitiv3d.class  GlutPrimitiv.class PPgJ3dTest.class PPgJ3d.class   ModelBase.class ModelPrim.class ModelQuadric.class NodeBase.class Node3d.class Leaf3d.class GamerHuman.class Gamer.class Engine.class UserControl3d.class DefaultUserControl3d.class World3d.class  ParticleEngine.class ParticleFactoryInterface.class ParticleInterface.class ParticleBase.class ParticleSimple.class

# JOGAMP_JAR=${JOGAMP_PATH}/jar/joal-natives-linux-i586.jar:${JOGAMP_PATH}/jar/gluegen-rt-natives-linux-i586.jar:${JOGAMP_PATH}/jar/jocl-natives-solaris-i586.jar:${JOGAMP_PATH}/jar/lwjgl-debug.jar:${JOGAMP_PATH}/jar/jogl-all-natives-linux-i586.jar

JOGAMP_JAR\
=${JOGAMP_PATH}/jar/joal.jar:${JOGAMP_PATH}/jar/joal-natives-linux-i586.jar:${JOGAMP_PATH}/jar/jocl.jar:${JOGAMP_PATH}/jar/jocl-natives-solaris-i586.jar\:${JOGAMP_PATH}/jar/jogl-all.jar:${JOGAMP_PATH}/jar/jogl-all-natives-linux-i586.jar:${JOGAMP_PATH}/jar/gluegen-rt.jar:${JOGAMP_PATH}/jar/gluegen-rt-natives-linux-i586.jar

jar::
	cd ../../../..; \
	rm  com/phipo/PPg/PPgJ3d/PPgJ3d.jar ; \
	jar cfmv com/phipo/PPg/PPgJ3d/PPgJ3d.jar com/phipo/PPg/PPgJ3d/manifest.mf; \
	jar uf  com/phipo/PPg/PPgJ3d/PPgJ3d.jar  com/phipo/PPg/PPgJ3d/*.class  com/phipo/PPg/PPgUtils/*.class  com/phipo/PPg/PPgMath/*.class;




zip::
	rm  -rf PPgJ3d ;\
	mkdir PPgJ3d; \
	mkdir PPgJ3d/jogamp; \
	mkdir PPgJ3d/jogamp/jar;\
  cp com/phipo/PPg/PPgJ3d/PPgJ3d.jar PPgJ3d; \
  cp ${JOGAMP_PATH}/jar/*.jar PPgJ3d/jogamp/jar ;\
	cp PPgJ3d.jar *.ico *.png  go go.bat gojar gojar.bat *.lnk PPgJ3d; \
	zip -9 -r PPgJ3d PPgJ3d ; \


clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../../..:${JOGAMP_JAR}  -deprecation -g  $*.java

