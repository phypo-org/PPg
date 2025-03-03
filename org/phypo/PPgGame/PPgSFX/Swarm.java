package org.phypo.PPgGame.PPgSFX;



import java.awt.Color;
import java.awt.Graphics2D;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math;

import java.util.*;

import java.awt.*;


import org.phypo.PPg.PPgMath.*;
import org.phypo.PPgGame.PPgGame.*;
 
//*************************************************

public abstract class Swarm extends ActorMobil{

		protected Rectangle2D.Double cBoundingBox = new Rectangle2D.Double();;
		protected Rectangle2D.Double getBoundingBox()    {  return null;}	


		public boolean  cBoidLocalGestion = true;

		protected Point2D.Double  cTargetPoint = new Point2D.Double();
		public void setTarget( double pX, double pY ) { cTargetPoint.x = pX; cTargetPoint.y = pY; }
		public void setTarget( Point2D.Double pTargetPoint ) { cTargetPoint.setLocation(pTargetPoint);  }


		protected double cNbBoids     = 50;
		int cRandomCreateDispersion=1;

		public double cTargetAttract    = 0.003;
		public double cCohesionAttract  = 0.002;
		public double cAlignAttract     = 0.003 ;
    public double cSeparationRepuls = 0.5;

    public double cSpeedMax=50;
    public double cSquareDistanceMax=300*300;
    public double cSquareDistanceEvit=50*50;
		public double cObstacleInteraction=1.0;

		public double cRalentissement=0.9;

		String cName = "Unknown" ;
		public String getName()   { return cName;}

		public Color  cColor= Color.green;

		public boolean cUseSqrt =true;


		public double cMaxAttract=500;


		protected SwarmBoidFactory cSwarmBoidFactory = null;

		
		protected ArrayList< Point2D.Double > cToAvoid   = null;
		protected ArrayList< Point2D.Double > cToAttract = null;

		protected ArrayList<ActorLocation> cActorToAvoid = null;

		//   double cAngleVision=90; //soit 180° au total

		protected ArrayList<SwarmBoid> cBoids= null; //new ArrayList<Boid>(100);

		public ArrayList<SwarmBoid> getBoids() { return cBoids; }

		
		public void setAvoidPoint( ArrayList<Point2D.Double> pToAvoid) { 
				cToAvoid = pToAvoid;
		}
		public void setAttractPoint( ArrayList<Point2D.Double> pToAttract) { 
				cToAttract	= pToAttract;
		}
		public void setAvoidActorLocation( ArrayList<ActorLocation> pToAvoid) { 
				cActorToAvoid = pToAvoid;
		}

		//-----------------------------------------------
		public Swarm( String pName,  Color pColor, 
									int pNbBoid, double pX, double pY, int pRandom ){

				super( pX, pY, EnumFaction.Neutral );
				cRandomCreateDispersion = pRandom;
				cName = pName;
				cNbBoids = pNbBoid;
				cBoids = new ArrayList<SwarmBoid>(pNbBoid);	
		}

