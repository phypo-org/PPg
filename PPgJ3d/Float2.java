package org.phypo.PPg.PPgJ3d;

import com.jogamp.opengl.*;

import org.phypo.PPg.PPgMath.*;

//*************************************************


public class Float2{
		
		float cVect[] = new float[2];

		//------------------------------------------------
		public Float2( float A, float B){
				cVect[0] = A;
				cVect[1] = B;
		}
		//------------------------------------------------
		public Float2( double A, double B ){
				cVect[0] = (float)A;
				cVect[1] = (float)B;
		}
		//------------------------------------------------
  	public Float2( Float2 pSrc ){
				cVect[0] = pSrc.cVect[0];
				cVect[1] = pSrc.cVect[1];
		}	 		
		//------------------------------------------------
		public Float2(){
				cVect[0] = 0f;
				cVect[1] = 0f;
		}		
		//------------------------------------------------
		public Float2(float pf){
				cVect[0] = pf;
				cVect[1] = pf;
		}		
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		final public float[]  get() { return cVect;}
		final public void set(float A, float B ) { 
				cVect[0]= A; 
				cVect[1]= B; 
		}
		//------------------------------------------------
		final public void set( Float2 pSrc ) { 
				cVect[0]= pSrc.cVect[0]; 
				cVect[1]= pSrc.cVect[1]; 
		}
		//------------------------------------------------
		final public void setX( float pVal ) { 
				cVect[0]= pVal;
		}
		//------------------------------------------------
		final public void setY( float pVal ) { 
				cVect[1]= pVal;
		}
		//------------------------------------------------
		public float x() { return cVect[0]; }
		public float y() { return cVect[1]; }
		//------------------------------------------------
		final public void add( Float3 pToAdd ) {

				cVect[0]+= pToAdd.cVect[0];
				cVect[1]+= pToAdd.cVect[1];
		}
		//------------------------------------------------
		final public void sub( Float2 pToAdd ) {

				cVect[0]-= pToAdd.cVect[0];
				cVect[1]-= pToAdd.cVect[1];
		}
		//------------------------------------------------
		final public void addDelta( Float2 pToAdd, float pDelta ) {

				cVect[0]+= pToAdd.cVect[0]*pDelta;
				cVect[1]+= pToAdd.cVect[1]*pDelta;
		}
		//------------------------------------------------
		final public void add(  float pVal ) {

				cVect[0] += pVal;
				cVect[1] += pVal;
		}
		//------------------------------------------------
		final public void sub(  float pVal ) {

				cVect[0] -= pVal;
				cVect[1] -= pVal;
		}
		//------------------------------------------------
		final public void multiply(  float pVal ) {

				cVect[0] *= pVal;
				cVect[1] *= pVal;
		}
		//------------------------------------------------
		final public void divide(  float pVal ) {

				pVal = 1.0f/pVal;

				cVect[0] *= pVal;
				cVect[1] *= pVal;
		}
		//------------------------------------------------		
		final public void limitVal( float pMax ) {
				for( int i=0; i< 2 ; i++ ){
						if( cVect[i] > pMax )
								cVect[i] = pMax;
						else
								if( cVect[i] < -pMax )
								cVect[i] = -pMax;
				}
		}
		//------------------------------------------------		
		final public String toString() {

				String lStr = new String();

				for( int i=0; i< 2 ; i++ ){						
						lStr = lStr + Float.toString( cVect[i] ) + ' ';
				}
				return lStr;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		final public double distanceSq( double pX, double pY ){

				double lDx = cVect[0] -pX;
				double lDy = cVect[1] -pY;

				return (lDx*lDx + lDy*lDy );
		}
		//------------------------------------------------
		final public double distanceSq( Float2 pF ){

				double lDx = cVect[0] -pF.cVect[0];
				double lDy = cVect[1] -pF.cVect[1] ;

				return (lDx*lDx + lDy*lDy );
		}
		//------------------------------------------------
		final public double distance( double pX, double pY ){
				return Math.sqrt( distanceSq( pX, pY) );
		}
		//------------------------------------------------
		final public double distance( Float2 pF  ){
				return Math.sqrt( distanceSq( pF ) );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		static final public void Diff( Float2 A, Float2 B ,Float2 C ) {

				C.cVect[0] = A.cVect[0] - B.cVect[0]; 
				C.cVect[1] = A.cVect[1] - B.cVect[1]; 
		}
		//------------------------------------------------
		static final public Float2 Diff( Float2 A, Float2 B ) {

				Float2 C = new Float2();

				C.cVect[0] = A.cVect[0] - B.cVect[0]; 
				C.cVect[1] = A.cVect[1] - B.cVect[1]; 

				return C;
		}
		//------------------------------------------------
		static public void Crossprod( Float2 A, Float2 B, Float2 Prod ) {

				Prod.cVect[0] = A.cVect[1] * B.cVect[2] - B.cVect[1] * A.cVect[2];
				Prod.cVect[1] = A.cVect[2] * B.cVect[0] - B.cVect[2] * A.cVect[0];
		}		
		//------------------------------------------------
		static public Float2 Crossprod( Float2 A, Float2 B ) {

				Float2 Prod = new Float2();

				Prod.cVect[0] = A.cVect[1] * B.cVect[2] - B.cVect[1] * A.cVect[2];
				Prod.cVect[1] = A.cVect[2] * B.cVect[0] - B.cVect[2] * A.cVect[0];

				return Prod;
		}		
		//------------------------------------------------
		static public Float2 Middle( Float2 A, Float2 B ) {
				
				Float2 Prod = new Float2( (float)(A.cVect[0] + B.cVect[0])*0.5f,
																	(float)(A.cVect[1] + B.cVect[1])*0.5f );
				return Prod;
		}		
		//------------------------------------------------
		static public Float2 Middle( Float2 A, Float2 B, Float2 C ) {
				
				Float2 Prod = new Float2( (float)((A.cVect[0] + B.cVect[0] + C.cVect[0])*0.33333333333333333),
																	(float)((A.cVect[1] + B.cVect[1] + C.cVect[1])*0.33333333333333333));
				return Prod;
		}		
		//------------------------------------------------
		static public Float2 Middle( Float2 A, Float2 B, Float2 C, Float2 D ) {
				
				return new Float2( (float)((A.cVect[0] + B.cVect[0] + C.cVect[0] + D.cVect[0] )*0.25),
													 (float)((A.cVect[1] + B.cVect[1] + C.cVect[1] + D.cVect[1] )*0.25) );																
		}		
		//------------------------------------------------
		static public Float2 Middle( Float2 A, Float2 B, Float2 C, Float2 D, Float2 E ) {
				
				return new Float2( (float)((A.cVect[0] + B.cVect[0] + C.cVect[0] + D.cVect[0]+ E.cVect[0] )*0.20),
													 (float)((A.cVect[1] + B.cVect[1] + C.cVect[1] + D.cVect[1]+ E.cVect[1] )*0.20) );																
		}		
		//------------------------------------------------
		static public Float2 Middle( Float2 pArray[] ) {
						
				double X=0;
				double Y=0;
				
				for( Float2 lVal : pArray ){
						X += lVal.cVect[0];
						Y += lVal.cVect[1];
				}

				double lFactor = 1.0/pArray.length;
				X *= lFactor;
				Y *= lFactor;
				
						
				return  new Float2( X, Y	);																
		}		
		//------------------------------------------------
		public void	normalize() {

		
				double d = cVect[0]  * cVect[0] + cVect[1] * cVect[1];
				if (d == 0.0f) {
						d = 1.0f;
				} else {
						d = (float)(1.0 / Math.sqrt( d ));
				}

				//	System.out.println( "Float2 normalize x=" + cVect[0] + " y="+ cVect[1] +" z=" + cVect[2] 
				//										+ " ->" + d  
				//										);			
				d = 1.0f / d;
				cVect[0] *= d;
				cVect[1] *= d;
				
		//	System.out.println( "Float2 normalize x=" + cVect[0] + " y="+ cVect[1] +" z=" + cVect[2] + " d:" +d );
		}
		//------------------------------------------------
		public void	normalize( float pVal) {

				
				float d = (float) Math.sqrt( cVect[0]  * cVect[0] + cVect[1] * cVect[1] );
				if (d == 0.0f) {
						d = 1.0f;
				}

				//		System.out.println( "Float2 normalize x=" + cVect[0] + " y="+ cVect[1] +" z=" + cVect[2] 
				//												+ " ->" + d   + " pVal:" + pVal );
														
				d = pVal / d;
				cVect[0] *= d;
				cVect[1] *= d;
				
				//	System.out.println( "Float2 normalize x=" + cVect[0] + " y="+ cVect[1] +" z=" + cVect[2] + " d:" +d );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		final public Float2 random( PPgRandom pRand, float pMax ){
				cVect[0] = pRand.nextFloat( pMax );
				cVect[1] = pRand.nextFloat( pMax );

				return this;
		}
		//------------------------------------------------
		final public Float2 randomXY( PPgRandom pRand, float pMax ){
				cVect[0] = pRand.nextFloat( pMax );
				cVect[1] = pRand.nextFloat( pMax );

				return this;
		}
		//------------------------------------------------
		final public Float2 randomX( PPgRandom pRand, float pMax ){
				cVect[0] = pRand.nextFloat( pMax );

				return this;
		}
		//------------------------------------------------
		final public Float2 randomX( PPgRandom pRand,  float pMin, float pMax ){
				cVect[0] = pMin + pRand.nextFloat( pMax-pMin );

				return this;
		}
		//------------------------------------------------
		final public Float2 randomY( PPgRandom pRand, float pMax ){
				cVect[1] = pRand.nextFloat( pMax );

				return this;
		}
		//------------------------------------------------
		final public Float2 randomY( PPgRandom pRand,  float pMin, float pMax ){
				cVect[1] = pMin + pRand.nextFloat( pMax-pMin );

				return this;
		}
		//------------------------------------------------
		final public Float2 randomPositif( PPgRandom pRand, float pMin, float pMax ){
				cVect[0] = pMin + pRand.nextFloat( pMax-pMin );
				cVect[1] = pMin + pRand.nextFloat( pMax-pMin );

				return this;
		}
		//------------------------------------------------
		static public Float2 GetRandom( PPgRandom pRand, float pMax ){
				Float2 lTmp = new Float2();
				return lTmp.random( pRand, pMax  );
		}
		//------------------------------------------------
		static public Float2 GetRandomXY( PPgRandom pRand, float pMax ){
				Float2 lTmp = new Float2();
				return lTmp.randomXY( pRand, pMax  );
		}
		//------------------------------------------------
		static public Float2 GetRandomPositif( PPgRandom pRand, float pMin,  float pMax ){
				Float2 lTmp = new Float2();
				return lTmp.randomPositif( pRand, pMin, pMax  );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------*
		final public void glTexCoord(GL2 pGl) { pGl.glTexCoord2fv( cVect, 0 );}
	
}

//*************************************************
