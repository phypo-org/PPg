all:: PPgProjEd.class FrameProject.class ObjBase.class Project.class DialogProject.class GrpAnim.class GrpImg.class Anim.class Dessin.class DialogDessin.class
 

clean::
	rm *.class 

%.class: %.java
	javac   -classpath ../../../.. -deprecation -g  $*.java
