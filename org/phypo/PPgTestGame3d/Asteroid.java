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
public class Asteroid  extends Sprite{

		static final Color4 cColorGrey = new Color4(  0.5, 0.5, 0.5, 1.0 );
		static final Color4 cColorSnow = new Color4(  0.6, 0.6, 1.0, 1.0 );

		static final Color4 cColorIce1  = new Color4(  0.4, 0.6, 1.0, 0.2);
		static final Color4 cColorIce2  = new Color4(  0.5, 0.5, 1.0, 1);

		//Color4 cColorCristal1 = new Color4(  0.3, 0.4, 0.8, 0.05);
		static final Color4 cColorCristal1 = new Color4(  0.8, 0.4, 0.2, 0.05);
		static final Color4 cColorCristal2 = new Color4(  0.8, 0.5, 0.8, 0.15);

		static final Color4 cColorAlien1 = new Color4( 0.2f, 0.3f, 0.5f, 1f );
		static final Color4 cColorAlien2 = new Color4(  0.2, 0.3, 1, 1f);

		static final Color4 cColorMars = new Color4(  0.6, 0.3, 0.1, 1);

		public enum AsteroidType{
			     	AsteroidAlien1,
						AsteroidIce,
						AsteroidMars,
								AsteroidWater
		};
		AsteroidType cAsterType;
		Color4       cColor;

		int cComplexity;


		public int getActorType() { return FactoryActor.ActorType.ActorAsteroideType.cCode;}

		//------------------------------------------------
		
		protected Asteroid(  NodeBase pNode3d, Float3 pLocation ) {
				super( pLocation, EnumFaction.Green, pNode3d );				
		}
		//------------------------------------------------
		
