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

import com.jogamp.opengl.util.texture.Texture;

//*************************************************
public class ShipMaker{

		static final Color4 sColorBody  = new Color4( 0.1, 0.7, 0.7, 1.0 );
		static final Color4 sColorGrid  = new Color4( 0.8, 0.8, 0.8, 1.0 );
		static final Color4 sColorGrid2 = new Color4( 0.8, 0.8, 0.8, 1.0 );
		static final Color4 sColorModul = new Color4( 0.2, 0.6, 0.8, 1.0 );
		static final Color4 sColorModul2 = new Color4( 0.0, 0.8, 0, 0.6 );
		static final Color4 sColorSoute1 = new Color4( 0.1, 0.8, 0.8, 1.0 );
		static final Color4 sColorSoute2 = new Color4( 0.2, 0.2, 0.2, 1.0 );
		static final Color4 sColorFan   = new Color4( 0.3, 0.9, 0.7, 1.0 );
		static final Color4 sColorReactFire   = new Color4( 0.1, 0, 0.3, 0.1 );
		static final Color4 sColorReactFire2  = new Color4( 0.4, 0, 0.6, 0.1 );
		//		static final Color4 sColorReactFire   = new Color4( 0.3, 0.3, 0.3, 0.4 );
		//		static final Color4 sColorReactFire2  = new Color4( 0.4, 0.4, 0.6, 0.4 );
		static final Color4 sColorDome  = new Color4( 0.3, 0.5, 0.6, 0.2 );

		static public Texture sTexBlindage;
		static public Texture sTexBody;
		static public Texture sTexMod;
		static public Texture sTexSpin;
		static public Texture sTexBleue;
		static public Texture sTexMaille;
		static public Texture sTexEcail;
		static public Texture sTexFlamme;
		static public Texture sTexDalle;
		static public Texture sTexEmail;

		static public  Aspect3d sPropsBlindage;
		static public  Aspect3d sPropsBody ;
		static public  Aspect3d sPropsGridLine ;
		static public  Aspect3d sPropsGridFill ;
		static public  Aspect3d sPropsModul ;
		static public  Aspect3d sPropsModul2 ;
		static public  Aspect3d sPropsModul3 ;

		static public  Aspect3d sPropsSoute1 ;
		static public  Aspect3d sPropsSoute2 ;
		static public  Aspect3d sPropsFan ;
		static public  Aspect3d sPropsReactFire ;
		static public  Aspect3d sPropsReactFire2 ;
		static public  Aspect3d sPropsDomeLine;
		static public  Aspect3d sPropsDomeFill;

