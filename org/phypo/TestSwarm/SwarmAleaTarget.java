package org.phypo.TestSwarm ;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;
import java.lang.Math;


import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPgGame.PPgSFX.*;

//*************************************************


public class SwarmAleaTarget extends Swarm {

		double cTimeChange = 0;

		public	SwarmAleaTarget( String pName, Color pColor, 
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
							 pMaxAttract, 
							 pObstacleInteraction);

				cTargetPoint.x = pX; 
				cTargetPoint.y = pY;
		}


		//-----------------------------------------------
		public void worldCallAct( double pTimeDelta ) {

				//					System.out.println( "Swarm.act " + cName );

				cTimeChange -= pTimeDelta;

				if( cTimeChange <= 0 ) {

						Random lRand = new Random();

						cTimeChange = lRand.nextFloat()*3+1;

						//						System.out.println( ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Random " + cName + " " + cTimeChange );

						cTargetPoint.x += ((lRand.nextFloat()*200.0) -100.0) *cTimeChange;
						cTargetPoint.y += ((lRand.nextFloat()*200.0) -100.0) *cTimeChange;


						World.Get().limitToWorld( cTargetPoint );						


				}
				super.worldCallAct( 	pTimeDelta );			 
		}	
		//-------------------------- 
		public void worldCallDraw( Graphics2D pG ){
				
				// 	System.out.println( "Swarm.draw:" + cName );
				/*
				for( SwarmBoid lBoid : cBoids ) {
						lBoid.draw( pG  );						
				}				
				*/
				computeSwarmPositionAndBoundingSphere();

				super.worldCallDraw( pG );

				double lSzCroix = getBoundingSphere();

				pG.setColor( cColor );

				pG.drawOval( (int)(cLocation.x-lSzCroix), (int)(cLocation.y-lSzCroix), (int) (lSzCroix*2), (int)(lSzCroix*2));
				//		pG.drawRect( (int)(cLocation.x-lSzCroix), (int)(cLocation.y-lSzCroix), (int) (lSzCroix*2), (int)(lSzCroix*2));


				pG.drawRect( (int)(cBoundingBox.getX()), (int)(cBoundingBox.getY()),
										 (int)(cBoundingBox.getWidth()), (int)(cBoundingBox.getHeight()));

		}
		//-------------------------- 
		
		public void    worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {

				System.out.println( "+++++++++++++++++++++ SwarmAleaTarget worldCallDetectCollision  +++++++++++++++++++++++" );


				if( ((SwarmBoid)pActor).cColor == Color.red ){    // c'est un projectile 

						System.out.println( "+++++++++++++++++++++ SwarmAleaTarget worldCallDetectCollision  +++++++++++++++++++++++  RED" );

						// On va gerer les collisions nous meme 
						World.Get().detectCollisionActor( (byte)(EnumFaction.Blue.getCode() + EnumFaction.Green.getCode()), false, 
																							pActor, (ArrayList<ActorLocation>)(ArrayList)cBoids, true );



						//						PPgExplosion lExplo = new PPgExplosion( getFaction(), cLocation, 32, 1.0f, 0.5f, 0f, 0.3 );
						//						World.Get().addActor(lExplo);
						//				setDeleted();
				}
						
		}
	 

};
//*************************************************
