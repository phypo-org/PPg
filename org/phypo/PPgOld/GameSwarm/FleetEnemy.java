package org.phypo.PPg.PPgSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;
import java.lang.Math;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgSFX.*;

//*************************************************


public class FleetEnemy extends Fleet {


		double  cTimeAutoFire1 = 0;
		double  cTimeAutoFire2 = 0;

		public	FleetEnemy( String pName, Color pColor, 
												int pNbBoid, double pX, double pY,
												int pRandom,												
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

				cTargetPoint.x = pX-200; 
				cTargetPoint.y = pY;
		}


		//-----------------------------------------------
		public void worldCallAct( double pTimeDelta ) {


				//				System.out.println( "SwarmFleet.act  vx:" + getSpeed().x + " vy:" +getSpeed().y );


				//			cTargetPoint.setLocation( cLocation );

				autoPilot(pTimeDelta);


				//===============================
				super.worldCallAct( 	pTimeDelta );
				//===============================

				computeSwarmPositionAndBoundingSphere();


				
				// destruction si on passe le bord de l'ecran gauche
				if( getLocation().x < -100 )
						setDeleted();


				
				// Tirs automatiques
				if( dectectEnemy() ) {
						double lTime = World.Get().getGameTime();

						if( lTime - cTimeAutoFire1 > 0.400 ){
								cTimeAutoFire1 = lTime;

								for( SwarmBoid lBoid : cBoids ) {
										((Ship)lBoid).fleetCallFire( ShipType.LaserShip );						
								}
						}		

						
						if( lTime - cTimeAutoFire2 > 0.700 ){
								cTimeAutoFire2 = lTime;

								for( SwarmBoid lBoid : cBoids ) {
										((Ship)lBoid).fleetCallFire( ShipType.MissileShip );						
				}
						}
				}
		}	
		//-------------------------- 
		// Verifie s'il faut tirer
		boolean dectectEnemy() {
				
				Fleet lTarget =((MyGamer)World.Get().getGamerHuman()).getMyFleet();
				Point2D.Double lTargetLocation = lTarget.getLocation();
				double lY = lTargetLocation.y - cLocation.y ;
				if( lY < 100 && lY > -100 ) {
						if( cLocation.x - lTargetLocation.x < 500 )
								return true;
				}
				return false;
		}
		//-------------------------- 
		// Dirige l'essaim
		void autoPilot(double pTimeDelta) {
				cTargetPoint.x += cSpeed.x * pTimeDelta;
				cTargetPoint.y += cSpeed.y * pTimeDelta;
		}

				
    //-----------------------------------------------------------
		public void worldCallClose() {

				// INCREASE COMPTEUR !!!!
		}	

};
//*************************************************
