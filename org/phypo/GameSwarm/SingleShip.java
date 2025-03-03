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


//*************************************************
public class SingleShip extends Sprite  implements ShipBase{

    public ShipType cShipType = ShipType.VoidShip;

    //======= implementation interface ShipBase
    double  cResistance =1;
    public double getShipResistance() { return cResistance; }

		
    double cFieldPowerMax=5;
    double cFieldPower=5;
    public double getFieldPower() { return cFieldPower; }
    public double getDamage()         { return (getBoundingSphere()/10)+1; }

    //========================================



    double  cTimeAutoFire1 = 0;
    double  cTimeAutoFire2 = 0;

	
    double cLastTimeAnim = 0;
    double cTimeAnimSeq =  0;
    double cAleaTimeAnim = 0;


    //------------------------------------------------
    // applique les degat au vaisseau, d'abord au champ de force
    // et ensuite s'il en reste au vaisseau lui meme
    // on renvoit les degats non absorbe (destruction) ou 0 (en vie)
		
    public double	callApplyDamage( double pDamage){

        double lResul = cFieldPower - pDamage;

        if( lResul < 0 ) {

            cFieldPower = 0;
            lResul = cResistance + lResul;

            if( lResul <  0 ) {								
                cResistance = 0;
                return -lResul;
            }
            // On pourrait faire varier l'aspect du vaisseau pour
            // refleter les degats subits
            cResistance = lResul;
            return 0;
        }
        cFieldPower = lResul;				
        return 0;
    }