		static public Asteroid CreateAsteroid( AsteroidType pType, float pDamage, int pComplexity, float pSize ){
				
				//		System.out.println( "Asteroid.CreateAsteroid " + pSize);

				
				Color4 lColorGen = null;
				
				//=====================================
				Node3d lNode  = new Node3d( );
				Leaf3d lLeaf;


				switch( pType ) {
						//=====================================
				case AsteroidAlien1 : {
						
						lColorGen =cColorAlien1;
						
						Primitiv3d.SubParamObject3d lParam01 = Primitiv3d.GetSubParamObject3d ( pComplexity, pSize,  false, Primitiv3d.SubNormalizeType.NORMALIZE_INC_SUB );
						ModelBase lM = Primitiv3d.Pyramid4( 0,0,0, pSize,        pSize,       lParam01).getObject3d();
						
						lNode.addChild( new Leaf3d( Primitiv3d.Pyramid4( 0,0,0, pSize, pSize,lParam01).getObject3d(),
																				new Transform3d( null, new Float3(), null ),
																				new Aspect3d( cColorAlien1, Aspect3d.DrawStyle.FILL ),
																				true ));
						
						lNode.addChild( new Leaf3d(  Primitiv3d.Pyramid4( 0,0,0, pSize*1.01f,  pSize*1.01f, lParam01).getObject3d(),
																				 new Transform3d( null, new Float3(), null ),
																				 new Aspect3d( cColorAlien2, Aspect3d.DrawStyle.LINE ),
																				 true ));
						
				}	break;
						
						//=====================================
				case AsteroidIce : {
						
						lColorGen = cColorIce1;
						
						for( float f =1f; f > 0.4f; f -= 0.2f ) {								
								Primitiv3d.SubParamObject3d lParam01 = Primitiv3d.GetSubParamObject3d ( (int)(pComplexity-f), pSize*f, true, Primitiv3d.SubNormalizeType.NORMALIZE );	
								lNode.addChild( new Leaf3d( Primitiv3d.Odron( lParam01 ).getObject3d(), 
																						new Transform3d( null, new Float3(), null ),
																						new Aspect3d( cColorIce1, Aspect3d.DrawStyle.FILL ),
																						true ));
						}								
				}	break;
						
						//=====================================
				case AsteroidMars : {
						Aspect3d lAspect = new Aspect3d( cColorMars, FactoryLevel.sTextureMars );
						
						lColorGen = cColorIce1;
						//					Primitiv3d.SubParamObject3d lParam = Primitiv3d.GetSubParamObject3d ( (int)(pComplexity-1), pSize, true, Primitiv3d.SubNormalizeType.NORMALIZE );	

						Primitiv3d.SubParamObject3d lParam = Primitiv3d.GetSubParamObject3d (  pComplexity, pSize, false, Primitiv3d.SubNormalizeType.NORMALIZE );	
						lParam.cUseTexture = true;
						
						lNode.addChild( new Leaf3d(  Primitiv3d.Odron( lParam ).getObject3d(),
																				new Transform3d( null, new Float3(), null ),
																				new Aspect3d( cColorIce1, FactoryLevel.sTextureMars ),
																				true ));												
				}	break;
						//=====================================
				case AsteroidWater : {
						
						lColorGen = cColorIce1;
						
						for( float f =1f; f > 0.4f; f -= 0.2f ) {								
								Primitiv3d.SubParamObject3d lParam01 = Primitiv3d.GetSubParamObject3d ( (int)(pComplexity-f), pSize*f, true, Primitiv3d.SubNormalizeType.NORMALIZE );	
								lNode.addChild( new Leaf3d(  Primitiv3d.Odron( lParam01 ).getObject3d(),
																				new Transform3d( null, new Float3(), null ),
																				new Aspect3d( cColorIce1, FactoryLevel.sTextureWater ),
																				true ));																				
						}				



						}	break;
						//=====================================

				}


				//=====================================
				


				Asteroid lAster =  new Asteroid( lNode, new Float3()  );	
				lAster.cAsterType = pType;
				
				lAster.setBoundingSphere( pSize );
				lAster.cColor = lColorGen;
				
				lAster.cResistance = lAster.getBoundingSphere();
				
				lAster.cComplexity = 	pComplexity;
				
				return lAster;
		}
		//------------------------------------------------
		public void callDestruction(){	
				
				if( getBoundingSphere() > 2f  ){
						
						float cNb = getBoundingSphere()*0.5f;
						//	cNb = (float)Math.sqrt( cNb );
						cNb +=   World3d.sGlobalRandom.nextFloatPositif( cNb*0.5f );

						if( cComplexity > 0 )
								cComplexity--;
						
						for( float i= 0; i< cNb; i++ ){ 

								float lSz = getBoundingSphere()/1.2f-(World3d.sGlobalRandom.nextFloatPositif( 1f  ));
								if( lSz < 0.5f )
										lSz = 0.5f;																																 
								
								Asteroid lAster = CreateAsteroid( cAsterType, lSz,  cComplexity, lSz );
								Float3 lSpeed = new Float3( World3d.sGlobalRandom.nextFloatPositif( LevelBase.cSpeedDefilX ), 
																						World3d.sGlobalRandom.nextFloat( LevelBase.cSpeedDefilX ),
																						0f);
								Float3 lLocation = new Float3( getLocation() );
								lLocation.add( new Float3( World3d.sGlobalRandom.nextFloat( cNb/2 ), 
																						World3d.sGlobalRandom.nextFloat( cNb/2 ),
																					 0f));
								
								lAster.setLocation( lLocation );
								lAster.setSpin( Float3.GetRandomZ( World3d.sGlobalRandom, 200f, 300f) );
								lAster.setSpeed( lSpeed );
								lAster.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.DeleteOutOfWorld ));
								World3d.sTheWorld.addActor( lAster );
						}

						//						Explosion lExplo = Explosion.CreateExplosion( EnumFaction.Neutral, cLocation, getBoundingSphere()*1.4f, 0f, cColor,  null, Explosion.ExploMode.Implosion,  0.4f  );
						//						World3d.sTheWorld.addActor( lExplo );

				}	
				
				setInflation( new Float3( -0.9f ));
											//		cSpeed.setZ( LevelBase.cSpeedDefilX*1f );
				setTimeOfLife( 0.6 );
				setFaction( EnumFaction.Neutral );
		}				
	//------------------------------------------------
}
//*************************************************
