package org.phypo.PPg.PPgImg;





import java.awt.geom.*;
import java.awt.*;
import java.awt.geom.Point2D.*;
import java.awt.event.*;


import javax.swing.ImageIcon;


//*************************************************

public class PPImg extends ImageIcon  implements  PPgImgBase {
		
		int lSz=0;

		public PPImg( String pStr ){
				super( pStr);
		}

		public int getWidth()  { return getIconWidth();  }
		public int getHeight() { return getIconHeight(); }

		void draw( Graphics2D pG, int pX, int pY ) {
				pG.drawImage( getImage(), pX-getIconWidth()/2, pY-getIconHeight(),  null);	
		}

		void prepareNextDraw( int pState, int pSequence, double pAngle ){
		}
};
//*************************************************
