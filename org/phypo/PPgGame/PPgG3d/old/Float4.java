package com.phipo.PPg.PPgG3d;


import org.lwjgl.opengl.*;

import java.nio.*;
import com.phipo.PPg.PPgMath.*;

//*************************************************


public class Float4{
		
		public float cVect[] = new float[4];

		public Float4( float A, float B, float C, float D ){
				cVect[0] = A;
				cVect[1] = B;
				cVect[2] = C;
				cVect[3] = D;
		}																				
		//------------------------------------------------
		public Float4( Float4 pSrc  ){
				cVect[0] = pSrc.cVect[0];
				cVect[1] = pSrc.cVect[1];
				cVect[2] = pSrc.cVect[2];
				cVect[3] = pSrc.cVect[3];
		}
		//------------------------------------------------
		public Float4(){
				cVect[0] = 0f;
				cVect[1] = 0f;
				cVect[2] = 0f;
				cVect[3] = 0f;
		}		
		//------------------------------------------------
		public float [] get() { return cVect;}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		final public Float4 random( PPgRandom pRand, float pMax ){
				cVect[0] = pRand.nextFloat( pMax );
				cVect[1] = pRand.nextFloat( pMax );
				cVect[2] = pRand.nextFloat( pMax );
				cVect[3] = pRand.nextFloat( pMax );

				return this;
		}
		//------------------------------------------------
		final public Float4 randomPositif( PPgRandom pRand, float pMin, float pMax ){
				cVect[0] = pMin + pRand.nextFloatPositif( pMax-pMin );
				cVect[1] = pMin + pRand.nextFloatPositif( pMax-pMin );
				cVect[2] = pMin + pRand.nextFloatPositif( pMax-pMin );
				cVect[3] = pMin + pRand.nextFloatPositif( pMax-pMin );

				return this;
		}
		//------------------------------------------------
		static public Float4 GetRandom( PPgRandom pRand, float pMax ){
				Float4 lTmp = new Float4();
				return lTmp.random( pRand, pMax  );
		}
		//------------------------------------------------
		static public Float4 GetRandomPositif( PPgRandom pRand, float pMin, float pMax ){
				Float4 lTmp = new Float4();
				return lTmp.randomPositif( pRand, pMin, pMax  );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		final public void glColor() {
				GL11.glColor4f( cVect[0], cVect[1], cVect[2], cVect[3] );
		}
		//------------------------------------------------
		final public void glMaterial() {
				
				ByteBuffer lTemp = ByteBuffer.allocateDirect(16);
				lTemp.order(ByteOrder.nativeOrder());
				
				GL11.glMaterial( GL11.GL_FRONT_AND_BACK, 
												 GL11.GL_AMBIENT_AND_DIFFUSE, 
												 (FloatBuffer)lTemp.asFloatBuffer().put(cVect).flip());
		}

		//------------------------------------------------

		final public void glEmission() { 
				ByteBuffer lTemp = ByteBuffer.allocateDirect(16);
				lTemp.order(ByteOrder.nativeOrder());
				
				GL11.glMaterial( GL11.GL_FRONT, 
												 GL11.GL_EMISSION,
												 (FloatBuffer)lTemp.asFloatBuffer().put(cVect).flip());
		}
		//------------------------------------------------
		final public void glFog() {
				
				ByteBuffer lTemp = ByteBuffer.allocateDirect(16);
				lTemp.order(ByteOrder.nativeOrder());
				
				GL11.glFog( GL11.GL_FOG_COLOR, 
										(FloatBuffer)lTemp.asFloatBuffer().put(cVect).flip());
		}
}

//*************************************************
