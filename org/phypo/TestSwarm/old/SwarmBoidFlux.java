package org.phypo.PPg.PPgTestSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgSFX.*;

//*************************************************

public class SwarmBoidFlux extends  SwarmBoid{

		public double cSize;

		//------------------------------------------------
		public SwarmBoidFlux( double pSize, double pX, double pY, Color pColor ) {
				super(  pX,  pY, pColor );
				
				cSize = pSize;


				System.out.println( "Size:" + cSize );
		}
		//------------------------------------------------*
    public void worldCallAct(double pTimeDelta) 
    {  
				pTimeDelta  *= 30;
				
				// cSpeed.add(cAcceleration, pTimeDelta);

				//   limitSpeed();

        move(  pTimeDelta );        


				if( cLocation.x < 0 ) {								
						Random lRand = new Random();


						cLocation.x = 1000;
						cLocation.y= lRand.nextInt()%1000;
						cSpeed.x=0;
						cSpeed.y=0;
				}
			
				//setRotation( cSpeed.getDirection() );
				//   setLocation(cLocation);   
    }

	
		//-------------------------- 
		public void worldCallDraw( Graphics2D pG){
 
				pG.setColor( cColor );

				//				pG.drawOval(  (int)x, (int)y, (int)(4+3/(cSpeedx/cNormSpeed)), (int)(4+3/(cSpeedy/cNormSpeed)) );
				/*
				double lRes =  cSpeed.x / cSpeed.y ;


				if( lRes < 0 )
						lRes = - lRes;

				if( lRes >4.0 ) {
						lRes = 4.0;
				}
						
				if( cNormSpeed > cSize-1 )
						cNormSpeed = cSize-1;
						
				double lSize = cSize;// - cNormSpeed;

				pG.drawOval( (int)cLocation.x, (int)cLocation.y, (int) (lSize-lRes), (int)(lSize+lRes));
				*/
				pG.drawOval( (int)(cLocation.x-cSize), (int)(cLocation.y-cSize), (int) (cSize*2), (int)(cSize*2));
		}
};

//*************************************************
