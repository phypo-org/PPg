package org.phypo.TestSwarm2;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPgGame.PPgGame.*;
import org.phypo.TestSwarm.SimplePanel;

//*************************************************


public class SwarmFlux extends Swarm {

		public	SwarmFlux( String pName, MyGamer pMyGamer, Color pColor, 
												int pNbBoid, int pX, int pY, int pRandom,
												
												double pTargetAttract,
												double pCohesionAttract,
												double pAlignAttract,
												double pSeparationRepuls,
												int pVitesseMax,
												int pDistanceMax,
												int pDistanceEvit,
												boolean pUseSqrt,
												double pMaxAttract ) {
				
				super( pName, pMyGamer,pColor, 
							 pNbBoid, pX,  pY,  pRandom,
							 
							 pTargetAttract,
							 pCohesionAttract,
							 pAlignAttract,
							 pSeparationRepuls,
							 pVitesseMax,
							 pDistanceMax,
							 pDistanceEvit,
							 pUseSqrt,
							 pMaxAttract);
		}


		//-----------------------------------------------
		public void worldCallAct( double pTimeDelta ) {

				//					System.out.println( "Swarm.act " + cName );
				Random lRand = new Random();

				for( Boid lBoid : cBoids ) {

						if( lBoid.x < 0 ) {								
								lBoid.x = 1000;
								lBoid.y= lRand.nextInt()%1000;
								lBoid.cSpeedx=0;
								lBoid.cSpeedy=0;
						}
			}

				moveAll( pTimeDelta, -12000, 300 );

		}
		//-------------------------- 
		public void worldCallDraw( Graphics2D pG ){
				
				//				System.out.println( "Swarm.draw:" + cName );

				for( Boid lBoid : cBoids ) {
						lBoid.drawBubble( pG, cColor );						
				}				
		}

};
//*************************************************
