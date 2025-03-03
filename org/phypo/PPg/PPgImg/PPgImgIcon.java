package org.phypo.PPg.PPgImg;

import java.awt.Graphics2D;

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
		@Override
		public int getWidth()  { return getIconWidth();  }
		@Override
		public int getHeight() { return getIconHeight(); }

		@Override
		public int    getAnimNbState()      { return 1; }
		@Override
		public int    getAnimSizeSequence() { return 1; }
		@Override
		public double getAnimDuration()     { return 0.0; }

		@Override
		public void draw( Graphics2D pG, int pX, int pY, int pState, double pTimeSeq ) {
				paintIcon( null, pG, pX-cSemiWidth, pY-cSemiHeight);

		}
}
//*************************************************
