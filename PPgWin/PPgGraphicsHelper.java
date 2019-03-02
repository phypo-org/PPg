package org.phypo.PPg.PPgWin;




import java.awt.*;
import java.util.*;

import java.awt.geom.*;

//***********************************
public class PPgGraphicsHelper{

		Graphics2D cGraf;

		public PPgGraphicsHelper( Graphics pG ){

				cGraf = (Graphics2D) pG ;
		}

		//------------------------------------------------
		public void drawPoint( Point pPoint){
				cGraf.drawLine( pPoint.x, pPoint.y, pPoint.x, pPoint.y);
		}

		//------------------------------------------------
		public void drawLine( Point pA, Point pB ){
				cGraf.drawLine( pA.x, pA.y, pB.x, pB.y);
		}
		//------------------------------------------------
		public void drawPolyLine( ArrayList<Point> lArray){
				
				if( lArray.size() == 0)
						return ;

				Point lMem = lArray.get(0);

				for( Point lPt: lArray ){
						drawLine( lMem, lPt );
				}
		}
		//------------------------------------------------
		static public void PrepareRect( Point pA, Point pB ){

				if( pB.x < pA.x  ){
						int lMem  = pB.x;
						pB.x= pA.x;
						pA.x = lMem;
				}
				if( pB.y < pA.y  ){
						int lMem  = pB.y;
						pB.y= pA.y;
						pA.y = lMem;
				}				
		}
		//------------------------------------------------
		
		static public Rectangle NewRect(  Point pA, Point pB ){

				Point lA = new Point( pA.x, pA.y );
				Point lB = new Point( pB.x, pB.y );

				if( lB.x < lA.x  ){
						int lMem  = lB.x;
						lB.x= lA.x;
						lA.x = lMem;
				}
				if( lB.y < lA.y  ){
						int lMem  = lB.y;
						lB.y= lA.y;
						lA.y = lMem;
				}
				return new Rectangle( lA.x, lA.y, lB.x- lA.x, lB.y - lA.y );
		}
		//------------------------------------------------
		public void drawRect( Point pA, Point pB, boolean pFill ){

				Point lA = new Point( pA );
				Point lB = new Point( pB );

				if( lB.x < lA.x  ){
						int lMem  = lB.x;
						lB.x= lA.x;
						lA.x = lMem;
				}
				if( lB.y < lA.y  ){
						int lMem  = lB.y;
						lB.y= lA.y;
						lA.y = lMem;
				}
										
				if( pFill )
						cGraf.fillRect( lA.x, lA.y, lB.x- lA.x, lB.y - lA.y );
				else
						cGraf.drawRect( lA.x, lA.y, lB.x- lA.x, lB.y - lA.y );
		}
		//------------------------------------------------
		public void drawRect( Rectangle lRect ){

				cGraf.drawRect( lRect.x, lRect.y, lRect.width, lRect.height );
		}
		//------------------------------------------------
		public void drawRect( Rectangle lRect, double pScale ){

				cGraf.drawRect( (int)(lRect.x*pScale), (int)(lRect.y*pScale), (int)(lRect.width*pScale), (int)(lRect.height*pScale) );
		}
		//------------------------------------------------
		public void drawRoundRect( Point pA, Point pB, boolean pFill, float pArcW, float pArcH ){

				Point lA = new Point( pA );
				Point lB = new Point( pB );

				if( lB.x < lA.x  ){
						int lMem  = lB.x;
						lB.x= lA.x;
						lA.x = lMem;
				}
				if( lB.y < lA.y  ){
						int lMem  = lB.y;
						lB.y= lA.y;
						lA.y = lMem;
				}
				
				int pW = lB.x- lA.x;
				int pH = lB.y- lA.y;
										
				if( pFill )
						cGraf.fillRoundRect( lA.x, lA.y, pW, pH, (int)( pW*pArcW), (int)(pH*pArcH));
				else
						cGraf.drawRoundRect( lA.x, lA.y, pW, pH, (int)( pW*pArcW), (int)(pH*pArcH));
		}
		//------------------------------------------------
		public void draw3dRect( Point pA, Point pB, boolean pFill, boolean pRaised ){


				Point lA = new Point( pA );
				Point lB = new Point( pB );

				if( lB.x < lA.x  ){
						int lMem  = lB.x;
						lB.x= lA.x;
						lA.x = lMem;
				}
				if( lB.y < lA.y  ){
						int lMem  = lB.y;
						lB.y= lA.y;
						lA.y = lMem;
				}
										
				if( pFill )
						cGraf.fill3DRect( lA.x, lA.y, lB.x - lA.x, lB.y - lA.y, pRaised );
				else
						cGraf.draw3DRect( lA.x, lA.y, lB.x - lA.x, lB.y - lA.y, pRaised );
		}
		//------------------------------------------------
		public void drawOval( Point pA, Point pB, boolean pFill ){

				Point lA = new Point( pA );
				Point lB = new Point( pB );

				if( lB.x < lA.x  ){
						int lMem  = lB.x;
						lB.x= lA.x;
						lA.x = lMem;
				}
				if( lB.y < lA.y  ){
						int lMem  = lB.y;
						lB.y= lA.y;
						lA.y = lMem;
				}
										
				if( pFill )
						cGraf.fillOval( lA.x, lA.y, lB.x- lA.x, lB.y - lA.y );
				else
						cGraf.drawOval( lA.x, lA.y, lB.x- lA.x, lB.y - lA.y );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		
}
//***********************************
