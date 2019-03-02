package org.phypo.PPg.PPgJ3d;

import org.phypo.PPg.PPgMath.*;


//*************************************************
public class ActorLocation extends ActorBase
{
    public  Float3 cLocation  = new  Float3();
		public  Float3 cRotation;
		public  Float3 cScale;           

		public NodeBase cNode3d;
		public void     setNode( Node3d pNode ){ cNode3d = pNode; }
		public NodeBase getNode()              { return cNode3d;  }


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
		public ActorLocation(EnumFaction pFaction	) {
				super( pFaction );
		}
		//------------------------------------------------
		public void destroy(){
				cNode3d.destroy();
		}
		//------------------------------------------------
    public void setRotation( Float3 pRotation)  {    
        cRotation = new Float3( pRotation );
    }
		public Float3 getLocation() { return cLocation;}
		//------------------------------------------------
    public void setLocation( Float3 pLocal)  {  
        cLocation = new Float3( pLocal );
    }
		//------------------------------------------------
    public void setScale( Float3 pScale)  {  
        cScale = new Float3( pScale );
    }
		//------------------------------------------------
		//------------- Collision detection -------------
 		//------------------------------------------------
		float cBoundingSphere = 0;
		public float getBoundingSphere() { return cBoundingSphere; }
		public void  setBoundingSphere( float pBoundingSphere) {	
				if( pBoundingSphere < 0 )
						cBoundingSphere = -pBoundingSphere ; 
				else
						cBoundingSphere = pBoundingSphere ;
		}
 		//------------------------------------------------
		public boolean collisionBoundingSphere( ActorLocation pActor ) {

				//		System.out.println(" collisionBoundingSphere " +getBoundingSphere()
				//										 + " " +  pActor.getBoundingSphere() );
				/*
				if( pActor.getName().compareTo( "SpritePilot") == 0 
						||getName().compareTo( "SpritePilot") == 0 ){

						float lBoundingSphere = getBoundingSphere()+pActor.getBoundingSphere();
						lBoundingSphere *= lBoundingSphere;

						cLocation.distanceSq( pActor.cLocation );


						System.out.println(" collisionBoundingSphere " +getBoundingSphere()
															 + " " +  pActor.getBoundingSphere() 
															 + " Bs:" + lBoundingSphere + " Ds:" + cLocation.distanceSq( pActor.cLocation )
															 + " Col:" + (cLocation.distanceSq( pActor.cLocation ) < lBoundingSphere) );
				}
				*/


			if(  getBoundingSphere() <=0 ||  pActor.getBoundingSphere() <=0 )
					return false;

			//				System.out.println(" collisionBoundingSphere " +getBoundingSphere()
			//												 + " " +  pActor.getBoundingSphere() );
	
				float lBoundingSphere = getBoundingSphere()+pActor.getBoundingSphere();
				lBoundingSphere *= lBoundingSphere;

				if(  cLocation.distanceSq( pActor.cLocation ) > lBoundingSphere )
						return false;

				
				//	System.out.println(" @@@@@@@@@@@@ COLLISION @@@@@@@@@@@ " );

				return true;
		}		

 		//------------------------------------------------
		public void worldCallInit() {                        // A la creation
				cNode3d.beforeFirstTurn(  );
		}
						
 		//------------------------------------------------
		public void worldCallAct( float pTimeDelta) {

				if( cListBehavior != null ) {
						for( Behavior lBehavior : cListBehavior	)
								lBehavior.exec( this );
				}

				cNode3d.updateTurn( pTimeDelta );
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
