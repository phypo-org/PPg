package org.phypo.PPgGame.PPgSFX;



import java.awt.Color;
import java.awt.Graphics2D;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math;

import java.util.*;

import java.awt.*;


import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgGame.*;
 




//*************************************************

class ParticleBase implements ParticleInterface {


		
		ParticleInterface.EnumParticleForm cParticleForm = ParticleInterface.EnumParticleForm.SQUARE;

		public void setParticleForm( ParticleInterface.EnumParticleForm pForm ){
				cParticleForm = pForm;
		}

		//---------- Deleted --------------
		boolean cIsDeleted;

		final public boolean isDeleted()  { return cIsDeleted;}
		final public void    setDeleted() { cIsDeleted = true;  }
		//---------- Deleted --------------


		
		//--------- TimeOfLife -------------
		double  cTimeOfLife = -1;
		double  cTimeToDie  = -1;


		public void setTimeOfLife( double lTime) {	
				cTimeOfLife = lTime; 
				cTimeToDie = World.Get().getGameTime() + cTimeOfLife;
		}

		public boolean testTimeOfLife(double lTime) { 
				if(  cTimeToDie== -1 )
						return false ;

				if( lTime > cTimeToDie )
						return true;

				return false ;				
		}
		//--------- TimeOfLife -------------



		//--------- Location -------------------
       public Point2D.Double cLocation     = new Point2D.Double ();

		public  Point2D.Double getLocation()            {return cLocation;}
        public void      setLocation(double x, double y) { cLocation.setLocation(x, y);}
		public void      setLocation( Point2D.Double v) { cLocation.x = v.x; cLocation.y = v.y; }

		public double getX() { return cLocation.x; }
		public double getY() { return cLocation.y; }
		//--------- Location -------------------



		public Point2D.Double cSpeed = new Point2D.Double();
    public Point2D.Double getSpeed() { return cSpeed;}
		public void           setSpeed( Point2D.Double pSpeed){
				cSpeed.x = pSpeed.x;
				cSpeed.y = pSpeed.y;
		}
		public void  setSpeed(double pX, double pY) {
				cSpeed.x = pX;
				cSpeed.y = pY;
		}
		
 
		public Point2D.Double cAcceleration = new  Point2D.Double();
    public Point2D.Double getAcceleration() { return cAcceleration;}
		public void           setAcceleration( Point2D.Double pAcceleration){
				cAcceleration.x = pAcceleration.x;
				cAcceleration.y = pAcceleration.y;
		}
		public void setAcceleration(double pX, double pY) {
				cAcceleration.x = pX;
				cAcceleration.y = pY;
		}
		
		double cAngle=0;
		double cSpin=0;
		double cSpinAcc=0;

		public double         getAngle() { return cAngle; }
		public void           setAngle( double pAngle, double pSpin, double pSpinAcc ){
				
				cAngle   = pAngle;
				cSpin    = pSpin;
				cSpinAcc = pSpinAcc;
		}

		double cSize =1;
		double cGrow =0;
		double cGrowAcc = 0;

		public double         getSize() { return cSize; }
		public void           setSize( double pSize, double pGrow, double pGrowAcc ) {
				cSize = pSize;
				cGrow =pGrow;
				cGrowAcc = pGrowAcc;
		}
		float cRed   = 1.0f;
		float cGreen = 1.0f;
		float cBlue  = 1.0f;
		float cAlpha = 1.0f;

		float cRedFade   = 0;
		float cGreenFade = 0;
		float cBlueFade  = 0;
		float cAlphaFade = 0;
		
		Color cCurrentColor = Color.white;
		
		public float getRed()   { return cRed;}
		public float getGreen() { return cGreen;}
		public float getBlue()  { return cBlue;}
		public float getAlpha() { return cAlpha;}
 
		public void   setColor( float pR, float pG, float pB, float pA ){
				cRed   = pR;
				cGreen = pG;
				cBlue  = pB;
				cAlpha = pA;
		}
		public void   setColorFade( float pR, float pG, float pB, float pA ){
				cRedFade   = pR;
				cGreenFade = pG;
				cBlueFade  = pB;
				cAlphaFade = pA;
		}
		
		//------------------------------------------------

		public ParticleBase( double pX, double pY ) {
				setLocation( pX, pY );
		}

		//------------------------------------------------

		public void callFactoryDraw( Graphics2D pG){

				Color lColor = new Color( cRed, cGreen, cBlue, cAlpha );
				pG.setColor( cCurrentColor);
				
				
				switch( cParticleForm ){
				case POINT:	
						pG.drawLine( (int)(cLocation.x), (int)(cLocation.y),
												 (int)(cLocation.x), (int)(cLocation.y));
						break;
				case SQUARE:
						pG.fillRect( (int)(cLocation.x-cSize), (int)(cLocation.y-cSize), (int)cSize, (int)cSize );
						break;
				case CIRCLE:
						pG.drawOval( (int)(cLocation.x-cSize), (int)(cLocation.y-cSize), (int)cSize, (int)cSize );
						break;

						
				case CIRCLE_FILL:
						pG.fillOval( (int)(cLocation.x-cSize), (int)(cLocation.y-cSize), (int)cSize, (int)cSize );
						break;
				}
		}
																							
		//------------------------------------------------
		public void callFactoryAct( double pTimeDelta ) {
				
				cLocation.x += cSpeed.x * pTimeDelta;
				cLocation.y += cSpeed.y * pTimeDelta;

				cSpeed.x += cAcceleration.x * pTimeDelta;
				cSpeed.y += cAcceleration.y * pTimeDelta;
				
				cAngle += cSpin * pTimeDelta;
				cSpin  += cSpinAcc * pTimeDelta;
								
				cSize  += cGrow * pTimeDelta;

				cRed    += cRedFade   * pTimeDelta;
				cGreen  += cGreenFade * pTimeDelta;
				cBlue   += cBlueFade  * pTimeDelta;
				cAlpha  += cAlphaFade * pTimeDelta;

			if( cRed > 1.0f )
						cRed = 1.0f;
				else
						if( cRed < 0 )
								cRed = 0;

				if( cGreen > 1.0f )
						cGreen = 1.0f;
				else
						if( cGreen < 0 )
								cGreen = 0;

				if( cBlue > 1.0f )
						cBlue = 1.0f;
				else
						if( cBlue < 0 )
								cBlue = 0;

				if( cAlpha > 1.0f )
						cAlpha = 1.0f;
				else
						if( cAlpha < 0 )
								cAlpha = 0;
				
				cCurrentColor = new Color( cRed, cGreen, cBlue, cAlpha);
		}

		//------------------------------------------------
		public void callFactoryClose(){
		}

};

//*************************************************
