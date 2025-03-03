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

import org.phypo.PPg.PPgImg.*;


//*************************************************
public class Ship extends SwarmBoid implements ShipBase {

		PPgImg  cImg = null;
		
		//======= implementation interface ShipBas
		double  cResistance =1;
		public double getShipResistance() { return cResistance; }
		public double getFieldPower()     { return((Fleet)cMySwarm).getFieldPower(); }
		public double getDamage()         { return (getBoundingSphere()/10)+1; }
		//========================================
		

		public ShipType cShipType = ShipType.LaserShip;
		

		//------------------------------------------------
		public Ship(  PPgImg pImg, double pX, double pY, Color pColor ) {
				super(  pX,  pY, pColor );

				cImg =pImg;
				if( cImg != null ) {
						setBoundingSphere(cImg.getHeight());
				}
				else
						setBoundingSphere( 7 );

				setActorType( SpriteType.SpriteShip.code );
		}
		 
		//-------------------------- 
	public static Color sFieldColor[]={
						new Color(  .2f,  .0f,  .7f,  .25f ),
						new Color(  .3f,  .0f,  .9f,  .2f ),
						new Color(  .3f,  .0f,  .8f,  .18f )

				/*			new Color( 1f, 0.0f, 0.0f, 0.1f ),
				new Color( 0f, 1f, 0f, 0.1f ),
				new Color( 0f, 0f, 1f, 0.1f )
				*/	
		};


		public static BasicStroke sFieldStroke[] = { 
				new BasicStroke( 1 ), 
				new BasicStroke( 2 ), 
				new BasicStroke( 3 ), 
				new BasicStroke( 4 ), 
				new BasicStroke( 5 ), 
				new BasicStroke( 6 ), 
				new BasicStroke( 7 ), 
				new BasicStroke( 8 ), 
				new BasicStroke( 9 ), 
				new BasicStroke( 10 ), 
				new BasicStroke( 11), 
				new BasicStroke( 12 ), 
				new BasicStroke( 13 ), 
				new BasicStroke( 14 ), 
				new BasicStroke( 15 ), 
				new BasicStroke( 16 ),
				new BasicStroke( 17 ), 
				new BasicStroke( 18 ), 
				new BasicStroke( 19 ), 
				new BasicStroke( 20 ), 
				new BasicStroke( 21 ), 
				new BasicStroke( 22 ), 
				new BasicStroke( 23 ), 
				new BasicStroke( 24 ), 
				new BasicStroke( 25 ), 
				new BasicStroke( 26 ), 
				new BasicStroke( 27 ), 
				new BasicStroke( 28 ), 
				new BasicStroke( 29 ), 
				new BasicStroke( 30 ), 
		};

	
		public void worldCallDraw( Graphics2D pG ){


				Point2D.Double lPos      = getLocation();
				/*
				pG.setColor( Color.white );
				pG.fillOval( (int)(lPos.getX()-1), 
										 (int)(lPos.getY()-1),
										 2,2);

				*/

				PPgVector lSpeed  = new PPgVector( 100, 0 );

				if(  getFaction() != EnumFaction.Blue )
						lSpeed  = getSpeed();
		
				int lSize = (int)getBoundingSphere();
				

				if(  cImg != null ){
						
						cImg.draw( pG, (int)lPos.x,  (int) lPos.y, 0, 0  );		
				}
				else {
						
						int lVx[]=new int[5];
						int lVy[]=new int[5];
						
				
						// La pointe
						lVx[0] = lSize;
						lVy[0] = 0;
						
						
						// partie basse
						lVx[1] = -lSize;
						lVy[1] = -lSize;
						
				// la base
						lVx[2] = -lSize/2;
						lVy[2] = 0;
						
						// partie haute
						lVx[3] = -lSize;
						lVy[3] = lSize;
						
						lVx[4] = lVx[0];
						lVy[4] = lVy[0];
						
						lSpeed.transformDirection( lVx, lVy, 5 );
						
						
						for( int i=0; i< 5; i++ ) {
								lVx[i] += lPos.x;
								lVy[i] += lPos.y;
						}
				
						pG.setColor( cShipType.cColor );
						
						//	pG.setColor( cColor);
						pG.fillPolygon( lVx, lVy, 4 );
						
						
						pG.setColor( cShipType.cColor.darker() );
						pG.drawPolyline(  lVx, lVy, 5 );
				
						for( int i=0; i< 5; i++ ) {
								lVx[i] -= 1;
								lVy[i] -= 1;
						}
						pG.setColor( cShipType.cColor.brighter() );
						pG.drawPolyline(  lVx, lVy, 5 );						
				}					

				
				lSize = (int)(lSize*(1.5f+ World.sGlobalRandom.nextFloatPositif(0.3f)));
				
				//			double lSz =(sFieldStroke.length-1)*(((Fleet)cMySwarm).getFieldPower())/((Fleet)cMySwarm).getFieldPowerMax();
				
				double lSz =(sFieldStroke.length-1)*(((Fleet)cMySwarm).getFieldPower())/ShipField.sFieldPowerMax;
				
				pG.setColor( sFieldColor[ World.sGlobalRandom.nextIntPositif(sFieldColor.length-1) ] );
				pG.setStroke( sFieldStroke[(int)lSz] );
				
				pG.drawOval( (int)(lPos.getX()-lSize/2), 
										 (int)(lPos.getY()-lSize/2),
										 lSize,lSize);
				
				pG.setStroke( sFieldStroke[0] );

				//			pG.drawLine( (int)lPos.getX(), (int)lPos.getY(), 
				//									 (int)(lPos.getX()+lSpeed.getX()/5), 
				//									 (int)(lPos.getY()+lSpeed.getY()/5) );
		}
		//------------------------------------------------
		public double fleetCallGetPowerMax()                      { return 0;}
		public double fleetCallGetPower(double pTimeDelta)        { return 0;}
		public void   fleetCallFire( ShipType pShipType )    {}	
	
