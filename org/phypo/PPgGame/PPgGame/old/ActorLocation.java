package org.phypo.PPg.PPgGame;

import java.awt.Graphics2D;


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


import org.phypo.PPg.PPgMath.*;


//*************************************************

public class ActorLocation extends ActorBase 
{
    public Point2D.Double cLocation     = new Point2D.Double ();

		public  Point2D.Double getLocation()            {return cLocation;}
    public void      setLocation(double x, double y) { cLocation.setLocation(x, y);}
		public void      setLocation( Point2D.Double v) { cLocation.x = v.x; cLocation.y = v.y; }

		public double getX() { return cLocation.x; }
		public double getY() { return cLocation.y; }



		//------------------------------------------------
		public ActorLocation( double pX, double pY, EnumFaction pFaction  ) {
				super( pFaction );
				cLocation.setLocation( pX, pY );
		}

 		
 		//------------------------------------------------
		//------------- Collision detection -------------
 		//------------------------------------------------
		double cBoundingSphere = 0;
		public double getBoundingSphere() { return cBoundingSphere; }
		public void   setBoundingSphere( double pBoundingSphere) {
				                            cBoundingSphere = pBoundingSphere ; }

		Rectangle2D.Double  getBoundingBox()    {  return null;}	

 		//------------------------------------------------
		public boolean collisionBoundingSphere( ActorLocation pActor ) {

				//		System.out.println(" collisionBoundingSphere " +getBoundingSphere()
				//										 + " " +  pActor.getBoundingSphere() );
				
			if(  getBoundingSphere() <=0 ||  pActor.getBoundingSphere() <=0 )
					return false;

			//			System.out.println(" ++++++     collisionBoundingSphere dist:" 
			//												 + Math.sqrt( cLocation.distanceSq( pActor.cLocation ))  
			//												 + " spheres:" + getBoundingSphere()+pActor.getBoundingSphere());
			
			
			//			if(  Math.sqrt( cLocation.distanceSq( pActor.cLocation )) 
			//					 > (getBoundingSphere()+pActor.getBoundingSphere()) )
			//					return false;

				double lBoundingSphere = getBoundingSphere()+pActor.getBoundingSphere();
				lBoundingSphere *= lBoundingSphere;

				if(  cLocation.distanceSq( pActor.cLocation ) > lBoundingSphere )
						return false;

				
				//	System.out.println(" @@@@@@@@@@@@ COLLISION @@@@@@@@@@@ " );

				return true;
		}		
 		//------------------------------------------------		
		public boolean collisionBoundingBox( ActorLocation pActor ) {
				
				if(  getBoundingSphere() <=0 ||  pActor.getBoundingSphere() <=0 )
						return false;


				double lBoundingSphere = getBoundingSphere()+pActor.getBoundingSphere();
				lBoundingSphere *= lBoundingSphere;

				if(  cLocation.distanceSq( pActor.cLocation ) > lBoundingSphere )
						return false;
				
				Rectangle2D.Double lA = getBoundingBox();
				Rectangle2D.Double lB = pActor.getBoundingBox();

				if( lA != null ){
						if( lB != null ) {
								return lA.intersects( lB );
						}
						else {
								// test de la bounding sphere de pActor par rapport au rectangle de lA
								double lSz = pActor.getBoundingSphere();
								if( lSz == 0 ){
										return lA.contains( pActor.cLocation );
								}
								// use an rectangle whitin an ellipse for faster compute
								lB = new Rectangle2D.Double( pActor.getLocation().x,  pActor.getLocation().y, lSz, lSz );
								return lA.intersects( lB );
						}
				}
				else { 
						if( lB != null ) {
								// test de la bounding sphere de this par rapport au rectangle de lB								
								double lSz = getBoundingSphere();
								if( lSz == 0 ){
										return lB.contains( this.cLocation );
								}
								lA = new Rectangle2D.Double( cLocation.x,  cLocation.y, lSz,lSz );
								return lA.intersects( lB  );						 
						}	
				} 						
				//	System.out.println(" @@@@@@@@@@@@ COLLISION @@@@@@@@@@@ " );

				// seule les boundings spheres comptent
				return true;
		}
 		//------------------------------------------------
		//------------- Collision detection -------------
 		//------------------------------------------------

		// Fonctions a derivé
		public boolean worldCallAcceptCollision( ActorLocation pActor )                { return true;}
		public void    worldCallDetectCollision( ActorLocation pActor, boolean first ) {;}

};