		static public  Aspect3d sPropsEcail ;
		static public  Aspect3d sPropsSpin ;
		static public  Aspect3d sPropsMaille ;
		static public  Aspect3d sPropsBleue ;
		static public  Aspect3d sPropsFlamme ;
		static public  Aspect3d sPropsDalle ;
		static public  Aspect3d sPropsEmail ;


		
		//----------------------------------------------
		static public boolean Init(  GL2  pGL ){

				Engine lEngine = World3d.Get().getEngine();

				sTexMod = lEngine.loadTexturePng( pGL, "Textures/5.png" );
				
				sTexBody = lEngine.loadTexturePng( pGL, "Textures/25.png" );
				sPropsBody = new Aspect3d( sColorBody );
				
				sTexBlindage = lEngine.loadTexturePng( pGL, "Textures/9.png" );
				sPropsBlindage = new Aspect3d(sTexBlindage );
				
				sTexSpin = lEngine.loadTexturePng( pGL, "Textures/32.png" );
				sPropsSpin = new Aspect3d( sTexSpin );
				
				sTexBleue= lEngine.loadTexturePng( pGL, "Textures/32.png" );
				
				sTexMaille = lEngine.loadTexturePng( pGL, "Textures/49.png" );
				sPropsMaille = new Aspect3d(  sTexMaille );
				
				sTexEcail = lEngine.loadTexturePng( pGL, "Textures/53.png" );
				sPropsEcail = new Aspect3d(  sTexEcail );
				
				sTexFlamme = lEngine.loadTexturePng( pGL, "Textures/38.png" );
				sPropsFlamme = new Aspect3d( sTexFlamme );
				
				sTexDalle  = lEngine.loadTexturePng( pGL, "Textures/63.png" );
				sPropsDalle = new Aspect3d( sTexDalle );
				
				sTexEmail = lEngine.loadTexturePng( pGL, "Textures/25.png" );
				sPropsEmail  = new Aspect3d( sTexEmail );


				sPropsDomeFill = new Aspect3d( sColorDome, sColorDome, Aspect3d.DrawStyle.FILL ); 
				sPropsDomeLine = new Aspect3d( sColorDome, sColorDome, Aspect3d.DrawStyle.LINE ); 

				
				sPropsGridFill = new Aspect3d( sColorGrid, Aspect3d.DrawStyle.FILL );
				sPropsGridLine = new Aspect3d( sColorGrid2, Aspect3d.DrawStyle.LINE );


				sPropsModul  = new Aspect3d( sColorModul, Aspect3d.DrawStyle.FILL );
				sPropsModul2 = new Aspect3d( sColorModul2, Aspect3d.DrawStyle.FILL );
				sPropsModul2.setColorEmission( sColorModul2 );

				sPropsModul3 = new Aspect3d( sColorModul, Aspect3d.DrawStyle.LINE );

				sPropsSoute1 = new Aspect3d( sColorSoute1, Aspect3d.DrawStyle.FILL );
				sPropsSoute2 = new Aspect3d( sColorSoute2, Aspect3d.DrawStyle.LINE );

				sPropsFan = new Aspect3d( sColorFan );
				sPropsReactFire  = new Aspect3d( sColorReactFire  , sColorReactFire );
				sPropsReactFire2 = new Aspect3d( sColorReactFire2, sColorReactFire2 );

				/*
				sPropsEcail = new Aspect3d( );
				sPropsSpin = new Aspect3d( );
				sPropsMaille = new Aspect3d( );
				sPropsBleue = new Aspect3d( );
				sPropsFlamme = new Aspect3d( );
				sPropsDalle = new Aspect3d( );
				sPropsEmail = new Aspect3d( );	
				*/

				return true;
		}

//----------------------------------------------
		static public NodeBase MakeReactor( float pSz ) {

				Node3d lNode = new Node3d(){
								public void userCall( double lVal ){										
										// On enleve le jet
										try {
												getChilds().remove(1);	
												getChilds().remove(1);	
										}catch( Exception e ){
												System.out.println( "*** Error in userCall for Reactor destroy ***" );
										}
								}
						};



				ModelBase lCone = new CompilObj(  ModelQuadric.GetCone( pSz, pSz*1.5f, 5, 5 ) );
				lNode.addChild( new Leaf3d( lCone, null, sPropsBody  ));

				
				ModelBase lJet =new CompilObj(  ModelQuadric.GetCone( pSz/1.5f, pSz*10f, 5, 5 ));				
				lNode.addChild( new Leaf3d( lJet, new Transform3d( null, new Float3( 0, 180, 0 ), null ), sPropsReactFire ));


				ModelBase lJet2 = new CompilObj( ModelQuadric.GetCone( pSz/2f, pSz*5f , 5, 5 ));
				lNode.addChild( new Leaf3d( lJet2, new Transform3d( null, new Float3( 0, 180, 0 ), null ), sPropsReactFire2 ));


				lNode.getNewTransf().rotate( new Float3(0, -90, 0 ));


				return lNode;
		}	
		//----------------------------------------------
		static public NodeBase MakeReactorCluster4( float pSz ) {

				Node3d lCluster = new Node3d();
				
				NodeBase lReact1 = MakeReactor(  pSz );
				NodeBase lReact2 = MakeReactor(  pSz );			
				NodeBase lReact3 = MakeReactor(  pSz );
				NodeBase lReact4 = MakeReactor(  pSz );
		
				lReact1.getTransf().move( new Float3( pSz*0.5f,  pSz*0.5f, 0f ));
				lReact2.getTransf().move( new Float3( pSz*0.5f, -pSz*0.5f, 0f ));
				lReact3.getTransf().move( new Float3( pSz*0.5f,  0,  pSz*0.5f ));
				lReact4.getTransf().move( new Float3( pSz*0.5f,  0, -pSz*0.5f ));


				lCluster.addChild( lReact1 );	
				lCluster.addChild( lReact2 );
				lCluster.addChild( lReact3 );				
				lCluster.addChild( lReact4 );

				Leaf3d lGrid = new Leaf3d( ModelQuadric.GetCylinder(  pSz*0.8f, pSz*0.8f,  pSz*0.8f, 7, 7 ), null, sPropsBody, true );
				lGrid.getNewTransf().moveX( -pSz*0.5f );
				lGrid.getTransf().rotateY( -90 );
				lCluster.addChild( lGrid  ) ;																									


				return lCluster;
		}	
		//----------------------------------------------
		static public NodeBase MakeGrid( float pH, float pL ) {

				Node3d lNode = new Node3d();

				ModelBase lGrid1 = new CompilObj(  ModelQuadric.GetCylinder(  pH,  pH,  pL, 5, 5 ));
				//	ModelBase lGrid2 = ModelQuadric.GetCylinder(  pH*1.1f,  pH*1.1f,  pL*1.1f, 5, 5 );
				//		lNode.addChild( new Leaf3d( lGrid1, null, sPropsGridFill ));
				lNode.addChild( new Leaf3d( lGrid1, null, sPropsGridLine ));

				//			lNode.getNewTransf().rotate( new Float3( 0f, 90f,  0f));

				lNode.getNewTransf().rotate( new Float3(0, -90, 0 ));

				
				return lNode;
		}		
		//----------------------------------------------
		static public Turret MakeTurret( float pSz ) {
				Turret lTurret = new Turret();

				ModelBase lMod = new CompilObj(  ModelQuadric.GetSphere( pSz, 7, 7 ));

				lTurret.addChild( new Leaf3d( lMod, null, sPropsModul ));
				lTurret.getNewTransf().rotate( new Float3(0, 0, 0 ));
				return lTurret;
		}
		//----------------------------------------------
		static public Turret MakeTurret2( float pSz ) {
				Turret lTurret = new Turret();

				ModelBase lCone = new CompilObj(  ModelQuadric.GetCone( pSz, pSz*1.5f, 5, 5 ));
				
				ModelBase lMod = new CompilObj(  ModelQuadric.GetSphere( pSz*0.85f, 7, 7 ));

				lTurret.addChild( new Leaf3d( lCone, null, sPropsModul ));

				Leaf3d lSphere =  new Leaf3d( lMod, null, sPropsModul2 );
				lSphere.getNewTransf().moveZ( -pSz*0.25f );
				lTurret.addChild( lSphere);

				 lMod = ModelQuadric.GetSphere( pSz*0.86f, 7, 7 );
				 lSphere =  new Leaf3d( lMod, null, sPropsModul3 );
				 lSphere.getNewTransf().moveZ( -pSz*0.25f );
				 lTurret.addChild( lSphere);

				lTurret.getNewTransf().rotate( new Float3(0, -90, 0 ));
				return lTurret;
		}
		//----------------------------------------------
		static public NodeBase MakeSoute( float pH, float pL ){


				pH *= 0.8f;
				pL *= 0.8f;
				
				Node3d lNode = new Node3d();
				ModelBase lSoute1 = new CompilObj(  ModelQuadric.GetCylinder(  pH,  pH, pL, 5, 5 ) );
 
				Leaf3d lLeaf1 =  new Leaf3d( lSoute1, null, sPropsSoute1  );
				//				lLeaf2.getNewTransf().rotate( new Float3(0, -90, 0 ));


				ModelBase lSoute2 = new CompilObj(  ModelQuadric.GetCylinder(  pH*1.1f,  pH*1.1f, pL, 5, 5 ));
				Leaf3d lLeaf2 =  new Leaf3d( lSoute2, null, sPropsSoute2  );

				lNode.addChild( lLeaf1 );
				lNode.addChild( lLeaf2 );
				lNode.getNewTransf().rotate( new Float3(0, -90, 0 ));

				return lNode;
		}
		//----------------------------------------------
		static public NodeBase MakeFan( float pL, float pH, int pFacette ) {
				
				ModelBase lFan = new CompilObj(  ModelQuadric.GetDisk( pH, pL, pFacette, pFacette ));

				Leaf3d lLeaf = new Leaf3d( lFan, null, sPropsFan  );
				lLeaf.getNewTransf().rotate( new Float3( 90, 0, 0 ));
				return lLeaf;
		}
		//----------------------------------------------
		static public Ship MakeShip1( EnumFaction pFaction, float pSz, Weapon.WeaponType pWeapon1 ) // Sprite3dObj* pSp, EnumWeapons pWeapon, int pNiveau ) {
		{
				Ship  lShip = new Ship( pFaction, 0.5f, 0.5f, 0f);

 
				Node3d lVect = new Node3d();
				float lLg = pSz*3;
	

				//			NodeBase lGrid1 = MakeGrid( pSz*0.5f,  lLg*2.5f );
				NodeBase lGrid1 = MakeGrid( pSz*0.4f,  lLg );
				lGrid1.getTransf().move( new Float3(lLg*0.6f*pSz,  0f,  0f ));
				lVect.addChild( lGrid1 );
				

				Turret lMod1 = MakeTurret( pSz );
				RectFloat3 lBox = new RectFloat3( -100, -30, -100, 100, 30,  100 );
				lMod1.set( lShip, 0.5f, Weapon.FireMode.FireFront, pWeapon1,  lBox  );				
				lMod1.getTransf().move( new Float3( -lLg*0.50f, 0,  0  ));
				lVect.addChild( lMod1 );
				
				
				NodeBase  lReact = MakeReactor( pSz);
				lReact.getTransf().move( new Float3(  lLg*0.65f, 0f, 0f ));
				lVect.addChild( lReact );

				

			//	/////			lVect.getNewTransf().rotate( new Float3(0, -90, 0 ));

				
				lShip.setNode( lVect );
				lShip.setBoundingSphere( pSz ); // A MODIFIER 
				lShip.setSpin( new Float3( 180, 0, 0 ));
				//				lShip.setLocation( new Float3( -pSz, 0,  0 ));
			return lShip;
		}
			//----------------------------------------------
		static public Ship MakeShip5( EnumFaction pFaction, float pSz, Weapon.WeaponType pWeapon1 ) // Sprite3dObj* pSp, EnumWeapons pWeapon, int pNiveau ) {
		{
				Ship  lShip = new Ship(pFaction, 5f, 5f, 0f );

 
				Node3d lVect = new Node3d();
				float lLg = pSz*4;
	

				//			NodeBase lGrid1 = MakeGrid( pSz*0.5f,  lLg*2.5f );
				NodeBase lGrid1 = MakeGrid( pSz*0.5f,  lLg );
				lGrid1.getTransf().move( new Float3(lLg*0.65f*pSz,  0f,  0f ));
				lVect.addChild( lGrid1 );
				

				Turret lTurret1 = MakeTurret2( pSz );
				RectFloat3 lBox = new RectFloat3( -100, -30, -100, 100, 30,  100 );
				lTurret1.set( lShip, 0.5f, Weapon.FireMode.FireFront, pWeapon1,  lBox  );				
				lTurret1.getTransf().move( new Float3( -pSz*0.60f, 0,  0  ));
				lVect.addChild( lTurret1 );
				
				
				NodeBase  lReact =MakeReactorCluster4(  pSz*0.8f);
				//	NodeBase  lReact = MakeReactor( pSz*0.8f);
				lReact.getNewTransf().move( new Float3(  lLg*0.7f, 0f, 0f ));
				lVect.addChild( lReact );

 

			//	/////			lVect.getNewTransf().rotate( new Float3(0, -90, 0 ));

				
				lShip.setNode( lVect );


					
				NodeBase lSoute = MakeSoute( pSz*0.5f,  lLg*0.5f );
				lSoute.getTransf().move( new Float3(  lLg*0.45f, 0f, pSz*0.7f  ));
				((Node3d)lShip.getNode()).addChild( lSoute );
				
				lSoute = MakeSoute( pSz*0.5f,  lLg*0.5f );
				lSoute.getTransf().move(  new Float3( lLg*0.45f, 0f, -pSz*0.7f  ));
				((Node3d)lShip.getNode()).addChild( lSoute );


				lShip.setBoundingSphere( pSz*2 );

				lShip.setSpin( new Float3( 180, 0, 0 ));
				//				lShip.setLocation( new Float3( -pSz, 0,  0 ));
			return lShip;
		}
		
