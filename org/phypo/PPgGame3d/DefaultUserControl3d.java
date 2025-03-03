package org.phypo.PPgGame3d;



import java.io.IOException;
import javax.swing.SwingUtilities;
import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.util .*;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Font;
import java.text.DecimalFormat;

import com.jogamp.newt.event.MouseAdapter;

import com.jogamp.nativewindow.util.Point;


//*************************************************

public class DefaultUserControl3d extends UserControl3d{

		static public String sTrace;
		static public String sComment;

		protected Float3 cEye = new Float3( 0, 1f, 0 );
		protected Float3 cCenter = new Float3( 0, 0, 0 );
		protected	Float3 cUp = new Float3( 1, 0, 0 );


		protected float cDelta = 0.05f;
		protected float cKamX = 0f;
		protected float cKamY = 0f;
		protected float cKamZ = 0f;

		protected float cRotX =-45f;
		protected float cRotY =-10f;
		protected float cRotZ =0f;

		protected float cScale = 1f;



		public ActorCursor  cActorCursor;

		//------------------------------------------------

		public void initFromKamera( Kamera3d pKam ){

				cKamX = pKam.getTransf().getTranslat().x();
				cKamY = pKam.getTransf().getTranslat().y();
				cKamZ = pKam.getTransf().getTranslat().z();

				cRotX = pKam.getTransf().getRotate().x();
				cRotY = pKam.getTransf().getRotate().y();
				cRotZ = pKam.getTransf().getRotate().z();
				
				cScale = pKam.getTransf().getScale().x();

		
		}
		//------------------------------------------------

		
		protected int   cMouseX;
		protected int   cMouseY;

