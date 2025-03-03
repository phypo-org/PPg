package org.phypo.PPgTestGame3d;

import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.util .*;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;

import com.jogamp.nativewindow.util.Point;

import org.phypo.PPgGame3d.*;
//*************************************************

public class Weapon extends Sprite {



		static public Color4 sColorWeaponPlasma  = new Color4(  0.2f, 0.4f, 0.8f, 0.3f );
		static public Color4 sColorPlasmaGreen   = new Color4(  0.0f, 0.6f, 0.3f, 0.2f );
		static public Color4 sColorPlasmaRed     = new Color4(  0.1f, 0.3f, 0f, 0.2f );
		static public Color4 sColorPlasmaBlue    = new Color4(  0.0f, 0.3f, 1f, 0.2f );

		static public Color4 sColorWeaponRocket  = new Color4(  0.4f, 0.4f, 0f, 0.9f );
		static public Color4 sColorWeaponMissile = new Color4(  0.4f, 0.6f, 1f, 0.9f );

		//=====================================
		public enum WeaponGeneralType {
				NoWeaponType,
						Plasma,						
						Ion,
						Rocket,
						Missile
						};
		//=====================================
		
		public enum WeaponType{ 
				NoWeapon( WeaponGeneralType.NoWeaponType, 0f, 0f, 2.5f, false, 0f, 0f, null, 0),
				WeaponSmallPlasma   ( WeaponGeneralType.Plasma, 1f, 150f,  2.5f,   false, 0.25f,  0.5f, sColorWeaponPlasma, 0 ),
				WeaponPlasma        ( WeaponGeneralType.Plasma, 2f, 150f,  2.5f,   false, 0.35f,  1f,   sColorWeaponPlasma, 0 ),
				WeaponPlasma2       ( WeaponGeneralType.Plasma, 3f, 150f,  2.5f,   false, 0.40f,  1f,   sColorWeaponPlasma, 0 ),
				WeaponPlasmaRed     ( WeaponGeneralType.Plasma, 5f, 150f,  2.5f,   false, 0.45f,  2f,   sColorPlasmaRed, 1 ),
				WeaponPlasmaGreen   ( WeaponGeneralType.Plasma, 8f, 150f,  2.5f,   false, 0.8f,  2f,   sColorPlasmaGreen, 1 ),
				WeaponPlasmaBlue    ( WeaponGeneralType.Plasma, 12, 150f,  2.5f,   false, 0.1f,  2f,   sColorPlasmaBlue, 1),
						
				WeaponIon           ( WeaponGeneralType.Ion, 1f, 150f,  2.5f,   false, 0.25f,  0.5f, sColorPlasmaRed, 0 ),
						
				WeaponSmallRocket   ( WeaponGeneralType.Rocket, 5f, 100f,  2.5f, false, 0.4f, 0.5f,  sColorWeaponRocket, 0 ),
				WeaponRocket        ( WeaponGeneralType.Rocket, 10f, 100f,  2.5f, false, 0.5f, 1f,   sColorWeaponRocket, 0 ),
				WeaponBigRocket     ( WeaponGeneralType.Rocket, 15f, 100f,  2.5f, false, 0.6f, 2f,   sColorWeaponRocket, 0),
						
				WeaponSmallMissile  ( WeaponGeneralType.Missile, 3f,  90f,  2.5f,  true, 0.4f, 0.5f, sColorWeaponMissile, 0 ),
				WeaponMissile       ( WeaponGeneralType.Missile, 6f,  90f,  3f,    true, 0.5f, 1f, sColorWeaponMissile, 0 ),
				WeaponBigMissile    ( WeaponGeneralType.Missile, 9f,  90f,  4f,    true, 0.6f, 2f, sColorWeaponMissile, 1 )
				;




				WeaponType( WeaponGeneralType pGenType,  float pDamage, float pSpeed, float pLife, boolean pAutoDirect, 
										float pSize, float pResistance, Color4 pColor, int pComplexity){
						cGenType = pGenType;
						cDamage     = pDamage;
						cSpeed      = pSpeed;
						cLife       = pLife;
						cAutoDirect = pAutoDirect;
						cSize       = pSize;
						cResistance = pResistance;
						cColor      = pColor; 
						cComplexity = pComplexity;
								
				}
				
				public WeaponGeneralType cGenType;
				public float   cDamage;
				public float   cSpeed;
				public boolean cAutoDirect;
				public float   cSize;
				public float   cResistance;
				public float   cLife;
				public Color4  cColor;
				public int     cComplexity;
		};
		//=====================================

		public enum FireMode{
				FireStopped,
						FireFront,
						FireDirect 
						}
		//=====================================

		public WeaponType cWeaponType;

		//------------------------------------------------

		static Primitiv3d cPrim = 	new Primitiv3d();


		public int getActorType() { return FactoryActor.ActorType.ActorWeaponType.cCode;}

