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
public class Bonus extends Sprite {

		static public Color4 sColorErg    = new Color4(0.3, 0.3, 0.1, 0.8 );
		static public Color4 sColorRocket = new Color4(0.1, 0.1, 0.4, 0.8 );
		static public Color4 sColorField  = new Color4(0.3, 0., 0.3, 0.8 );
		static public Color4 sColorLife   = new Color4(0.8, 0.2, 0.2, 0.8 );
		static public Color4 sColorGold   = new Color4(0.4, 0.4, 0.4, 0.8 );
		static public Color4 sColorUpgrad = new Color4(0.5, 1, 0.5, 0.8 );

		static public Color4 sColorCage = new Color4(0.5, 0.5, 0, 0.2);


		static public  Aspect3d sPropsErg    = new Aspect3d( sColorErg, sColorErg,	Aspect3d.DrawStyle.FILL  );
		static public  Aspect3d sPropsRocket = new Aspect3d( sColorRocket,  sColorRocket,	Aspect3d.DrawStyle.FILL);
		static public  Aspect3d sPropsField  = new Aspect3d( sColorField,  sColorField,	Aspect3d.DrawStyle.FILL);
		static public  Aspect3d sPropsLife   = new Aspect3d( sColorLife,  sColorLife,	Aspect3d.DrawStyle.FILL);
		static public  Aspect3d sPropsGold   = new Aspect3d( sColorGold, sColorGold,	Aspect3d.DrawStyle.FILL );
		static public  Aspect3d sPropsUpgrad = new Aspect3d( sColorUpgrad, sColorUpgrad,	Aspect3d.DrawStyle.FILL );

		static public  Aspect3d sAspectCage = new Aspect3d( sColorCage,	sColorCage,  Aspect3d.DrawStyle.LINE );

		public enum BonusType{
				BonusVoid( 0, null, 0, 0  ),

						BonusErg( 1, sPropsErg, 10, 0.5f ),
						SuperBonusErg( 3, sPropsErg, 30, 0.75f ),

						BonusRocket( 1,   sPropsRocket, 10, 0.5f ),
						SuperBonusRocket( 3,  sPropsRocket, 30, 0.75f ),

						BonusField( 1, sPropsField, 10, 0.5f ),
						SuperBonusField( 3,  sPropsField, 30, 0.75f ),

			
						BonusGold( 1, sPropsGold, 10, 0.5f ),
						SuperBonusGold( 10, sPropsGold, 100, 0.75f ),

						BonusLife( 10, sPropsLife, 1, 0.5f ),
						SuperBonusLife( 30, sPropsLife, 3, 0.75f ),

						BonusUpgrad( 10, sPropsUpgrad, 10, 0.5f ),
						SuperBonusUpgrad( 30,  sPropsUpgrad, 30, 0.75f )
						;
				
				BonusType( int pCout, Aspect3d pAspect, double pVal, double pSize ){
						cCout   = pCout;
						cAspect = pAspect;
						cVal    = (float)pVal;
						cSize   = (float)pSize;
				}

				public int cCout;
				public Aspect3d cAspect;
				public float cVal;
				public float cSize;
		};

		static Primitiv3d sPrim = 	new Primitiv3d();


		public BonusType cBonusType;
		float cVal;
		float cSize;

		public int getActorType() { return FactoryActor.ActorType.ActorBonusType.cCode;}