		//----------------------------------------------
		static public Ship MakeShip2(  EnumFaction pFaction, float pSz, int pLevel ) {

				Weapon.WeaponType lWeaponType = Weapon.WeaponType.WeaponSmallPlasma;
				
				if( pLevel == 1 ){
						lWeaponType	 = Weapon.WeaponType.WeaponPlasma;
				} else	if( pLevel >=  2 ){
						lWeaponType = Weapon.WeaponType.WeaponPlasma2;
				}
				
				
				Ship lShip = MakeShip1( pFaction, pSz, lWeaponType );
				lShip.setSpriteProperties( 1f, 1f, 0f );
				
				float lLg = pSz*1.8f;
				
							
				NodeBase lSoute = MakeSoute( pSz*0.5f,  lLg );
				lSoute.getTransf().move( new Float3(  lLg*0.60f*pSz, 0f, pSz*0.7f  ));
				((Node3d)lShip.getNode()).addChild( lSoute );
				
				lSoute = MakeSoute( pSz*0.5f,  lLg );
				lSoute.getTransf().move(  new Float3( lLg*0.60f*pSz, 0f, -pSz*0.7f  ));
				((Node3d)lShip.getNode()).addChild( lSoute );
				lShip.setBoundingSphere( pSz*1.3f ); // A MODIFIER 

				return lShip;
		}
		//----------------------------------------------
		static public Ship MakeShip3(  EnumFaction pFaction, float pSz, int pLevel ) {

				Weapon.WeaponType lWeaponType = Weapon.WeaponType.WeaponSmallPlasma;
				
				if( pLevel == 1 ){
						lWeaponType	 = Weapon.WeaponType.WeaponPlasma;
				} else	if( pLevel >=  2 ){
						lWeaponType = Weapon.WeaponType.WeaponPlasma2;
				}
				
				
				Ship lShip = MakeShip2( pFaction, pSz, pLevel );
				lShip.setSpriteProperties( 2f, 2f, 0f );

				float lLg = pSz*1.8f;
				
				NodeBase lSoute = MakeSoute( pSz*0.6f,  lLg );
				lSoute.getTransf().move( new Float3(  lLg*0.55f*pSz,  pSz*0.7f, 0f ));
				((Node3d)lShip.getNode()).addChild( lSoute );
				
				lSoute = MakeSoute( pSz*0.6f,  lLg );
				lSoute.getTransf().move(  new Float3( lLg*0.55f*pSz, -pSz*0.7f, 0f ));
				((Node3d)lShip.getNode()).addChild( lSoute );
				lShip.setBoundingSphere( pSz*1.5f ); // A MODIFIER 

							
				return lShip;
		}
		//----------------------------------------------
		static public Ship MakeShip4(  EnumFaction pFaction, float pSz, int pLevel ) {

				Weapon.WeaponType lWeapon1 = Weapon.WeaponType.WeaponSmallPlasma;
				Weapon.WeaponType lWeapon2 = Weapon.WeaponType.WeaponSmallRocket;


				if( pLevel== 1 ) {
						lWeapon1 = Weapon.WeaponType.WeaponPlasma2;
						lWeapon2 = Weapon.WeaponType.WeaponRocket;	
				} else	if( pLevel ==  2 ){
						lWeapon1 = Weapon.WeaponType.WeaponPlasmaRed;
						lWeapon2 = Weapon.WeaponType.WeaponBigRocket;
				} else	if( pLevel >=  3 ){
						lWeapon1 = Weapon.WeaponType.WeaponPlasmaGreen;
						lWeapon2 = Weapon.WeaponType.WeaponSmallMissile;
				}


				Ship lShip = MakeShip2( pFaction, pSz, pLevel );
				lShip.setSpriteProperties( 3f, 3f, 0f );

				float lLg = pSz*4;
				NodeBase lGrid1 = MakeGrid( pSz*0.4f,  lLg );
								lGrid1.getTransf().rotate( new Float3( 90, 0, 0 ));
				lGrid1.getTransf().move( new Float3( 0, lLg*0.5, 0  ));
				((Node3d)lShip.getNode()).addChild( lGrid1 );


			
				
				Turret lTurret1 = MakeTurret( pSz*0.7f );
				RectFloat3 lBox = new RectFloat3( -100, -30, -100, 100, 30,  100 );
				lTurret1.set( lShip, 0.5f, Weapon.FireMode.FireFront, lWeapon2,  lBox  );				
				//	lTurret1.getNewTransf().move( new Float3( 0,  0,  lLg*3.0 ));
				lTurret1.getTransf().move( new Float3( 0, -lLg*0.65f, 0));
				((Node3d)lShip.getNode()).addChild( lTurret1 );

				Turret lTurret2 = MakeTurret( pSz*0.7f );
				lTurret2.set( lShip, 0.5f, Weapon.FireMode.FireFront, lWeapon2,  lBox  );				
				//	lTurret1.getNewTransf().move( new Float3( 0,  0,  lLg*3.0 ));
				lTurret2.getTransf().move( new Float3( 0, lLg*0.65f, 0));
				((Node3d)lShip.getNode()).addChild( lTurret2 );

				lShip.setBoundingSphere( pSz*3 ); // A MODIFIER 

				return lShip;

		}
}
//*************************************************

							
