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

public class Ship  extends Sprite{


		public Ship(  EnumFaction pFaction ) {
				super(pFaction );				
		}

		public Ship(  EnumFaction pFaction,
									float   pResistance,	
									float   pDamage,
									float   pFieldPower ) {
				super(pFaction );				
		}


		//------------------------------------------------
		public void callDestruction(){	
				
				Explosion lExplo = null;
				Node3d lNode = ((Node3d)getNode());

				setTimeOfLife( 0 );

				if( getFaction() == EnumFaction.Red ){
						// PROVOQUE UN GROS PROBLEME DE RENDU (SANS DOUTE DUT AU LIGNES )
						Bonus.LaunchBonus( (cDamage+getBoundingSphere())/3f, false, getLocation() );
				}

				float lSz = getBoundingSphere()*0.5f+ getDamage();

				lSz = World3d.sGlobalRandom.nextFloatPositif( lSz )+lSz ;
				for( NodeBase lChild: lNode.getChilds() ){
						
					 	Float3 lLocation = Float3.Add( cLocation,  Float3.GetRandom( World3d.sGlobalRandom, getBoundingSphere()));
						
						Node3d lVect = new Node3d();
						lVect.addChild( lChild );			

						// Pour les reacteurs des vaisseaux 
						lChild.userCall( 0 );
						//			lVect.getNewTransf().rotate( new Float3(0, -90, 0 ));
						
						Sprite lDebris = new Sprite( cLocation, EnumFaction.Neutral, lVect );/* {

										//==============================
										public void worldCallClose() {
												Explosion lExplo = null;
												
												if( getNode().getTransf() != null &&  getNode().getTransf().getTranslat() != null){
														World3d.Get().addActor( Explosion.CreateExplosion(  EnumFaction.Neutral, 
																																								Float3.Add( cLocation, getNode().getTransf().getTranslat()),
																																								1+World3d.sGlobalRandom.nextFloatPositif(1),   0f, 
																																								new Color4(1.0f, 0.2f, 0.1f, 0.5f),  null,  Explosion.ExploMode.Explosion,  0.1f));
												}
												else {
														World3d.Get().addActor( Explosion.CreateExplosion(  EnumFaction.Neutral, cLocation,
																																								1+World3d.sGlobalRandom.nextFloatPositif(1), 
																																								0f, new Color4(1.0f, 0.2f, 0.1f, 0.5f),  null,  Explosion.ExploMode.Explosion,  0.1f ));														
												}
										}
										};*/

						//==============================
						
						lDebris.setSpin( Float3.GetRandom( World3d.sGlobalRandom, 100f) );
						
						Float3 lSpeed = Float3.GetRandom( World3d.sGlobalRandom, LevelBase.cSpeedDefilX/3);
						lSpeed.setX( lSpeed.x()+LevelBase.cSpeedDefilX );
						lDebris.setSpeed(lSpeed );
						
						lDebris.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.DeleteOutOfWorld ));
						World3d.Get().addActor( lDebris );
						
						//		cSpeed.setZ( LevelBase.cSpeedDefilX*1f );
						lDebris.setTimeOfLife( 0.24 );
						lDebris.setInflation( new Float3( -0.9f ));


						lExplo = Explosion.CreateExplosion(  EnumFaction.Neutral, lLocation,
																								 lSz,
																								 //1.5f+World3d.sGlobalRandom.nextFloatPositif(2f),
																								 0f, new Color4(1.0f, 0.2f, 0.1f, 0.5f),  null,  
																								 Explosion.ExploMode.Explosion,  0.3f  );				
						lExplo.setSpeed(lSpeed );
						World3d.Get().addActor(lExplo );
						
						lExplo = Explosion.CreateExplosion(  EnumFaction.Neutral, lLocation,
																								 lSz*0.5f,
																								 //	 1f+World3d.sGlobalRandom.nextFloatPositif(1f),
																								 0f, new Color4(0.8f, 0.6f, 0.1f, 0.6f),  null,  
																								 Explosion.ExploMode.Explosion,  0.25f  );				
						lExplo.setSpeed(lSpeed );
						World3d.Get().addActor(lExplo );
						
						
						//						setFaction( EnumFaction.Neutral );				}

						//						Explosion lExplo = Explosion.CreateExplosion( EnumFaction.Neutral, cLocation, getBoundingSphere()*1.4f, 0f, cColor,  null, Explosion.ExploMode.Implosion,  0.4f  );
						//						World3d.sTheWorld.addActor( lExplo );

				}	
				//		World3d.Get().addActor(  Explosion.CreateExplosion(  EnumFaction.Neutral, cLocation, getBoundingSphere(),   0f, new Color4(1.0f, 0.2f, 0.1f, 0.5f),  null,  Explosion.ExploMode.Explosion,  0.2f  ));
				

		}
	//------------------------------------------------


}

//*************************************************
