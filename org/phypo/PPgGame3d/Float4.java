package org.phypo.PPgGame3d;

import com.jogamp.opengl.*;


import java.nio.*;
import org.phypo.PPg.PPgMath.*;

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
		public Float4( double A, double B, double C, double D ){
				cVect[0] = (float)A;
				cVect[1] = (float)B;
				cVect[2] = (float)C;
				cVect[3] = (float)D;
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
		//------------------------------------------------
		final public void add( Float4 pToAdd ) {

				cVect[0]+= pToAdd.cVect[0];
				cVect[1]+= pToAdd.cVect[1];
				cVect[2]+= pToAdd.cVect[2];
				cVect[3]+= pToAdd.cVect[3];
		}
		//------------------------------------------------
		final public void sub( Float4 pToAdd ) {

				cVect[0]-= pToAdd.cVect[0];
				cVect[1]-= pToAdd.cVect[1];
				cVect[2]-= pToAdd.cVect[2];
				cVect[3]-= pToAdd.cVect[3];
		}
		//------------------------------------------------
		final public void addDelta( Float4 pToAdd, float pDelta ) {

				cVect[0]+= pToAdd.cVect[0]*pDelta;
				cVect[1]+= pToAdd.cVect[1]*pDelta;
				cVect[2]+= pToAdd.cVect[2]*pDelta;
				cVect[3]+= pToAdd.cVect[3]*pDelta;
		}
		//------------------------------------------------
		final public void add(  float pVal ) {

				cVect[0] += pVal;
				cVect[1] += pVal;
				cVect[2] += pVal;
		}
		//------------------------------------------------
		final public void sub(  float pVal ) {

				cVect[0] -= pVal;
				cVect[1] -= pVal;
				cVect[2] -= pVal;
				cVect[3] -= pVal;
		}
		//------------------------------------------------
		final public void multiply(  float pVal ) {

				cVect[0] *= pVal;
				cVect[1] *= pVal;
 				cVect[2] *= pVal;
 				cVect[3] *= pVal;
		}
		//------------------------------------------------
		final public void divide(  float pVal ) {

				pVal = 1.0f/pVal;

				cVect[0] *= pVal;
				cVect[1] *= pVal;
				cVect[2] *= pVal;
				cVect[3] *= pVal;
		}
		//------------------------------------------------		
		final public void limitVal( float pMax ) {
				for( int i=0; i< 4 ; i++ ){
						if( cVect[i] > pMax )
								cVect[i] = pMax;
						else
								if( cVect[i] < -pMax )
								cVect[i] = -pMax;
				}
		}
		//------------------------------------------------		
		final public void limitVal( float pMin, float pMax ) {
				for( int i=0; i< 4 ; i++ ){

						if( cVect[i] > pMax )
								cVect[i] = pMax;
						else
								if( cVect[i] < pMin )
								cVect[i] = pMin;
				}
		}
		//------------------------------------------------		
		final public String toString() {

				String lStr = new String();

				for( int i=0; i< 4 ; i++ ){						
						lStr = lStr + Float.toString( cVect[i] ) + ' ';
				}
				return lStr;
		}
}

//*************************************************