		static public DefaultUserControl3d sTheDefaultUserControl3d = new DefaultUserControl3d();
		//------------------------------------------------
		protected DefaultUserControl3d( Kamera3d pKam ) {
				initFromKamera( pKam );
		}
		protected DefaultUserControl3d() {
		}
		//------------------------------------------------
		void Trace(){
				sTrace = "X:" + cKamX + " Y:" + cKamY + " Rx:" + cRotX + " Ry:" + cRotY + " Rz:" + cRotZ + " S:" + cScale ;
		}
			//------------------------------------------------
		void setView(){
				World3d.sTheWorld.getCurrentKamera().getTransf().move( new Float3( cKamX, cKamY, 0 ) );				
				World3d.sTheWorld.getCurrentKamera().getTransf().rotate( new Float3( cRotX, cRotY, cRotZ ) );
				World3d.sTheWorld.getCurrentKamera().getTransf().scale( new Float3( cScale, cScale,  cScale) );

				Trace();
		}
		//------------------------------------------------
		@Override public void keyPressed(KeyEvent e) {
				
				int lKeyCode    = e.getKeyCode();
				char lKey       = e.getKeyChar();
				boolean lAction = e.isActionKey();

			System.out.println( "DefaultUserControl3d.key:" + lKeyCode + " " + lKey + " "+ lAction );

				if ( lKey == 'Q' || lKey == 'q'  ) {
							System.exit(0);
				} else    	if ( lKey == 'P' || lKey == 'p') {
						World3d.sTheWorld.FlipPause();
				}
				

				if (lKeyCode == KeyEvent.VK_LEFT )
						cRotY -= 0.5f;
				
				if (lKeyCode == KeyEvent.VK_RIGHT)
						cRotY += 0.5f;
								
				if (lKeyCode == KeyEvent.VK_UP)
						cRotX -= 0.5f;
				
				if (lKeyCode == KeyEvent.VK_DOWN)
						cRotX += 0.5f;

				if (lKeyCode == KeyEvent.VK_PAGE_DOWN)
						cRotZ -= 0.5;

				if (lKeyCode == KeyEvent.VK_PAGE_UP)
						cRotZ += 0.5;

				if (lKeyCode == KeyEvent.VK_SUBTRACT
				    || lKeyCode == KeyEvent.VK_DIVIDE){

				    float dW = -1;
				    cScale += 50.0f*dW*cDelta*0.01*cScale;
				    if( cScale > 100 )
					cScale = 100;
				    else if( cScale < 0.001f )
					cScale =  0.001f;
				}  
				if (lKeyCode == KeyEvent.VK_ADD
				    || lKeyCode == KeyEvent.VK_MULTIPLY){
				    float dW = 1;
				    cScale += 50.0f*dW*cDelta*0.01*cScale;
				    if( cScale > 100 )
					cScale = 100;
				    else if( cScale < 0.001f )
					cScale =  0.001f;
				}

					if ( lKey == 'I' || lKey == 'i' ) {
							World3d.sTheWorld.getCurrentKamera().resetOrtho(null);
							//				World3d.sTheWorld.getCurrentKamera().setOrtho( null, -0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f);
							cRotX =-90f;
							cRotY =-0f;
							cRotZ = 0f;
							cKamX = cKamY = 0f;
							cScale = 2f;
					}
					if (lKey == 'O' || lKey == 'o' ) {
							World3d.sTheWorld.getCurrentKamera().setOrtho( null, -5f, 5f, -5f, 5f, -5f, 5f);
							World3d.sTheWorld.getCurrentKamera().getTransf().scale( new Float3( 1f, 1f, 1f ) );
							cRotX =-45f;
							cRotY =-10f;
							cRotZ =0f;
							cKamX = cKamY = 0f;
							cScale = 0.1f;
					}

					if (lKey == 'U' || lKey == 'u') {
							if( World3d.sTheWorld.getCurrentKamera().isOrtho() ) {
									System.out.println( " ********************** PERPECTIVE " );
									World3d.sTheWorld.getCurrentKamera().setPerspective( null, cEye, cCenter, cUp );
									cRotX =-45f;
									cRotY =-10f;
									cRotZ =0f;
									cKamX = cKamY = 0f;

									//									World3d.sTheWorld.getCurrentKamera().getTransf().move( new Float3( 0, 0, 0 ) );
									//									World3d.sTheWorld.getCurrentKamera().getTransf().rotate( new Float3( 0, 0, 0 ) );
							}	
				}			

				/*				cTextRender.beginRendering( Engine.sTheGlDrawable.getWidth(), Engine.sTheGlDrawable.getHeight());
				cTextRender.draw( "Demo de texte ", 1, 30);
				cTextRender .endRendering();				
				*/
           
				// boolean 	isModifierKey()
				
				// static boolean 	isModifierKey(int keyCode)
				setView();
		}
		//------------------------------------------------
		protected Point cLastMousePos  = new Point();
		protected Point cPressMousePos = new Point();

		
		@Override public void 	mouseDragged(MouseEvent e) {

				//				System.out.println( "Mouse Dragged : " + e.getButton()  );
				
				if( e.getButton() == 1 ){
				
						int lDx = cLastMousePos.getX()-e.getX();
						int lDy = cLastMousePos.getY()-e.getY();

						cLastMousePos.setX(e.getX());
						cLastMousePos.setY(e.getY());
						
						//						System.out.println( "Mouse : " + lDx  + ","+ lDy);
						
						
						float lfDx = lDx* cDelta;
						float lfDy = -lDy* cDelta;
						
						if( lfDx > 0.1f )
								lfDx = 0.1f;
						else 	if( lfDx < -0.1f )
								lfDx = -0.1f;
						
						if( lfDy > 0.1f )
								lfDy = 0.1f;						
						else 	if( lfDy < -0.1f )
								lfDy = -0.1f;
						
						cKamX += lfDx ;
						cKamY += lfDy ;
			}	
				setView();
		}		
		//------------------------------------------------
		@Override public void 	mouseMoved(MouseEvent e) {
								
				
				// stock mouse position in the gamer's sprite
				if( cActorCursor != null ){
						cActorCursor.cLastMousePos.setX( e.getX() );
						cActorCursor.cLastMousePos.setY( e.getY() );
				}

				//	cActorGamer.setAccelaration( new Float3( lDx*20, lDy*20, 0 ));
				//	setAccelaration
		}		
	//------------------------------------------------

		ActorMobil cSelect; 


