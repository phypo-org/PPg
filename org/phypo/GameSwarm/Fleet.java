package org.phypo.GameSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;

import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPgGame.PPgGame.*;
import org.phypo.PPgGame.PPgSFX.Swarm;
import org.phypo.PPgGame.PPgSFX.SwarmBoid;

import org.phypo.PPg.PPgImg.*;
import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgMath.*;

//*************************************************


public class Fleet extends Swarm implements InterfaceUnit {

    public static Color sColorField2 = new Color( 0f, 0.5f, 0.9f, 0.2f );


    double cResistance;
    double cFieldPowerMax=0;
    double cFieldPower=0;
		
    public double getFieldPower()    { return cFieldPower;}
    public double getFieldPowerMax() { return cFieldPowerMax;}

    public double	callApplyDamage( double pDamage){

        double lResul = cFieldPower - pDamage;

        if( lResul < 0 ) {
            cFieldPower = 0;
            return -lResul;
        }
        cFieldPower = lResul;				
        return 0;
    }
    //-----------------------------------------------
    public double getDamage() { return (getBoundingSphere()/10)+1; }



    //-----------------------------------------------
    public	Fleet( String pName, Color pColor, 
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



        setActorType( SpriteType.SpriteFleet.code );				
    }
    //-----------------------------------------------
    public SwarmBoid  createBoid( double pX, double pY, Color pColor, int pUser1, double pUser2  )
    {

        int lAlea = World.sGlobalRandom.nextIntPositif( 3 );

        SwarmBoid lBoid ;

        if( lAlea < 1 )
            lBoid =   new  ShipLaser( Ressources.sShipLaserImg, pX, pY );
        else if( lAlea < 2 )
            lBoid =   new  ShipMissile( Ressources.sShipMissileImg, pX, pY );
        else
            lBoid =   new  ShipField( Ressources.sShipFieldImg, pX, pY );
				
        return lBoid;
    }
		
    //------------------------------------------------
    public void worldCallInit() {
        super.worldCallInit();

        computeShieldPower(30);
    }
		
    //------------------------------------------------
    void computeShieldPower( double pTimeDelta) {
        // Calcul du champ de force
        cFieldPowerMax=0;
        for( SwarmBoid lBoid : cBoids ) {	

            cFieldPowerMax += ((Ship)lBoid).fleetCallGetPowerMax();
            cFieldPower    += ((Ship)lBoid).fleetCallGetPower(pTimeDelta);
        }

        if( cFieldPowerMax > ShipField.sFieldPowerMax ){
            cFieldPowerMax = ShipField.sFieldPowerMax;
        }	
				
        if( cFieldPower > cFieldPowerMax ){
            cFieldPower = cFieldPowerMax;
        }	

    }
		
    //-----------------------------------------------
    public void worldCallAct( double pTimeDelta ) {


        //===============================
        super.worldCallAct(  pTimeDelta );	
        //===============================

				

        // Calcul du champ de force
        computeShieldPower(pTimeDelta );

    }

    //-------------------------- 
    public void worldCallDraw( Graphics2D pG ){
        super.worldCallDraw( pG );

        /*				Point2D.Double lPos = getLocation();
	
					pG.setColor( Color.white );

					int lSize = (int)getBoundingSphere();

					pG.drawOval( (int)(lPos.getX()-lSize), 
                                        (int)(lPos.getY()-lSize),
                                        lSize*2,lSize*2);
        */
    }
    //-------------------------- 
    //  Il y a eu collision de la flotte avec qq chose

    public void  worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {
			 
        double lDamage=0;
        if( pActor.getActorType() == SpriteType.SpriteWeapon.code )
            lDamage =  ((Weapon)pActor).getDamage();
        else if( pActor.getActorType() == SpriteType.SpriteExplosion.code)
            lDamage = 1.0;   // Il faudrait un callDamage a l'explosion
        else 
            lDamage = ((InterfaceUnit)pActor).getDamage();

        if( lDamage >0  && (lDamage = callApplyDamage( lDamage )) > 0 ) {

            // il faut renvoyer le damage resultant dans l'arme/explosion pour la suite
            if( pActor.getActorType() == SpriteType.SpriteWeapon.code )
                ((Weapon)pActor).setDamage(lDamage );

            // Il faut renvoyer les degats aux vaisseaux 
            World.Get().detectCollisionActor( (byte)(getFaction().getCode()+ pActor.getFaction().getCode()), false, 
                                              pActor, (ArrayList<ActorLocation>)(ArrayList)cBoids, true );
        }
        else {
            // le champs a tout absorbe !
        }
				
        /*
          if( pActor.getActorType() == SpriteType.SpriteWeapon.code ){
          // Il faut renvoyer les degats aux vaisseaux 
          World.Get().detectCollisionActor( (byte)(EnumFaction.Blue.getCode() + EnumFaction.Green.getCode()), false, 
          pActor, (ArrayList<ActorLocation>)(ArrayList)cBoids, true );
          }
        */
        // Tester le cas d'une autre flotte
        // Tester le cas d'un vaisseau ?				

    }		
}
