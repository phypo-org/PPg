package org.phypo.PPgGame3d;

import java.util.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.gl2.*;
import com.jogamp.opengl.glu.GLU;


//*************************************************

public class Primitiv3d {

		static Primitiv3d  sThePrimitiv3d;

		public enum PrimtivType{
				Pyramid3,
						Pyramid4,
						Cube,
						Parallelepiped,
						Tetrahedron,
						Octahedron,
						Icosahedron,
						Dodecahedron,
						Cylinder,
						Cone,
						Disk,
						PartialDisk,
						Sphere
						};

		public Primitiv3d(){
		}

	static public void Red(GL2 pGl)	      { pGl.glColor3f( 1.0f, 0.0f, 0.0f); 	}
	static public void Green(GL2 pGl)     { pGl.glColor3f( 0.0f, 1.0f, 0.0f); 	}
	static public void Blue(GL2 pGl)      { pGl.glColor3f( 0.0f, 0.0f, 1.0f ); }
	static public void LightBlue(GL2 pGl) { pGl.glColor3f( 0.5f, 0.5f, 1.0f ); }

	static public void Yellow(GL2 pGl)	   { pGl.glColor3f( 1.0f, 1.0f, 0.0f); 	}
	static public void Pink(GL2 pGl)	     { pGl.glColor3f( 1.0f, 0.0f, 1.0f); 	}

	static public void White(GL2 pGl)	   { pGl.glColor3f( 1.0f, 1.0f, 1.0f); 	}
	static public void Black(GL2 pGl)     { pGl.glColor3f( 0.0f, 0.0f, 0.0f ); }
   
	static public void Grey(GL2 pGl)	     { pGl.glColor3f( 0.5f, 0.5f, 0.5f); 	}
	static public void LightGrey(GL2 pGl) { pGl.glColor3f( 0.7f, 0.7f, 0.7f); 	}
	static public void DarkGrey(GL2 pGl)  { pGl.glColor3f( 0.3f, 0.3f, 0.3f ); }


		
		
