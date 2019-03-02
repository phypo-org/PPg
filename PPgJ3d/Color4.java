package org.phypo.PPg.PPgJ3d;

import com.jogamp.opengl.*;


import java.nio.*;
import org.phypo.PPg.PPgMath.*;

//*************************************************
public class Color4 extends Float4 {




		static final public Color4 Red         = new Color4( 1.0f, 0.0f, 0.0f, 1.0f);
		
		static final public Color4 Green       = new Color4( 0.0f, 1.0f, 0.0f, 1f);  
		static final public Color4 Blue        = new Color4( 0.0f, 0.0f, 1.0f, 1f);
		static final public Color4 LightBlue   = new Color4( 0.5f, 0.5f, 1.0f, 1f); 
		
		static final public Color4 Yellow      = new Color4( 1.0f, 1.0f, 0.0f, 1f); 	
		static final public Color4 Pink        = new Color4( 1.0f, 0.0f, 1.0f, 1f); 	
		
		static final public Color4 White       = new Color4( 1.0f, 1.0f, 1.0f, 1f); 	
		static final public Color4 Black       = new Color4( 0.0f, 0.0f, 0.0f, 1f); 
		
		static final public Color4 Grey        = new Color4( 0.5f, 0.5f, 0.5f, 1f); 
		static final public Color4 LightGrey   = new Color4( 0.7f, 0.7f, 0.7f, 1f);  
		static final public Color4 DarkGrey    = new Color4( 0.3f, 0.3f, 0.3f, 1f);



		public Color4(  float A, float B, float C, float D ){
				super( A,B,C,D );
		}
		//------------------------------------------------
		public Color4( double A, double B, double C, double D ){
				super( A,B,C,D );
		}
		//------------------------------------------------
		public Color4( Float4 pSrc  ){
				super( pSrc );
		}
		//------------------------------------------------
		public Color4( float pSrc   ){
				super( pSrc, pSrc, pSrc, 1f );
		}
		public Color4(){
				super();
		}
		//------------------------------------------------

		public float r() { return cVect[0]; }
		public float g() { return cVect[1]; }
		public float b() { return cVect[2]; }
		public float a() { return cVect[3]; }

		public void setR( float pVal ) {  cVect[0] = pVal; }
		public void setG( float pVal ) {  cVect[1] = pVal; }
		public void setB( float pVal ) {  cVect[2] = pVal; }
		public void setA( float pVal ) {  cVect[3] = pVal; }


		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		static final public Color4 Diff( Color4 A, Color4 B ) {

				Color4 C = new Color4();

				C.cVect[0] = A.cVect[0] - B.cVect[0]; 
				C.cVect[1] = B.cVect[1] - B.cVect[1]; 
				C.cVect[2] = C.cVect[2] - B.cVect[2]; 
				C.cVect[3] = C.cVect[3] - B.cVect[3]; 

				return C;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		final public Color4 random( PPgRandom pRand, float pMax ){
				cVect[0] = pRand.nextFloat( pMax );
				cVect[1] = pRand.nextFloat( pMax );
				cVect[2] = pRand.nextFloat( pMax );
				cVect[3] = pRand.nextFloat( pMax );

				return this;
		}
		//------------------------------------------------
		final public Color4 randomPositif( PPgRandom pRand, float pMin, float pMax ){
				cVect[0] = pMin + pRand.nextFloatPositif( pMax-pMin );
				cVect[1] = pMin + pRand.nextFloatPositif( pMax-pMin );
				cVect[2] = pMin + pRand.nextFloatPositif( pMax-pMin );
				cVect[3] = pMin + pRand.nextFloatPositif( pMax-pMin );

				return this;
		}
		//------------------------------------------------
		static public Color4 GetRandom( PPgRandom pRand, float pMax ){
				Color4 lTmp = new Color4();
				return lTmp.random( pRand, pMax  );
		}
		//------------------------------------------------
		static public Color4 GetRandomPositif( PPgRandom pRand, float pMin, float pMax ){
				Color4 lTmp = new Color4();
				return lTmp.randomPositif( pRand, pMin, pMax  );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		final public void glColor(  GL2 pGl ) {
				pGl.glColor4fv( cVect, 0);
		}
		//------------------------------------------------
		final public void glMaterial( GL2 pGl ) {
				
				pGl.glMaterialfv( GL.GL_FRONT_AND_BACK, 
													GL2.GL_AMBIENT_AND_DIFFUSE,  
													cVect, 0);													 		
		}

		//------------------------------------------------

		final public void glEmission( GL2 pGl ) { 

				pGl.glMaterialfv(  GL.GL_FRONT, 
													 GL2.GL_EMISSION,
													 cVect, 0);				
		}
		//------------------------------------------------
		final public void glFogColor( GL2 pGl ) {
				
				pGl.glFogfv( GL2.GL_FOG_COLOR,  cVect, 0 );
		}
}
