package org.phypo.PPgGame3d;





//*************************************************

public class RectFloat3  {

		public Float3     cXYZ;
		public DimFloat3  cSize;

		public RectFloat3( float pX, float pY, float pZ,
											 float pSzX, float pSzY, float pSzZ){

				cXYZ = new Float3( pX, pY, pZ);
				cSize = new DimFloat3( pSzX, pSzY, pSzZ );
		}

		public RectFloat3( Float3 pPoint, DimFloat3 pSize ){
				cXYZ  = new Float3( pPoint );
				cSize = new DimFloat3( pSize );
		}

		public RectFloat3( DimFloat3 pSize ){
				this( new Float3( -pSize.x()/2,
													-pSize.y()/2, 
													-pSize.z()/2),
							pSize );
		}
		//------------------------------------------------
		public final float getWidth()   { return cSize.cVect[0];}
		public final float getHeight()  { return cSize.cVect[1];}
		public final float getDepth()   { return cSize.cVect[2];}

		//------------------------------------------------
		public final boolean contains( Float3 pPos ){
				
				for( int i = 0; i< 3; i ++ ) {

						if( pPos.cVect[i] < cXYZ.cVect[i] 
								||  pPos.cVect[i] > (cXYZ.cVect[i] + cSize.cVect[i] ))
								return false;
				}
				return true;
		}
		//------------------------------------------------		
		final public String toString() {

				String lStr = new String();

				lStr = '(' + cXYZ.toString() + ':'+ cSize.toString() +')';

				return lStr;
		}
}
//*************************************************
