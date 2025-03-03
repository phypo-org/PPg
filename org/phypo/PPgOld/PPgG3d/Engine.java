package com.phipo.PPg.PPgG3d;



import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


//*************************************************
public class Engine{
		
		World cMyWorld;
		public int cWidth;
		public int cHeight;
		boolean cFullScreen = false;
		//------------------------------------------------
		public Engine( int pW, int pH, boolean pFullScreen) {
				cWidth  = pW;
				cHeight = pH;
				cFullScreen = pFullScreen;
		}
		//------------------------------------------------
		public void  initEngine( World pWorld ){

				cMyWorld = pWorld;


				try {
						//2	if( cWidth < 0 || cHeight < 0 ) {
						//								DisplayMode[] lDM = 	Display.getAvailableDisplayModes( 640, 480, 2048, 1980 );
						//	Display.setDisplayMode( lDM, 
						//						}

						Display.setDisplayMode( new DisplayMode( cWidth, cHeight ));
						
						if( cFullScreen )
								Display.setFullscreen(true);


						//			Display.setVSyncEnabled(true);
						Display.create();
						Keyboard.create();
						Keyboard.enableRepeatEvents(true);
						Mouse.create();

				} catch (LWJGLException ex) {
						ex.printStackTrace();
						System.exit(0);
				}


				initGl();
				cMyWorld.callEngineInit();
		}
		//------------------------------------------------
		public void stopEngine(){

				cMyWorld.callEngineStop();
				Display.destroy();
				Mouse.destroy();
				Keyboard.destroy();
		}
		//------------------------------------------------
		public boolean isOk() {

				if( Display.isCloseRequested() ) return false;				
				return true;
		}
		//------------------------------------------------
		public boolean beginDraw( )  {
				if( isOk() == false )
						return false;


						// Clear the screen and depth buffer
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
				
				
				GL11.glMatrixMode( GL11.GL_MODELVIEW );
				GL11.glLoadIdentity();

				cMyWorld.callEngineTurnBegin();

				return true;
		}
		//------------------------------------------------
		public boolean endDraw()  {

				Display.update();		

				cMyWorld.callEngineTurnEnd();

				GL11.glPopMatrix();

				return true;
		}
		//------------------------------------------------
		public boolean 	render( Transform3d pTransf, NodeBase pNode ){

				//		System.out.println( "*** Engine3d.render Node " );
				// Clear the screen and depth buffer
				//				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				if( pTransf != null )		GL11.glPushMatrix();

				pTransf.renderGL();	
				pNode.renderGL();									
				
				if( pTransf != null )		GL11.glPopMatrix();				
	 						
			return true;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public void enableFog( Float4 pFogColor, float pDensity, float pStart, float pEnd ){

				GL11.glEnable( GL11.GL_FOG );

				changeFog( pFogColor, pDensity, pStart,  pEnd );				
		}
		//------------------------------------------------
		public void disableFog( Float4 pFogColor, float pDensity, float pStart, float pEnd ){

				GL11.glDisable( GL11.GL_FOG );
		}
		//------------------------------------------------
		public void changeFog( Float4 pFogColor, float pDensity, float pStart, float pEnd ){
				
				GL11.glFogi ( GL11.GL_FOG_MODE, GL11.GL_EXP);
				pFogColor.glFog();

				GL11.glFogf(  GL11.GL_FOG_DENSITY, pDensity );
				GL11.glFogf(  GL11.GL_FOG_START, pStart );
				GL11.glFogf(  GL11.GL_FOG_END, pEnd );
		}	
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public void initGl(){
				// init OpenGL
				GL11.glMatrixMode( GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				//	GL11.glOrtho(0, 800, 0, 600, 1, -1);
				GL11.glOrtho(-800, 800, -600, 600, -1, -1);
				GL11.glMatrixMode( GL11.GL_MODELVIEW);

				//			GL11.glEnable( GL11.GL_COLOR_MATERIAL );
					GL11.glColorMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
  
				

					
					//				GL11.glEnable( GL11.GL_AUTO_NORMAL);  //couteux
					//					GL11.glEnable( GL11.GL_NORMALIZE );   //couteux
					//		GL11.glFrontFace( GL11.GL_CCW);

				GL11.glClearColor( 0.10f, 0.10f, 0.2f, 0.0f );


				GL11.glEnable( GL11.GL_POINT_SMOOTH );
				GL11.glEnable( GL11.GL_LINE_SMOOTH );
				GL11.glEnable( GL11.GL_POLYGON_SMOOTH );

				GL11.glEnable( GL11.GL_DEPTH_TEST );
				GL11.glDisable( GL11.GL_CULL_FACE );


				//		enableFog( new Float4(0.5f, 0.5f, 0.5f, 1 ), 0.35f,  1.0f, 5.0f );
        
		}	
}
//*************************************************
