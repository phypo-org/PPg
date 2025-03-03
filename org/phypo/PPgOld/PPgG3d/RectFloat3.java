package com.phipo.PPg.PPgG3d;





//*************************************************

public class RectFloat3  {

		public Float3     cXYZ;
		public DimFloat3  cSize;

		public RectFloat3( Float3 pPoint, Float3 pSize ){
				cXYZ = new Float3( pPoint );
				cSize = new DimFloat3( pSize );
		}
		//------------------------------------------------
		public final float getWidth()   { return cSize.cVect[0];}
		public final float getHeight()  { return cSize.cVect[1];}
		public final float getDepth()   { return cSize.cVect[2];}

		//------------------------------------------------
		public final boolean contains( Float3 pPos ){
				
				for( int i = 0; i< 3; i ++ ) {

						if( pPos.cVect[i] < cXYZ.cVect[i] )
								return true;
						
						if( pPos.cVect[i] > cXYZ.cVect[i] + cSize.cVect[i] )
								return true;
				}
				return false;
		}
}
//*************************************************
