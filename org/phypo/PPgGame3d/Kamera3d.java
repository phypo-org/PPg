package org.phypo.PPgGame3d;

import java.util.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.*;

import com.jogamp.opengl.glu.gl2.GLUgl2;

//*************************************************
public class Kamera3d extends Leaf3d{
		
		public Float3  cEye    = new Float3( 1, 1, 1 );
		public Float3  cCenter = new Float3();
		public Float3  cUp     = new Float3( 0, 10, 0);
				

		boolean cFlagOrtho = true;

		float  cFovY;
		float  cAspect;
		float  cZnear;
		float  cZfar;

		double cLeft;
		double cRight;
		double cBottom;
		double cTop;


		public boolean isOrtho() { return cFlagOrtho; }

		//------------------------------------------------
		public void setOrtho(  GL2 pGl, boolean pFlag  ){
				cFlagOrtho = pFlag;
				setProjectionMode( pGl );
		}
		//------------------------------------------------
		public Kamera3d(  Node3d pParent  ){
				super( pParent, null, new Transform3d( null, null, null) , null);

				resetOrtho(null);
						//	setOrtho(		-1000,  + 1000, -1000,  +1000, -100000, 100000 );
						
		}
		//------------------------------------------------
		public Kamera3d( Node3d pParent, TransformBase pTransf,
										 Float3 pEye, Float3 pCenter, Float3 pUp ){			
				super( pParent, null, pTransf, null);

				setPerspective( null, pEye,	pCenter, pUp );
 	
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

				setOrtho( null, pLeft, pRight,  pBottom, pTop, pZnear, pZfar );
		}

		//------------------------------------------------
		void setProjectionMode( GL2 pGl){
				if( pGl == null )
						return ;

				if( cFlagOrtho == false  ){
						Engine.sTheGlu.gluLookAt( cEye.cVect[0],cEye.cVect[1],cEye.cVect[2],
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
						pGl.glOrtho( cLeft, cRight, cBottom, cTop, cZnear, cZfar  );	
				}
		}
		//------------------------------------------------
		public void resetOrtho( GL2 pGl){
				/*
				int w = World3d.sTheWorld.cEngine.cWidth;
				int h = World3d.sTheWorld.cEngine.cHeight;
				int d =(w+h)/2 ;
				*/
				/*
				float w = World3d.sTheWorld.getWidth();
				float h = World3d.sTheWorld.getHeight();
				float d = World3d.sTheWorld.getDepth();
				*/
				float w = World3d.sTheWorld.getWidth();
				float h = World3d.sTheWorld.getHeight();
				float d = World3d.sTheWorld.getDepth();
				setOrtho( pGl, -h, h, -h, h, -h, h );

				//			setOrtho( -800, 800, -600,  600,  -1, -1 );
		}
		//------------------------------------------------
		public void resetPerspective(GL2 pGl){
				setPerspective( pGl, new Float3( 1, 1, 1), new Float3( 0, 0, 0), new Float3( 0,10,0) );
				Transform3d lTransf = (Transform3d)cMyTransf;
				lTransf.cScale = new Float3( 0.1f, 0.1f, 0.1f );					 
				setProjectionMode(pGl);
		}
		//------------------------------------------------
		public void resetTransf(GL2 pGl){


				Transform3d lTransf = (Transform3d)cMyTransf;
				
				//		lTransf.cPos   = new Float3( 0f, 30.0f, 0f );
				lTransf.cPos   = new Float3( 0.0f, 0.0f, 0.0f );
				//			lTransf.cRot   = null;
				lTransf.cScale = new Float3( 0.1f, 0.1f, 0.1f );					 
				setProjectionMode(pGl);
		}				
		//------------------------------------------------

		public void setPerspective( GL2 pGl, Float3 pEye, Float3 pCenter, Float3 pUp ){
				cFlagOrtho = false;

				cEye = pEye ;
				cCenter = pCenter;
				cUp = pUp;

				setProjectionMode(pGl);
		}
		//------------------------------------------------
		public void setOrtho( GL2 pGl,
													double pLeft,
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

			 
				setProjectionMode(pGl);
		}
		

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		public void renderGL( GL2 pGl ){

				if( cMyTransf != null ) {

						//						System.out.println( "************ Kamera.renderGL");

						pGl.glMatrixMode( GL2.GL_PROJECTION);
						pGl.glLoadIdentity();

						cMyTransf.renderGL( pGl );
						setProjectionMode( pGl );


					//		cMyTransf.rotate( ( new Float3( 30f, 10f, 60f ) ); 
					//	cMyTransf.renderGL();

					//					GL11.glRotatef( 30.0f,1.0f,0.0f,0.0f); 
					//GL11.glRotatef( 30.0f,0.0f,1.0f,0.0f); 
					//GL11.glRotatef( 30.0f,0.0f,0.0f,1.0f); 
					
						pGl.glMatrixMode( GL2.GL_MODELVIEW );
				}			
				
		}
		//------------------------------------------------
		// Transform int windows coordinates

		public Double3 project( GL2 pGl2,  Float3 pVal){
				
				int lViewport[]       = new int[4];
				pGl2.glGetIntegerv( GL2.GL_VIEWPORT, lViewport, 0 );
	
				double lModelviewMatrix[]   = new double[16];
				pGl2.glGetDoublev(  GL2.GL_MODELVIEW_MATRIX, lModelviewMatrix, 0 );
				
				double lProjectMatrix[]  = new double[16];
				pGl2.glGetDoublev(  GL2.GL_PROJECTION_MATRIX, lProjectMatrix, 0 );

				Double3 pWcoord = new Double3();

				Engine.sTheGlu.gluProject( (double) pVal.x(), (double) pVal.y(), (double) pVal.z(), 
												lModelviewMatrix, 0, 
												lProjectMatrix, 0, 
												lViewport, 0,
																	 pWcoord.get(), 0);

				return pWcoord;
					 
		}
		//------------------------------------------------
		// transform in object coordinates

		public Double3  unproject( GL2 pGl2, Float3 pVal){

					
				int lViewport[]       = new int[4];
				pGl2.glGetIntegerv( GL2.GL_VIEWPORT, lViewport, 0 );
	
				double lModelviewMatrix[]   = new double[16];
				pGl2.glGetDoublev(  GL2.GL_MODELVIEW_MATRIX, lModelviewMatrix, 0 );

				double lProjectMatrix[]  = new double[16];
				pGl2.glGetDoublev(  GL2.GL_PROJECTION_MATRIX, lProjectMatrix, 0 );

				float pPosX, pPosY, pPosZ;
				Double3 pWcoord = new Double3();

				Engine.sTheGlu.gluUnProject(  (double) pVal.x(), (double) pVal.y(), (double) pVal.z(),
																			lModelviewMatrix, 0, 
																			lProjectMatrix, 0, 
																			lViewport, 0,
																			pWcoord.get(), 0 );
				return pWcoord;
		}

}
//*************************************************
