package org.phypo.PPg.PPgJ3d;



import com.jogamp.opengl.*;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.newt.event.*;
import com.jogamp.opengl.util.awt.TextRenderer;

import com.jogamp.opengl.glu.gl2.*;

import com.jogamp.nativewindow.util.Point;

import java.nio.*;
import java.util.*;
import java.awt.Font;
import java.text.DecimalFormat;

import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgMath.*;





//*************************************************

public class PPgJ3d  extends World3d {
														 //	implements KeyListener, MouseListener{ 
		
		PPgRandom cRand = new PPgRandom(666);

		static FloatBuffer sFBuf = ByteBuffer.allocateDirect(256).asFloatBuffer();

		static float sGreyAmbient[]  =   { 0.5f,  0.5f, 0.5f, 1.0f };
		static float sWhiteDiffuse[]  =  { 1.0f,  1.0f, 1.0f, 1.0f };

		static float sPosition[] =       { 1.0f, 1.0f, 0.0f, 0.0f }; //  a l'infini
		protected ArrayList<ActorBase>   cDemoActor = new ArrayList();

		boolean cFlagDemo2 = false;

		static boolean sFlagXY = false;
		static boolean sViewGrid3d = true;

		static boolean sViewRepere = true;
		static float sGenSize=1f;


		TextRenderer cTextRender;
		TextRenderer cTextRender2;
	//------------------------------------------------
		void setViewGrid(){
				sViewGrid3d = ! sViewGrid3d;
		}
	//------------------------------------------------
		void setViewRepere(){
				sViewRepere = ! sViewRepere;
		}
	//------------------------------------------------
		public  PPgJ3d( DimFloat3 pWorldBox, Engine pEngine, boolean pSmooth ){
				super( pWorldBox, pEngine );

				
				
				//				getGLWindow().addKeyListener( this );
							//				getGLWindow().addMouseListener( this );


		}
		//------------------------------------------------
		void createSprite( ModelBase pO1, ModelBase pO2, String pComment ){
		
				Aspect3d lAspect1;
				if( cRand.nextInt( 100 ) > 0 || pO2 == null)
						lAspect1 = new Aspect3d( Color4.GetRandomPositif( cRand, 0.1f, 0.6f ),
																		 Aspect3d.DrawStyle.FILL );//Aspect3d.DrawStyle.FILL);
				else
						lAspect1 = new Aspect3d( new Color4( cRand.nextFloatPositif(1),cRand.nextFloatPositif(1),cRand.nextFloatPositif(1),1)
																		 , Aspect3d.DrawStyle.FILL );//Aspect3d.DrawStyle.FILL);

				Aspect3d lAspect2 = new Aspect3d( Color4.GetRandomPositif( cRand, 0.1f, 0.6f ),
																					//	new Color4( 0f, 0.6f, 0.8f, 0.5f ),
																					Aspect3d.DrawStyle.LINE );//Aspect3d.DrawStyle.FILL);

				
				pO1 = new CompilObj( pO1 );
				
				if( pO2 != null )
						pO2 = new CompilObj( pO2 );
			 

		
				Transform3d lTransf = new Transform3d( null, Float3.GetRandom( cRand, 360f), null );
				
				Node3d lNode = new Node3d( );
				
				Leaf3d lLeaf1 = new Leaf3d( pO1, lTransf, lAspect1 );
				lNode.addChild( lLeaf1 );
				if( pO2 != null ){
						Leaf3d lLeaf2 = new Leaf3d( pO2, lTransf, lAspect2 );								
						lNode.addChild( lLeaf2 );
				}

				ActorMobil lMobil;
				if( sFlagXY )
						World3d.sTheWorld.addActor( (lMobil = new ActorMobil( Float3.GetRandomXY( cRand, 10f),  EnumFaction.Neutral,	lNode) ));
				else 
						World3d.sTheWorld.addActor( (lMobil = new ActorMobil( Float3.GetRandom( cRand, 10f),  EnumFaction.Neutral,	lNode) ));

				lMobil.cComment = pComment;

				cDemoActor.add( 	lMobil  );
				
				if( sFlagXY )
						lMobil.setSpeed( Float3.GetRandomXY( cRand, 0.6f) );
				else 
						lMobil.setSpeed( Float3.GetRandom( cRand, 0.6f) );

				lMobil.setSpin( Float3.GetRandom( cRand, 50f) );

				//			lMobil.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.LimitToWorld ));
				lMobil.addBehavior( new BehaviorSimpleMove( BehaviorSimpleMove.MoveType.BounceOnLimit ));
		}
		//------------------------------------------------
		public void createDemoSprites( int pNb, boolean lCentralPoint, float lSize, int lDepth, Primitiv3d.SubNormalizeType  pNormalize ){

				lSize *= sGenSize;
				
				if( pNormalize != Primitiv3d.SubNormalizeType.NORMALIZE_INC_INIT )
						lSize *=2;
				else
						lSize *= 0.7f;

				Primitiv3d lPrim = 	new Primitiv3d();
				Primitiv3d.SubParamObject3d lParam01 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize,       lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam02 = lPrim.GetSubParamObject3d ( 0+lDepth, lSize*1.01f, lCentralPoint, pNormalize );

				lParam01.cHoleFacet = 4;
				lParam01.cHoleDepth = 2;

				lParam01.cDepthGrowFactor = 0.5f;
				lParam02.cDepthGrowFactor = 0.5f;

				String cComment =  " Cp:" + lCentralPoint + " Sz:" +lSize +" Depth:"+ lDepth 	+ " Norm:" + pNormalize ; 

				Object3d  lCube1 =  Primitiv3d.Parallelepiped( lSize, lSize, lSize, lParam01).getObject3d();
				Object3d  lCube2 =  Primitiv3d.Parallelepiped( lSize*1.01f, lSize*1.01f, lSize*1.01f, lParam01).getObject3d();

				Object3d  lPyr1 =  Primitiv3d.Pyramid4( 0,0,0, lSize, lSize,  lParam01).getObject3d();
				Object3d  lPyr2 =  Primitiv3d.Pyramid4( 0,0,0, lSize*1.01f,  lSize*1.01f, lParam01).getObject3d();

				
				Primitiv3d.SubParamObject3d lParam11 = lPrim.GetSubParamObject3d ( 1+lDepth, lSize,        lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam12 = lPrim.GetSubParamObject3d ( 1+lDepth, lSize*1.003f, lCentralPoint, pNormalize );

  			Primitiv3d.SubParamObject3d lParam21 = lPrim.GetSubParamObject3d ( 2+lDepth, lSize,        lCentralPoint, pNormalize );
				Primitiv3d.SubParamObject3d lParam22 = lPrim.GetSubParamObject3d ( 2+lDepth, lSize*1.003f, lCentralPoint, pNormalize );
				

				Object3d  lPrimTetrahedron01 =  GlutPrimitiv.Tetrahedron( lParam01).getObject3d();
				Object3d  lPrimTetrahedron02 =  GlutPrimitiv.Tetrahedron( lParam02).getObject3d();
				
				//			Object3d  lPrimTetrahedron11 =  GlutPrimitiv.Tetrahedron( lParam11).getObject3d();
				//			Object3d  lPrimTetrahedron12 =  GlutPrimitiv.Tetrahedron( lParam12).getObject3d();
				
				//			Object3d  lPrimTetrahedron21 =  GlutPrimitiv.Tetrahedron( lParam21).getObject3d();
				//			Object3d  lPrimTetrahedron22 =  GlutPrimitiv.Tetrahedron( lParam22).getObject3d();
				


				Object3d  lPrimOctahedron01 =  GlutPrimitiv.Octahedron( lParam01).getObject3d();
				Object3d  lPrimOctahedron02 =  GlutPrimitiv.Octahedron( lParam02).getObject3d();

				//			Object3d  lPrimOctahedron11 =  GlutPrimitiv.Octahedron( lParam11).getObject3d();
				//			Object3d  lPrimOctahedron12 =  GlutPrimitiv.Octahedron( lParam12).getObject3d();
		
				//			Object3d  lPrimOctahedron21 =  GlutPrimitiv.Octahedron( lParam21).getObject3d();
				//			Object3d  lPrimOctahedron22 =  GlutPrimitiv.Octahedron( lParam22).getObject3d();
				


				Object3d lPrimIcosahedron01 = GlutPrimitiv.Icosahedron( lParam01).getObject3d();
				Object3d lPrimIcosahedron02 = GlutPrimitiv.Icosahedron( lParam02 ).getObject3d();


				
				//			Object3d lPrimIcosahedron11 = GlutPrimitiv.Icosahedron( lParam11).getObject3d();
				//			Object3d lPrimIcosahedron12 = GlutPrimitiv.Icosahedron( lParam12).getObject3d();

				//			Object3d lPrimIcosahedron21 = GlutPrimitiv.Icosahedron( lParam21).getObject3d();
				//			Object3d lPrimIcosahedron22 = GlutPrimitiv.Icosahedron( lParam22).getObject3d();
				
				Object3d lPrimDodecahedron01 = GlutPrimitiv.Dodecahedron( lParam01).getObject3d();
				Object3d lPrimDodecahedron02 = GlutPrimitiv.Dodecahedron( lParam02 ).getObject3d();

				for( int i= 0; i<  pNb ; i++) {
						
						if( lSize < 0 || ( pNormalize == Primitiv3d.SubNormalizeType.NORMALIZE_NONE  && lDepth > 2) ){

								createSprite( lPrimTetrahedron01,  null, "Tetrahedron" + cComment);
								//						createSprite( lPrimTetrahedron11,  null, "Tetrahedron" + cComment);
								//						createSprite( lPrimTetrahedron21,  null, "Tetrahedron" + cComment);
								
								createSprite( lPrimOctahedron01,  null, "Octahedron" + cComment);
								//						createSprite( lPrimOctahedron11,  null, "Octahedron" + cComment);
								//						createSprite( lPrimOctahedron21,  null, "Octahedron" + cComment);
								
								
								createSprite( lPrimIcosahedron01,  null, "Icosahedron" + cComment);
								//						createSprite( lPrimIcosahedron11,  null, "Icosahedron" + cComment);
								//						createSprite( lPrimIcosahedron21,  null, "Icosahedron" + cComment);
								createSprite( lPrimDodecahedron01,  null, "Dodecahedron" + cComment );
		
								createSprite( lCube1, lCube2,	"Cube" + cComment );
								createSprite( lPyr1, lPyr2,	"Pyramide" + cComment ); 
				}else{
								createSprite( lPrimTetrahedron01,  lPrimTetrahedron02, "Tetrahedron" + cComment);
								//						createSprite( lPrimTetrahedron11,  lPrimTetrahedron12, "Tetrahedron" + cComment);
								//						createSprite( lPrimTetrahedron21,  lPrimTetrahedron22, "Tetrahedron" + cComment);
								
								createSprite( lPrimOctahedron01,  lPrimOctahedron02, "Octahedron" + cComment);
								//						createSprite( lPrimOctahedron11,  lPrimOctahedron12, "Octahedron" + cComment);
								//						createSprite( lPrimOctahedron21,  lPrimOctahedron22, "Octahedron" + cComment);
								
								
								createSprite( lPrimIcosahedron01, lPrimIcosahedron02, "Icosahedron" + cComment );
								//				createSprite( lPrimIcosahedron11, lPrimIcosahedron12, "Icosahedron" + cComment );
								//		createSprite( lPrimIcosahedron21, lPrimIcosahedron22, "Icosahedron" + cComment );								
								createSprite( lPrimDodecahedron01, lPrimDodecahedron02, "Dodecahedron" + cComment   );

								createSprite( lCube1, lCube2,	"Cube" + cComment );
								createSprite( lPyr1, lPyr2,	"Pyramide" + cComment );
				}

			}
		}
		//------------------------------------------------
		public void	DemoEngine( int pNum ) {

				System.out.println( "******* DemoEngine **********" );

				ParticleEngine lFire = ParticleSimple.CreateParticleFactoryFire( new Float3( 0.4f, 0, 0.04f ), 0.04f, 5000, 30 );
				ParticleEngine lSmoke = ParticleSimple.CreateParticleFactorySmoke( new Float3( 0.4f, 0, 0.04f ), 0.04f, 5000, 80 );
				lSmoke.getNewTransf().move( new Float3(0.38f, 5f, 0.04f ));

				Aspect3d lAspect = new Aspect3d( new Color4( 0.47f, 0.64f, 0.8f, 0.5f ), Aspect3d.DrawStyle.LINE );//Aspect3d.DrawStyle.FILL);


				ModelQuadric lQuad = new ModelQuadric();
				lQuad.setCylinder( 1, 1, 1, 10, 10  );
				Node3d lVect = new Node3d();
				lVect.addChild( lFire );
				lVect.addChild( lSmoke);
				
				lVect.addChild( new Leaf3d( lQuad, null, lAspect ));

				ActorMobil lMobil ;
				World3d.sTheWorld.addActor( (lMobil = new ActorMobil( new Float3(0, -4, 0 ),  EnumFaction.Neutral,	lVect ) ));
		}
		//------------------------------------------------
		public void	DemoGlu( int pNb, float lSize ) {

				ModelQuadric lQuad01 = new ModelQuadric();
				lQuad01.setCylinder( lSize, lSize, lSize*2, 10, 10  );
				ModelQuadric lQuad11 = new ModelQuadric();
				lQuad11.setCylinder( lSize*1.001f, lSize*1.001f, lSize*1.01f*2, 10, 10  );


				ModelQuadric lQuad02 = new ModelQuadric();
				lQuad02.setCone( lSize, lSize*2, 10, 10  );
				ModelQuadric lQuad12 = new ModelQuadric();
				lQuad12.setCone( lSize*1.001f, lSize*1.001f*2, 10, 10  );



				ModelQuadric lQuad03 =  new ModelQuadric();
				lQuad03.setDisk( lSize, lSize*2, 10, 6  );
				ModelQuadric lQuad13 =  new ModelQuadric();
				lQuad13.setDisk( lSize*1.001f, lSize*1.001f*2, 10, 6  );



				ModelQuadric lQuad04 =  new ModelQuadric();
				lQuad04.setPartialDisk( lSize, lSize*2, 10, 6, 0f, 270f  );
				ModelQuadric lQuad14 =  new ModelQuadric();
				lQuad14.setPartialDisk( lSize*1.001f, lSize*1.001f*2, 10, 6, 0f, 270f  );


				ModelQuadric lQuad05 = new ModelQuadric();
				lQuad05.setSphere( lSize, 10, 10 );
				ModelQuadric lQuad15 = new ModelQuadric();
				lQuad15.setSphere( lSize*1.001f, 10, 10 );




				for( int i= 0; i<  pNb ; i++) {
						
						createSprite( lQuad01,  lQuad11, null );
								createSprite( lQuad02,  lQuad12, null);
								createSprite( lQuad03,  lQuad13, null);
								
								createSprite( lQuad04,  lQuad14, null);
								createSprite( lQuad05,  lQuad15, null);
			}
			
		}
		//------------------------------------------------
		public void	Demo( Primitiv3d.SubNormalizeType  pNormalize ) {
				// formes bien reguliere
				createDemoSprites( 1, false, 0.3f, 0, pNormalize );
				createDemoSprites( 1, false, 0.3f, 1, pNormalize );
				createDemoSprites( 1, false, 0.5f, 2, pNormalize );
				createDemoSprites( 1, false, 0.5f, 3, pNormalize );

				// formes regulieres mais originales avec decoupes
				
				createDemoSprites( 1, true, 0.4f, 0, pNormalize  ); 
				createDemoSprites( 1, true, 0.4f, 1, pNormalize ); 
				createDemoSprites( 1, true, 0.4f, 2, pNormalize ); 
				createDemoSprites( 1, true, 0.4f, 3, pNormalize ); 

				
				// formes cristalines ( doivent etre transparentes ! )
				createDemoSprites( 1, false, -0.3f, 0, pNormalize );
				createDemoSprites( 1, false, -0.3f, 1, pNormalize );
				createDemoSprites( 1, false, -0.3f, 2, pNormalize );
				createDemoSprites( 1, false, -0.3f, 3, pNormalize );

				// formes cristalines aussi mais plus exotiques
				createDemoSprites( 1, true, -0.3f, 0, pNormalize );				
				createDemoSprites( 1, true, -0.3f, 1, pNormalize );
				createDemoSprites( 1, true, -0.3f, 2, pNormalize );	
				createDemoSprites( 1, true, -0.3f, 3, pNormalize );	
		}
		//------------------------------------------------
		public void Demo( int i ){


				System.out.println( "Demo:" + i );

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
		public void callEngineInit( GL2 pGl ){

				System.out.println( "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT PPgG3d.callEngineInit TTTTTTTTTTTTTTTTT" );
				pGl.glEnable( GL2.GL_TEXTURE_2D );
				// FAIRE DES LUMIERE QUI DERIVE DE MODELBASE !!!!


				pGl.glClearColor( 0.20f, 0.20f, 0.2f, 0.0f );

	
				pGl.glEnable( GL2.GL_LIGHTING);
				pGl.glEnable( GL2.GL_LIGHT0);
				pGl.glEnable( GL2.GL_LIGHT1);

				//=============== Add camera =============
				{
						cKamera=  new Kamera3d( null, new Transform3d( new Float3(),  new Float3(),  new Float3( 2f, 2f, 2f)),
																		-0.5, 0.5, -0.5, 0.5, -0.5, 0.5 );
						//				-800, 800, -600, 600, -1, -1 );
						//				-800, 800, -600, 600, -500, 500 );
						
						
						//				cKamera=  new Kamera3d( null, new Transform3d( null, null, null),
						//																-800, 800, -600, 600, -1, -1 );
						
						//			cKamera.getTransf().rotate( new Float3( -45f, -10f, 0f ) );

						cKamera.resetOrtho(null);

						//	World3d.sTheWorld.setKamera(  new ActorLocation( 0.5f, 0.5f, -0.5f, EnumFaction.Neutral, cKamera ));
						World3d.sTheWorld.setKamera(  new ActorLocation( 1f, 1f, -1f, EnumFaction.Neutral, cKamera ));

						World3d.sTheWorld.getUserControl().initFromKamera( cKamera );

						//		cKam.getTransf().rotate( new Float3( 30f, 30f, 0f ) );
						//		cKam.getTransf().scale( new Float3( 0.1f, 0.1f, 0.1f ) );
						//
						//cKam.getTransf().move( new Float3( 0f, 0f, -10f ) );
				}
				((DefaultUserControl3d)World3d.sTheWorld.getUserControl()).makeActorCursor( false, 0.5f, 2,  Primitiv3d.SubNormalizeType.NORMALIZE );

				cTextRender = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
				cTextRender.setColor( 1f, 1.0f, 0f, 0.5f );
				cTextRender2 = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));		
				cTextRender2.setColor( 1f, 0.5f, 0.8f, 0.5f );
			//	Demo(1);

		}
		//------------------------------------------------
		@Override
		public void callEngineDrawBegin(GL2 pGl){		

				//				System.out.println( "PPgG3d callEngineRenderGL.callEngineRenderGL" );
				
				ByteBuffer lTemp = ByteBuffer.allocateDirect(16);
				lTemp.order(ByteOrder.nativeOrder());

				pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_AMBIENT, sGreyAmbient, 0);
				pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_DIFFUSE, sWhiteDiffuse, 0);								
				pGl.glLightfv( GL2.GL_LIGHT0, GL2.GL_POSITION, sPosition, 0 );

				//				sFBuf.put( sModelAmbient, 0, 4 );
				//	pGl.glLightModel( GL2.GL_LIGHT_MODEL_AMBIENT, sFBuf);

				
         float yellowAmbientDiffuse[] = {1.0f, 1.0f, 0f, 1.0f};	
				 float sPosition2[] = {-2.0f, 2.0f, -5.0f, 0.0f}; // a l'inifni

				 pGl.glLightfv( GL2.GL_LIGHT1, GL2.GL_AMBIENT, yellowAmbientDiffuse, 0);
				 pGl.glLightfv( GL2.GL_LIGHT1, GL2.GL_DIFFUSE,  yellowAmbientDiffuse, 0);
				 pGl.glLightfv( GL2.GL_LIGHT1, GL2.GL_POSITION,  sPosition2, 0 );


				 //	Primitiv3d.Green( pGl );
				 if( sViewGrid3d ) {
						 Primitiv3d.DrawGridXZ(  pGl, getWidth()/2, getDepth()/2, 3 );
						 Primitiv3d.DrawGridXY(  pGl, getWidth()/2, getHeight()/2, 3 );
						 Primitiv3d.DrawGridYZ(  pGl, getHeight()/2, getDepth()/2, 3 );
				 }

				 if( sViewRepere ) {
						 Primitiv3d.DrawRepere(  pGl, 1f, true );	
				 }


				
				pGl.glDisable(GL2.GL_LIGHTING);
				pGl.glColor3f( 0.0f, 0.0f, 0.9f );

				Primitiv3d lPrim = 	new Primitiv3d();
				Primitiv3d.SubParamDrawing lParam01 = lPrim.GetSubParamDrawing(  0, 1.0f, false, Primitiv3d.SubNormalizeType.NORMALIZE_NONE, GLUgl2.GLU_SILHOUETTE );

				//	System.out.println( "call Parallelepiped W:" +  getWidth()+ " H:" +  getHeight()+ " D:" +  getDepth());

				Primitiv3d.Parallelepiped( getWidth(), getHeight(), getDepth(), lParam01 );
				pGl.glEnable(GL2.GL_LIGHTING);




		}
		//------------------------------------------------
		
		@Override
				public void worldCallEndSceneDraw(GL2 pGl) {

		    //	System.out.println( "PPJ3d.worldCallEndSceneDraw" 
		    //			+ " Actors:" + World3d.sTheWorld.getActorLocation().size()
														    //												+ " CompilOBj:" + CompilObj.sNbCompilObjectInUse );
				/*
				if( DefaultUserControl3d.sTrace != null ){
						cTextRender.beginRendering( Engine.sTheGlDrawable.getWidth(), Engine.sTheGlDrawable.getHeight());
						cTextRender.draw( DefaultUserControl3d.sTrace, 1, 30);
						cTextRender.endRendering();				
				}
				
				if( DefaultUserControl3d.sComment != null ) {
						cTextRender2.beginRendering( Engine.sTheGlDrawable.getWidth(), Engine.sTheGlDrawable.getHeight());
						cTextRender2.draw(DefaultUserControl3d.sComment, 1, Engine.sTheGlDrawable.getHeight() -30);
						cTextRender2.endRendering();				
				}	
				*/
				}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

    public static void main( String [] args ) {
 
				//				int lW      = PPgParam.GetInteger( args, "-w", 1280 );
				//				int lH      = PPgParam.GetInteger( args, "-h", 1024);
				int lW      = PPgParam.GetInteger( args, "-w", 1280);
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

				int lFps     = PPgParam.GetInteger( args, "-F", 30);

				World3d.sDebugFps = PPgParam.GetBoolean( args, "-f", false);
				PPgJ3d.sFlagXY = PPgParam.GetBoolean( args, "-XY", false);

				boolean lSmooth = PPgParam.GetBoolean( args, "-t", false);
				PPgJ3d.sViewGrid3d = PPgParam.GetBoolean( args, "-g", true);
				PPgJ3d.sViewRepere = PPgParam.GetBoolean( args, "-r", true);

				PPgJ3d.sGenSize = PPgParam.GetFloat( args, "-s", PPgJ3d.sGenSize);
				System.out.println("Size:" +  PPgJ3d.sGenSize );

				//Pb sur la taille de World et le rebond
				//	PB sur la vitesse : tous a gauche !
				
				Engine lEngine = new Engine( "Test JOGL",  lW, lH,  lStrFullScreen != null, lSmooth, true  );
 				PPgJ3d lTest = new PPgJ3d( new DimFloat3( 20,  20, 20), lEngine, lSmooth  );		
				lTest.setUserControl( new PPgJ3dTest( lTest) );
				lEngine.start( lFps );
		}
}
//*************************************************
