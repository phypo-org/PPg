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
import org.phypo.PPg.PPgMath.*;

//*************************************************

class ParticleFactoryReactor extends ParticleEngine {



		public ParticleFactoryReactor( float pUnit,  int pMaxNbParticle, int pNbCreateAtOnce ){
				//				super(ParticleInterface.EnumParticleForm.POINT, pMaxNbParticle, pNbCreateAtOnce, false );
				super( pMaxNbParticle, pNbCreateAtOnce, false );

				cUnit = pUnit;

				ModelQuadric lQuad = new ModelQuadric();
				lQuad.setSphere( cUnit*1.5f, 5, 5);

				cSharedModel =  new CompilObj( lQuad  );
		}						
		//------------------------------------------------
		public ParticleInterface createParticle( PPgRandom pRand, int lNum){
			 
				
				PPgRandom lRand =	World3d.sGlobalRandom;
				
				ParticleBase lPart = new ParticleBase( Float3.GetRandom(  lRand, cUnit*2f ));


				lPart.cSpeed =  new Float3( -500f*cUnit+lRand.nextFloat(cUnit*10f),
																						-8f*cUnit+lRand.nextFloat(cUnit*2f),
																						-8f*cUnit+lRand.nextFloat(cUnit*2f )); // -4f*cUnit+lRand.nextFloat(cUnit*1f));
				
				lPart.cInflation = new Float3( -5f*cUnit);
				
				lPart.cColor = new Color4( 0.15f+lRand.nextFloatPositif(0.15f),
																	 0.10f+lRand.nextFloatPositif(0.1f),
																	 0.45f+lRand.nextFloatPositif(0.25f), 
																	 0.2f);
				
				lPart.cColorFade =  new Color4( -0.25f, -0.15f, -0.65f, 0.4f);
				lPart.setTimeOfLife( 0.2 + lRand.nextDouble(0.1) );

				
		/*
		//			lPart.setAcceleration( new Float3( lRand.nextDouble(0.3f*cUnit),
		//																				 0.6f*cUnit+lRand.nextFloat(0.1f*cUnit),
				//																				 lRand.nextDouble(0.3f*cUnit)));
		
				//		lPart.setSize( 7f*cUnit+lRand.nextFloat(3*cUnit), -2f*cUnit, -0.5f*cUnit );
				*/

				return lPart;
		}

};
//*************************************************





public class SpritePilot  extends  Sprite{

		public Point cLastMousePos = new Point();
		public Point cOldMousePos = new Point();
		
		public float cSensibily=15f;
		HumanControl cControl;

		public String getName()   { return "SpritePilot";}

		public int getActorType() { return FactoryActor.ActorType.ActorGamerType.cCode;}

		static String sTrace;



		public float cEnergy=0;
		public float cMaxEnergy=0;

		public int   cRocket=0;
		public int   cMaxRocket=0;

		public int   cLife = 0;
		public int   cMoney = 0;
		public int   cUpgrade=0;
		

