package org.phypo.PPgTestGame3d;

import org.phypo.PPgGame3d.*;


//*************************************************

public class LevelTest extends LevelBase{

    int cMode;

    //------------------------------------------------
    public LevelTest( String pLevelName, EnumFaction pFaction, float pTimeOfLife, float pIntervalle, int pMode ){
				
        super( pLevelName, pFaction, pTimeOfLife, pIntervalle  );

        cMode = pMode;

        System.out.println( "LevelTest.LevelTest" );
				
    }		
    //------------------------------------------------
    static ActorMobil Relocation( ActorMobil pActor ) {
				
        int i=0;
        for( i=0; i < sMaxRelocation; i++ ) {	
            System.out.println( "ActorMobil.Relocation i=" +i );

            pActor.cLocation = new Float3( sXAncor, World3d.sGlobalRandom.nextFloat( PPgTestGame3d.sTheTestGame.getHeight()/2f ), 0f );	
						
            if( PPgTestGame3d.sTheTestGame.detectCollisionActor( (byte) 0, false, pActor, PPgTestGame3d.sTheTestGame.getActorLocation(), false ) == false )
                break;
												
            if( i == sMaxRelocation ){		
                //		System.out.println( "ActorMobil.Relocation return null" );
                return null;
            }
        }
        //	System.out.println( "ActorMobil.Relocation return ok" );
        return pActor;
        
    }		
    //----------------------------------------------------------
    @Override	protected void callCreateSprites( float  pTimeDelta ){

        //				System.out.println( "LevelTest.callCreateSprites" );

        //			Fleet lFleet =	null;
        //	if( 1+1 == 2 )return ;
				
        float lAlea =  World3d.sGlobalRandom.nextFloatPositif( 100f );


        //			if( lAlea > 80 )

        //				System.out.println( "Ancor:" + sXAncor + " sUnit:" + sUnit );

        float lSize       = 25 +  World3d.sGlobalRandom.nextIntPositif(30); 
        float lComplexity = (int)(lSize/10); // + World3d.sGlobalRandom.nextIntPositif(2); 
        /*
          ActorMobil lMobil = FactoryActor.MakeAsteroid( new Float3(),
          lComplexity, 
          sUnit*(lComplexity*6f + World3d.sGlobalRandom.nextFloat(lComplexity*5f)), 
          null,
          new Aspect3d( new Color4( 0.4f, 0.6f, 9.0f, 0.3f+World3d.sGlobalRandom.nextFloat(0.1f) ),
          Aspect3d.DrawStyle.FILL ));
        */

        ActorMobil lMobil = null;
						
        if( lAlea > 90f ) {
            lMobil = ShipMaker.MakeShip1( EnumFaction.Red, 1f, Weapon.WeaponType.WeaponSmallPlasma );
        } 
        else if( lAlea > 80f ) {
            lMobil = ShipMaker.MakeShip2( EnumFaction.Red, 1f,  0);					 
        }
				
        else if( lAlea > 70f ) {
            lMobil = ShipMaker.MakeShip3( EnumFaction.Red, 1.2f, 0 );					 
        }	

        else if( lAlea > 60f ) {
            lMobil = ShipMaker.MakeShip4( EnumFaction.Red, 1.2f, 0 );					 
        }	
				
        else if( lAlea > 50f ) {
            lMobil = ShipMaker.MakeShip5( EnumFaction.Red, 1.4f, Weapon.WeaponType.WeaponPlasmaGreen );	

        }
				
        else {
            switch( cMode ){
                //=====================================
            case 0:  lMobil = Asteroid.CreateAsteroid( Asteroid.AsteroidType.AsteroidAlien1,
                                                       1.0f,
                                                       (int)(lComplexity-2f), 
                                                       sUnit*(lSize-5)  );
                break;
                //=====================================
						
            case 1: lMobil = Asteroid.CreateAsteroid( Asteroid.AsteroidType.AsteroidIce,
                                                      1.0f,
                                                      (int)lComplexity, 
                                                      sUnit*lSize  );
                break;
								
                //=====================================
								
            case 2: lMobil = Asteroid.CreateAsteroid( Asteroid.AsteroidType.AsteroidMars,
                                                      1.0f,
                                                      (int)lComplexity, 
                                                      sUnit*lSize );
                break;
            case 3: lMobil = Asteroid.CreateAsteroid( Asteroid.AsteroidType.AsteroidWater,
                                                      1.0f,
                                                      (int)lComplexity, 
                                                      sUnit*lSize );
                break;
                //=====================================
            default:
								
            }
            lMobil.setSpin( Float3.GetRandomZ( World3d.sGlobalRandom, 200f, 300f) );
        }
				
	
				
        lMobil = Relocation( lMobil );
        // Pour visualiser la BoundingSphere
        // 	((Node3d)lMobil.getNode()).addChild( new Leaf3d( ModelQuadric.GetSphere( lMobil.getBoundingSphere(), 7, 7 ), null, new Aspect3d( new Color4( 0, 0, 1, 0.3 ))));

        /*	else 
                if( lAlea > 50 ) 								
                lActor = FactoryActor.MakeShip1( getFaction() );
                else  
                if( lAlea > 40 )							
                lActor	 = FactoryActor.MakeContainer();
                else  
                if( lAlea > 20 )
                lActor	 = FactoryActor.MakeAsteroid11( getFaction() );
                else  
                if( lAlea > 10 )
                lActor	 = FactoryActor.MakeAsteroid12( getFaction() );
                else  
                if( lAlea > 0 )
                lActor	 = FactoryActor.MakeAsteroid2( getFaction() );
        */
        if( lMobil == null )
            return ;

				
        cLastTime = (float)World3d.Get().getGameTime();		


        lMobil.setSpeed( new Float3( cSpeedDefilX, 0f, 0f ));
        //			lMobil.setRotation( Float3.GetRandom( World3d.sGlobalRandom, 1000f) );
        lMobil.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.DeleteOutOfWorld ));

        World3d.sTheWorld.addActor( lMobil );
    }
};
