package com.phipo.PPg.PPgG3d;

import com.phipo.PPg.PPgMath.*;


//*************************************************
public class ActorLocation extends ActorBase 
{
    public  Float3 cLocation  = new  Float3();
		public  Float3 cRotation;
		public  Float3 cScale;           

		public NodeBase cNode3d;
		public void setNode( Node3d pNode ){ cNode3d = pNode; }

		//------------------------------------------------
		public ActorLocation( float pX, float pY, float pZ, 
													EnumFaction pFaction, 
													NodeBase pNode3d  ) {
				super( pFaction );

				cLocation.set( pX, pY, pZ );
				cNode3d = pNode3d;
		}
		//------------------------------------------------
		public ActorLocation( Float3 pF, 
													EnumFaction pFaction, 
													NodeBase pNode3d  ) {
				super( pFaction );

				cLocation = new Float3( pF );
				cNode3d = pNode3d;
		}		
		//------------------------------------------------
		//------------- Collision detection -------------
 		//------------------------------------------------
		float cBoundingSphere = 0;
		public float getBoundingSphere() { return cBoundingSphere; }
		public void  setBoundingSphere( float pBoundingSphere) {	cBoundingSphere = pBoundingSphere ; }


 		//------------------------------------------------
		public boolean collisionBoundingSphere( ActorLocation pActor ) {

				//		System.out.println(" collisionBoundingSphere " +getBoundingSphere()
				//										 + " " +  pActor.getBoundingSphere() );
				
			if(  getBoundingSphere() <=0 ||  pActor.getBoundingSphere() <=0 )
					return false;

				float lBoundingSphere = getBoundingSphere()+pActor.getBoundingSphere();
				lBoundingSphere *= lBoundingSphere;

				if(  cLocation.distanceSq( pActor.cLocation ) > lBoundingSphere )
						return false;

				
				//	System.out.println(" @@@@@@@@@@@@ COLLISION @@@@@@@@@@@ " );

				return true;
		}		


 		//------------------------------------------------
		public void worldCallDraw( Engine  pGraf ) {
				
				Transform3d lTransf = new Transform3d( cLocation, cRotation,  cScale );       
				pGraf.render( lTransf, cNode3d );				
		}	    
 		//------------------------------------------------
		//------------- Collision detection -------------
 		//------------------------------------------------

		// Fonctions a derivé
		public boolean worldCallAcceptCollision( ActorLocation pActor )                { return true;}
		public void    worldCallDetectCollision( ActorLocation pActor, boolean first ) {;}

};
//*************************************************
