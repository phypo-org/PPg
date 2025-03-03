package com.phipo.PPg.PPgG3d;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
 
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


import org.lwjgl.util.glu.*;

import java.nio.*;
import java.util.*;

import com.phipo.PPg.PPgUtils.*;


import com.phipo.PPg.PPgMath.*;

//*************************************************

public class PPgG3d extends World{

		PPgRandom cRand = new PPgRandom(666);

		static FloatBuffer sFBuf = ByteBuffer.allocateDirect(256).asFloatBuffer();

		static float sGreyAmbient[]  =   { 0.5f,  0.5f, 0.5f, 1.0f };
		static float sWhiteDiffuse[]  =  { 1.0f,  1.0f, 1.0f, 1.0f };

		static float sPosition[] =       { 1.0f, 1.0f, 0.0f, 0.0f }; //  a l'inini
		protected ArrayList<ActorBase>   cDemoActor = new ArrayList();

		//------------------------------------------------
		public PPgG3d( int pW, int pH, boolean pFull){
				super( new RectFloat3( new Float3( -5, -4, -2), 
															 new Float3( 10, 8, 4 )),
							 new Engine( pW, pH, pFull) );
		}
		//------------------------------------------------
		void createSprite( ModelBase pO1, ModelBase pO2 ){
		
				Aspect3d lAspect1;
				if( cRand.nextInt( 100 ) > 0 || pO2 == null)
						lAspect1 = new Aspect3d( Float4.GetRandomPositif( cRand, 0.1f, 0.6f ),
																		 Aspect3d.DrawStyle.FILL );//Aspect3d.DrawStyle.FILL);
				else
						lAspect1 = new Aspect3d( new Float4( cRand.nextFloatPositif(1),cRand.nextFloatPositif(1),cRand.nextFloatPositif(1),1)
																		 , Aspect3d.DrawStyle.FILL );//Aspect3d.DrawStyle.FILL);

				Aspect3d lAspect2 = new Aspect3d( new Float4( 0f, 0.6f, 0.8f, 0.5f ), Aspect3d.DrawStyle.LINE );//Aspect3d.DrawStyle.FILL);

				
				pO1 = new CompilObj( pO1 , true, lAspect1 );
				
				if( pO2 != null )
						pO2 = new CompilObj( pO2 , true, lAspect2 );
				

		
				Transform3d lTransf = new Transform3d( null, Float3.GetRandom( cRand, 360f), null );
				
				Node3d lNode = new Node3d( );
				
				Leaf3d lLeaf1 = new Leaf3d( pO1, lTransf, lAspect1 );
				lNode.addChild( lLeaf1 );
				if( pO2 != null ){
						Leaf3d lLeaf2 = new Leaf3d( pO2, lTransf, lAspect2 );								
						lNode.addChild( lLeaf2 );
				}

				ActorMobil lMobil;
				World.sTheWorld.addActor( (lMobil = new ActorMobil( Float3.GetRandom( cRand, 2f),  EnumFaction.Neutral,	lNode) ));

				cDemoActor.add( 	lMobil  );
				
				lMobil.setSpeed( Float3.GetRandom( cRand, 0.6f) );
				
				lMobil.setSpin( Float3.GetRandom( cRand, 50f) );

				//			lMobil.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.LimitToWorld ));
				lMobil.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.BounceOnLimit ));
		}
		//------------------------------------------------
		public void createDemoSprites( int pNb, boolean lCentralPoint, float lSize, int lDepth, Primitiv3d.SubNormalizeType  pNormalize ){

				Primitiv3d lPrim = 	new Primitiv3d();
				Primitiv3d.SubParamObject3d lParam01 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize,       lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam02 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize*1.01f, lCentralPoint, pNormalize );

				lParam01.cDepthGrowFactor = 0.5f;
				lParam02.cDepthGrowFactor = 0.5f;


				Object3d  lCube1 =  Primitiv3d.Parallelepiped( lSize, lSize, lSize, lParam01).getObject3d();
				Object3d  lCube2 =  Primitiv3d.Parallelepiped( lSize*1.01f, lSize*1.01f, lSize*1.01f, lParam01).getObject3d();

				Object3d  lPyr1 =  Primitiv3d.Pyramid4( 0,0,0, lSize, lSize,  lParam01).getObject3d();
				Object3d  lPyr2 =  Primitiv3d.Pyramid4( 0,0,0, lSize*1.01f,  lSize*1.01f, lParam01).getObject3d();

				
				/*			Primitiv3d.SubParamObject3d lParam11 = lPrim.GetSubParamObject3d ( 1+lDepth, lSize,        lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam12 = lPrim.GetSubParamObject3d ( 1+lDepth, lSize*1.003f, lCentralPoint, pNormalize );

  			Primitiv3d.SubParamObject3d lParam21 = lPrim.GetSubParamObject3d ( 2+lDepth, lSize,        lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam22 = lPrim.GetSubParamObject3d ( 2+lDepth, lSize*1.003f, lCentralPoint, pNormalize );
				*/

				Object3d  lPrimTetrahedron01 =  GlutPrimitiv.Tetrahedron( lParam01).getObject3d();
				Object3d  lPrimTetrahedron02 =  GlutPrimitiv.Tetrahedron( lParam02).getObject3d();
				/*
				Object3d  lPrimTetrahedron11 =  GlutPrimitiv.Tetrahedron( lParam11).getObject3d();
				Object3d  lPrimTetrahedron12 =  GlutPrimitiv.Tetrahedron( lParam12).getObject3d();
				
				Object3d  lPrimTetrahedron21 =  GlutPrimitiv.Tetrahedron( lParam21).getObject3d();
				Object3d  lPrimTetrahedron22 =  GlutPrimitiv.Tetrahedron( lParam22).getObject3d();
				*/


				Object3d  lPrimOctahedron01 =  GlutPrimitiv.Octahedron( lParam01).getObject3d();
				Object3d  lPrimOctahedron02 =  GlutPrimitiv.Octahedron( lParam02).getObject3d();
				/*
				Object3d  lPrimOctahedron11 =  GlutPrimitiv.Octahedron( lParam11).getObject3d();
				Object3d  lPrimOctahedron12 =  GlutPrimitiv.Octahedron( lParam12).getObject3d();
		
				Object3d  lPrimOctahedron21 =  GlutPrimitiv.Octahedron( lParam21).getObject3d();
				Object3d  lPrimOctahedron22 =  GlutPrimitiv.Octahedron( lParam22).getObject3d();
				*/


				Object3d lPrimIcosahedron01 = GlutPrimitiv.Icosahedron( lParam01).getObject3d();
				Object3d lPrimIcosahedron02 = GlutPrimitiv.Icosahedron( lParam02 ).getObject3d();
				/*
				Object3d lPrimIcosahedron11 = GlutPrimitiv.Icosahedron( lParam11).getObject3d();
				Object3d lPrimIcosahedron12 = GlutPrimitiv.Icosahedron( lParam12).getObject3d();


				Object3d lPrimIcosahedron21 = GlutPrimitiv.Icosahedron( lParam21).getObject3d();
				Object3d lPrimIcosahedron22 = GlutPrimitiv.Icosahedron( lParam22).getObject3d();
				*/


				for( int i= 0; i<  pNb ; i++) {
						
						if( lSize < 0 || ( pNormalize == Primitiv3d.SubNormalizeType.NORMALIZE_NONE  && lDepth > 2) ){

								createSprite( lPrimTetrahedron01,  null);
								//		createSprite( lPrimTetrahedron11,  null);
								//			createSprite( lPrimTetrahedron21,  null);
								
								createSprite( lPrimOctahedron01,  null);
								//			createSprite( lPrimOctahedron11,  null);
								//		createSprite( lPrimOctahedron21,  null);
								
								
								createSprite( lPrimIcosahedron01,  null);
								//		createSprite( lPrimIcosahedron11,  null);
								//		createSprite( lPrimIcosahedron21,  null);
		
								createSprite( lCube1, lCube2 );
								createSprite( lPyr1, lPyr2 );
				}else{
								createSprite( lPrimTetrahedron01,  lPrimTetrahedron02);
								//		createSprite( lPrimTetrahedron11,  lPrimTetrahedron12);
								//		createSprite( lPrimTetrahedron21,  lPrimTetrahedron22);
								
								createSprite( lPrimOctahedron01,  lPrimOctahedron02);
								//		createSprite( lPrimOctahedron11,  lPrimOctahedron12);
								//		createSprite( lPrimOctahedron21,  lPrimOctahedron22);
								
								
								createSprite( lPrimIcosahedron01, lPrimIcosahedron02 );
								//			createSprite( lPrimIcosahedron11, lPrimIcosahedron12 );
								//			createSprite( lPrimIcosahedron21, lPrimIcosahedron22 );								

								createSprite( lCube1, lCube2 );
								createSprite( lPyr1, lPyr2 );
								}

			}
		}
		//------------------------------------------------
		public void	Demo( Primitiv3d.SubNormalizeType  pNormalize ) {
				// formes bien reguliere
				createDemoSprites( 1, false, 0.3f, 1, pNormalize );
				createDemoSprites( 1, false, 0.5f, 2, pNormalize );
				
				// formes regulieres mais originales avec decoupes
				
				createDemoSprites( 1, true, 0.4f, 0, pNormalize  ); 
				createDemoSprites( 1, true, 0.4f, 1, pNormalize ); 
				createDemoSprites( 1, true, 0.4f, 2, pNormalize ); 
				
				
				// formes cristalines ( doivent etre transparentes ! )
				createDemoSprites( 1, false, -0.3f, 0, pNormalize );
				createDemoSprites( 1, false, -0.3f, 1, pNormalize );
				createDemoSprites( 1, false, -0.3f, 2, pNormalize );
				
				// formes cristalines aussi mais plus exotiques
				createDemoSprites( 1, true, -0.3f, 0, pNormalize );				
				createDemoSprites( 1, true, -0.3f, 1, pNormalize );
				createDemoSprites( 1, true, -0.3f, 2, pNormalize );	
		}
		//------------------------------------------------
		public void Demo( int i ){

				switch( i ){

				case 1: Demo( Primitiv3d.SubNormalizeType.NORMALIZE ); break;
				case 2: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_ONLY_SUB ); break;
				case 3: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_HALF_INIT ); break;
				case 4: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_DEC_INIT ); break;
				case 5: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_INC_INIT ); break;
				case 6: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_MUL_INIT ); break;
				case 7: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_DEC_SUB ); break;
				case 8: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_INC_SUB ); break;
				case 9: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_MUL_SUB ); break;
				case 10: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_ONLY_INIT ); break;
				case 11: Demo( Primitiv3d.SubNormalizeType.NORMALIZE_NONE ); break;
				}
		}
		//------------------------------------------------
		@Override
		public void callEngineInit(){

				System.out.println( "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT PPgG3d.callEngineInit TTTTTTTTTTTTTTTTT" );
				//  glEnable( GL_TEXTURE_2D );
				// FAIRE DES LUMIERE QUI DERIVE DE MODELBASE !!!!


				GL11.glClearColor( 0.20f, 0.20f, 0.2f, 0.0f );

	
				GL11.glEnable( GL11.GL_LIGHTING);
				GL11.glEnable( GL11.GL_LIGHT0);
				GL11.glEnable( GL11.GL_LIGHT1);

				//=============== Add camera =============
				{
						cKamera=  new Kamera3d( null, new Transform3d( null, null, null),
																		-0.5, 0.5, -0.5, 0.5, -0.5, 0.5 );
						//				-800, 800, -600, 600, -1, -1 );
						//				-800, 800, -600, 600, -500, 500 );
						
						
						//				cKamera=  new Kamera3d( null, new Transform3d( null, null, null),
						//																-800, 800, -600, 600, -1, -1 );
						
						cKamera.getTransf().rotate( new Float3( -45f, -10f, 0f ) );
						World.sTheWorld.setKamera(  new ActorLocation( 0.5f, 0.5f, -0.5f, EnumFaction.Neutral, cKamera ));
						
						//		cKam.getTransf().rotate( new Float3( 30f, 30f, 0f ) );
						//		cKam.getTransf().scale( new Float3( 0.1f, 0.1f, 0.1f ) );
						//
						//cKam.getTransf().move( new Float3( 0f, 0f, -10f ) );
				}
						
				//=============== Add camera =============
				//				Mouse.setGrabbed( true );
				
				cMouseX = Mouse.getDX();
				cMouseY = Mouse.getDY();
        Mouse.getDWheel();

				/*
				Aspect3d lAspectGlobal = new Aspect3d( new Float4( 07f, 0.2f, 0.7f, 1f ), Aspect3d.DrawStyle.FILL);
				CompilObj lCompilObj = new CompilObj( Primitiv3d.CreateObjectParallelepiped( 1f, 0.5f, 0.3f ), true, lAspectGlobal );


	
				CompilObj lCompilObj2 = new CompilObj();
				lCompilObj2.freeBeginRecord();


				GL11.glColor3f( 0.0f, 0.0f, 0.9f );

				lCompilObj2.freeEndRecord();
				*/
				
				//=====================================
			

				//	Demo(  Primitiv3d.SubNormalizeType.NORMALIZE );


				//=====================================
				// formes en etoiles (pas de normalisation ! )
				//	Demo( Primitiv3d.SubNormalizeType.NORMALIZE_ONLY_INIT);
				
				//	Demo(Primitiv3d.SubNormalizeType.NORMALIZE_MUL_SUB);


				//=====================================
				
				
				//				Leaf3d lLeaf4 = new Leaf3d( lQuadric, lTransf4, lAspect );
				
				
				/* Bon pour la perpective
					 cKam =  new Kamera3d(null, new Transform3d( null, null, null), 
					 //														45f, 1, 5f, 10000f);
					 45f, 1, 1f, 10000f);
					 
					 cKam.getTransf().scale( new Float3( 0.1f, 0.1f, 0.1f ) );
				*/
		}
		//------------------------------------------------
		Float3 cEye = new Float3( 0, 1f, 0 );
		Float3 cCenter = new Float3( 0, 0, 0 );
		Float3 cUp = new Float3( 1, 0, 0 );

		void  inputPerpective(){
				
				float lDelta = 0.01f;
				

				
				float fraction = 0.1f;
				float angle;


				if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) 
						cRotZ -= 0.5;

				if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) 
						cRotZ += 0.5;

				//	cKamera.getTransf().move( new Float3( cKamX, cKamY, 0 ) );

		}
		//------------------------------------------------

		float cDelta = 0.05f;
		float cKamX = 0f;
		float cKamY = 0f;

		float cKamZ = 0f;

		float cRotX =-45f;
		float cRotY =-10f;
		float cRotZ =0f;

		float cScale = 1f;

		
		int   cMouseX;
		int   cMouseY;

		void  inputOrtho(){

				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) 
						cRotY -= 0.5f;
				
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) 
						cRotY += 0.5f;
								
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) 
						cRotX -= 0.5f;
				
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
						cRotX += 0.5f;


				if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) 
						cRotZ -= 0.5;

				if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) 
						cRotZ += 0.5;


				if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
						cKamX =0f;
						cKamY = 0f;

						cRotX =-45f;
						cRotY =-10f;
						cRotZ =0f;
						cScale = 1f;
				}			
	}
	//------------------------------------------------
		void inputDemo(){

				if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
						Demo( 1);
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
						Demo( 2 );
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
						Demo(3 );
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
						Demo( 4);
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
						Demo( 5 );
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
						Demo( 6 );
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
						Demo( 7 );
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
						Demo( 8 );
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
						Demo( 9);
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
						Demo( 10 );
				} else 
			if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
						Demo( 11);
				} 

			if (Keyboard.isKeyDown(Keyboard.KEY_0)) {

					for( ActorBase lActor: cDemoActor )
							lActor.setDeleted();
			} 
		}
	//------------------------------------------------
		void input(){
				
			while (Mouse.next()) {		


					int mouseX = Mouse.getEventX();
					int mouseY = Mouse.getEventY();
					int mouseWheel = Mouse.getEventDWheel() ; 
					int button = Mouse.getEventButton();
					boolean buttonDown = Mouse.getEventButtonState();

					System.out.println( "MouseEvent " 
															+ " x:" + mouseX
															+ " y:" + mouseY
															+ " wheel:" + mouseWheel
															+ " button:" + button
															+ " down:" + buttonDown );
															
									// UTILSER LES VALEURS LU
										

					if( Mouse.isButtonDown(0) ){

							float lDx = Mouse.getDX()* cDelta;
							float lDy = Mouse.getDY()* cDelta;
							
							if( lDx > 0.1f )
									lDx = 0.1f;
							else 	if( lDx < -0.1f )
									lDx = -0.1f;
							
							if( lDy > 0.1f )
									lDy = 0.1f;

							else 	if( lDy < -0.1f )
									lDy = -0.1f;
							
							cKamX += lDx ;
							cKamY += lDy ;
							cKamera.getTransf().move( new Float3( cKamX, cKamY, 0 ) );				
							cKamera.getTransf().rotate( new Float3( cRotX, cRotY, cRotZ ) );
					}
					
				
					float dW = Mouse.getDWheel();
					//			System.out.println( "MMMMMMMMMMMM mouse dx : " + dW );
					if( dW != 0 ){
							cScale += dW*cDelta*0.01*cScale;
							if( cScale > 100 )
									cScale = 100;
							else if( cScale < 0.001f )
									cScale =  0.001f;
							
							cKamera.getTransf().scale( new Float3( cScale, cScale,  cScale) );
					}
			}
				


			//=====================================
			while( Keyboard.next() ){

					System.out.println( "KeyBoard Event " 
															+" key:" +Keyboard.getEventKey() 
															+" char:" +Keyboard.getEventCharacter()
															+" state:"+Keyboard.getEventKeyState() );
			
				// UTILSER LES VALEURS LUES

		
					if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
							System.exit(0);
					}
				
					if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
							World.sTheWorld.FlipPause();
					}

					if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
							cKamera.resetOrtho();
							cKamera.getTransf().scale( new Float3( 1f, 1f, 1f ) );
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
							cKamera.setOrtho(-0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f);
							cKamera.getTransf().scale( new Float3( 1f, 1f, 1f ) );
					}
		
					if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
							if( cKamera.isOrtho() ) {
									System.out.println( " ********************** PERPECTIVE " );
									cKamera.setPerspective( cEye, cCenter, cUp );
									cKamera.getTransf().move( new Float3( 0, 0, 0 ) );
									cKamera.getTransf().rotate( new Float3( 0, 0, 0 ) );
							}	
					}
								
					inputDemo();
								
					if( cKamera.isOrtho())
							inputOrtho();
					else
							inputPerpective();
			}

			cKamera.getTransf().move( new Float3( cKamX, cKamY, 0 ) );				
			cKamera.getTransf().scale( new Float3( cScale, cScale,  cScale) );
			cKamera.getTransf().rotate( new Float3( cRotX, cRotY, cRotZ ) );
		}
		//------------------------------------------------
		@Override
		public void worldCallInputTurn( ){
				System.out.println( "input " );
				input();
		}
		//------------------------------------------------

		@Override
		public void worldCallBeginTurn( double pTimeDiff){


				//				System.out.println( "QuadExample callEngineUpdate pTimeDiff:" + pTimeDiff );
		}
		//------------------------------------------------
		@Override
		public void callEngineTurnBegin(){		

				//				System.out.println( "PPgG3d callEngineRenderGL.callEngineRenderGL" );
				
				ByteBuffer lTemp = ByteBuffer.allocateDirect(16);
				lTemp.order(ByteOrder.nativeOrder());

				GL11.glLight( GL11.GL_LIGHT0, GL11.GL_AMBIENT, 	(FloatBuffer)lTemp.asFloatBuffer().put(sGreyAmbient).flip());
				GL11.glLight( GL11.GL_LIGHT0, GL11.GL_DIFFUSE, (FloatBuffer)lTemp.asFloatBuffer().put(sWhiteDiffuse).flip());								
				GL11.glLight( GL11.GL_LIGHT0, GL11.GL_POSITION, (FloatBuffer)lTemp.asFloatBuffer().put(sPosition).flip());

				//				sFBuf.put( sModelAmbient, 0, 4 );
				//	GL11.glLightModel( GL11.GL_LIGHT_MODEL_AMBIENT, sFBuf);

				
         float yellowAmbientDiffuse[] = {1.0f, 1.0f, 0f, 1.0f};	
				 float sPosition2[] = {-2.0f, 2.0f, -5.0f, 0.0f}; // a l'inifni

				 GL11.glLight( GL11.GL_LIGHT1, GL11.GL_AMBIENT, (FloatBuffer)lTemp.asFloatBuffer().put(yellowAmbientDiffuse).flip());
				 GL11.glLight( GL11.GL_LIGHT1, GL11.GL_DIFFUSE,  (FloatBuffer)lTemp.asFloatBuffer().put(yellowAmbientDiffuse).flip());
				 GL11.glLight( GL11.GL_LIGHT1, GL11.GL_POSITION,  (FloatBuffer)lTemp.asFloatBuffer().put(sPosition2).flip());


				Primitiv3d.Green();

				Primitiv3d.DrawGrid( getWidth()/2, getDepth()/2, 3 );
				
				Primitiv3d.DrawRepere( 0.5f, 1 );	

				
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glColor3f( 0.0f, 0.0f, 0.9f );

				Primitiv3d lPrim = 	new Primitiv3d();
				Primitiv3d.SubParamDrawing lParam01 = lPrim.GetSubParamDrawing( 0, 1.0f, false, Primitiv3d.SubNormalizeType.NORMALIZE_NONE, GLU.GLU_SILHOUETTE );


				Primitiv3d.Parallelepiped( getWidth(), getHeight(), getDepth(), lParam01 );
				GL11.glEnable(GL11.GL_LIGHTING);


				Keyboard.enableRepeatEvents(true);

		}
		//------------------------------------------------
    public static void main(String[] args) {
 
				int lW      = PPgParam.GetInteger( args, "-w", 1280 );
				int lH      = PPgParam.GetInteger( args, "-h", 1024);
				
				String lStrFullScreen = PPgParam.GetString( args, "-S", null );
				String lSizeStr = lStrFullScreen;

				String lStrWindown = PPgParam.GetString( args, "-W", null );
				if( lSizeStr == null )
						lSizeStr = lStrWindown;

				if( lSizeStr != null ){

						if( lSizeStr.compareTo( "640x480" ) ==0 || lSizeStr.compareTo("1") ==0 ){
								lW = 640;
								lH = 480;
						}	else	if( lSizeStr.compareTo( "800x600") ==0 || lSizeStr.compareTo("2")==0 ){
								lW = 800;
								lH = 600;
						}	else	if( lSizeStr.compareTo( "1280x1024") ==0 || lSizeStr.compareTo("3")==0 ){
								lW = 1280;
								lH = 1024;
						}	else	if( lSizeStr.compareTo( "1920x1080") ==0 || lSizeStr.compareTo("4")==0 ){
								lW = 1920;
								lH = 1080;
						}	else	if( lSizeStr.compareTo( "1920x1200") ==0 || lSizeStr.compareTo("(5")==0 ){
								lW = 1920;
								lH = 1200;
						}
				}
				
				PPgG3d lPPgG3d = new PPgG3d( lW, lH,  lStrFullScreen != null );
        lPPgG3d.run();
    }
}

//*************************************************
 