		//------------------------------------------------
		public SpritePilot( HumanControl pControl, Float3 pLoc, 
											 EnumFaction pFaction, 
											 NodeBase pNode3d ) {

				super( pLoc, pFaction, pNode3d );
				cControl = pControl;

				setFieldPowerMax( 50f );
				setFieldPowerFill( 5f );
				cFieldPower = 30f;

				cEnergy= cMaxEnergy=100;

				cRocket = cMaxRocket = 30;			 				
		}
		//------------------------------------------------
		@Override public void worldCallAct( float pTimeDelta ) {

				if( cResistance <= 0 ) {
					 super.worldCallAct( pTimeDelta );
					 return;
				}


						
				Kamera3d lKam = World3d.sTheWorld.getCurrentKamera();
				lKam.renderGL( Engine.sTheGl );				

				Double3 lPosSprite = lKam.project( Engine.sTheGl, cLocation );	
				// On projette la souris dans l'espace objets
				Float3 lMouse = new Float3( cLastMousePos.getX(), cLastMousePos.getY(), lPosSprite.z());
				Double3 lMouseObj = lKam.unproject(	Engine.sTheGl,  lMouse );	
				 
				 float lDx = (float)(lMouseObj.x() - cLocation.x()) ;
				 float lDy = (float)(-lMouseObj.y() - cLocation.y()) ;
				 cSpeed.set( lDx*cSensibily, lDy*cSensibily, 0f );

				 double lTime = World3d.Get().getGameTime();


				 if( cControl.cButtonPressed[1] ){
						 //	 System.out.println( "************** cButtonPressed 1" ); 
						 if( lTime - cControl.cTimeAutoFire[1] > 0.05 ){
								cControl.cTimeAutoFire[1] = lTime;
								fire1();
						}
				 }
				 if( cControl.cButtonPressed[3] ){
						 //			 System.out.println( "************** cButtonPressed 3" ); 
						 if( lTime - cControl.cTimeAutoFire[3] > 0.10 ){
								cControl.cTimeAutoFire[3] = lTime;
								fire3();
						}
				 }

				 float lD = 10; //getBoundingSphere()/2f;

				 super.worldCallAct( pTimeDelta );
				 sTrace = "Field:"+((int)getFieldPower() + " Erg:"+((int)cEnergy)+" Rocket:" + cRocket );

				 float h = World3d.sTheWorld.getHeight();
				 float w = World3d.sTheWorld.getWidth();

				 //		 float h = World3d.sTheWorld.getHeight()*0.8f;
				 //		 float w = World3d.sTheWorld.getWidth()*0.8f;
				 float d = h;
				 float lDecX = 0f;
				 float lDecY = 0f;
				 /*
				 float lLimitXmin = -w/2f+lD*0.5f;
				 float lLimitXmax =  w/2f-lD*0.5f;

				 float lLimitYmin = -h/2f+lD*2.5f;
				 float lLimitYmax =  h/2f-lD*2.5f;
				 


				 if( cLocation.x() < lLimitXmin ) {
						 lDecX = cLocation.x()-lLimitXmin;
				 } else if( cLocation.x() > lLimitXmax ) {
						 lDecX = cLocation.x()-lLimitXmax;
				 }

				 if( cLocation.y() < lLimitYmin ) {
						 lDecY = cLocation.y()-lLimitYmin;
				 } else if( cLocation.y() > lLimitYmax ) {
						 lDecY = cLocation.y()-lLimitYmax;
				 }
				 */

				 World3d.Get().getCurrentKamera().setOrtho( null,
																										-w+lDecX, w+lDecX, 
																										-h+lDecY, h+lDecY, 
																										-d, d );
		 

		}
		//------------------------------------------------
		void fire1( ) {
				//		System.out.println( "************** fire1 ");
				if( cEnergy > Weapon.WeaponType.WeaponSmallPlasma.cDamage ){
						cEnergy -= Weapon.WeaponType.WeaponSmallPlasma.cDamage;

						Weapon.FireWeapon( Weapon.WeaponType.WeaponSmallPlasma, getFaction(),  cLocation );
				}
							
		}
	//------------------------------------------------
		void fire3( ) {
				//	System.out.println( "************** fire2 ");
				if( cRocket > 0 ){
						cRocket--;
						Weapon.FireWeapon( Weapon.WeaponType.WeaponSmallRocket, getFaction(),  cLocation );				
				}
			
		}
	//------------------------------------------------
		public void keepBonus( Bonus pBonus ){

				switch( pBonus.cBonusType ){
				case  SuperBonusErg:
				case BonusErg: cEnergy += pBonus.cBonusType.cVal ;
					if( cEnergy > cMaxEnergy )
								 cEnergy= cMaxEnergy;
						break;
						
				case BonusRocket:
				case SuperBonusRocket:
						cRocket += pBonus.cBonusType.cVal ;
						if( cRocket > cMaxRocket )
								cRocket = cMaxRocket;
						break;

				case BonusField:
				case SuperBonusField:
						cFieldPower += pBonus.cBonusType.cVal ;
						if( cFieldPower > cFieldPowerMax )
								cFieldPower= cFieldPowerMax;
						break;

				case BonusLife:
				case SuperBonusLife:
						cLife += pBonus.cBonusType.cVal ;
						break;

				case BonusGold:
				case SuperBonusGold:
						cMoney += pBonus.cBonusType.cVal ;
						break;

				case BonusUpgrad:
				case SuperBonusUpgrad:
						cUpgrade += pBonus.cBonusType.cVal ;
						break;		
				}
		}
	//------------------------------------------------
	//------------------------------------------------
	//------------------------------------------------
		static SpritePilot Make( HumanControl pControl ){
				SpritePilot lPilot =  Make( pControl,  true, -LevelBase.sUnit*30, 3,  Primitiv3d.SubNormalizeType.NORMALIZE_ONLY_INIT);
				pControl.setSpritePilot( lPilot );
				return lPilot;
		}