    //------------------------------------------------
    public SingleShip( double pX, double pY, EnumFaction pFaction, ShipType pShipType, double pResistance, double pShieldPower, PPgImgBase pImg) {
        super( pX, pY, pFaction, pImg  );

        PPgRandom cRand = new PPgRandom();

        cShipType = pShipType;

        setActorType( SpriteType.SpriteShip.code );
				
        cFieldPower = cFieldPowerMax = pShieldPower;
        cResistance = pResistance;

        cLastTimeAnim =  cRand.nextDoublePositif( 100) ;
        cAleaTimeAnim = 1+cRand.nextDoublePositif( 1 ) ;
    }
    //-----------------------------------------------
    public void worldCallAct( double pTimeDelta ) {

				
        //		System.out.println( "Ship worldCallAct" );

        //===============================
        super.worldCallAct( 	pTimeDelta );
        //===============================
				
        // destruction si on passe le bord de l'ecran gauche
        if( getLocation().x < -100 )
            setDeleted();

				
        // Tirs automatiques
        if( dectectEnemy() ) {
            double lTime = World.Get().getGameTime();

            if( lTime - cTimeAutoFire1 > 0.400 ){
                cTimeAutoFire1 = lTime;

                callFireLaser( ShipType.LaserShip );						
            }
						
            if( lTime - cTimeAutoFire2 > 0.700 ){
                cTimeAutoFire2 = lTime;

                callFireMissile( ShipType.MissileShip );						
            }
        }
        //============== ANIM ==============
        cTimeAnimSeq = (cLastTimeAnim/ getAnimDuration())%1.0;
        setTimeAnim( cTimeAnimSeq );

        cLastTimeAnim += pTimeDelta*cAleaTimeAnim;

        //==================================

        computeBoundingBox();
    }
    //------------------------------------------------
    public void callFireLaser( ShipType pTypeFire ){
				
        if( pTypeFire != cShipType )
            return;
				
        Weapon lMissile =  new Weapon( WeaponType.WeaponLaser, getFaction(), 1.0,
                                       cLocation.x, cLocation.y, 
                                       ShipLaser.sColorLaser );
		

        if( getFaction() == EnumFaction.Blue )
            lMissile.setSpeed( 500, 0 );
        else
            lMissile.setSpeed( -500, 0 );

        lMissile.setTimeOfLife( 2.5 );
        lMissile.setBoundingSphere( 1	);
							
        World.Get().addActor(lMissile);						
    }
    //------------------------------------------------
    public void callFireMissile( ShipType pTypeFire ){
				
        if( pTypeFire != cShipType )
            return;
				
        Weapon lMissile =  new Weapon( WeaponType.WeaponMissile, getFaction(), 5.0,
                                       cLocation.x, cLocation.y,
                                       ShipMissile.sColorMissile );
		
        if( getFaction() == EnumFaction.Blue )
            lMissile.setSpeed( 300, 0 );
        else
            lMissile.setSpeed( -300, 0 );
	
        lMissile.setTimeOfLife( 3 );
        lMissile.setBoundingSphere( 3	);
										
				
        World.Get().addActor(lMissile);						
    }
    //-------------------------- 
    // Verifie s'il faut tirer
    boolean dectectEnemy() {
				
        Fleet lTarget =((MyGamer)World.Get().getGamerHuman()).getMyFleet();
        Point2D.Double lTargetLocation = lTarget.getLocation();
        double lY = lTargetLocation.y - cLocation.y ;
        if( lY < 100 && lY > -100 ) {
            if( cLocation.x - lTargetLocation.x < 500 )
                return true;
        }
        return false;
    }
    //------------------------------------------------
    //  Il y a eu collision du vaisseau avec qq chose
    public void worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {

        double lDamage=0;
        if( pActor.getActorType() == SpriteType.SpriteWeapon.code )
            lDamage =  ((Weapon)pActor).getDamage();
        else if( pActor.getActorType() == SpriteType.SpriteExplosion.code)
            lDamage = 1.0;   // Il faudrait un callDamage a l'explosion
				
				
        if( lDamage >0  && ( lDamage =	callApplyDamage( lDamage )) > 0 ) {
            callDestruction();
        }							 																			 		 				
    }
    //------------------------------------------------
    public void callDestruction(){	
        PPgExplosion 
            lExplo = new PPgExplosion( EnumFaction.Neutral, cLocation, (int)getBoundingSphere()*5,   1f, .4f, .2f, 0.6f,  0.5, true );
        World.Get().addActor(lExplo);
        lExplo = new PPgExplosion( EnumFaction.Neutral, cLocation, (int)(getBoundingSphere()*3.5),     1f, .7f, .4f, 0.8f,  0.4, true );
        World.Get().addActor(lExplo);
        lExplo = new PPgExplosion( EnumFaction.Neutral, cLocation, (int)(getBoundingSphere()*1.5),       1f,  1.0f, 1.0f, 1.0f,  0.3, true );
        World.Get().addActor(lExplo);
								
        setDeleted();
    } 
    //------------------------------------------------

    //FAIRE UN TRACE DE SHIELD COMMUN !!!
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

    public void worldCallDraw( Graphics2D pG) {

        super.worldCallDraw( pG );



        if( getFieldPower() > 0) {
            Point2D.Double lPos      = getLocation();

            int lSize = (int)getBoundingSphere();
            lSize = (int)(lSize*(1.5f+ World.sGlobalRandom.nextFloatPositif(0.3f)));
						
            //			double lSz =(sFieldStroke.length-1)*(((Fleet)cMySwarm).getFieldPower())/((Fleet)cMySwarm).getFieldPowerMax();
						
            double lSz =(sFieldStroke.length-1)*((getFieldPower())/ShipField.sFieldPowerMax);
						
            pG.setColor( sFieldColor[ World.sGlobalRandom.nextIntPositif(sFieldColor.length-1) ] );
            pG.setStroke( sFieldStroke[(int)lSz] );
						
            pG.drawOval( (int)(lPos.getX()-lSize/2), 
                         (int)(lPos.getY()-lSize/2),
                         lSize,lSize);
						
            pG.setStroke( sFieldStroke[0] );																								 
        }
    }

    //------------------------------------------------

};

//*************************************************
