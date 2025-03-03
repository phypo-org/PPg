package org.phypo.PPgGame.PPgG3d;

import java.util.*;

import org.lwjgl.opengl.*;
 
//import org.lwjgl.util.*;
// import org.lwjgl.util.glu.Project;
//import org.lwjgl.util.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


//*************************************************
public class Kamera3d extends Leaf3d{
		
		public Float3  cEye = new Float3( 1, 1, 1 );
		public Float3  cCenter= new Float3();
		public Float3  cUp =new Float3( 0, 10, 0);
				

		boolean cFlagOrtho = true;

		float   cFovY;
		float   cAspect;
		float   cZnear;
		float   cZfar;

		double cLeft;
		double cRight;
		double cBottom;
		double cTop;

		public boolean isOrtho() { return cFlagOrtho; }

		//------------------------------------------------
		public void setOrtho( boolean pFlag  ){
				cFlagOrtho = pFlag;
				setProjectionMode();
		}
		//------------------------------------------------
		public Kamera3d( Node3d pParent  ){
				super( pParent, null, new Transform3d( null, null, null) , null);

				resetOrtho();
						//	setOrtho(		-1000,  + 1000, -1000,  +1000, -100000, 100000 );
						
		}
		//------------------------------------------------
		public Kamera3d( Node3d pParent, TransformBase pTransf,
										 Float3 pEye, Float3 pCenter, Float3 pUp ){			
				super( pParent, null, pTransf, null);

				setPerspective( pEye,	pCenter, pUp );
 	
		}
		//------------------------------------------------
		public Kamera3d( Node3d pParent, TransformBase pTransf,
										 double pLeft,
										 double pRight,
										 double pBottom,
										 double pTop,
										 double pZnear,
										 double pZfar ) {

				super( pParent, null, pTransf, null); 

				setOrtho( pLeft, pRight,  pBottom, pTop, pZnear, pZfar );
		}

		//------------------------------------------------
		void setProjectionMode(){

				if( cFlagOrtho == false  ){
						Project.gluLookAt( cEye.cVect[0],cEye.cVect[1],cEye.cVect[2],
															 cCenter.cVect[0],cCenter.cVect[1], cCenter.cVect[2],
															 cUp.cVect[0], cUp.cVect[1], cUp.cVect[2]);
				}
				else		{
						/*
						System.out.println( "GL11.glOrtho cLeft:" +cLeft+
																"cRight:" + cRight+
																"cBottom:" + cBottom+
																"cTop:" + cTop+
																"cZnear:" + cZnear+
																"cZfar:" + cZfar );
						*/
						GL11.glOrtho( cLeft, cRight, cBottom, cTop, cZnear, cZfar  );	
				}
		}
		//------------------------------------------------
		public void resetOrtho(){

				int w = World.sTheWorld.cEngine.cWidth;
				int h = World.sTheWorld.cEngine.cHeight;
				int d = 50;

				setOrtho( -w, w, -h, h, -d, d );

				//			setOrtho( -800, 800, -600,  600,  -1, -1 );
				setProjectionMode();
		}
		//------------------------------------------------
		public void resetPerspective(){
				setPerspective( new Float3( 1, 1, 1), new Float3( 0, 0, 0), new Float3( 0,10,0) );
				Transform3d lTransf = (Transform3d)cMyTransf;
				lTransf.cScale = new Float3( 0.1f, 0.1f, 0.1f );					 
				setProjectionMode();
		}
		//------------------------------------------------
		public void resetTransf(){


				Transform3d lTransf = (Transform3d)cMyTransf;
				
				//		lTransf.cPos   = new Float3( 0f, 30.0f, 0f );
				lTransf.cPos   = new Float3( 0.0f, 0.0f, 0.0f );
				//			lTransf.cRot   = null;
				lTransf.cScale = new Float3( 0.1f, 0.1f, 0.1f );					 
				setProjectionMode();
		}				
		//------------------------------------------------

		public void setPerspective( Float3 pEye, Float3 pCenter, Float3 pUp ){
				cFlagOrtho = false;

				cEye = pEye ;
				cCenter = pCenter;
				cUp = pUp;

				setProjectionMode();
		}
		//------------------------------------------------
		public void setOrtho( double pLeft,
													double pRight,
													double pBottom,
													double pTop,
													double pZnear,
													double pZfar ) {

				cFlagOrtho = true;

				cLeft   = pLeft;
				cRight  = pRight;
				cBottom = pBottom;
				cTop    = pTop;
				cZnear = (float) pZnear;
				cZfar  = (float) pZfar;

				setProjectionMode();
		}
		

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		public void renderGL( ){

				if( cMyTransf != null ) {

						//						System.out.println( "************ Kamera.renderGL");

						GL11.glMatrixMode(GL11.GL_PROJECTION);
						GL11.glLoadIdentity();

						cMyTransf.renderGL();
						setProjectionMode();


					//		cMyTransf.rotate( ( new Float3( 30f, 10f, 60f ) ); 
					//	cMyTransf.renderGL();

					//					GL11.glRotatef( 30.0f,1.0f,0.0f,0.0f); 
					//GL11.glRotatef( 30.0f,0.0f,1.0f,0.0f); 
					//GL11.glRotatef( 30.0f,0.0f,0.0f,1.0f); 
					
						GL11.glMatrixMode( GL11.GL_MODELVIEW );
				}			
				
		}


}
//*************************************************