		@Override public void 	mousePressed(MouseEvent e) {
				
				
				cLastMousePos.setX( e.getX());
				cLastMousePos.setY(e.getY());
				cPressMousePos.setX( e.getX());
				cPressMousePos.setY(e.getY());

				//				System.out.println("Button=" +  e.getButton() );
				
				if( cActorCursor == null )
						return;


				if( e.getButton() == 3 ){
					

						//						System.out.println( "find x:" + 	cActorCursor.cLocation.x() + " y:" +  cActorCursor.cLocation.y() +" z:" + cActorCursor.cLocation.z());

								/*
						Kamera3d lKam = World3d.sTheWorld.getCurrentKamera();
						lKam.renderGL( Engine.sTheGl );
						
						Double3 lPosSprite = lKam.project( Engine.sTheGl, cActorCursor.cLocation );	
						
						Float3 lMouse = new Float3( e.getX(), e.getY(),  lPosSprite.z() );

						Double3 lMouseObj = lKam.unproject(	Engine.sTheGl,  lMouse );	
						System.out.println( "find:" + lMouseObj + " x:"+ lMouseObj.x() + " y:" + lMouseObj.y() );
								*/
						ActorMobil lSprite = (ActorMobil)World3d.sTheWorld.findActorLocation( cActorCursor );

					 if( lSprite != null && lSprite.cComment != null ){
							 sComment = lSprite.cComment;

							 if( cSelect == null ){
									 ModelQuadric lQuad = new ModelQuadric();
									 //lQuad.setSphere( lSprite.cBoundingSphere*2, 5, 5);

									 lQuad.setSphere( 1.5f, 5, 5);
									 Aspect3d lAspect = new Aspect3d( new Color4( 1f, 1f, 0f, 0.3f ), Aspect3d.DrawStyle.FILL );
									 Transform3d lTransf = new Transform3d( null, new Float3(), null );
									 Leaf3d lLeaf = new Leaf3d( lQuad , lTransf, lAspect );
									 cSelect = new ActorMobil( lSprite.cLocation, EnumFaction.Neutral, lLeaf );
									 cSelect.cSpeed.set( lSprite.cSpeed );
									 cSelect.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.BounceOnLimit ));
									 World3d.sTheWorld.addActor( cSelect );
							 }
							 else {
									 
									 cSelect.cLocation.set( lSprite.cLocation );
									 cSelect.cSpeed.set( lSprite.cSpeed );
							 }
					 }					 
				}
				
		}
		//------------------------------------------------
		@Override public void 	mouseWheelMoved( MouseEvent e)  {

		    //  int dW = e.getWheelRotation();
		    
				//////			System.out.println( "Wheel:" + dW );
					    
		    float lRot[] = e.getRotation();
		    float dW = lRot[0] + lRot[1] + lRot[2];

		    cScale += 50.0f*dW*cDelta*0.01*cScale;
		    
		    if( cScale > 100 )
			cScale = 100;
		    else if( cScale < 0.001f )
			cScale =  0.001f;
		    
		    
		    setView();

		}
		//------------------------------------------------
		public ActorCursor makeActorCursor(  boolean lCentralPoint, float lSize, int lDepth, Primitiv3d.SubNormalizeType  pNormalize ){

				Primitiv3d lPrim = 	new Primitiv3d();
				//				Primitiv3d.SubParamObject3d lParam01 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize,       lCentralPoint, pNormalize );
				//				Primitiv3d.SubParamObject3d lParam02 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize*1.01f, lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam01 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize,       lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam02 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize*1.01f, lCentralPoint, pNormalize );

				lParam01.cHoleFacet = 4;
				lParam01.cHoleDepth = 2;

				lParam01.cDepthGrowFactor = 0.5f;
				lParam02.cDepthGrowFactor = 0.5f;

				Object3d  lPrimOctahedron01 =  ((Primitiv3d.SubParamObject3d)GlutPrimitiv.Octahedron( lParam01)).getObject3d();
				Object3d  lPrimOctahedron02 =  ((Primitiv3d.SubParamObject3d)GlutPrimitiv.Octahedron( lParam02)).getObject3d();



				Aspect3d lAspect1 = new Aspect3d( new Color4(1f,0f,0f,0.3f),
																 Aspect3d.DrawStyle.FILL );//Aspect3d.DrawStyle.FILL);
				Aspect3d lAspect2 = new Aspect3d( new Color4(0,0,0,1f), Aspect3d.DrawStyle.LINE );
				
				ModelBase pO1 = new CompilObj(lPrimOctahedron01 );
				ModelBase pO2 = new CompilObj(lPrimOctahedron02 );

				Transform3d lTransf = new Transform3d( null, new Float3(), null );
				Node3d lNode  = new Node3d( );
				Leaf3d lLeaf1 = new Leaf3d( pO1, lTransf, lAspect1 );
				lNode.addChild( lLeaf1 );
				Leaf3d lLeaf2 = new Leaf3d( pO2, lTransf, lAspect2 );								
				///////////		lNode.addChild( lLeaf2 );
				cActorCursor  = new ActorCursor( new Float3(),  EnumFaction.Blue,	lNode);

				//		cActorGamer.setMaxSpeed( 10 );
				cActorCursor.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.MoebiusWorld ));
				cActorCursor.setSpin( Float3.GetRandom(  World3d.sGlobalRandom, 50f) );

				World3d.sTheWorld.addActor( cActorCursor  );

				return cActorCursor;
		}}
//*************************************************
