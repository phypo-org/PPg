package org.phypo.PPg.PPgWin;




import java.awt.*;
import java.io.File;

import java.awt.event.*;
import javax.swing.*;

import java.text.DateFormat;
import java.net.*;
import java.awt.image.*;
import java.awt.geom.Point2D;


import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgImg.*;

//***********************************
public class PPgSelectBox extends Rectangle{

		int cSz =0;
		int cHalfSz = 0;

		public enum TypeSelectBox {

			    	TopLeft,
						MiddleLeft,
						BottomLeft,

						TopRight,
						MiddleRight,
						BottomRight,

						MiddleTop,
						MiddleBottom,

						Center,
						None
						};


		public PPgSelectBox( int pX, int pY, int pW, int pH, int pSz ){
				super( pX, pY, pW, pH );
							
						cSz = pSz;
						cHalfSz = cSz/2;
				}

		public PPgSelectBox( Rectangle pRect, int pSz ){
				super(  pRect  );
							
						cSz = pSz;
						cHalfSz = cSz/2;
				}

		
		public Rectangle getTopLeft(){
				return new Rectangle( x-cHalfSz, y - cHalfSz, cSz, cSz );
		}
		
		public Rectangle getMiddleLeft(){
				return new Rectangle( x-cHalfSz, y + (height/2) - cHalfSz , cSz, cSz );
		}
		
		public Rectangle getBottomLeft(){
				return new Rectangle( x-cHalfSz, y + height - cHalfSz , cSz, cSz );
		}

		
		public Rectangle getTopRight(){
				return new Rectangle( x+width-cHalfSz, y - cHalfSz, cSz, cSz );
		}
		
		public Rectangle getMiddleRight(){
				return new Rectangle( x+width-cHalfSz, y + (height/2) - cHalfSz , cSz, cSz );
		}
		
		public Rectangle getBottomRight(){
				return new Rectangle( x+width-cHalfSz, y + height - cHalfSz , cSz, cSz );
		}
		
		
		public Rectangle getMiddleTop(){
				return new Rectangle( x+(width/2)-cHalfSz, y  - cHalfSz , cSz, cSz );
		}

		public Rectangle getMiddleBottom(){
				return new Rectangle( x+(width/2)-cHalfSz, y + height - cHalfSz , cSz, cSz );
		}

		public Rectangle getCenter(){
				return new Rectangle( x+(width/2)-cHalfSz, y +(height/2) - cHalfSz , cSz, cSz );
		}

		
			//------------------------------------------------

		public void draw( Graphics2D pG ){

				PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );

				lGH.drawRect( this  );
				lGH.drawRect( getTopLeft() );
				lGH.drawRect( getMiddleLeft());
				lGH.drawRect(getBottomLeft());

				lGH.drawRect(getTopRight());
				lGH.drawRect(getMiddleRight());
				lGH.drawRect(getBottomRight());

				lGH.drawRect(getMiddleTop());
				lGH.drawRect(getMiddleBottom());
		}
			//------------------------------------------------
	public void draw( Graphics2D pG, double pScale ){

				PPgGraphicsHelper lGH = new PPgGraphicsHelper( pG );

				lGH.drawRect( this, pScale);
				lGH.drawRect( getTopLeft(), pScale );
				lGH.drawRect( getMiddleLeft(), pScale);
				lGH.drawRect(getBottomLeft(), pScale);

				lGH.drawRect(getTopRight(), pScale);
				lGH.drawRect(getMiddleRight(), pScale);
				lGH.drawRect(getBottomRight(), pScale);

				lGH.drawRect(getMiddleTop(), pScale);
				lGH.drawRect(getMiddleBottom(), pScale);
		}

		//------------------------------------------------
		public TypeSelectBox witchHandle( Point pPt ){
				return witchHandle( pPt.x, pPt.y );
		}

		public TypeSelectBox witchHandle( int pX, int pY ){

				if( getTopLeft().contains( pX, pY ))
						return TypeSelectBox.TopLeft;
				if( getMiddleLeft().contains( pX, pY ))
						return TypeSelectBox.MiddleLeft;
				if( getBottomLeft().contains( pX, pY ))
			     	return TypeSelectBox.BottomLeft;

				if( getTopRight().contains( pX, pY ))
			     	return TypeSelectBox.TopRight;
				if( getMiddleRight().contains( pX, pY ))
			     	return TypeSelectBox.MiddleRight;
				if( getBottomRight().contains( pX, pY ))
			     	return TypeSelectBox.BottomRight;

				if( getMiddleTop().contains( pX, pY ))
			     	return TypeSelectBox.MiddleTop;
				if( getMiddleBottom().contains( pX, pY ))
			     	return TypeSelectBox.MiddleBottom;
				return TypeSelectBox.None;
		}			
}
//***********************************
