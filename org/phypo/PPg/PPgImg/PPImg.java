package org.phypo.PPg.PPgImg;





import java.awt.*;
import javax.swing.ImageIcon;


//*************************************************

public class PPImg extends ImageIcon  implements  PPgImgBase {
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 4571075702893794403L;
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

		@Override
		public int getAnimNbState() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getAnimSizeSequence() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public double getAnimDuration() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void draw(Graphics2D pG, int pX, int pY, int pState, double pTimeSeq) {
			// TODO Auto-generated method stub
			
		}
};
//*************************************************
