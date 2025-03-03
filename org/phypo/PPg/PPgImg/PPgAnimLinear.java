package org.phypo.PPg.PPgImg;




import java.awt.Graphics2D;
import java.awt.Image;


//*******************************************************
// Une seule animation pour plusieurs lignes et colonnes
// de haut en base de gauche a droite

public class PPgAnimLinear extends PPgAnimImg  {


		int cNbTotal =1;

		public PPgAnimLinear( String pStr, int pNbW, int pNbH, int pNbTotal, double pDuration ){
				super( pStr, pNbW, pNbH, pDuration );
				cNbTotal = pNbTotal;
		}

		//------------------------------------------------

		public PPgAnimLinear( Image pImg, int pNbW, int pNbH,  int pNbTotal, double pDuration ){
				super( pImg, pNbW, pNbH, pDuration );

				cNbTotal = pNbTotal;
		}

		@Override
		public int    getNbAnimState()      { return 1; }
		@Override
		public int    getSizeAnimSequence() { return cNbTotal; }

		@Override
		public void draw( Graphics2D pG, int pX, int pY, int pState, double pTimeSeq ) {

				int lSequence = (int)(pTimeSeq*cNbTotal);

				if( lSequence >= cNbTotal )
						lSequence = cNbTotal-1;

				int lSeqX = lSequence % cNbW ;
				int lSeqY = lSequence / cNbW ;

				pX -= cSemiWidth;
				pY -= cSemiHeight;

				int lSrcX =  lSeqX*cSzW;
				int lSrcY =  lSeqY*cSzH;

				pG.drawImage( cImg, pX, pY, pX+cSzW, pY+cSzH,
											lSrcX, lSrcY, lSrcX+cSzW, lSrcY+cSzH, null);
		}
}
//*************************************************