 		//------------------------------------------------
		public double	callApplyDamage( double pDamage){

				double lResul = cResistance- pDamage;

				if( lResul < 0 ) {						
						cResistance = 0;
						return -lResul;
				}
				// On pourrait faire varier l'aspect du vaisseau pour
				// refleter les degats subits
				cResistance = lResul;
				return 0;
		}

 		//------------------------------------------------
		public void callDestruction(){	
				PPgExplosion lExplo = new PPgExplosion( EnumFaction.Neutral, cLocation, (int)getBoundingSphere()*3, 1.0f, .8f, .4f, .9f, 0.3, true );
				World.Get().addActor(lExplo);
								
				setDeleted();
		} 
 		//------------------------------------------------
		//  Il y a eu collision du vaisseau avec qq chose
		public void worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {


				double lDamage=0;
				if( pActor.getActorType() == SpriteType.SpriteWeapon.code )
						lDamage =  ((Weapon)pActor).getDamage();
				else if( pActor.getActorType() == SpriteType.SpriteExplosion.code)
						lDamage = 1.0;   // Il faudrait un callDamage a l'explosion
				else 
						lDamage =( pActor.getBoundingSphere()/5)+1;

				
				if( lDamage >0  && ( lDamage = callApplyDamage( lDamage )) > 0 ) {
						//				if( lDamage >0  && ( lDamage =	((Fleet)cMySwarm).callApplyDamage( lDamage )) > 0 ) {
						// Le champ de force c'est effondré !		dans lDamage il reste les dommages non absorbes
						// il faudrait tester la resistance du vaisseau ...
						callDestruction();
				}							 																			 		 				
		}

		//------------------------------------------------
};
		/*
				case LaserShip:
				case MissileShip:
				case SupporShip:
				case LeaderShip:
				case FieldShip :   
		*/