package org.phypo.PPg.PPgImg;





import java.awt.Graphics2D;
import java.awt.Image;


//*************************************************
public class PPgSubImg extends PPgImg {

		protected int cSrcX;
		protected int cSrcY;
		protected int cWidth;
		protected int cHeight;

		public PPgSubImg( String pStr, int pSrcX, int pSrcY, int pWidth, int pHeight ){
				super( pStr);

				cSrcX = pSrcX;
				cSrcY = pSrcY;

				cWidth = pWidth;
				cHeight = pHeight;

				init();
		}

		//------------------------------------------------

		public PPgSubImg( Image pImg, int pSrcX, int pSrcY, int pWidth, int pHeight ){
				super( pImg );

				cSrcX = pSrcX;
				cSrcY = pSrcY;

				cWidth = pWidth;
				cHeight = pHeight;

				init();
		}

		//------------------------------------------------
		void init(){
				cSemiWidth  = cWidth /2;
				cSemiHeight = cHeight/2;
		}
		//------------------------------------------------

		@Override
		public int getWidth()  { return cWidth;  }
		@Override
		public int getHeight() { return cHeight; }

		public void draw( Graphics2D pG, int pX, int pY ) {
				pX -= cSemiWidth;
				pY -= cSemiHeight;
				pG.drawImage( getImage(),  pX, pY,  null);
		}

		private Image getImage() {
			// TODO Auto-generated method stub
			return null;
		}

		public void prepareNextDraw( int pState, int pSequence, double pAngle ){
		}
}
//*************************************************
