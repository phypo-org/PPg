package org.phypo.PPg.PPgTest;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPg.PPgGame.*;


//*************************************************

class Boid extends Point2D.Double  {

		
		//		public  Point2D.Double cPosition;

		public  double cSpeedx=0;
		public  double cSpeedy=0;

		public  double cNormSpeed=1;

		//------------------------------------------------
		public Boid( int pX, int pY ) {
				super(  pX,  pY );

				//				System.out.print( "Boid x:" + x + " y:" + y);
		}

		//------------------------------------------------

		public void move( double pTimeDelta, double pSpeedMax) {

				
				if( pSpeedMax !=0 ){

						// on fera sqrt plus tard s'il y a lieu 
						cNormSpeed = cSpeedx*cSpeedx+cSpeedy*cSpeedy;

												
						//				System.out.print( "move x:" + (int)x +" y:" +(int)y + " vx:" + cSpeedx + " vy:" + cSpeedy +  " VA:" + cNormSpeed + " MAX:" + pSpeedMax);
						 


						if(  cNormSpeed > pSpeedMax*pSpeedMax ) { 

								cNormSpeed = Math.sqrt(cNormSpeed );

								//	System.out.println( "move :" + pSpeedMax  + " speed:" + cNormSpeed);
								double lFact =  pSpeedMax/cNormSpeed;
								
								//						System.out.print( " Modif:" + pSpeedMax/cNormSpeed );
								cSpeedx *= lFact;
								cSpeedy *= lFact;
						}
				}
				
				x += cSpeedx*pTimeDelta;
				y += cSpeedy*pTimeDelta;

				//			System.out.println( " ->  x:" + (int)x +" y:" +(int)y + " vx:" + cSpeedx + " vy:" + cSpeedy );
		}
 		//-------------------------- 
		public void draw( Graphics pG, Color pColor ){

				//				System.out.println( "draw x:" +x +" y:" + y );

				pG.setColor( pColor );

				pG.drawLine( (int)x, (int)y, (int)(x+cSpeedx), (int)(y+cSpeedy) );	
					
				int lSz = 1;
						pG.drawLine( (int)x+lSz, (int)y+lSz, (int)(x+cSpeedx+lSz), (int)(y+cSpeedy)-lSz );				
			 			pG.drawLine( (int)x-lSz, (int)y-lSz, (int)(x+cSpeedx-lSz), (int)(y+cSpeedy)+lSz );	
							
		}				
  		//-------------------------- 
		public void drawBubble( Graphics pG, Color pColor ){

				//				System.out.println( "draw x:" +x +" y:" + y );

				pG.setColor( pColor );

				if( cNormSpeed < 0.01 ) cNormSpeed = 1 ;
						
				//				pG.drawOval(  (int)x, (int)y, (int)(4+3/(cSpeedx/cNormSpeed)), (int)(4+3/(cSpeedy/cNormSpeed)) );
				pG.drawOval(  (int)x, (int)y,(int)( 4+(cSpeedy/cNormSpeed)), (int)(4+(cSpeedx/cNormSpeed)));

		}				
   
};

//*************************************************

public class Swarm extends ActorBase{
		double cNbBoids     = 500;

		public double cTargetAttract    = 0.003;
		public double cCohesionAttract  = 0.01;
		public double cAlignAttract     = 0.1 ;
    public double cSeparationRepuls = 0.1;

    public double cSpeedMax=50;
    public double cSquareDistanceMax=200*200;
    public double cSquareDistanceEvit=50*50;


		String cName = "Unknown" ;
		Color  cColor;

		public boolean cUseSqrt;

		MyGamer cMyGamer;

		public double cMaxAttract=0;
		
		ArrayList< Point2D.Double > cToAvoid   = new ArrayList< Point2D.Double >();
		ArrayList< Point2D.Double > cToAttract = new ArrayList< Point2D.Double >();

