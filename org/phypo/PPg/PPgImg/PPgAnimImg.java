package org.phypo.PPg.PPgImg;




import java.awt.Graphics2D;
import java.awt.Image;


//*************************************************
// Plusieurs animations horizontales par colonnes
// de gauche a droite
// chaque lignes etant une animation differente

public class PPgAnimImg extends PPgImg  {

		protected int cNbW =0; // nombre d'image en W
		protected int cNbH =0; // nombre d'image en H

		protected int cSzW = 0;
		protected int cSzH = 0;

		protected double cDuration=0.0;


		public PPgAnimImg( String pStr, int pNbW, int pNbH, double pDuration ){
				super( pStr);

				init( pNbW, pNbH, pDuration );
		}

		//------------------------------------------------

		public PPgAnimImg(  Image pImg, int pNbW, int pNbH, double pDuration ){
				super( pImg);

				init( pNbW, pNbH, pDuration );
		}
		//------------------------------------------------

		void init( int pNbW, int pNbH, double pDuration) {

				if( pNbW == 0)
						pNbW =1;

				cNbW = pNbW;

				cSzW = super.getWidth()/cNbW;

				if( pNbH == 0)
						pNbH =1;

				cNbH = pNbH;

				cSzH = super.getHeight()/cNbH;

				cSemiWidth = cSzW/2;
				cSemiHeight= cSzH/2;

				cDuration = pDuration;



					System.out.println( " PPgAnimImg  nbW:" + pNbW + " nbH:" + pNbH + " szW:" + cSzW + " szH:" + cSzH );
		}



		//=====================================

		@Override
		public int getWidth()  { return cSzW; }
		@Override
		public int getHeight() { return cSzH; }

		public int    getNbAnimState()      { return cNbH; }
		public int    getSizeAnimSequence() { return cNbW; }
		@Override
		public double getAnimDuration()     { return cDuration; }

		//------------------------------------------------

		@Override
		public void draw( Graphics2D pG, int pX, int pY, int pState, double pTimeSeq ) {

				int lSequence = (int)(pTimeSeq*cNbW);
				if( lSequence >= cNbW )
						lSequence = cNbW-1;

				pX -= cSemiWidth;
				pY -= cSemiHeight;

				int lSrcX =  lSequence*cSzW;
				int lSrcY =  pState*cSzH;


				//		System.out.println( " PPgAnimImg draw pTimeSeq:" + pTimeSeq + " lSequence:" + lSequence + " lSrcX:" + lSrcX +" lSrcY:" + lSrcY );

				pG.drawImage( cImg, pX, pY, pX+cSzW, pY+cSzH,
											lSrcX, lSrcY, lSrcX+cSzW, lSrcY+cSzH, null);
		}
}
//*************************************************