		final static public void DrawMinus( GL2 pGl, float pX, float pY, float pZ, float pSz ){
				pGl.glBegin( GL2.GL_LINES );
				pGl.glVertex3f( pX-2*pSz, pY, pZ );
				pGl.glVertex3f( pX-pSz, pY, pZ );
				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawX( GL2 pGl, float pX, float pY, float pZ, float pSz ) {
				pGl.glBegin( GL2.GL_LINES );
				//	pGl.glColor3f( 1.0, 0.0, 0.0 );
				pGl.glVertex3f( pX-pSz, pY-pSz, pZ );
				pGl.glVertex3f( pX + pSz, pY + pSz, pZ );
				
				pGl.glVertex3f( pX+pSz, pY-pSz, pZ  );
				pGl.glVertex3f( pX - pSz, pY + pSz, pZ );
				pGl.glEnd();				
		}

		//------------------------------------------------

		final static public void DrawY( GL2 pGl, float pX, float pY, float pZ, float pSz ) {
				pGl.glBegin( GL2.GL_LINE_STRIP );
				pGl.glVertex3f( pX-pSz, pY+pSz, pZ );
				pGl.glVertex3f( pX, pY, pZ );
				pGl.glVertex3f( pX, pY - pSz, pZ );
				pGl.glVertex3f( pX, pY, pZ );
				pGl.glVertex3f( pX+pSz, pY+pSz, pZ );
				pGl.glEnd();
		}
		//------------------------------------------------
	
		static public void DrawZ( GL2 pGl, float pX, float pY, float pZ, float pSz ){
				pGl.glBegin( GL2.GL_LINE_STRIP );
				pGl.glVertex3f( pX-pSz, pY+pSz, pZ );
				pGl.glVertex3f( pX + pSz, pY + pSz, pZ );
				pGl.glVertex3f( pX-pSz, pY-pSz, pZ );
				pGl.glVertex3f( pX+pSz, pY- pSz, pZ );
				pGl.glEnd();
				
		}
		//------------------------------------------------
		
		final static public void DrawRepere( GL2 pGl, float pSz, boolean  pNeg ) {
				
				pGl.glDisable(GL2.GL_LIGHTING);
				
				Red(pGl);
				pGl.glBegin( GL2.GL_LINES );
				pGl.glVertex3f( 0.0f, 0.0f, 0.0f );
				pGl.glVertex3f( pSz, 0.0f, 0.0f );
				pGl.glEnd();
				DrawX( pGl, pSz *1.1f, 0.0f, 0.0f, pSz / 20.0f );
				
				
				Green(pGl);
				pGl.glColor3f( 0.0f, 1.0f, 0.0f );

				pGl.glBegin( GL2.GL_LINES );
				pGl.glVertex3f( 0.0f, 0.0f, 0.0f );
				pGl.glVertex3f( 0.0f, pSz, 0.0f );
				pGl.glEnd();
				DrawY( pGl, 0.0f, pSz*1.1f, 0.0f, pSz/20.0f );
				
				
				Blue(pGl);	
				pGl.glBegin( GL2.GL_LINES );
				pGl.glVertex3f( 0.0f, 0.0f, 0.0f );
				pGl.glVertex3f( 0.0f, 0.0f, pSz );
				pGl.glEnd();
				DrawZ( pGl, 0.0f, 0.0f, pSz*1.1f, pSz/20.0f );
				
				
				
				
				if( pNeg ) {
						pGl.glLineStipple( 1, (short)0xFF );
						pGl.glEnable( GL2.GL_LINE_STIPPLE );
						
						Red(pGl);
						pGl.glBegin( GL2.GL_LINES );
						pGl.glVertex3f( 0.0f, 0.0f, 0.0f );
						pGl.glVertex3f( -pSz, 0.0f, 0.0f );
						pGl.glEnd();
						
						Green(pGl);
						pGl.glBegin( GL2.GL_LINES );
						pGl.glVertex3f( 0.0f, 0.0f, 0.0f );
						pGl.glVertex3f( 0.0f, -pSz, 0.0f );
						pGl.glEnd();
						
						Blue(pGl);	
						pGl.glBegin( GL2.GL_LINES );
						pGl.glVertex3f( 0.0f, 0.0f, 0.0f );
						pGl.glVertex3f( 0.0f, 0.0f, -pSz );
						pGl.glEnd();
						
						pGl.glDisable( GL2.GL_LINE_STIPPLE );
						
						Red(pGl);
						DrawMinus( pGl, pSz*-1.1f, 0.0f, 0.0f, pSz/20.0f );
						DrawX( pGl, pSz*-1.1f, 0.0f, 0.0f, pSz/20.0f );
						
						Green(pGl);
						DrawMinus( pGl, 0.0f, -pSz*1.1f, 0.0f, pSz/20.0f );
						DrawY( pGl, 0.0f, -pSz*1.1f, 0.0f, pSz/20.0f );
						
						Blue(pGl);	
						DrawMinus( pGl, 0.0f, 0.0f, -pSz*1.1f, pSz/20.0f );
						DrawZ( pGl, 0.0f, 0.0f, -pSz *1.1f, pSz / 20.0f );
				}

				White( pGl );

				pGl.glEnable(GL2.GL_LIGHTING);
				
		}
		//------------------------------------------------		
		final static public void DrawGridXZ(  GL2 pGl, float pW, float pH, int pNb) {
				// Turn the lines GREEN
 				pGl.glDisable( GL2.GL_LIGHTING );
				Green(pGl);
				float lPasW = pW / pNb;
				float lPasH = pH / pNb;
				
				// Draw a 1x1 grid along the X and Z axis'
				for(float i = -pH; i <= pH; i += lPasH ) {
						
						// Start drawing some lines
						pGl.glBegin(GL2.GL_LINES);
						
						// Do the horizontal lines (along the X)
						pGl.glVertex3f(-pW, 0f, i);
						pGl.glVertex3f(pW, 0f, i);
						pGl.glEnd();
				}	
				
				for(float i = -pW; i <= pW; i += lPasW ) {
						
						pGl.glBegin(GL2.GL_LINES);

						// Do the vertical lines (along the Z)
						pGl.glVertex3f(i, 0f, -pH);
						pGl.glVertex3f(i, 0f, pH);
				
						// Stop drawing lines
						pGl.glEnd();
				}
				pGl.glEnable(GL2.GL_LIGHTING);
		}
		//------------------------------------------------		
		final static public void DrawGridYZ(  GL2 pGl, float pW, float pH, int pNb) {
				// Turn the lines GREEN
 				pGl.glDisable( GL2.GL_LIGHTING );
				Green(pGl);
				float lPasW = pW / pNb;
				float lPasH = pH / pNb;
				
				// Draw a 1x1 grid along the X and Z axis'
				for(float i = -pH; i <= pH; i += lPasH ) {
						
						// Start drawing some lines
						pGl.glBegin(GL2.GL_LINES);
						
						// Do the horizontal lines (along the X)
						pGl.glVertex3f( 0f, -pW, i);
						pGl.glVertex3f( 0f,  pW, i);
						pGl.glEnd();
				}	
				
				for(float i = -pW; i <= pW; i += lPasW ) {
						
						pGl.glBegin(GL2.GL_LINES);

						// Do the vertical lines (along the Z)
						pGl.glVertex3f( 0f, i,  -pH);
						pGl.glVertex3f( 0f, i,  pH);
				
						// Stop drawing lines
						pGl.glEnd();
				}
				pGl.glEnable(GL2.GL_LIGHTING);
		}
		//------------------------------------------------		
		final static public void DrawGridXY(  GL2 pGl, float pW, float pH, int pNb) {
				// Turn the lines GREEN
 				pGl.glDisable( GL2.GL_LIGHTING );
				Green(pGl);
				float lPasW = pW / pNb;
				float lPasH = pH / pNb;
				
				// Draw a 1x1 grid along the X and Z axis'
				for(float i = -pH; i <= pH; i += lPasH ) {
						
						// Start drawing some lines
						pGl.glBegin(GL2.GL_LINES);
						
						// Do the horizontal lines (along the X)
						pGl.glVertex3f(-pW, i, 0f);
						pGl.glVertex3f(pW, i, 0f);
						pGl.glEnd();
				}	
				
				for(float i = -pW; i <= pW; i += lPasW ) {
						
						pGl.glBegin(GL2.GL_LINES);

						// Do the vertical lines (along the Y)
						pGl.glVertex3f(i,  -pH, 0f);
						pGl.glVertex3f(i,  pH, 0f);
				
						// Stop drawing lines
						pGl.glEnd();
				}
				pGl.glEnable(GL2.GL_LIGHTING);
		}
		//------------------------------------------------		
		final static public void DrawGridXZ( GL2 pGl, float pSz, int pNb ) {
				// Turn the lines GREEN
 				pGl.glDisable( GL2.GL_LIGHTING );
				Green(pGl);
				float lPas = pSz / pNb;
				
				// Draw a 1x1 grid along the X and Z axis'
				for(float i = -pSz; i <= pSz; i += lPas ) {
						
						// Start drawing some lines
						pGl.glBegin(GL2.GL_LINES);
						
						// Do the horizontal lines (along the X)
						pGl.glVertex3f(-pSz, 0f, i);
						pGl.glVertex3f(pSz, 0f, i);
						
						// Do the vertical lines (along the Z)
						pGl.glVertex3f(i, 0f, -pSz);
						pGl.glVertex3f(i, 0f, pSz);
						
						// Stop drawing lines
						pGl.glEnd();
				}
				pGl.glEnable(GL2.GL_LIGHTING);
		}
		//------------------------------------------------		
		static public void DrawSurf2D( GL2 pGl, float pSz, int pNb ) {

				float lPas = pSz / pNb;
				float lOrg = pSz / 2;
				
				pGl.glDisable( GL2.GL_LIGHTING );
				
				LightGrey(pGl);

				pGl.glBegin( GL2.GL_POINTS );
				for( int x = 0; x < pNb; x++ )
						{
								Float xval = x * lPas - lOrg;
								
								for( int z = 0; z < pNb; z++ )
										{
												float zval = z *lPas - lOrg;
												
												pGl.glVertex3f( xval, 0.0f, zval );
										} 
						}
				pGl.glEnd();
				
				pGl.glEnable(GL2.GL_LIGHTING);
		}
		//------------------------------------------------		
		static public void DrawSurf3D( GL2 pGl, float pSz, int pNb )	{

				float lPas = pSz / pNb;
				float lOrg = pSz / 2;
				
				
				pGl.glDisable( GL2.GL_LIGHTING );
				pGl.glBegin( GL2.GL_POINTS );

				for( int x = 0; x < pNb; x++ )
						{
								float xval = x * lPas - lOrg;
								
								for( int y = 0; y < pNb; y++ )
										{
												float yval = y *lPas - lOrg;

												for( int z = 0; z < pNb; z++ )
														{
																float zval = z *lPas - lOrg;

																pGl.glVertex3f( xval, yval, zval );

														}
										} 
						}
				pGl.glEnd();
				pGl.glEnable(GL2.GL_LIGHTING);
		}		

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		final static public void DrawTriangle( GL2 pGl, Float3  v1, Float3  v2,  Float3  v3, Float3 pNorm ){
				pGl.glBegin( GL2.GL_TRIANGLES );      

				if( pNorm != null )	pNorm.glNormal(pGl);
				v1.glVertex(pGl );
				if( pNorm != null )	pNorm.glNormal(pGl);
				v2.glVertex(pGl  );
				if( pNorm != null )	pNorm.glNormal(pGl);
				v3.glVertex(pGl);

				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawTriangleStrip( GL2 pGl, Float3  v1, Float3  v2,  Float3  v3 ){

				pGl.glBegin( GL2.GL_LINE_STRIP );

				v1.glVertex(pGl);
				v2.glVertex(pGl);
				v3.glVertex(pGl);
				v1.glVertex(pGl);

				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawTriangleNorm( GL2 pGl, Float3  v1, Float3  v2,  Float3  v3 ){
     
				Float3 lNorm = new Float3();

				Calcul3d.Normal( v1, v2, v3, lNorm );

				pGl.glBegin( GL2.GL_TRIANGLES ); 

				lNorm.glNormal(pGl );
				v1.glVertex( pGl  );
				lNorm.glNormal(pGl );
				v2.glVertex( pGl );
				lNorm.glNormal(pGl );
				v3.glVertex( pGl );

				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawTriangleTex( GL2 pGl, Float3  v1, Float3  v2,  Float3  v3,
																							Float2 t1, Float2 t2,  Float2 t3 ){
     
				Float3 lNorm = new Float3();
				Calcul3d.Normal( v1, v2, v3, lNorm );

				pGl.glBegin( GL2.GL_TRIANGLES ); 

				lNorm.glNormal(pGl );
				v1.glVertex( pGl  );
				t1.glTexCoord( pGl  );
				lNorm.glNormal(pGl );
				v2.glVertex( pGl );
				t2.glTexCoord( pGl  );
				lNorm.glNormal(pGl );
				v3.glVertex( pGl );
				t3.glTexCoord( pGl  );

				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawSquare( GL2 pGl, Float3 v1, Float3  v2, Float3  v3, Float3  v4, Float3 pNorm ){

					pGl.glBegin( GL2.GL_QUADS );
				if( pNorm != null )		pNorm.glNormal(pGl);
				v1.glVertex(pGl );
				v2.glVertex(pGl );
				v3.glVertex(pGl );
				v4.glVertex(pGl );
				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawSquareStrip( GL2 pGl, Float3 v1, Float3  v2, Float3  v3, Float3  v4){


				pGl.glBegin( GL2.GL_LINE_STRIP );
				v1.glVertex( pGl );
				v2.glVertex( pGl );
				v3.glVertex( pGl );
				v4.glVertex( pGl );
				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawSquareNorm( GL2 pGl, Float3 v1, Float3  v2, Float3  v3, Float3  v4 ){

				Float3 lNorm = new Float3();
				Calcul3d.Normal( v1, v2, v3, lNorm );
				pGl.glBegin( GL2.GL_QUADS );
				lNorm.glNormal(pGl );
				v1.glVertex( pGl  );
				lNorm.glNormal(pGl );
				v2.glVertex( pGl );
				lNorm.glNormal(pGl );
				v3.glVertex( pGl );
				lNorm.glNormal(pGl );
				v4.glVertex( pGl );

				pGl.glEnd();
		}
		//------------------------------------------------
		//------------------------------------------------
		final static public void DrawFacet( GL2 pGl, Float3 pVect[], Float3 pNorm ){

				if( pVect.length == 3 )
						pGl.glBegin( GL2.GL_TRIANGLES );
				else if( pVect.length == 4 )
						pGl.glBegin( GL2.GL_QUADS );
				else 
						pGl.glBegin( GL2.GL_POLYGON );


				if( pNorm != null )		pNorm.glNormal(pGl);

				for( Float3 lFloat3 : pVect )
						lFloat3.glVertex(pGl );

				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawFacetStrip( GL2 pGl, Float3 pVect[]){


				pGl.glBegin( GL2.GL_LINE_STRIP );

				for( Float3 lFloat3 : pVect )
						lFloat3.glVertex(pGl );

				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawFacetNorm( GL2 pGl, Float3 pVect[] ){

				Float3 lNorm = new Float3();
				Calcul3d.Normal( pVect[0], pVect[1], pVect[2], lNorm );

				if( pVect.length == 3 )
						pGl.glBegin( GL2.GL_TRIANGLES );
				else if( pVect.length == 4 )
						pGl.glBegin( GL2.GL_QUADS );
				else 
						pGl.glBegin( GL2.GL_POLYGON );

				lNorm.glNormal(pGl );

				for( Float3 lFloat3 : pVect )
						lFloat3.glVertex(pGl );

				pGl.glEnd();
		}
		
		//------------------------------------------------
		//------------------------------------------------
		final static public void DrawPenta( GL2 pGl, Float3 v1, Float3  v2, Float3  v3, Float3  v4, Float3  v5, Float3 pNorm ){

				pGl.glBegin( GL2.GL_POLYGON );

				if( pNorm != null )		pNorm.glNormal(pGl);

				v1.glVertex(pGl );
				//				if( pNorm != null )	pNorm.glNormal();
				v2.glVertex(pGl );
				//				if( pNorm != null )	pNorm.glNormal();
				v3.glVertex(pGl );
				//				if( pNorm != null )	pNorm.glNormal();
				v4.glVertex(pGl );

				v5.glVertex(pGl );

				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawPentaStrip( GL2 pGl, Float3 v1, Float3  v2, Float3  v3, Float3  v4, Float3  v5){

				pGl.glBegin( GL2.GL_LINE_STRIP );

				v1.glVertex( pGl );
				v2.glVertex( pGl );
				v3.glVertex( pGl );
				v4.glVertex( pGl );
				v5.glVertex( pGl );

				pGl.glEnd();
		}
		//------------------------------------------------
		final static public void DrawPentaNorm( GL2 pGl, Float3  v1, Float3  v2,  Float3  v3, Float3  v4, Float3  v5 ){

     
				Float3 lNorm = new Float3();
				Calcul3d.Normal( v1, v2, v3, lNorm );

				pGl.glBegin( GL2.GL_POLYGON ); 

				lNorm.glNormal(pGl );

				v1.glVertex( pGl  );
				v2.glVertex( pGl );
				v3.glVertex( pGl );
				v4.glVertex( pGl );
				v5.glVertex( pGl );

				pGl.glEnd();
		}

		//================================================

		public enum SubNormalizeType{
				NORMALIZE,
						NORMALIZE_ONLY_INIT, // BELLE ETOILE QD DEPTH
						NORMALIZE_INC_INIT,  // FORME IRREGULIERE 
						NORMALIZE_DEC_INIT, // FORME IRREGULIERE avec trou
						NORMALIZE_HALF_INIT, //BOF
						NORMALIZE_ONLY_SUB,
						NORMALIZE_MUL_SUB,				
						NORMALIZE_DEC_SUB, //  BON
						NORMALIZE_INC_SUB, // TRES BON
						NORMALIZE_MUL_INIT, // Structure avec trou ou pic celon cInitGrowFactor
						NORMALIZE_NONE  // DEVIENT TROP PETIT 
						};
		
		//================================================
		public class SubParam{

				public boolean cUseTexture=false;

				public int     cDepth;
				public float   cFact;


				public SubNormalizeType cNormalize;
				public boolean cCentralPoint;

				public int     cFlagStripFill;
				ArrayList<Facet> cFacet;

				public float cDepthGrowFactor = 1f;
				public float cInitGrowFactor = 0.3f;

				public int    cHoleFacet = -1;
				public int    cHoleDepth = -1;



				//------------------------------------------------
				protected SubParam( int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType pNormalize ){
						reset( pDepth, pFact, pCentralPoint, pNormalize );
				}
				//------------------------------------------------
				protected  void reset( int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType pNormalize){						
						cDepth = pDepth;
						cFact  = pFact;
						cCentralPoint = pCentralPoint;
						cNormalize = pNormalize;
				}
				public Object3d getObject3d() {
						return null;
				}
				//------------------------------------------------
				public void normEffectSub( Float3 pVal, int  pDepth){
						
						switch( cNormalize ){
						case NORMALIZE:
						case NORMALIZE_ONLY_SUB:
						case NORMALIZE_HALF_INIT:
						case NORMALIZE_DEC_INIT:
						case NORMALIZE_INC_INIT:
						case NORMALIZE_MUL_INIT:
								pVal.normalize( cFact );
								break;
								
						case 	NORMALIZE_DEC_SUB:
								pVal.normalize( cFact / (1+pDepth*cDepthGrowFactor) );
								break;
								
						case 	NORMALIZE_INC_SUB:
								pVal.normalize( cFact * (1+pDepth*cDepthGrowFactor) );
								break;
						case 	NORMALIZE_MUL_SUB:
								pVal.normalize( cFact *cDepthGrowFactor );
								break;

						case NORMALIZE_ONLY_INIT:								
						case NORMALIZE_NONE:
								pVal.multiply( cFact );
								break;
						}
				}	 
				//------------------------------------------------
				public void normEffectInit( Float3 pArray [] ){
						
						for( int i = 0; i< pArray.length; i++ ) {
								
								switch( cNormalize ){
										
								case NORMALIZE:
								case NORMALIZE_DEC_SUB:
								case NORMALIZE_INC_SUB:
								case NORMALIZE_MUL_SUB:
								case NORMALIZE_ONLY_INIT:	
										pArray[i].normalize( cFact );
										break;
										
								case NORMALIZE_HALF_INIT:
										if( i % 2 == 0  )
												pArray[i].normalize( cFact );
										else
												pArray[i].multiply( cFact );
										break;
										
								case NORMALIZE_DEC_INIT:
										pArray[i].normalize( cFact / (1+i*cInitGrowFactor) );
										break;
										
								case NORMALIZE_INC_INIT:			
										pArray[i].normalize( cFact * (1+i*cInitGrowFactor) );
										break;

								case	NORMALIZE_MUL_INIT:								
										pArray[i].normalize( cFact *cInitGrowFactor );
										break;


								case NORMALIZE_NONE:										
								case NORMALIZE_ONLY_SUB:
										pArray[i].multiply( cFact );
										break;
								}
						}	 
				}
		}				
		//================================================
		// For direct drawing 
		public class SubParamDrawing extends SubParam {
				
				SubParamDrawing( int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType pNormalize,  int pFlagStripFill ){
						super( pDepth, pFact, pCentralPoint, pNormalize);

						cFlagStripFill =  pFlagStripFill;
				}
				//------------------------------------------------
				public void set( int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType pNormalize,  int pFlagStripFill  ){	
						
						reset( pDepth, pFact, pCentralPoint, pNormalize );
						cFlagStripFill =  pFlagStripFill;
				}
		}
		//================================================
		// For create an Object3d
		public class SubParamObject3d extends SubParam {
								
				public SubParamObject3d( int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType pNormalize ){
						super( pDepth, pFact, pCentralPoint, pNormalize );
						
						cFacet = new ArrayList();
				}	 
				//------------------------------------------------
				public void set( int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType  pNormalize ){	
						
						reset( pDepth, pFact, pCentralPoint, pNormalize );

						cFacet = new ArrayList();
				}
				//------------------------------------------------
				public Object3d getObject3d() {

						Facet [] lFacetArray = new Facet[ cFacet.size() ];
						Object3d lObject3d = new Object3d( cFacet.toArray( lFacetArray )  );
						lObject3d.setUseTexture( cUseTexture);

						cFacet = new ArrayList(); // reset for future use !
					
						return lObject3d; 
				}
				//------------------------------------------------
				
		}

		//================================================
		public Primitiv3d.SubParamObject3d getSubParamObject3d(  int pDepth, float pFact, boolean pCentralPoint,  SubNormalizeType pNormalize ) {
				return new Primitiv3d.SubParamObject3d( pDepth, pFact, pCentralPoint, pNormalize );
		}
		//------------------------------------------------
		static public Primitiv3d.SubParamObject3d GetSubParamObject3d(  int pDepth, float pFact, boolean pCentralPoint,  SubNormalizeType pNormalize ) {
				if( sThePrimitiv3d == null)	 sThePrimitiv3d= new Primitiv3d();
				
				return sThePrimitiv3d.getSubParamObject3d(  pDepth, pFact, pCentralPoint, pNormalize );
		}
		//================================================
		public Primitiv3d.SubParamDrawing getSubParamDrawing(  int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType pNormalize, int pFlagStripFill ) {
				return new Primitiv3d.SubParamDrawing(   pDepth, pFact, pCentralPoint, pNormalize, pFlagStripFill );
		}
		//------------------------------------------------
		static public Primitiv3d.SubParamDrawing GetSubParamDrawing(  int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType pNormalize, int pFlagStripFill ) {
					if( sThePrimitiv3d == null)	sThePrimitiv3d = new Primitiv3d();

					return sThePrimitiv3d.getSubParamDrawing( pDepth, pFact, pCentralPoint, pNormalize, pFlagStripFill );
		}
		//------------------------------------------------
		// PAS TESTER  PAS TESTER  PAS TESTER  PAS TESTER  PAS TESTER  PAS TESTER  PAS TESTER  PAS TESTER 
		final static public void SubdivideN( Float3 pVect[],  int  pDepth,  Primitiv3d.SubParam pParam )		{

				if( pDepth <= 0 ){	

						if( pParam.cHoleFacet >= pVect.length   && pParam.cHoleDepth <= pParam.cDepth  )				return;
	
						if( pParam.cFacet == null ) {
								if( pParam.cFlagStripFill == GLUgl2.GLU_SILHOUETTE )
										DrawFacetStrip( Engine.sTheGl, pVect );
								else
										DrawFacetNorm( Engine.sTheGl, pVect );
						} else {
								pParam.cFacet.add( new Facet( pVect));
						}
						return;
				}

				pDepth--;

				if( pParam.cCentralPoint ) {
						Float3 v0 = Float3.Middle( pVect );	
						pParam.normEffectSub( v0, pDepth  );

						for( int i=0; i< pVect.length; i++ ){
								Subdivide( pVect[i], pVect[(i+1)%pVect.length], v0, pDepth, pParam );
						}
				} else  {
						Float3 vn []  = new Float3[ pVect.length ];

						for( int i= 0; i < pVect.length; i++ ){
								vn[i] = Float3.Middle( pVect[i], pVect[(i+1)%pVect.length ] );
								pParam.normEffectSub( vn[i] , pDepth  );
						}

						for( int i= 0; i < pVect.length; i++ ){
								Subdivide( pVect[i], vn[i], vn[(i+pVect.length-1)%pVect.length], pDepth, pParam );
						}
				}
		}

		//------------------------------------------------
		final static public void Subdivide5( Float3 v1, Float3 v2, Float3 v3,  Float3 v4, Float3 v5, int  pDepth,  Primitiv3d.SubParam pParam )		{


				if( pDepth <= 0  ){	

					if( pParam.cHoleFacet <= 5 && pParam.cHoleDepth <= pParam.cDepth 
							&& pParam.cHoleDepth != -1 && pParam.cDepth != -1 )				return;

						if( pParam.cFacet == null ) {
								if( pParam.cFlagStripFill == GLUgl2.GLU_SILHOUETTE )
										DrawPentaStrip( Engine.sTheGl, v1, v2, v3, v4, v5 );
								else
										DrawPentaNorm( Engine.sTheGl, v1, v2, v3, v4, v5 );
						} else {
								pParam.cFacet.add( new Facet( v1, v2, v3, v4, v5 ));
						}
						return;
				}

				pDepth--;

				if( pParam.cCentralPoint ) {
						Float3 v0 = Float3.Middle( v1, v2, v3, v4, v5 );	
						pParam.normEffectSub( v0, pDepth  );

						Subdivide( v1, v2, v0, pDepth, pParam );
						Subdivide( v2, v3, v0, pDepth, pParam );
						Subdivide( v3, v4, v0, pDepth, pParam );
						Subdivide( v4, v5, v0, pDepth, pParam );
						Subdivide( v5, v1, v0, pDepth, pParam );

				} else  {
						Float3 v12 = Float3.Middle( v1, v2 );
						Float3 v23 = Float3.Middle( v2, v3 );
						Float3 v34 = Float3.Middle( v3, v4 );
						Float3 v45 = Float3.Middle( v4, v5 );
						Float3 v51 = Float3.Middle( v5, v1 );

						pParam.normEffectSub( v12, pDepth  );
						pParam.normEffectSub( v23, pDepth  );
						pParam.normEffectSub( v34, pDepth  );
						pParam.normEffectSub( v45, pDepth  );
						pParam.normEffectSub( v51, pDepth  );
						
						Subdivide( v1, v12, v51, pDepth, pParam );
						Subdivide( v2, v23, v12, pDepth, pParam);
						Subdivide( v3, v34, v23, pDepth, pParam );
						Subdivide( v4, v45, v34, pDepth, pParam );
						Subdivide( v5, v51, v45, pDepth, pParam );

						Subdivide5( v12, v23, v34, v45, v51, pDepth, pParam );
				}
		}
		//------------------------------------------------
		final static public void Subdivide( Float3 v1, Float3 v2, Float3 v3,  Float3 v4, int  pDepth,  Primitiv3d.SubParam pParam )		{


				if( pDepth <= 0  ){							

						if( pParam.cHoleFacet <= 4  && pParam.cHoleDepth <= pParam.cDepth
								&& pParam.cHoleDepth != -1 && pParam.cDepth != -1)				return;




						if( pParam.cFacet == null ) {
								
								if( pParam.cFlagStripFill == GLUgl2.GLU_SILHOUETTE ) {
										DrawSquareStrip( Engine.sTheGl, v1, v2, v3, v4 );
								}
								else {
										DrawSquareNorm( Engine.sTheGl, v1, v2, v3, v4 );
								}
						} else {

								pParam.cFacet.add( new Facet( v1, v2, v3, v4 ));
						}
						return;
				}

				pDepth--;

				if( pParam.cCentralPoint ) {
						Float3 v0 = Float3.Middle( v1, v2, v3, v4 );	
						pParam.normEffectSub( v0, pDepth  );

						Subdivide( v1, v2, v0, pDepth, pParam );
						Subdivide( v2, v3, v0, pDepth, pParam );
						Subdivide( v3, v4, v0, pDepth, pParam );
						Subdivide( v4, v1, v0, pDepth, pParam );

				} else  {
						Float3 v12 = Float3.Middle( v1, v2 );
						Float3 v23 = Float3.Middle( v2, v3 );
						Float3 v34 = Float3.Middle( v3, v4 );
						Float3 v41 = Float3.Middle( v4, v1 );
						
						pParam.normEffectSub( v12, pDepth  );
						pParam.normEffectSub( v23, pDepth  );
						pParam.normEffectSub( v34, pDepth  );
						pParam.normEffectSub( v41, pDepth  );
						
						Subdivide( v1, v12, v41, pDepth, pParam );
						Subdivide( v2, v23, v12, pDepth, pParam);
						Subdivide( v3, v34, v23, pDepth, pParam );
						Subdivide( v4, v41, v34, pDepth, pParam);
						Subdivide( v12, v23, v34, v41, pDepth, pParam );
				}
		}

		//------------------------------------------------
		final static public void Subdivide( Float3 v1, Float3 v2, Float3 v3, int  pDepth,  Primitiv3d.SubParam pParam )		{
				
				if( pDepth <= 0 ){							

						if( pParam.cFacet == null ) {
								if( pParam.cFlagStripFill ==  GLUgl2.GLU_SILHOUETTE )
										DrawTriangleStrip( Engine.sTheGl, v1, v2, v3 );
								else
										DrawTriangleNorm( Engine.sTheGl, v1, v2, v3 );
						} else {
								pParam.cFacet.add( new Facet( v1, v2, v3 ) );
						}
						return;
				}

				pDepth--;

				if( pParam.cCentralPoint ) {

						Float3 v0 = Float3.Middle( v1, v2, v3 );	

						pParam.normEffectSub( v0, pDepth  );

						Subdivide( v1, v2, v0, pDepth, pParam );
						Subdivide( v2, v3, v0, pDepth, pParam );
						Subdivide( v3, v1, v0, pDepth, pParam );
				} else { 		
								
						Float3 v12 = Float3.Middle( v1, v2 );
						Float3 v23 = Float3.Middle( v2, v3 );
						Float3 v31 = Float3.Middle( v3, v1 );
						
						pParam.normEffectSub( v12, pDepth  );
						pParam.normEffectSub( v23, pDepth  );
						pParam.normEffectSub( v31, pDepth  );

						Subdivide( v1, v12, v31,  pDepth, pParam);
						Subdivide( v2, v23, v12,  pDepth, pParam );
						Subdivide( v3, v31, v23,  pDepth, pParam);
						Subdivide( v12, v23, v31, pDepth, pParam );
				}
		}
		//------------------------------------------------
		final static public void Subdivide3Tex( Float3 v1, Float3 v2, Float3 v3, 
																				Float2 t1, Float2 t2, Float2 t3,
																				int  pDepth,  Primitiv3d.SubParam pParam )		{
				
				System.out.println( "*** Subdivide3Tex" );

				if( pDepth <= 0 ){			
						if( pParam.cFacet == null ) {		
								DrawTriangleTex( Engine.sTheGl, v1, v2, v3, t1, t2, t3 );
						} else {
								pParam.cFacet.add( new Facet( v1, v2, v3, t1, t2, t3 ));
						}
						return;
				}
				pDepth--;

				if( pParam.cCentralPoint ) {

						Float3 v0 = Float3.Middle( v1, v2, v3 );	

						pParam.normEffectSub( v0, pDepth  );

						Float2 t0 = Float2.Middle( t1, t2, t3 );	

						Subdivide3Tex( v1, v2, v0, t1, t2, t0, pDepth, pParam );
						Subdivide3Tex( v2, v3, v0, t2, t3, t0, pDepth, pParam );
						Subdivide3Tex( v3, v1, v0, t3, t1, t0, pDepth, pParam );

				} else { 		
								
						Float3 v12 = Float3.Middle( v1, v2 );
						Float3 v23 = Float3.Middle( v2, v3 );
						Float3 v31 = Float3.Middle( v3, v1 );
												
						pParam.normEffectSub( v12, pDepth  );
						pParam.normEffectSub( v23, pDepth  );
						pParam.normEffectSub( v31, pDepth  );

						Float2 t12 = Float2.Middle( t1, t2 );
						Float2 t23 = Float2.Middle( t2, t3 );
						Float2 t31 = Float2.Middle( t3, t1 );

						Subdivide3Tex( v1, v12, v31, t1, t12, t31,  pDepth, pParam);
						Subdivide3Tex( v2, v23, v12, t2, t23, t12,  pDepth, pParam );
						Subdivide3Tex( v3, v31, v23, t3, t31, t23,  pDepth, pParam);
						Subdivide3Tex( v12, v23, v31, t12, t23, t31, pDepth, pParam );
				}
		}
		//------------------------------------------------
		static public Primitiv3d.SubParam
				Parallelepiped( float pSzX, float pSzY, float pSzZ, Primitiv3d.SubParam pParam ){


				
				Float3 lA = new Float3(  pSzX*0.5f, -pSzY*0.5f, -pSzZ*0.5f ); 
				Float3 lB = new Float3(  pSzX*0.5f,  pSzY*0.5f, -pSzZ*0.5f );    
				Float3 lC = new Float3(  -pSzX*0.5f,  pSzY*0.5f, -pSzZ*0.5f );    
				Float3 lD = new Float3(  -pSzX*0.5f,  -pSzY*0.5f, -pSzZ*0.5f );    

				Float3 lE = new Float3(  pSzX*0.5f, -pSzY*0.5f, pSzZ*0.5f ); 
				Float3 lF = new Float3(  pSzX*0.5f,  pSzY*0.5f, pSzZ*0.5f );    
				Float3 lG = new Float3(  -pSzX*0.5f,  pSzY*0.5f, pSzZ*0.5f );    
				Float3 lH = new Float3(  -pSzX*0.5f,  -pSzY*0.5f, pSzZ*0.5f );  


				Float3 lData[] = { 
						lA, lB, lC, lD, lE, lF, lG, lH 
				};

				pParam.normEffectInit( lData );

				Subdivide( lA, lB, lC, lD, pParam.cDepth, pParam );						
				Subdivide( lE, lH, lG, lF, pParam.cDepth, pParam );					
				Subdivide( lA, lE, lF, lB, pParam.cDepth, pParam );						
				Subdivide( lD, lC, lG, lH, pParam.cDepth, pParam );						
				Subdivide( lB, lF, lG, lC, pParam.cDepth, pParam );
				Subdivide( lA, lD, lH, lE, pParam.cDepth, pParam );			

				return pParam;
		}

		//------------------------------------------------
		static public Primitiv3d.SubParam Cube(  float pSz, Primitiv3d.SubParam pParam ){
				return Parallelepiped( pSz, pSz, pSz, pParam);
		}																																			 
   //------------------------------------------------
		final static public Primitiv3d.SubParam
				Pyramid4(  float pX, float pY, float pZ, float  pWidth, float pHeight, Primitiv3d.SubParam pParam  ){

				Float3 lTop    = new Float3( pX, pY + pHeight, pZ );
				Float3 lFrontLeft  = new Float3( pX - pWidth, pY - pHeight, pZ + pWidth);    // Front left point
				Float3 lFrontRight = new Float3( pX + pWidth, pY - pHeight, pZ + pWidth);    // Front right point
        Float3 lBackRight  = new Float3( pX + pWidth, pY - pHeight, pZ - pWidth);    // Back right point
        Float3 lBackLeft   = new Float3 (pX - pWidth, pY - pHeight, pZ - pWidth);    // Back left point

				Float3 lData[] = { lTop, lFrontLeft, lFrontRight, lBackRight, lBackLeft };
				pParam.normEffectInit( lData );

				
				Facet lPyramid4[] = new Facet[5];

				Subdivide( lTop, lFrontRight, lFrontLeft, pParam.cDepth, pParam );
				Subdivide( lTop, lFrontLeft, lBackLeft, pParam.cDepth, pParam );
				Subdivide( lTop, lBackLeft, lBackRight, pParam.cDepth, pParam );
				Subdivide( lTop, lBackRight, lFrontRight, pParam.cDepth, pParam );
				Subdivide( lFrontRight, lFrontLeft, lBackLeft, lBackRight, pParam.cDepth, pParam );

				return pParam;
		}
			//------------------------------------------------
		final static float T  = 1.73205080756887729f;
		
		final static public Primitiv3d.SubParam Odron( Primitiv3d.SubParam pParam ) {
				
				Float3 lData[] = new Float3[4];
				
				lData[0] = new Float3(  T,  T,  T );
				lData[1] = new Float3(  T, -T, -T );
				lData[2] = new Float3( -T,  T, -T );
				lData[3] = new Float3( -T, -T,  T );

				pParam.normEffectInit( lData );


				if( pParam.cUseTexture ){

						//				System.out.println( "********** Odron Text");
						// A MODIFIER  il faut modifier les coordonnees qui sont fauses

						Float2 lTexa[] = new Float2[ 3 ];
						lTexa[ 0 ] = new Float2( 0.5f, 1.0f ); //0.0						
						lTexa[ 1 ] = new Float2( 0.0f, 0.0f ); //1.0;						
						lTexa[ 2 ] = new Float2( 1.0f, 0.0f ); //1.0
	
						
						Subdivide3Tex( lData[0], lData[1], lData[3], lTexa[0], lTexa[1], lTexa[2], pParam.cDepth, pParam );
						Subdivide3Tex( lData[2], lData[1], lData[0], lTexa[0], lTexa[1], lTexa[2], pParam.cDepth, pParam );
						Subdivide3Tex( lData[3], lData[2], lData[0], lTexa[0], lTexa[1], lTexa[2], pParam.cDepth, pParam );
						Subdivide3Tex( lData[1], lData[2], lData[3], lTexa[0], lTexa[1], lTexa[2], pParam.cDepth, pParam );		
						
				} else {						
						Subdivide( lData[0], lData[1], lData[3], pParam.cDepth, pParam );
						Subdivide( lData[2], lData[1], lData[0], pParam.cDepth, pParam );
						Subdivide( lData[3], lData[2], lData[0], pParam.cDepth, pParam );
						Subdivide( lData[1], lData[2], lData[3], pParam.cDepth, pParam );		
				}




				return pParam;
		}
		//------------------------------------------------
		final static public  Primitiv3d.SubParam Octodron( Primitiv3d.SubParam pParam )
		{
				Float3 lData[] = new Float3[6];
				
				lData[0] = new Float3(  T,  0,  0 );
				lData[1] = new Float3( -T,  0,  0 );
				lData[2] = new Float3(  0,  T,  0 );
				lData[3] = new Float3(  0, -T,  0 );
				lData[4] = new Float3(  0,  0,  T );
				lData[5] = new Float3(  0,  0, -T );

				pParam.normEffectInit( lData );

				Subdivide( lData[0], lData[4], lData[2], pParam.cDepth, pParam );
				Subdivide( lData[2], lData[4], lData[1], pParam.cDepth, pParam );
				Subdivide( lData[1], lData[4], lData[3], pParam.cDepth, pParam );
				Subdivide( lData[3], lData[4], lData[0], pParam.cDepth, pParam );
				Subdivide( lData[0], lData[2], lData[5], pParam.cDepth, pParam );
				Subdivide( lData[2], lData[1], lData[5], pParam.cDepth, pParam );
				Subdivide( lData[1], lData[3], lData[5], pParam.cDepth, pParam );
				Subdivide( lData[3], lData[0], lData[5], pParam.cDepth, pParam );
				return pParam;
		}
		//------------------------------------------------

}
//*************************************************
