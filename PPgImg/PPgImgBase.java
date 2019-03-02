package org.phypo.PPg.PPgImg;





import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;

//*************************************************

public interface PPgImgBase{

		public int getWidth();
		public int getHeight();
 
		public int    getAnimNbState();
		public int    getAnimSizeSequence();
		public double getAnimDuration();

		public void draw( Graphics2D pG, int pX, int pY, int pState, double pTimeSeq);



};
//*************************************************