		//-----------------------------------------------
		public Swarm( String pName,  Color pColor, 
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
					 double pObstacleInteraction) {
				
				super( pX, pY, EnumFaction.Neutral );
				cNbBoids = pNbBoid;
				cBoids = new ArrayList<SwarmBoid>(pNbBoid);	

				cRandomCreateDispersion = pRandom;
				cName = pName;
				
				cUseSqrt = pUseSqrt;
				cMaxAttract = pMaxAttract;

				cColor = pColor;


				cTargetAttract    = pTargetAttract;
				cCohesionAttract  = pCohesionAttract;
				cAlignAttract     = pAlignAttract;
				cSeparationRepuls = pSeparationRepuls;

				cSpeedMax    = pVitesseMax;
				cSquareDistanceMax = pDistanceMax  * pDistanceMax; //*pDistanceMax;
				cSquareDistanceEvit =pDistanceEvit * pDistanceEvit;

				cObstacleInteraction = pObstacleInteraction;
		}
		//------------------------------------------------
		public SwarmBoid  createBoid( double pX, double pY, Color pColor, int pUser1, double pUser2  )
		{
				SwarmBoid lBoid = new SwarmBoid( pX, pY, pColor );

				lBoid.setBoundingSphere( 7 );
				return lBoid;
		}
		//------------------------------------------------
		public void addBoid( Random pRand, int pUser1, double pUser2) {

				SwarmBoid lBoid = null;
				if( cSwarmBoidFactory != null)
						lBoid = cSwarmBoidFactory.newInstance( this, cLocation.x + (pRand.nextInt()%cRandomCreateDispersion*2)-cRandomCreateDispersion, 
																									 cLocation.y+ (pRand.nextInt()%cRandomCreateDispersion*2)-cRandomCreateDispersion,
																									 cColor, pUser1, pUser2 );
				else
						lBoid = createBoid( cLocation.x + (pRand.nextInt()%cRandomCreateDispersion*2)-cRandomCreateDispersion, 
																cLocation.y+ (pRand.nextInt()%cRandomCreateDispersion*2)-cRandomCreateDispersion,
																cColor, pUser1, pUser2 );
				
				cBoids.add(lBoid );
				lBoid.cMySwarm = this;
				
				lBoid.setFaction( getFaction() );
				
				if( cBoidLocalGestion == false )
						World.Get().addActor( lBoid );				
		}
		//------------------------------------------------
		public void addBoid( SwarmBoid pBoid ) {

				pBoid.setLocation( cLocation );

				cBoids.add(pBoid );
				pBoid.cMySwarm = this;
				
				pBoid.setFaction( getFaction() );
				
				if( cBoidLocalGestion == false )
						World.Get().addActor( pBoid );				
		}
		//------------------------------------------------
		public void worldCallInit(){

				Random lRand = new Random();
				for( int i=0; i< cNbBoids ; i++) {

						addBoid(lRand, 0, 0 ); 					
				}						
		}
		//------------------------------------------------
		public void  setFaction( EnumFaction pFaction )  {
				super.setFaction( pFaction );
				
				for( SwarmBoid lBoid : cBoids ) {
						lBoid.setFaction( pFaction); 					
				}				
		}						
		//------------------------------------------------
		//------------------------------------------------
		//-----------------------------------------------
		// Ajouter une force/intensite + rayon 
		void addAvoidPoint(  Point2D.Double pPoint  )
		{
				cToAvoid.add( pPoint );
		}
		//-----------------------------------------------
		void addAttractPoint(  Point2D.Double pPoint  )
		{
				cToAttract.add( pPoint );
		}
		//-----------------------------------------------
		void avoidPoint( Point2D.Double pPt, Point2D.Double pToAvoid,  
								 Point2D.Double pDeltaV, double pDistance ) {

				if( pDistance < 0.1 )
						pDistance = 0.1 ;

				pDeltaV.x = (( pPt.x - pToAvoid.x )/pDistance)*cObstacleInteraction;
				pDeltaV.y = (( pPt.y - pToAvoid.y )/pDistance)*cObstacleInteraction;				
		}
		//-----------------------------------------------
		void attractPoint( Point2D.Double pPt, Point2D.Double pToAvoid,  
								 Point2D.Double pDeltaV, double pDistance ) {

				if( pDistance < 1 )
						pDistance = 1 ;

				pDeltaV.x = (( pToAvoid.x - pPt.x)/pDistance)*cObstacleInteraction;
				pDeltaV.y = (( pToAvoid.y - pPt.y)/pDistance)*cObstacleInteraction;				
		}
		
		//------------------------------------------------
		//------------------------------------------------
		//-----------------------------------------------
    // Verify if  A see B
		boolean see( SwarmBoid pA, SwarmBoid pB, double pDistance ) {
				
							
				double lDx = pA.cLocation.x - pB.cLocation.x;
				double lDy = pA.cLocation.y - pB.cLocation.y;

				if( ((lDx*lDx)+(lDy*lDy)) > pDistance ) {
						return false;
				}
				
				return true;

				// On ne tient pas compte de l'angle
				//				float lAngle = Math.abs( arctan2( x, y )*180/pi);
				//				return lAngle <= sAngleVision ;
		}		
		//-----------------------------------------------
		void avoid( Point2D.Double pPt, Point2D.Double pToAvoid,  
								Point2D.Double pDeltaV  ) {

				
				double lDistX =  	pToAvoid.x 	- pPt.x ;
				double lDistY =  	pToAvoid.y 	- pPt.y ;
				
				//				double lDist = Math.sqrt( lDistX*lDistX+lDistY*lDistY );
				double lDist = lDistX*lDistX+lDistY*lDistY ;
				
				if( lDist < 0.001 ) 
						lDist = 0.001;
				
				//			pDeltaV.x += (-1.0*lDistX)/lDist;
				//				pDeltaV.y += (-1.0*)lDistY)/lDist;

				pDeltaV.x += -lDistX/lDist;
				pDeltaV.y += -lDistY/lDist;
		}
		//-----------------------------------------------
		//-----------------------------------------------
		//-----------------------------------------------