		//------------------------------------------------
		public Bonus( Float3 pLocation, BonusType pBtype,  NodeBase pNode ) {
				super( pLocation, EnumFaction.Green, pNode, 10, 0f, 0f );
				
		}
		//------------------------------------------------
		static public Bonus LaunchBonus( BonusType pBtype,
																			Float3 pLocation ) {
				
				Bonus lBonus;
				World3d.Get().addActor(lBonus=(CreateBonus( pBtype, pLocation )));
				
				return lBonus;
		}
		//------------------------------------------------
		static public void LaunchBonus( float pVal, boolean pSuper, Float3 pLocation ) {
				System.out.println( "LaunchBonus ");


				BonusType lBonus = BonusType.BonusVoid;
				
				while( pSuper && pVal > 0 ){								
						int lAlea = World3d.sGlobalRandom.nextIntPositif( 100 );
						
						if( lAlea > 70 ) lBonus = BonusType.BonusLife;
						else 	if( lAlea > 40 ) lBonus = BonusType.BonusUpgrad;
						else 	if( lAlea > 20 ) lBonus = BonusType.SuperBonusLife;
						else 	if( lAlea > 0 )	 lBonus = BonusType.SuperBonusUpgrad;
						pVal -= lBonus.cCout;
						CreateBonus( lBonus, pLocation );
				}
				
				while( pVal > 0 ){
						int lAlea = World3d.sGlobalRandom.nextIntPositif( 100 );
						
					System.out.println( "LaunchBonus Alea:" +lAlea);

					if(  lAlea < 20 )lBonus = BonusType.BonusErg;
						else if(  lAlea < 40 )lBonus = BonusType.BonusRocket;
						else if(  lAlea < 60 )lBonus = BonusType.BonusField;
						else if(  lAlea < 75 )lBonus = BonusType.BonusGold;
						else if(  lAlea < 80 )lBonus = BonusType.SuperBonusErg;
						else if(  lAlea < 85 )lBonus = BonusType.SuperBonusRocket;
						else if(  lAlea < 90 )lBonus = BonusType.SuperBonusField;
						else if(  lAlea < 95 )lBonus = BonusType.SuperBonusGold;
						pVal -= lBonus.cCout;

						if( lBonus != BonusType.BonusVoid )
								LaunchBonus( lBonus, pLocation );
				}
		}
		//------------------------------------------------
		static public Bonus CreateBonus( BonusType pCtype,
																		 Float3 pLocation ) {

				Bonus lBonus = null;
				
				Primitiv3d.SubParamObject3d lParam01 = sPrim.GetSubParamObject3d ( 1, pCtype.cSize,  true, Primitiv3d.SubNormalizeType.NORMALIZE);
				Primitiv3d.SubParamObject3d lParam02 = sPrim.GetSubParamObject3d ( 1, pCtype.cSize*1.5f,  true, Primitiv3d.SubNormalizeType.NORMALIZE);


				ModelBase  lO1  = new CompilObj( Primitiv3d.Odron( lParam01).getObject3d());			
				ModelBase  lO2  = new CompilObj( Primitiv3d.Odron( lParam02).getObject3d());			

				Transform3d lTransf = new Transform3d( null, pLocation, null );
				
				Leaf3d lLeaf1 = new Leaf3d( lO1, lTransf, pCtype.cAspect);								
				Leaf3d lLeaf2 = new Leaf3d( lO2, lTransf, sAspectCage);								
				Node3d lNode = new Node3d();	
							
				
				// ATTENTION BUG ASPECT LINE : METTRE leaf1 en dernier 
				lNode.addChild( lLeaf2, lLeaf1 );

				lBonus = new Bonus( pLocation, pCtype,  lNode  );
				lBonus.setRotation( Float3.GetRandom( World3d.sGlobalRandom, 360f) );					
				lBonus.setSpin( Float3.GetRandom( World3d.sGlobalRandom, 360f) );					

				//=====================================

				lBonus.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.DeleteOutOfWorld ));

				lBonus.setBoundingSphere( pCtype.cSize*1.5f	);
				lBonus.setRotation( Float3.GetRandom( World3d.sGlobalRandom, 300f) );					

				Float3 lSpeed = new Float3( World3d.sGlobalRandom.nextFloatPositif( LevelBase.cSpeedDefilX ), 
																		World3d.sGlobalRandom.nextFloat( LevelBase.cSpeedDefilX ),
																		0f);

				lBonus.cSpeed.set( lSpeed );


				lBonus.cBonusType = pCtype;
				lBonus.setActorType( SpriteType.SpriteBonus.code );
	
	
				lBonus.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.DeleteOutOfWorld ));


				return lBonus;
		}

		//------------------------------------------------
		public void callDestruction(){	

				Explosion lExplo;
				lExplo = Explosion.CreateExplosion( getFaction(), cLocation, 0f, 0f,  cBonusType.cAspect.cColorMaterial,  null, Explosion.ExploMode.Implosion, 0.1f );
				lExplo.setActorType( SpriteType.SpriteExplosion.code ); 
				
				lExplo.cUniformPlane = true;
				
				World3d.Get().addActor(lExplo );
								
				setDeleted();
		}
};
