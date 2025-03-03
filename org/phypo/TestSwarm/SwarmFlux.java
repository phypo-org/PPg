package org.phypo.TestSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPg.PPgMath.*;

import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPgGame.PPgSFX.*;

//*************************************************


public class SwarmFlux extends Swarm {

		double cBoidSize;
		Random cRand;

		public	SwarmFlux( double pSize, String pName,  Color pColor, 
												int pNbBoid, double pX, double pY, int pRandom,
												
												double pTargetAttract,
												double pCohesionAttract,
												double pAlignAttract,
												double pSeparationRepuls,
												int pVitesseMax,
												int pDistanceMax,
												int pDistanceEvit,
												boolean pUseSqrt,
												double pMaxAttract,
											 double pObstacleInteraction ) {
				
				super( pName, pColor, 
							 pNbBoid, pX,  pY,  pRandom,
							 
							 pTargetAttract,
							 pCohesionAttract,
							 pAlignAttract,
							 pSeparationRepuls,
							 pVitesseMax,
							 pDistanceMax,
							 pDistanceEvit,
							 pUseSqrt,
							 pMaxAttract, pObstacleInteraction);

				cBoidSize=pSize;
				cRand = new Random();

				
		}
		//-----------------------------------------------
		public void worldCallAct( double pTimeDelta ) {
				setTarget( -12000, 300 );
				super.worldCallAct( 	pTimeDelta );			 

		}

		//------------------------------------------------
		public SwarmBoid createBoid( double pX, double pY, Color pColor  )
		{				
				double lr = cRand.nextFloat();
				double lDiv =  cBoidSize/2.0;
				
				
				System.out.println( "lr:" + lr + " div:" + lDiv + " res:" +  lr * lDiv );
				

				double lSz = cBoidSize + (( cRand.nextFloat()*cBoidSize/2.0 ));

				return new SwarmBoidFlux( lSz, pX, pY, pColor );
		}
 		//-------------------------- 
		static double sMemFps =0;
		//-------------------------- 
		public void worldCallDraw( Graphics2D pG ){
				
				//				System.out.println( "Swarm.draw:" + cName );

				int lSzCroix = 8;

				pG.setColor( Color.red );

				for( Point2D.Double lToAvoid : cToAvoid ) {						
						pG.drawLine( (int)(lToAvoid.x-lSzCroix), (int)(lToAvoid.y-lSzCroix), (int)(lToAvoid.x+lSzCroix), (int)(lToAvoid.y+lSzCroix) );							
						pG.drawLine( (int)(lToAvoid.x-lSzCroix), (int)(lToAvoid.y+lSzCroix), (int)(lToAvoid.x+lSzCroix), (int)(lToAvoid.y-lSzCroix) );	
						
				}
				

				pG.setColor( Color.green );

				for( Point2D.Double lToAttract : cToAttract) {						
						pG.drawLine( (int)(lToAttract.x-lSzCroix), (int)(lToAttract.y-lSzCroix), (int)(lToAttract.x+lSzCroix), (int)(lToAttract.y+lSzCroix) );							
						pG.drawLine( (int)(lToAttract.x-lSzCroix), (int)(lToAttract.y+lSzCroix), (int)(lToAttract.x+lSzCroix), (int)(lToAttract.y-lSzCroix) );	
						
				}


				/*
				if( sMemFps != World.sCurrentFpsTime ) {
						System.out.println( "fps:" + 1/World.sCurrentFpsTime ); //  + " = " + World.sCurrentFpsTime  + " " + World.Get().cWantedFrameDuration );	
						sMemFps =  World.sCurrentFpsTime;
				}
				*/

		super.worldCallDraw( pG );			 



		}

};
//*************************************************
