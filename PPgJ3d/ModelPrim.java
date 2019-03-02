package org.phypo.PPg.PPgJ3d;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.gl2.*;


//*************************************************
public class ModelPrim extends ModelBase {

		public enum PrimTyp{ REPERE, GRID,
						SURF2D, SURF3D, ODRON };

		PrimTyp cPrimType;
		
		float cSz;
		int   cNeg;
		float cX;
		float cY;
		float cZ;		
		float cWidth;
		float cHeight;

		int cFlagStripFill =  GLUgl2.GLU_FILL ;

		//------------------------------------------------
		private ModelPrim(PrimTyp pPrim ){
				cPrimType= pPrim;
		}		
	
		//------------------------------------------------
		public void renderGL( GL2 pGl, Aspect3d pAspect ){

				if( pAspect != null ){
				}
				
				switch( cPrimType ){
				case REPERE:	Primitiv3d.DrawRepere( pGl, cSz, cNeg == 1 );  break;
				case GRID:    Primitiv3d.DrawGridXZ(   pGl, cSz, cNeg );  break;
				case SURF2D:  Primitiv3d.DrawSurf2D( pGl, cSz, cNeg );  break;
				case SURF3D:  Primitiv3d.DrawSurf3D( pGl, cSz, cNeg );  break;
				case ODRON:
						Primitiv3d lPrim = new Primitiv3d();
						//						Primitiv3d.SubParamDrawing lParam = lPrim.GetSubParamDrawing( 0, cSz, cNeg, false, true, cFlagStripFill );
						//						Primitiv3d.Odron( lParam );
						break;
				}
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static ModelPrim MakeRepere( float pSz, int  pNeg){
				
				ModelPrim lPrim =  new ModelPrim( PrimTyp.REPERE );
				lPrim.cSz = pSz;
				lPrim.cNeg = pNeg;
				
				return lPrim;
		}
		//------------------------------------------------
		public static ModelPrim MakeGrid( float pSz, int  pNeg){
				
				ModelPrim lPrim =  new ModelPrim( PrimTyp.GRID );
				lPrim.cSz = pSz;
				lPrim.cNeg = pNeg;
				
				return lPrim;
		} 
		//------------------------------------------------
		public static ModelPrim MakeOdron( float pSz, int  pNeg,
																					int pFlagStripFill){
				
				ModelPrim lPrim =  new ModelPrim( PrimTyp.ODRON );
				lPrim.cFlagStripFill = pFlagStripFill;
				lPrim.cSz = pSz;
				lPrim.cNeg = pNeg;
				
				return lPrim;
		}
		//------------------------------------------------
		public static ModelPrim MakeSurf2d( float pSz, int  pNeg){
				
				ModelPrim lPrim =  new ModelPrim( PrimTyp.SURF2D );
				lPrim.cSz = pSz;
				lPrim.cNeg = pNeg;
				
				return lPrim;
		}
		//------------------------------------------------
		public static ModelPrim MakeSurf3d( float pSz, int  pNeg){
				
				ModelPrim lPrim =  new ModelPrim( PrimTyp.SURF3D );
				lPrim.cSz = pSz;
				lPrim.cNeg = pNeg;
				
				return lPrim;
		}

}
//*************************************************