		static ArrayList< Point2D.Double > sToAvoid   = new ArrayList< Point2D.Double >();
		static ArrayList< Point2D.Double > sToAttract = new ArrayList< Point2D.Double >();

		//   double cAngleVision=90; //soit 180° au total

		ArrayList<Boid> cBoids= null; //new ArrayList<Boid>(100);

		//-----------------------------------------------
		Swarm( String pName, MyGamer pMyGamer, Color pColor, 
					 int pNbBoid, int pX, int pY, int pRandom,

					 double pTargetAttract,
					 double pCohesionAttract,
					 double pAlignAttract,
					 double pSeparationRepuls,
					 int pVitesseMax,
					 int pDistanceMax,
					 int pDistanceEvit,
					 boolean pUseSqrt,
					 double pMaxAttract )
		{
				super( EnumFaction.Neutral );
				cName = pName;
				cMyGamer = pMyGamer;

				cUseSqrt = pUseSqrt;
				cMaxAttract = pMaxAttract;

				cColor = pColor;

				cNbBoids = pNbBoid;
				cBoids = new ArrayList<Boid>(pNbBoid);	

				cTargetAttract    = pTargetAttract;
				cCohesionAttract  = pCohesionAttract;
				cAlignAttract     = pAlignAttract;
				cSeparationRepuls = pSeparationRepuls;

				cSpeedMax    = pVitesseMax;
				cSquareDistanceMax = pDistanceMax  * pDistanceMax; //*pDistanceMax;
				cSquareDistanceEvit =pDistanceEvit * pDistanceEvit;

				Random lRand = new Random();

				for( int i=0; i< pNbBoid ; i++) {
						cBoids.add( new Boid( pX + (lRand.nextInt()%pRandom*2)-pRandom, pY+ (lRand.nextInt()%pRandom*2)-pRandom));
				}						
				/*
				cToAvoid.add(	 new Point2D.Double( 300, 300 ) );
				cToAvoid.add(	 new Point2D.Double( 400, 200 ) );
				cToAvoid.add(	 new Point2D.Double( 600, 200 ) );
								
				cToAttract.add(	 new Point2D.Double( 600, 400 ) );
				*/
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
		static void AddAvoidPoint(  Point2D.Double pPoint  )
		{
				sToAvoid.add( pPoint );
		}
		//-----------------------------------------------
		static void AddAttractPoint(  Point2D.Double pPoint  )
		{
				sToAttract.add( pPoint );
		}
		
		//-----------------------------------------------
		void avoidPoint( Point2D.Double pPt, Point2D.Double pToAvoid,  
								 Point2D.Double pDeltaV, double pDistance ) {

				if( pDistance < 0.1 )
						pDistance = 0.1 ;

				pDeltaV.x = ( pPt.x - pToAvoid.x )/pDistance;
				pDeltaV.y = ( pPt.y - pToAvoid.y )/pDistance;				
		}
		//-----------------------------------------------
		void attractPoint( Point2D.Double pPt, Point2D.Double pToAvoid,  
								 Point2D.Double pDeltaV, double pDistance ) {

				if( pDistance < 1 )
						pDistance = 1 ;

				pDeltaV.x = ( pToAvoid.x - pPt.x)/pDistance;
				pDeltaV.y = ( pToAvoid.y - pPt.y)/pDistance;				
		}
		
		//------------------------------------------------
		//------------------------------------------------
		//-----------------------------------------------
    // Verify if  A see B
		boolean see( Boid pA, Boid pB, double pDistance ) {
				
							
				double lDx = pA.x - pB.x;
				double lDy = pA.y - pB.y;

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

		void 	moveAll( double pTimeDelta,  int pTargetX, int pTargetY ) {
				
				pTimeDelta *= 30; // valeur arbitraire !!!


				// On passe tout les Boids en revue
				int i=0; 
				for( Boid lBoid : cBoids ) {

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
						for( Boid lOtherBoid : cBoids ) {
								
								if( lBoid == lOtherBoid || see( lBoid, lOtherBoid, cSquareDistanceMax ) == false ) 
										continue;		 // troploin		
								
								lNbBoid++;
								
								// Calcul du centre
								lCohesionX += lOtherBoid.x;
								lCohesionY += lOtherBoid.y;
								
								// Direction générale
								lAlignX += lOtherBoid.cSpeedx;
								lAlignY += lOtherBoid.cSpeedy;
								

								/*
								double lFactor = 0.3;
								double lFactorDiv = lFactor*3;
								*/
								
								// anti collision
								if( lBoid == lOtherBoid || see( lBoid, lOtherBoid, cSquareDistanceEvit ) ) {											
										Point2D.Double lSep = new Point2D.Double();			
										avoid( lBoid, lOtherBoid, lSep );
										
										lSeparationX += lSep.x;
										lSeparationY += lSep.y;
								}
								
						}

						// si il y a des voisins, on fini les calculs des moyennes
						if( lNbBoid > 0 ) {
								
								double lDiv = 1.0 / (double) lNbBoid;
								
								lCohesionX = ((lCohesionX * lDiv) - lBoid.x) * cCohesionAttract;
								lCohesionY = ((lCohesionY * lDiv) - lBoid.y) * cCohesionAttract;
								
								lAlignX = ((lAlignX * lDiv )-lBoid.cSpeedx) * cAlignAttract;
								lAlignY = ((lAlignY * lDiv )-lBoid.cSpeedy) * cAlignAttract;
								
								lSeparationX = lSeparationX * cSeparationRepuls;
								lSeparationY = lSeparationY * cSeparationRepuls;				
						}


						// la dernière force les poussent tous vers la cible
						
						lCenterX = pTargetX - lBoid.x;
						lCenterY = pTargetY - lBoid.y;
						
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
						double lParamDistance = 50;

						for( Point2D.Double lToAvoid : cToAvoid ) {

								double lDistance =  lBoid.distance( lToAvoid );
								if( lDistance > lParamDistance )
										continue;
							
								avoidPoint( lBoid, lToAvoid, lDeltaAvoid,  lDistance );

								if( lDistance < 0.001 )
										lDistance = 0.001;

								lDeltaVx += lDeltaAvoid.x*lParamDistance/(lDistance*lDistance);
								lDeltaVy += lDeltaAvoid.y*lParamDistance/(lDistance*lDistance);
						}



						// Les points attracteurs
					  lParamDistance = 30;
						for( Point2D.Double lToAttract : cToAttract ) {

								double lDistance =  lBoid.distance( lToAttract );
								if( lDistance > lParamDistance )
										continue;
							
								attractPoint( lBoid, lToAttract, lDeltaAvoid,  lDistance );
								
								if( lDistance < 0.001 )
										lDistance = 0.001;

								lDeltaVx += lDeltaAvoid.x*lParamDistance/lDistance; //*lDistance;
								lDeltaVy += lDeltaAvoid.y*lParamDistance/lDistance; //*lDistance;
						}
					 
						for( Point2D.Double lToAvoid : sToAvoid ) {

								double lDistance =  lBoid.distance( lToAvoid );
								if( lDistance > lParamDistance )
										continue;

								if( lDistance < 0.001 )
										lDistance = 0.001;

								avoidPoint( lBoid, lToAvoid, lDeltaAvoid,  lDistance );
								
								lDeltaVx += lDeltaAvoid.x*lParamDistance/(lDistance*lDistance);
								lDeltaVy += lDeltaAvoid.y*lParamDistance/(lDistance*lDistance);
						}

						// Les points attracteurs
					  lParamDistance = 30;
						for( Point2D.Double lToAttract : sToAttract ) {

								double lDistance =  lBoid.distance( lToAttract );
								if( lDistance > lParamDistance )
										continue;

								if( lDistance < 0.001 )
										lDistance = 0.001;

								attractPoint( lBoid, lToAttract, lDeltaAvoid,  lDistance );
								
								lDeltaVx += lDeltaAvoid.x*lParamDistance/lDistance; //*lDistance;
								lDeltaVy += lDeltaAvoid.y*lParamDistance/lDistance; //*lDistance;
						}
					 


						// Respect des limites de l'ecran 
						if( lBoid.x < 0 )
								lDeltaVx += 10;

						if( lBoid.y < 0 )
								lDeltaVy += 10;

							if( lBoid.x >1280  )
								lDeltaVx -= 10;

						if( lBoid.y > 1024 )
								lDeltaVy -= 10;

						
					


						lBoid.cSpeedx *= 0.9;
						lBoid.cSpeedy *= 0.9;



						// A ENLEVER ????
						if( cUseSqrt ) {
								if( lDeltaVx > 0 )
										lBoid.cSpeedx += Math.sqrt( lDeltaVx );
								else
										lBoid.cSpeedx -= Math.sqrt( -lDeltaVx );
								
								if( lDeltaVy > 0)
										lBoid.cSpeedy += Math.sqrt( lDeltaVy );
								else
										lBoid.cSpeedy -= Math.sqrt( -lDeltaVy );
						}
						else 
						
						{
								lBoid.cSpeedx += lDeltaVx; 
								lBoid.cSpeedy += lDeltaVy;
						}

						



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

						lBoid.move( pTimeDelta, cSpeedMax );
				} 
				//				System.out.println( "move :" +i  );
		}
		//-----------------------------------------------
		public void worldCallAct( double pTimeDelta ) {
	
		}
		//-------------------------- 
				static double sMemFps =0;
		//-------------------------- 
		public void worldCallDraw( Graphics2D pG ){
				
				//				System.out.println( "Swarm.draw:" + cName );

				for( Boid lBoid : cBoids ) {
						lBoid.draw( pG, cColor );						
				}				


				pG.setColor( Color.red );

				for( Point2D.Double lToAvoid : cToAvoid ) {						
						pG.drawLine( (int)(lToAvoid.x-10), (int)(lToAvoid.y-10), (int)(lToAvoid.x+10), (int)(lToAvoid.y+10) );							
						pG.drawLine( (int)(lToAvoid.x-10), (int)(lToAvoid.y+10), (int)(lToAvoid.x+10), (int)(lToAvoid.y-10) );	
						
				}
				for( Point2D.Double lToAvoid : sToAvoid ) {						
						pG.drawLine( (int)(lToAvoid.x-10), (int)(lToAvoid.y-10), (int)(lToAvoid.x+10), (int)(lToAvoid.y+10) );							
						pG.drawLine( (int)(lToAvoid.x-10), (int)(lToAvoid.y+10), (int)(lToAvoid.x+10), (int)(lToAvoid.y-10) );	
						
				}

				pG.setColor( Color.green );

				for( Point2D.Double lToAttract : cToAttract) {						
						pG.drawLine( (int)(lToAttract.x-10), (int)(lToAttract.y-10), (int)(lToAttract.x+10), (int)(lToAttract.y+10) );							
						pG.drawLine( (int)(lToAttract.x-10), (int)(lToAttract.y+10), (int)(lToAttract.x+10), (int)(lToAttract.y-10) );	
						
				}
				for( Point2D.Double lToAttract : sToAttract) {						
						pG.drawLine( (int)(lToAttract.x-10), (int)(lToAttract.y-10), (int)(lToAttract.x+10), (int)(lToAttract.y+10) );							
						pG.drawLine( (int)(lToAttract.x-10), (int)(lToAttract.y+10), (int)(lToAttract.x+10), (int)(lToAttract.y-10) );	
						
				}



				if( sMemFps != World.sCurrentFpsTime ) {
						System.out.println( "fps:" + 1/World.sCurrentFpsTime ); //  + " = " + World.sCurrentFpsTime  + " " + World.Get().cWantedFrameDuration );	
						sMemFps =  World.sCurrentFpsTime;
				}
		}
		//-----------------------------------------------
}
//*************************************************