	//------------------------------------------------
		static SpritePilot Make( HumanControl pControl, boolean lCentralPoint, float lSize, int lDepth, Primitiv3d.SubNormalizeType  pNormalize ){

				SpritePilot lSprite;
				
				Primitiv3d lPrim = 	new Primitiv3d();

				//				Primitiv3d.SubParamObject3d lParam01 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize,       lCentralPoint, pNormalize );
				//				Primitiv3d.SubParamObject3d lParam02 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize*1.01f, lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam01 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize,       lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam02 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize*1.01f, lCentralPoint, pNormalize );

				//			lParam01.cHoleFacet = 4;
				//			lParam01.cHoleDepth = 2;

				//			lParam01.cDepthGrowFactor = 0.5f;
				//			lParam02.cDepthGrowFactor = 0.5f;

				//			Object3d  lPrimOctahedron01 =  ((Primitiv3d.SubParamObject3d)GlutPrimitiv.Octahedron( lParam01)).getObject3d();
				//				Object3d  lPrimOctahedron02 =  ((Primitiv3d.SubParamObject3d)GlutPrimitiv.Octahedron( lParam02)).getObject3d();


				Object3d  lO1  = Primitiv3d.Pyramid4( 0,0,0, lSize,        lSize,       lParam01).getObject3d();
				Object3d  lO2 =  Primitiv3d.Pyramid4( 0,0,0, lSize*1.01f,  lSize*1.01f, lParam01).getObject3d();

				ModelQuadric lOField = new ModelQuadric();
				lOField.setSphere( lSize*2f, 10, 10 );


				Aspect3d lAspect1 = new Aspect3d( Color4.Yellow,
																 Aspect3d.DrawStyle.FILL );//Aspect3d.DrawStyle.FILL);
				Aspect3d lAspect2 = new Aspect3d( new Color4( 0.3f, 0.3f, 0.1f, 0.1f ), Aspect3d.DrawStyle.LINE );

				Aspect3d lAspectField = new Aspect3d( new Color4( 0.1, 0.1, 0.8, 0.3f ),
																 Aspect3d.DrawStyle.FILL );//Aspect3d.DrawStyle.FILL);
			
				//			ModelBase pO1 = new CompilObj( lO1,  );
				//		ModelBase pO2 = new CompilObj( lO2 );
				//		ModelBase pOF = new CompilObj( lOField );

				Transform3d lTransf = new Transform3d( null, new Float3( 0f, 0f, -90f),  new Float3( 0.5f, 1f, 0.5f) );

				Node3d lNode  = new Node3d( );
				Leaf3d lLeaf1 = new Leaf3d( lO1, lTransf, lAspect1, true );
				lNode.addChild( lLeaf1 );
				Leaf3d lLeaf2 = new Leaf3d( lO2, lTransf, lAspect2, true );								
				lNode.addChild( lLeaf2 );
				Leaf3d lFieldLeaf3 = new Leaf3d( lOField, lTransf, lAspectField );	// pas de compil pour le champ !
				lNode.addChild( lFieldLeaf3 );


				//			lSprite.cScale =  new Float3( 0.5f, 1f, 0.5f);

				//***********************
				ParticleEngine lEngine = new ParticleFactoryReactor( LevelBase.sUnit*2f, 150,  50 );
				lNode.addChild( lEngine );
				//***********************

				lSprite  = new SpritePilot( pControl, new Float3(),  EnumFaction.Blue,	lNode);
				lSprite.setBoundingSphere(  lSize*1.2f);


				//		lSprite.setMaxSpeed( 10 );
				lSprite.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.LimitToWorld ));
				//			lSprite.cRotation =  new Float3( 0f, 0f, -90f);
				lSprite.setSpin( new Float3( 600f, 0f, 0f) );

				World3d.sTheWorld.addActor( lSprite  );

				lSprite.cResistance = 10.0f;
				lSprite.cDamage =5f;


				return lSprite;	
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		@Override
		public void worldCallDetectCollision( ActorLocation pActor, boolean pFirst ) {

				if( pActor.getActorType() == FactoryActor.ActorType.ActorBonusType.cCode ){
						keepBonus( ((Bonus)pActor) );
				}
				else {
						super.worldCallDetectCollision( pActor, pFirst );
				}
		}
		//------------------------------------------------
		@Override
		public void callDestruction(){	
				
				
				setTimeOfLife( 0.2 );
				setFaction( EnumFaction.Neutral );

				for( int i=0; i< 7; i++ ){
						Explosion lExplo = Explosion.CreateExplosion( EnumFaction.Neutral, cLocation, 
																								getBoundingSphere()/2+World3d.sGlobalRandom.nextFloatPositif(getBoundingSphere()/2),
																								0.0f, new Color4(1.0f, 0.2f, 0.1f, 0.3f),  null,  
																								Explosion.ExploMode.Explosion,
																								0.3f+World3d.sGlobalRandom.nextFloatPositif( 0.5f  ));
						lExplo.setSpeed( cSpeed );
						lExplo.setSpeed( cSpeed );
						World3d.Get().addActor(lExplo );

						
						Sprite lDebris = Make( cControl, true, getBoundingSphere()/2f, -1,  Primitiv3d.SubNormalizeType.NORMALIZE_ONLY_INIT);

						Float3 lSpeed = new Float3( World3d.sGlobalRandom.nextFloatPositif( LevelBase.cSpeedDefilX ), 
																				World3d.sGlobalRandom.nextFloat( LevelBase.cSpeedDefilX ),
																				0f);
						lDebris.setSpeed( lSpeed );

						Float3 lLocation = new Float3( getLocation() );
						lDebris.setLocation( lLocation );
						lDebris.setFaction(  EnumFaction.Neutral );
						lDebris.setTimeOfLife( 2+World3d.sGlobalRandom.nextFloat( 1f  ));
						World3d.Get().addActor(lDebris );
				}
		}				

		
};

//*************************************************