		void 	moveAll( double pTimeDelta,  double pTargetX, double pTargetY ) {
				
				//	pTimeDelta *= 5; // valeur arbitraire !!!


				// On passe tout les Boids en revue
				int i=0; 
				for( SwarmBoid lBoid : cBoids ) {

						i++ ;

						double lCohesionX = 0;
						double lCohesionY = 0;
						double lAlignX = 0;
						double lAlignY = 0;
						double lSeparationX = 0; 
						double lSeparationY = 0; 
						double lCenterX = 0;
						double lCenterY = 0;

						int lNbBoid=0;
					 

						// On passe en revu tout ses voisins
						for( SwarmBoid lOtherBoid : cBoids ) {
								
								if( lBoid == lOtherBoid || see( lBoid, lOtherBoid, cSquareDistanceMax ) == false ) 
										continue;		 // troploin		
								
								lNbBoid++;
								
								// Calcul du centre
								Point2D.Double lBoidLocation = lOtherBoid.getLocation();
								lCohesionX += lBoidLocation.x;
								lCohesionY += lBoidLocation.y;
								
								// Direction générale

								PPgVector lBoidSpeed = lOtherBoid.getSpeed();
								lAlignX += lBoidSpeed.x;
								lAlignY += lBoidSpeed.y;
								

								/*
								double lFactor = 0.3;
								double lFactorDiv = lFactor*3;
								*/
								
								// anti collision
								if( lBoid == lOtherBoid || see( lBoid, lOtherBoid, cSquareDistanceEvit ) ) {											
										Point2D.Double lSep = new Point2D.Double();			
										avoid( lBoid.getLocation(), lOtherBoid.getLocation(), lSep );
										
										lSeparationX += lSep.x;
										lSeparationY += lSep.y;
								}
								
						}

						// si il y a des voisins, on fini les calculs des moyennes
						if( lNbBoid > 0 ) {
								
								double lDiv = 1.0 / (double) lNbBoid;
								
								lCohesionX = ((lCohesionX * lDiv) - lBoid.cLocation.x) * cCohesionAttract;
								lCohesionY = ((lCohesionY * lDiv) - lBoid.cLocation.y) * cCohesionAttract;
								
								
								lAlignX = ((lAlignX * lDiv )-lBoid.cSpeed.x) * cAlignAttract;
								lAlignY = ((lAlignY * lDiv )-lBoid.cSpeed.y) * cAlignAttract;
								
								lSeparationX = lSeparationX * cSeparationRepuls;
								lSeparationY = lSeparationY * cSeparationRepuls;				
						}


						// la dernière force les poussent tous vers la cible
						
						lCenterX = pTargetX - lBoid.cLocation.x;
						lCenterY = pTargetY - lBoid.cLocation.y;
						
						if( cMaxAttract !=0 ) { // seulement si assez pres du centre dans certain cas 
								if( (lCenterX*lCenterX+ lCenterY*lCenterY ) > cMaxAttract*cMaxAttract ) {
										
										//2		if( (lCenterX*lCenterX+ lCenterY*lCenterY ) > cMaxAttract*cMaxAttract*4 ) {

										//												lCenterX = 0;
										//	lCenterY = 0;
										//2										}
										//										else
										{
												
												if( cUseSqrt ) {
														if( lCenterX < 0 )
																lCenterX = - Math.sqrt( - lCenterX );
														else
																lCenterX =  Math.sqrt( lCenterX );
														
														if( lCenterY < 0 )
																lCenterY = - Math.sqrt( - lCenterY );
														else
																lCenterY =  Math.sqrt( lCenterY );
												}
										}										
								}
						}

						lCenterX  *=  cTargetAttract;
						lCenterY  *=  cTargetAttract;
								
						/*
						if( cUseSqrt ) {
								if( lCenterX < 0 )
										lCenterX = - Math.sqrt( - lCenterX );
								else
										lCenterX =  Math.sqrt( lCenterX );

								if( lCenterY < 0 )
										lCenterY = - Math.sqrt( - lCenterY );
								else
										lCenterY =  Math.sqrt( lCenterY );
	
										}*/

						// on combine toutes les infos pour avoir la nouvelle vitesse						
						//					lBoid.cSpeedx += lCohesionX+lAlignX+lCenterX;
						//						lBoid.cSpeedy = lCohesionY+lAlignY+lCenterY;
					
											
						double lDeltaVx = lCenterX;
						double lDeltaVy = lCenterY;
						
						lDeltaVx += lCohesionX;
						lDeltaVy += lCohesionY;
						
						lDeltaVx += lAlignX;
						lDeltaVy += lAlignY;
						
						lDeltaVx += lSeparationX;
						lDeltaVy += lSeparationY;
						

						
						
						// Les points a eviter
						Point2D.Double lDeltaAvoid = new Point2D.Double();
						double lParamDistance = 50; // A MODIFIER
						
						if( cToAvoid != null)
						for( Point2D.Double lToAvoid : cToAvoid ) {

								double lDistance =  lBoid.getLocation().distance( lToAvoid );
								if( lDistance > lParamDistance )
										continue;

								if( lDistance < 0.001 )
										lDistance = 0.001;
							
								avoidPoint( lBoid.getLocation(), lToAvoid, lDeltaAvoid,  lDistance );

								lDeltaVx += lDeltaAvoid.x*lParamDistance/(lDistance*lDistance);
								lDeltaVy += lDeltaAvoid.y*lParamDistance/(lDistance*lDistance);
						}


						if( cActorToAvoid != null)
						for( ActorLocation lActorToAvoid : cActorToAvoid ) {
								
								if( lActorToAvoid == this ) // pas lui meme !
										continue;

								lParamDistance = lActorToAvoid.getBoundingSphere();


								double lDistance =  lBoid.getLocation().distance( lActorToAvoid.getLocation() );
								if( lDistance > lParamDistance )
										continue;

								if( lDistance < 0.001 )
										lDistance = 0.001;
							
								avoidPoint( lBoid.getLocation(), lActorToAvoid.getLocation(), lDeltaAvoid,  lDistance );

								lDeltaVx += lDeltaAvoid.x*lParamDistance/(lDistance*lDistance);
								lDeltaVy += lDeltaAvoid.y*lParamDistance/(lDistance*lDistance);
						}

						// Les points attracteurs
					  lParamDistance = 30;
						if( cToAttract != null)
						for( Point2D.Double lToAttract : cToAttract ) {

								double lDistance =  lBoid.getLocation().distance( lToAttract );
								if( lDistance > lParamDistance )
										continue;
							
								if( lDistance < 0.001 )
										lDistance = 0.001;

								attractPoint( lBoid.getLocation(), lToAttract, lDeltaAvoid,  lDistance );
							

								lDeltaVx += lDeltaAvoid.x*lParamDistance/lDistance; //*lDistance;
								lDeltaVy += lDeltaAvoid.y*lParamDistance/lDistance; //*lDistance;
						}
					 




						lBoid.cSpeed.x *= cRalentissement;  
						lBoid.cSpeed.y *= cRalentissement;


						double lSpeedX = lBoid.getSpeed().x;
						double lSpeedY = lBoid.getSpeed().y ;

						// A ENLEVER ????
						if( cUseSqrt ) {

								if( lDeltaVx > 0 )
										 lSpeedX += Math.sqrt( lDeltaVx );
								else
										lSpeedX-= Math.sqrt( -lDeltaVx );
								
								if( lDeltaVy > 0)
										lSpeedY += Math.sqrt( lDeltaVy );
								else
										lSpeedY -= Math.sqrt( -lDeltaVy );
						}
						else 
						
						{
								lSpeedX += lDeltaVx; 
								lSpeedY += lDeltaVy;
						}

						//=========
						if( cSpeedMax !=0 ){
								
								// on fera sqrt plus tard s'il y a lieu 
								lBoid.cNormSpeed = lSpeedX*lSpeedX+lSpeedY*lSpeedY;

								
								//				System.out.print( "move x:" + (int)x +" y:" +(int)y + " vx:" + cSpeedx + " vy:" + cSpeedy +  " VA:" + cNormSpeed + " MAX:" + pSpeedMax);
								
								
								
								if(  lBoid.cNormSpeed > cSpeedMax*cSpeedMax ) { 
										
										lBoid.cNormSpeed = Math.sqrt(lBoid.cNormSpeed );
										
										//	System.out.println( "move :" + pSpeedMax  + " speed:" + cNormSpeed);
										double lFact =  cSpeedMax/lBoid.cNormSpeed;
										
										//						System.out.print( " Modif:" + pSpeedMax/cNormSpeed );
										lSpeedX *= lFact;
										lSpeedY *= lFact;
								}
						}
						//=========

						//						lBoid.getLocation().recompute();
						
						
						lBoid.setSpeed( lSpeedX, lSpeedY );

						/*
						lBoid.cSpeedx +=  lDeltaVx /3;	
						lBoid.cSpeedy +=  lDeltaVy /3;

						*/

						/*						
						System.out.println( "i:" +i  + " nb:" + lNbBoid + " Center:" + (int)lCenterX + "/" +(int)lCenterY 
																+ " Cohesion:" + (int)lCohesionX + "/" + (int)lCohesionY
																+ " Align:" + (int)lAlignX +"/" +(int)lAlignY 
																+ " Repuls:" + cSeparationRepuls+ " Separ:" + (int)lSeparationX + "/" 
																+ (int)lSeparationY 
																);
						*/

						//						lBoid.move( pTimeDelta, cSpeedMax );
				} 
				//				System.out.println( "move :" +i  );
		}
		//-----------------------------------------------
		public void worldCallAct( double pTimeDelta ) {


				super.worldCallAct(pTimeDelta );

 
				moveAll( pTimeDelta, cTargetPoint.x, cTargetPoint.y );	

				
				if( cBoidLocalGestion )
						{
								double lCurrentTime = World.Get().getGameTime();
								for( SwarmBoid lBoid : cBoids ) {
										if( lBoid.testTimeOfLife( lCurrentTime ) ){
												lBoid.setDeleted();								
												lBoid.worldCallClose();
										}
										else
												lBoid.worldCallAct( pTimeDelta  );						
								}
						}

				ListIterator<SwarmBoid> lIterBoid = cBoids.listIterator() ;
				while( lIterBoid.hasNext() ){
						SwarmBoid lBoid =lIterBoid.next(); 
						if( lBoid.isDeleted() ){
								if( cBoidLocalGestion )
										lBoid.worldCallClose();

								lIterBoid.remove();	
						}			 
				}


				// Si le Swarm est vide il est detruit
				if( cBoids.size() == 0){
						setDeleted(); 
				}
						
		}

