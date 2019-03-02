package org.phypo.PPg.PPgImg;

import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;


import javax.swing.ImageIcon;


//*************************************************
public class PPgImgIcon  extends ImageIcon implements  PPgImgBase {
		
		protected int cSemiWidth;
		protected int cSemiHeight;

		public PPgImgIcon( String pStr ){
				super( pStr );

				cSemiWidth  = getWidth()/2;
				cSemiHeight = getHeight()/2;
		}

		//=====================================
		public int getWidth()  { return getIconWidth();  }
		public int getHeight() { return getIconHeight(); }

		public int    getAnimNbState()      { return 1; }
		public int    getAnimSizeSequence() { return 1; }
		public double getAnimDuration()     { return 0.0; }

		public void draw( Graphics2D pG, int pX, int pY, int pState, double pTimeSeq ) {
				paintIcon( null, pG, pX-cSemiWidth, pY-cSemiHeight);	

		}
};
//*************************************************
