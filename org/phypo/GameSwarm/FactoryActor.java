package org.phypo.GameSwarm;


import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;

import javax.swing.ImageIcon;

import org.phypo.PPg.PPgImg.*;

import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPgGame.PPgSFX.*;
import org.phypo.PPg.PPgMath.*;


//**************************************************
class FactoryActor{

    static double sBeginPosX = 200;
    static double sBeginPosY = 50;
    static double sSpeed = -150;


    //------------------------------------------------
    static ActorMobil Relocation( ActorMobil pActor ) {
        double lW = World.Get().getWidth();
        double lH = World.Get().getHeight();
				
        int i=0;
        for( i=0; i < 5; i++ ) {	
            pActor.setLocation(  lW+200,  50+World.sGlobalRandom.nextDoublePositif(lH-100) );	
						
            if( World.Get().detectCollisionActor((byte) 0, false, pActor,  World.Get().getActorLocation(), false ) == false )
                break;
        }
				
        if( i == 5 ){
            return null;
        }

        pActor.setSpeed( sSpeed, 0 );
        World.Get().addActor( pActor);
        return pActor;
    }
    //------------------------------------------------
    public static ActorMobil MakeShip1(	EnumFaction pFaction ) {
        System.out.println( "************** FactoryActor  SHIP 1 *************** ");
				
        ActorMobil lActor  = new SingleShip( 0, 0, pFaction,
                                             ShipType.LaserShip, 5, 5, Ressources.sMyShipImg );
			
        return Relocation( lActor );
    }		
    //------------------------------------------------
    public static ActorMobil MakeBigShip( EnumFaction pFaction, PPgImg pImg ) {
        
        System.out.println( "************** FactoryActor BIG SHIP *************** ");
        
        /*, mettre une map de localisation des depart de tirs  + animation  ) 
          
        // Avoir des couleurs precise 
        - bleue clair -> laser  
        -	orange ->plasma 
        - vert -> missiles
                                                                                      
        Bleue Reacteur
        Plasma
        jaune lumiere 
        ...
						
        la localisation du point de couleur sur la map permet de preciser la localistation de l action 
        sa couleur le type d action 

        on a une phase d annalyse de ces bitmap particuliere (sinon on pourrait mettre dans la meme map ! avec des couleur standards de reference
        ensuite on analyse la map en memoire a la recherche des points particulier qui sont ensuite implemente en focntion de leurs couleurs en objets
        actif !!!!!
        */
								
        //		ActorMobil lActor=  new SingleBigShip( 0, 0, pFaction,
        //																				ShipType.LaserShip, 15, 20, pImg );
        

        ActorMobil lActor  = new SingleShip( 0, 0, pFaction,
                                             ShipType.LaserShip, 5, 5, Ressources.sSingleShipImg );
        lActor.setBoundingSphere( lActor.getBoundingSphere()*0.5 );

        return Relocation( lActor );
    }		
    //------------------------------------------------
    public static ActorMobil MakeMiddleShip(	EnumFaction pFaction, PPgImg pImg ) {
        
        System.out.println( "************** FactoryActor MIDDLE SHIP 1*************** ");

				
        ActorMobil lActor  = new SingleShip( 0, 0, pFaction,
                                             ShipType.LaserShip, 5, 5, Ressources.sSingleShipImg );
        /*
          ActorMobil lActor=  new SingleMiddleShip( 0, 0, pFaction,
          ShipType.LaserShip, 15, 20, pImg );
        */
        lActor.setBoundingSphere( lActor.getBoundingSphere()*0.5 );

        return Relocation( lActor );
    }		
    //------------------------------------------------
    public static ActorMobil MakeContainer(	  ) {
				
        System.out.println( "************** FactoryActor CONTAINER 1*************** ");
        ActorMobil lActor  = new SingleShip( 0,  0,
                                             EnumFaction.Neutral,  ShipType.VoidShip,  1, 0, Ressources.sContainerImg );

        return Relocation( lActor );
				
    }		
    //------------------------------------------------
    public static ActorMobil MakeAsteroid11( EnumFaction pFaction ) {
				
        System.out.println( "************** FactoryActor ASTEROID 11 *************** ");
        ActorMobil lActor  = new SingleShip( 0,  0,
                                             pFaction, 
                                             ShipType.VoidShip,
                                             100, 0, 
                                             Ressources.sAsteroidImg11 );

        // correction de la pixmap
        lActor.setBoundingSphere( lActor.getBoundingSphere()*0.5 );
				
			
        return Relocation( lActor );
    }
    //------------------------------------------------
    public static ActorMobil MakeAsteroid12( EnumFaction pFaction ) {
				
        System.out.println( "************** FactoryActor ASTEROID 12 *************** ");
        ActorMobil lActor  = new SingleShip( 0,  0,
                                             pFaction, 
                                             ShipType.VoidShip,
                                             100, 0, 
                                             Ressources.sAsteroidImg12 );

        // correction de la pixmap
        lActor.setBoundingSphere( lActor.getBoundingSphere()*0.5 );
        return Relocation( lActor );
    }
    //------------------------------------------------
    public static ActorMobil MakeAsteroid2( EnumFaction pFaction ) {
				
        System.out.println( "************** FactoryActor ASTEROID 2 *************** ");
        ActorMobil lActor  = new SingleShip( 0,  0,
                                             pFaction, 
                                             ShipType.VoidShip,
                                             100, 0, 
                                             Ressources.sAsteroidImg2 );

        // correction de la pixmap
        lActor.setBoundingSphere( lActor.getBoundingSphere()*0.8 );
        return Relocation( lActor );
    }
    //------------------------------------------------
    public static ActorMobil MakeEnemyFleet( EnumFaction pFaction, int pNbBoid, int pNbBoidRand  ) {
        
        System.out.println( "**************  FactoryActor ENEMY FLEET *************** ");
				
        Fleet lFleet =	new FleetEnemy( "",   Color.green,
                                        pNbBoid+World.sGlobalRandom.nextIntPositif(pNbBoidRand), 
                                        0,  0, 
                                        50,
                                        0.003  +World.sGlobalRandom.nextDouble(0.001),  // TargetAttrack
                                        0.0003 +World.sGlobalRandom.nextDouble(0.0001),   // CohesionAttrack
                                        0.001  +World.sGlobalRandom.nextDouble(0.0095),   // AlignAttrack
                                        10.3   +World.sGlobalRandom.nextDouble(0.5), // SeparationRepuls
                                        10     +World.sGlobalRandom.nextInt(5), // VitesseMax
                                        1000, // distanceMax) ;
                                        300    +World.sGlobalRandom.nextInt(10), // Evitement
                                        true, 1000, 0.5 );

        lFleet.setBoundingSphere( 90 ) ;
        if( Relocation( lFleet ) != null ){

            lFleet.setFaction( pFaction );
	
            lFleet.setSpeed( -150.0, 0 );
            lFleet.setTarget( lFleet.getLocation().x -200, lFleet.getLocation().y );

            return lFleet;
        }
        return null;
    }
		
    //------------------------------------------------

}
//**************************************************