		//-------------------------- 
		public void worldCallDraw( Graphics2D pG ){
				
				// 	System.out.println( "Swarm.draw:" + cName );
				/*
				for( SwarmBoid lBoid : cBoids ) {
						lBoid.draw( pG  );						
				}				
				*/

				if( cBoidLocalGestion )
						for( SwarmBoid lBoid : cBoids ) {
								lBoid.worldCallDraw( pG  );						
						}

		}
		//-----------------------------------------------

		public void computeSwarmPositionAndBoundingSphere() {

				double lMiddleX=0, lMiddleY=0;
				double lMinX =  9E10;
				double lMinY =  9E10;
				double lMaxX = -9E10;
				double lMaxY = -9E10;

				for( SwarmBoid lBoid : cBoids ) {

						double lX = lBoid.cLocation.x;
						double lY = lBoid.cLocation.y;

						lMiddleX += lX;
						lMiddleY += lY;

					 
						if( lX-lBoid.getBoundingSphere() < lMinX  )
								lMinX = lX-lBoid.getBoundingSphere();
						
						if( lX+lBoid.getBoundingSphere() > lMaxX  )
								lMaxX = lX+lBoid.getBoundingSphere();

						if( lY-lBoid.getBoundingSphere() < lMinY  )
								lMinY = lY-lBoid.getBoundingSphere();
						
						if( lY+lBoid.getBoundingSphere() > lMaxY  )
								lMaxY = lY+lBoid.getBoundingSphere();
				}	

				lMiddleX /= cBoids.size();
				lMiddleY /= cBoids.size();

				setLocation( lMiddleX, lMiddleY );

				

				cBoundingBox.setRect(lMinX, lMinY, lMaxX-lMinX, lMaxY-lMinY);


				double lDistX = Math.max(lMaxX - lMiddleX, lMiddleX - lMinX);
				double lDistY = Math.max(lMaxY - lMiddleY, lMiddleY - lMinY);

				double lDistMax = Math.max( lDistX, lDistY );
				//	setBoundingSphere( Math.sqrt( lDistMax*lDistMax*2)  );
				setBoundingSphere( lDistMax) ;
				
		}
}
//*************************************************