		//------------------------------------------------
		protected Weapon( Float3 pLocation, WeaponType pWType,  EnumFaction pFaction, NodeBase pNode3d ) {


				super( pLocation, pFaction, pNode3d, pWType.cResistance, pWType.cDamage, 0f );
				cWeaponType = pWType;
		}
		//------------------------------------------------
		static public Weapon FireWeapon( WeaponType pWtype,
																			 EnumFaction pFaction, 
																			 Float3 pLocation ) {

				Weapon lWeapon;
				World3d.Get().addActor( lWeapon=(CreateWeapon( pWtype, pFaction, pLocation )));

				return lWeapon;
		}
		//------------------------------------------------
		static public Weapon CreateWeapon( WeaponType pWtype,
																			 EnumFaction pFaction, 
																			 Float3 pLocation ) {


				// UTILSER DES NODE SHARED POUR CREER LES ARMES

				Weapon lWeapon = null;
				
				Primitiv3d.SubParamObject3d lParam01 = cPrim.GetSubParamObject3d ( 0, pWtype.cSize,  true, Primitiv3d.SubNormalizeType.NORMALIZE);
						
				Aspect3d lAspect1 = new Aspect3d( pWtype.cColor,  pWtype.cColor,	Aspect3d.DrawStyle.FILL );
				ModelBase  lO1  = new CompilObj( Primitiv3d.Odron( lParam01).getObject3d());			
				
				Transform3d lTransf = new Transform3d( null, pLocation, null );
				
				Leaf3d lLeaf = new Leaf3d( lO1, lTransf, lAspect1);								
				
				lWeapon = new Weapon( pLocation, pWtype, pFaction, lLeaf  );
				lWeapon.setRotation( Float3.GetRandom( World3d.sGlobalRandom, 360f) );					


				switch( pWtype.cGenType ){
						
						//=====================================
				case  Plasma : {
						
						lWeapon.setSpin( new Float3( 0, 0, World3d.sGlobalRandom.nextFloatPositif( 360f) ));					
				} break;
						
						//=====================================						
				case  Rocket : {
						
						lWeapon.setRotation( Float3.GetRandom( World3d.sGlobalRandom, 360f) );					
						lWeapon.setSpin(  new Float3(  World3d.sGlobalRandom.nextFloatPositif(90f), 0, 0 ));					
				} break;
						
						//=====================================
				case Missile: {
				}
				case Ion: {
				}
						
				case NoWeaponType:
				}
				//=====================================


				lWeapon.setTimeOfLife( pWtype.cLife );
				lWeapon.setBoundingSphere( pWtype.cSize	);
				lWeapon.setRotation( Float3.GetRandom( World3d.sGlobalRandom, 300f) );					

				if( pFaction == EnumFaction.Blue )
						lWeapon.cSpeed.set( pWtype.cSpeed, 0, 0 );
				else
						lWeapon.cSpeed.set( -pWtype.cSpeed, 0, 0 );



				lWeapon.setFaction( pFaction );		


				lWeapon.cWeaponType = pWtype;
				lWeapon.setActorType( SpriteType.SpriteWeapon.code );
	
	
				lWeapon.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.DeleteOutOfWorld ));


				return lWeapon;
		}
		//------------------------------------------------
		//------------------------------------------------*
    public void worldCallAct(double pTimeDelta) 
    {  
						
				// Respect des limites de l'ecran 
				if( World3d.Get().outOfWorld( cLocation ) )
						setDeleted();

				//		setRotation( cSpeed.getDirection() );
				//		setLocation(cLocation);   
    }
		
		//------------------------------------------------
		public boolean worldCallAcceptCollision( ActorLocation pActor ) { 
				
				return true;
		}

		//------------------------------------------------
		// Il y a eu collision de l'arme avec qq chose 

		public void worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {
				Explosion lExplo;


				System.out.println( "Weapon ********* worldCallDetectCollision ");

	

				switch( cWeaponType.cGenType ){

						//=====================================
				case Plasma: {
						if( pActor.getActorType() == SpriteType.SpriteWeapon.code ){
								switch(  ((Weapon)pActor).cWeaponType.cGenType ) {
								case Plasma : return;
								default:
								}
						}
								
						float lSz = cDamage;

						if( lSz > 30 )								lSz *= 0.3f;
						if( lSz > 20 )								lSz *= 0.4f;
						else if( lSz > 10 )						lSz *= 0.46f;
						else if( lSz > 8 )						lSz *= 0.5f;
						else if( lSz > 6 )           	lSz *= 0.6f;
						else if( lSz > 4 )           	lSz *= 0.8f;
					
										
						lExplo = Explosion.CreateExplosion( getFaction(), cLocation, lSz, 0f,  cWeaponType.cColor,  null, Explosion.ExploMode.Implosion, 0.1f );
						lExplo.setActorType( SpriteType.SpriteExplosion.code ); 
						
						lExplo.cUniformPlane = true;
						
						World3d.Get().addActor(lExplo );								
						} break;
						
						
						//=====================================
				case Missile:
				case Rocket: {
						
						lExplo = Explosion.CreateExplosion( getFaction(), cLocation, cDamage,   0f, new Color4(1.0f, 0.2f, 0.1f, 0.5f),  null,  Explosion.ExploMode.Explosion,  0.2f  );
						World3d.Get().addActor(lExplo );
						
						lExplo = Explosion.CreateExplosion( getFaction(), cLocation, cDamage*0.75f,  0f,  new Color4(1.0f, 0.6f, 0.3f, 0.6f),  null,  Explosion.ExploMode.Explosion, 0.21f );
						World3d.Get().addActor(lExplo );
						
						lExplo = Explosion.CreateExplosion( getFaction(), cLocation, cDamage*0.5f,  0f,  new Color4(1.0f, 0.8f, 0.5f, 0.8f),  null,  Explosion.ExploMode.Explosion, 0.22f );
						World3d.Get().addActor(lExplo );
				} break;
						//=====================================						
				}
				
				setDeleted();
		}						
};
