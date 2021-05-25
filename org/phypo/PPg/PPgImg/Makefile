all:: ImgUtils.class Bmp.class PPgImgBase.class  PPgImgIcon.class PPgImg.class PPgAnimImg.class PPgAnimLinear.class PPgImgTab.class


clean::
	rm  -f *.class 

%.class: %.java
	javac   -classpath ../../../.. -deprecation -g  $*.java
 
