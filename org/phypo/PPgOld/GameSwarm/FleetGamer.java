package org.phypo.PPg.PPgSwarm;



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
// La flotte diriger par le joeur

public class FleetGamer extends Fleet {

		GamerHuman cMyGamer;

		double  cTimeAutoFire1 = 0;
		double  cTimeAutoFire2 = 0;


		public	FleetGamer( String pName, GamerHuman pMyGamer, Color pColor, 
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

				//===============================
				super.worldCallAct(  pTimeDelta );	
				//===============================

				computeSwarmPositionAndBoundingSphere();


								
				if( cTimeAutoFire1 != 0 ){
						double lTime = World.Get().getGameTime();
						if( lTime - cTimeAutoFire1 > 0.20 ){
								cTimeAutoFire1 = lTime;
								fire1();
						}
				}
				if( cTimeAutoFire2 != 0 ){
						double lTime = World.Get().getGameTime();
						if( lTime - cTimeAutoFire2 > 0.35 ){
								cTimeAutoFire2 = lTime;
								fire2();
						}
				}

		}	
		//-----------------------------------------------------------
		public void worldCallClose() {
				////////				World.Get().worldExternalCallEndLevel( this, true );	
		}	
		//-------------------------- 
		public void worldCallDraw( Graphics2D pG ){
				super.worldCallDraw( pG );

				pG.setColor( Color.white );
				pG.drawRect( 7, 30, 100, 5 );

				pG.setColor( Color.green );
				pG.fillRect( 8, 31, (int)((100*cFieldPower)/cFieldPowerMax), 3);
				
		}
		//------------------------------------------------
		//------------- Gamer call  ----------------------
		//------------------------------------------------

		public void gamerCallFire1On(){
				
				fire1();
				cTimeAutoFire1 = World.Get().getGameTime();

				//		System.out.println( "=========== Fire1" );
		}
		//------------------------------------------------
		public void gamerCallFire1Off(){
				
				cTimeAutoFire1 = 0;

				//		System.out.println( "=========== Fire1" );
		}
		//------------------------------------------------
		public void gamerCallFire2On(){
				
				fire2();
				cTimeAutoFire2 = World.Get().getGameTime();

				//		System.out.println( "=========== Fire2" );
		}
		//------------------------------------------------
		public void gamerCallFire2Off(){
				
				cTimeAutoFire2 = 0;

				//		System.out.println( "=========== Fire2" );
		}
	
		//------------------------------------------------
		public void gamerCallWarp( Point2D.Double pDestination ){

				World.Get().addActor( new PPgSimpleExplosion( EnumFaction.Neutral, cLocation, 70, 0f, 1.0f, 1.0f, .5f, 0.2 ));

				setLocation( pDestination );
				setTarget( pDestination );

				for( SwarmBoid lBoid : cBoids ) {
						lBoid.setLocation( pDestination );
				}
		
				World.Get().addActor( new PPgSimpleExplosion( EnumFaction.Neutral, cLocation, 70,   0f, 1.0f, 1.0f, .5f, 0.2 ));
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		void fire1(){
				
				for( SwarmBoid lBoid : cBoids ) {
						((Ship)lBoid).fleetCallFire( ShipType.LaserShip );						
				}
		}		
		//------------------------------------------------
		void fire2(){
				
				for( SwarmBoid lBoid : cBoids ) {
						((Ship)lBoid).fleetCallFire(ShipType.MissileShip );						
				}
		}
	};
//*************************************************
