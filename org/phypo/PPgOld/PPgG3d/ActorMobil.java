package com.phipo.PPg.PPgG3d;



import com.phipo.PPg.PPgMath.*;


//*************************************************
public class ActorMobil extends ActorLocation 
{
		public Float3 cSpeed         = new Float3();
		public Float3 cAcceleration ;
		
    private float cMaxSpeed = 0; 
    
		public Float3 cSpin;
		public Float3 cInflation;


		//------------------------------------------------
		public ActorMobil( float pX, float pY, float pZ, 
											 EnumFaction pFaction, 
													NodeBase pNode3d ) {

				super( pX, pY, pZ, pFaction, pNode3d );
		}

		//------------------------------------------------
		public ActorMobil( Float3 pLoc, 
											 EnumFaction pFaction, 
											 NodeBase pNode3d ) {

				super( pLoc, pFaction, pNode3d );
		}
		//------------------------------------------------
    public void move( float pTimeDelta )  {

				//		System.out.println( "\t\tActorMobil.Move  " + pTimeDelta + " " + cSpeed.x() + " " + cSpeed.y() + " " +   cSpeed.z() );
				//			System.out.println( "\t\tActorMobil.Move "  + pTimeDelta
				//												+ " x:" + cLocation.x() +  " " + cSpeed.x() + "=" + (pTimeDelta*cSpeed.x()) 
				//											+ " ->" + (cLocation.x()+pTimeDelta*cSpeed.x()));

				/*		if( cAcceleration != null ){
						cSpeed.addDelta( cAcceleration, pTimeDelta);
				
						limitSpeed();
						}*/

				cLocation.addDelta( cSpeed, pTimeDelta );
								
				//	System.out.println( "\t\tActorMobil.Move2"  + pTimeDelta
				//											+ " x:" + cLocation.x());

				//  setRotation( cSpeed.getDirection() );
    }
		//------------------------------------------------
    public void simpleMove( float pTimeDelta) 
    {             
				System.out.println( "\t\tActorMobil.simpleMove");

				cLocation.addDelta(  cSpeed, pTimeDelta );
		}
		//------------------------------------------------
    final public void limitSpeed() {
				if( cMaxSpeed > 0 )
						cSpeed.limitVal( cMaxSpeed );
    }  
		//------------------------------------------------

    public void setAccelaration( Float3 pAcc)  {        
        cAcceleration.set( pAcc );
    }
		//------------------------------------------------
    
    public void setSpeed( Float3 pSpeed ) {
        cSpeed.set( pSpeed );
    } 
		//------------------------------------------------
 
    public void setMaxSpeed(float pSpeed)  {
        cMaxSpeed = pSpeed;
    }
		//------------------------------------------------
    public void setSpin( Float3 pSpin)  {    
				if( cRotation == null )
						cRotation = new Float3();
        cSpin = new Float3( pSpin );
    }
		//------------------------------------------------
		//------------------------------------------------
    public void setInflation( Float3 pInflate)  {  
				if( cScale == null )
						cScale = new Float3( 1, 1, 1);
        cInflation = new Float3( pInflate );
    }
		
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		@Override
    public void worldCallAct( float pTimeDelta ) {
					
				//	System.out.println("\t ActorMobil.worldCallAct" );

					move(pTimeDelta);

					if( cSpin != null ){
							cRotation.addDelta( cSpin, pTimeDelta );
					}

					if( cInflation != null ){
							cScale.addDelta( cInflation, pTimeDelta );
					}

					super.worldCallAct( pTimeDelta );
    }        
   

}

//*************************************************
