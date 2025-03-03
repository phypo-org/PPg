package org.phypo.PPg.PPgSwarm;



import java.awt.Color;
import java.awt.Graphics;


import java.awt.geom.Point2D;
import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;

import java.awt.*;


import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgMath.*;
import org.phypo.PPg.PPgSFX.*;



//*************************************************

public class Weapon extends ActorMobil {


		

		WeaponType cWeaponType;
		Color      cColor;


		//------------------------------------------------
		public Weapon( WeaponType pWtype,  EnumFaction pFaction, double pDamage,
									 double pX, double pY, Color pColor ) {
				super(  pX,  pY, pFaction );

				cWeaponType = pWtype;
				cDamage     = pDamage;
				cColor      = pColor;

				setActorType( SpriteType.SpriteWeapon.code );
		}
		//------------------------------------------------
		double     cDamage=1;
		public double getDamage()                { return cDamage; }
		public void   setDamage( double pDamage) { cDamage = pDamage; }

		//------------------------------------------------*
    public void worldCallAct(double pTimeDelta) 
    {  
				//			pTimeDelta  *= 30;
				
				// cSpeed.add(cAcceleration, pTimeDelta);

				//   limitSpeed();

				////        cLocation.add( cSpeed, pTimeDelta );
        
				cLocation.x += cSpeed.x*pTimeDelta;
				cLocation.y += cSpeed.y*pTimeDelta;

						
				// Respect des limites de l'ecran 
				if( World.Get().outOfWorld( cLocation ) )
						setDeleted();

				//		setRotation( cSpeed.getDirection() );
				//		setLocation(cLocation);   
    }
		//------------------------------------------------
		public void worldCallDraw( Graphics2D pG ){

				Point2D.Double lPos = getLocation();
				pG.setColor( cColor );

				int lSize = (int)getBoundingSphere();

				if( cWeaponType == WeaponType.WeaponMissile ){
							
						pG.fillRect( (int)(lPos.getX()-lSize*0.5), 
												 (int)(lPos.getY()-lSize*0.5),
												 lSize,lSize);					 
				}
				else {
						pG.fillRect( (int)(lPos.getX()-lSize), 
												 (int)(lPos.getY()),
												 lSize*4, 1);
				}

		}
		//------------------------------------------------
		public boolean worldCallAcceptCollision( ActorLocation pActor ) { 
				
				return true;
		}

		//------------------------------------------------
		// Il y a eu collision de l'arme avec qq chose 

		public void worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {

				//				if( pActor.getActorType() ==SpriteType.SpriteWeapon.code
				//						&& getActorType() == SpriteType.SpriteWeapon.code ) {

						//						PPgExplosion lExplo = new PPgExplosion( getFaction(), cLocation, 32, 1.0f, 0.7f, 0.1f, 0.3 );
						//						PPgExplosion lExplo = new PPgExplosion( EnumFaction.Neutral, cLocation, 20, 1.0f, 1.0f, 0.5f, 0.3 );



				// Il faudrait une fontion virtuelle pour creer l'explosion
				if( pActor.getActorType() == SpriteType.SpriteFleet.code && ((Fleet)pActor).getFieldPower()<=0){
						// Si c'est une flotte et que le champ de force est a 0, on n'est pas detruit
						return ;
				}
						

				ActorLocation lExplo ;
				if( cWeaponType == WeaponType.WeaponMissile ){						
						// L'explotion est dangereuse (effet de zone ) 
						// ajoute un damage a l'explosion ? 

						lExplo = new PPgExplosion( getFaction(), cLocation, (int)(pActor.getBoundingSphere()*2.5),  1.0f, 0.2f, 0.1f, 0.5f, 0.3, true );
						lExplo.setActorType( SpriteType.SpriteExplosion.code ); 
						World.Get().addActor(lExplo );
 
						lExplo = new PPgExplosion( getFaction(), cLocation, (int)(pActor.getBoundingSphere()*1.8),  1.0f, 0.6f, 0.3f, 0.6f, 0.3, true );
						lExplo.setActorType( SpriteType.SpriteExplosion.code ); 
						World.Get().addActor(lExplo );
 
						lExplo = new PPgExplosion( getFaction(), cLocation, (int)pActor.getBoundingSphere(),  1.0f, 0.8f, 0.5f, 0.8f, 0.3, true );
						lExplo.setActorType( SpriteType.SpriteExplosion.code ); 
						World.Get().addActor(lExplo );
 				}
				else {
						// C'est un laser !
						
						if( pActor.getActorType() == SpriteType.SpriteWeapon.code && ((Weapon)pActor).cWeaponType ==  WeaponType.WeaponLaser ){
								// Les laser ne se neutralise pas entre eux !
								return;
						}

						lExplo = new PPgExplosion( EnumFaction.Neutral, cLocation, (int)pActor.getBoundingSphere(), .7f, .8f, 1.0f, 1.0f, .2, true );
						lExplo.setActorType( SpriteType.SpriteExplosion.code ); 
						World.Get().addActor(lExplo );
				}				

				setDeleted();
		}						
};
