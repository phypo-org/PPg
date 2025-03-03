package org.phypo.PPg.PPgTestSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgSFX.*;

//*************************************************


public class SwarmMouse extends Swarm {

		MyGamer cMyGamer;

		public	SwarmMouse( String pName, MyGamer pMyGamer, Color pColor, 
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

				cMyGamer = pMyGamer;
		}


		//-----------------------------------------------
		public void worldCallAct( double pTimeDelta ) {

				//					System.out.println( "Swarm.act " + cName );

				Point2D.Double lMousePos = 	cMyGamer.getLastPoint();

				setTarget( lMousePos.x,  lMousePos.y );
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


				//			 pG.drawLine( (int)(cLocation.x-lSzCroix), (int)(cLocation.y-lSzCroix), (int)(cLocation.x+lSzCroix), (int)(cLocation.y+lSzCroix) );						 
				//			 pG.drawLine( (int)(cLocation.x-lSzCroix), (int)(cLocation.y+lSzCroix), (int)(cLocation.x+lSzCroix), (int)(cLocation.y-lSzCroix) );	
						
			 //			 pG.drawLine( (int)(cLocation.x-lSzCroix), (int)(cLocation.y), (int)(cLocation.x+lSzCroix), (int)(cLocation.y) );							
			 //			 pG.drawLine( (int)(cLocation.x), (int)(cLocation.y+lSzCroix), (int)(cLocation.x), (int)(cLocation.y-lSzCroix) );							

		}

		//------------------------------------------------
		public void fire1(){

				System.out.println( "=========== Fire1" );


				SwarmBoid lBoid =  new SwarmBoidMissile( cLocation.x, cLocation.y, Color.red);
				//	lBoid.setFaction( getFaction() );
				lBoid.setFaction( EnumFaction.Blue );		
				PPgVector lSpeedBoid = new PPgVector( cTargetPoint.x -cLocation.x, cTargetPoint.y -cLocation.y );
				//	lSpeedBoid.divide( 5.0 );
				lSpeedBoid.setLength( 10 );
				
				lBoid.setSpeed( lSpeedBoid );
				lBoid.setTimeOfLife( 3 );
				lBoid.setBoundingSphere( 10	);
				lBoid.setActorType( 666 );

				World.Get().addActor(lBoid);

		}

};
//*************************************************
