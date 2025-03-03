package org.phypo.PPgGame.PPgG3d;

import java.util.*;

import org.lwjgl.opengl.GL11;

import org.lwjgl.util.glu.*;

//*************************************************

public class Primitiv3d {


		public Primitiv3d(){
		}

	static public void Red()	     { GL11.glColor3f( 1.0f, 0.0f, 0.0f); 	}
	static public void Green()     { GL11.glColor3f( 0.0f, 1.0f, 0.0f); 	}
	static public void Blue()      { GL11.glColor3f( 0.0f, 0.0f, 1.0f ); }
	static public void LightBlue() { GL11.glColor3f( 0.5f, 0.5f, 1.0f ); }

	static public void Yellow()	   { GL11.glColor3f( 1.0f, 1.0f, 0.0f); 	}
	static public void Pink()	     { GL11.glColor3f( 1.0f, 0.0f, 1.0f); 	}

	static public void White()	   { GL11.glColor3f( 1.0f, 1.0f, 1.0f); 	}
	static public void Black()     { GL11.glColor3f( 0.0f, 0.0f, 0.0f ); }
   
	static public void Grey()	     { GL11.glColor3f( 0.5f, 0.5f, 0.5f); 	}
	static public void LightGrey() { GL11.glColor3f( 0.7f, 0.7f, 0.7f); 	}
	static public void DarkGrey()  { GL11.glColor3f( 0.3f, 0.3f, 0.3f ); }


		
		
		final static public void DrawMinus( float pX, float pY, float pZ, float pSz ){
				GL11.glBegin( GL11.GL_LINES );
				GL11.glVertex3f( pX-2*pSz, pY, pZ );
				GL11.glVertex3f( pX-pSz, pY, pZ );
				GL11.glEnd();
		}
		//------------------------------------------------
		final static public void DrawX( float pX, float pY, float pZ, float pSz ) {
				GL11.glBegin( GL11.GL_LINES );
				//	GL11.glColor3f( 1.0, 0.0, 0.0 );
				GL11.glVertex3f( pX-pSz, pY-pSz, pZ );
				GL11.glVertex3f( pX + pSz, pY + pSz, pZ );
				
				GL11.glVertex3f( pX+pSz, pY-pSz, pZ  );
				GL11.glVertex3f( pX - pSz, pY + pSz, pZ );
				GL11.glEnd();				
		}

		//------------------------------------------------

		final static public void DrawY( float pX, float pY, float pZ, float pSz ) {
				GL11.glBegin( GL11.GL_LINE_STRIP );
				GL11.glVertex3f( pX-pSz, pY+pSz, pZ );
				GL11.glVertex3f( pX, pY, pZ );
				GL11.glVertex3f( pX, pY - pSz, pZ );
				GL11.glVertex3f( pX, pY, pZ );
				GL11.glVertex3f( pX+pSz, pY+pSz, pZ );
				GL11.glEnd();
		}
		//------------------------------------------------
	
		static public void DrawZ( float pX, float pY, float pZ, float pSz ){
				GL11.glBegin( GL11.GL_LINE_STRIP );
				GL11.glVertex3f( pX-pSz, pY+pSz, pZ );
				GL11.glVertex3f( pX + pSz, pY + pSz, pZ );
				GL11.glVertex3f( pX-pSz, pY-pSz, pZ );
				GL11.glVertex3f( pX+pSz, pY- pSz, pZ );
				GL11.glEnd();
				
		}
		//------------------------------------------------
		
		final static public void DrawRepere( float pSz, int  pNeg ) {
				
				GL11.glDisable(GL11.GL_LIGHTING);
				
				Red();
				GL11.glBegin( GL11.GL_LINES );
				GL11.glVertex3f( 0.0f, 0.0f, 0.0f );
				GL11.glVertex3f( pSz, 0.0f, 0.0f );
				GL11.glEnd();
				DrawX( pSz *1.1f, 0.0f, 0.0f, pSz / 20.0f );
				
				
				Green();
				GL11.glColor3f( 0.0f, 1.0f, 0.0f );

				GL11.glBegin( GL11.GL_LINES );
				GL11.glVertex3f( 0.0f, 0.0f, 0.0f );
				GL11.glVertex3f( 0.0f, pSz, 0.0f );
				GL11.glEnd();
				DrawY( 0.0f, pSz*1.1f, 0.0f, pSz/20.0f );
				
				
				Blue();	
				GL11.glBegin( GL11.GL_LINES );
				GL11.glVertex3f( 0.0f, 0.0f, 0.0f );
				GL11.glVertex3f( 0.0f, 0.0f, pSz );
				GL11.glEnd();
				DrawZ( 0.0f, 0.0f, pSz*1.1f, pSz/20.0f );
				
				
				
				
				if( pNeg == 1 ) {
						GL11.glLineStipple( 1, (short)0xFF );
						GL11.glEnable( GL11.GL_LINE_STIPPLE );
						
						Red();
						GL11.glBegin( GL11.GL_LINES );
						GL11.glVertex3f( 0.0f, 0.0f, 0.0f );
						GL11.glVertex3f( -pSz, 0.0f, 0.0f );
						GL11.glEnd();
						
						Green();
						GL11.glBegin( GL11.GL_LINES );
						GL11.glVertex3f( 0.0f, 0.0f, 0.0f );
						GL11.glVertex3f( 0.0f, -pSz, 0.0f );
						GL11.glEnd();
						
						Blue();	
						GL11.glBegin( GL11.GL_LINES );
						GL11.glVertex3f( 0.0f, 0.0f, 0.0f );
						GL11.glVertex3f( 0.0f, 0.0f, -pSz );
						GL11.glEnd();
						
						GL11.glDisable( GL11.GL_LINE_STIPPLE );
						
						Red();
						DrawMinus( pSz*-1.1f, 0.0f, 0.0f, pSz/20.0f );
						DrawX( pSz*-1.1f, 0.0f, 0.0f, pSz/20.0f );
						
						Green();
						DrawMinus( 0.0f, -pSz*1.1f, 0.0f, pSz/20.0f );
						DrawY( 0.0f, -pSz*1.1f, 0.0f, pSz/20.0f );
						
						Blue();	
						DrawMinus( 0.0f, 0.0f, -pSz*1.1f, pSz/20.0f );
						DrawZ( 0.0f, 0.0f, -pSz *1.1f, pSz / 20.0f );
				}

				GL11.glEnable(GL11.GL_LIGHTING);
				
		}
		//------------------------------------------------		
		final static public void DrawGrid(  float pW, float pH, int pNb) {
				// Turn the lines GREEN
 				GL11.glDisable( GL11.GL_LIGHTING );
				Green();
				float lPasW = pW / pNb;
				float lPasH = pH / pNb;
				
				// Draw a 1x1 grid along the X and Z axis'
				for(float i = -pH; i <= pH; i += lPasH ) {
						
						// Start drawing some lines
						GL11.glBegin(GL11.GL_LINES);
						
						// Do the horizontal lines (along the X)
						GL11.glVertex3f(-pW, 0f, i);
						GL11.glVertex3f(pW, 0f, i);
						GL11.glEnd();
				}	
				
				for(float i = -pW; i <= pW; i += lPasW ) {
						
						GL11.glBegin(GL11.GL_LINES);

						// Do the vertical lines (along the Z)
						GL11.glVertex3f(i, 0f, -pH);
						GL11.glVertex3f(i, 0f, pH);
				
						// Stop drawing lines
						GL11.glEnd();
				}
				GL11.glEnable(GL11.GL_LIGHTING);
		}
		//------------------------------------------------		
		final static public void DrawGrid( float pSz, int pNb ) {
				// Turn the lines GREEN
 				GL11.glDisable( GL11.GL_LIGHTING );
				Green();
				float lPas = pSz / pNb;
				
				// Draw a 1x1 grid along the X and Z axis'
				for(float i = -pSz; i <= pSz; i += lPas ) {
						
						// Start drawing some lines
						GL11.glBegin(GL11.GL_LINES);
						
						// Do the horizontal lines (along the X)
						GL11.glVertex3f(-pSz, 0f, i);
						GL11.glVertex3f(pSz, 0f, i);
						
						// Do the vertical lines (along the Z)
						GL11.glVertex3f(i, 0f, -pSz);
						GL11.glVertex3f(i, 0f, pSz);
						
						// Stop drawing lines
						GL11.glEnd();
				}
				GL11.glEnable(GL11.GL_LIGHTING);
		}
		//------------------------------------------------		
		static public void DrawSurf2D( float pSz, int pNb ) {

				float lPas = pSz / pNb;
				float lOrg = pSz / 2;
				
				GL11.glDisable( GL11.GL_LIGHTING );
				
				LightGrey();

				GL11.glBegin( GL11.GL_POINTS );
				for( int x = 0; x < pNb; x++ )
						{
								Float xval = x * lPas - lOrg;
								
								for( int z = 0; z < pNb; z++ )
										{
												float zval = z *lPas - lOrg;
												
												GL11.glVertex3f( xval, 0.0f, zval );
										} 
						}
				GL11.glEnd();
				
				GL11.glEnable(GL11.GL_LIGHTING);
		}
		//------------------------------------------------		
		static public void DrawSurf3D( float pSz, int pNb )	{

				float lPas = pSz / pNb;
				float lOrg = pSz / 2;
				
				
				GL11.glDisable( GL11.GL_LIGHTING );
				GL11.glBegin( GL11.GL_POINTS );

				for( int x = 0; x < pNb; x++ )
						{
								float xval = x * lPas - lOrg;
								
								for( int y = 0; y < pNb; y++ )
										{
												float yval = y *lPas - lOrg;

												for( int z = 0; z < pNb; z++ )
														{
																float zval = z *lPas - lOrg;

																GL11.glVertex3f( xval, yval, zval );

														}
										} 
						}
				GL11.glEnd();
				GL11.glEnable(GL11.GL_LIGHTING);
		}		

		//------------------------------------------------
		final static public void DrawTriangle( Float3  v1, Float3  v2,  Float3  v3, Float3 pNorm ){
				GL11.glBegin( GL11.GL_TRIANGLES );      

				if( pNorm != null )	pNorm.glNormal();
				v1.glVertex( );
				//pNorm.glNormal();
				v2.glVertex();
				//pNorm.glNormal();
				v3.glVertex();

				GL11.glEnd();
		}
		//------------------------------------------------
		final static public void DrawTriangleStrip( Float3  v1, Float3  v2,  Float3  v3 ){

				GL11.glBegin( GL11.GL_LINE_STRIP );

				v1.glVertex();
				v2.glVertex();
				v3.glVertex();
				v1.glVertex( );

				GL11.glEnd();
		}
		//------------------------------------------------
		final static public void DrawSquare( Float3 v1, Float3  v2, Float3  v3, Float3  v4, Float3 pNorm ){

				GL11.glBegin( GL11.GL_QUADS );

				if( pNorm != null )		pNorm.glNormal();

				v1.glVertex( );
				//				if( pNorm != null )	pNorm.glNormal();
				v2.glVertex();
				//				if( pNorm != null )	pNorm.glNormal();
				v3.glVertex();
				//				if( pNorm != null )	pNorm.glNormal();
				v4.glVertex();

				GL11.glEnd();
		}
		//------------------------------------------------
		final static public void DrawSquareStrip( Float3 v1, Float3  v2, Float3  v3, Float3  v4){

				GL11.glBegin( GL11.GL_LINE_STRIP );

				v1.glVertex( );
				v2.glVertex();
				v3.glVertex();
				v4.glVertex();

				GL11.glEnd();
		}

		//------------------------------------------------
		final static public void DrawTriangleNorm( Float3  v1, Float3  v2,  Float3  v3 ){

     
				Float3 lNorm = new Float3();
				Calcul3d.Normal( v1, v2, v3, lNorm );

				GL11.glBegin( GL11.GL_TRIANGLES ); 

				lNorm.glNormal();

				v1.glVertex( );
				//pNorm.glNormal();
				v2.glVertex();
				//pNorm.glNormal();
				v3.glVertex();

				GL11.glEnd();
		}
		//------------------------------------------------
		final static public void DrawSquareNorm( Float3 v1, Float3  v2, Float3  v3, Float3  v4 ){

				Float3 lNorm = new Float3();
				Calcul3d.Normal( v1, v2, v3, lNorm );

				GL11.glBegin( GL11.GL_QUADS );

				lNorm.glNormal();

				v1.glVertex( );
				//				if( pNorm != null )	pNorm.glNormal();
				v2.glVertex();
				//				if( pNorm != null )	pNorm.glNormal();
				v3.glVertex();
				//				if( pNorm != null )	pNorm.glNormal();
				v4.glVertex();

				GL11.glEnd();
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
		protected class SubParam{

				
				int     cDepth;
				float   cFact;


				SubNormalizeType cNormalize;
				boolean cCentralPoint;

				int     cFlagStripFill;
				ArrayList<Facet> cFacet;

				public float cDepthGrowFactor = 1f;
				public float cInitGrowFactor = 0.3f;

				
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

						cFacet = new ArrayList(); // reset for future use !
						
						return lObject3d; 
				}
				//------------------------------------------------
				
		}

		//================================================
		public Primitiv3d.SubParamObject3d GetSubParamObject3d(  int pDepth, float pFact, boolean pCentralPoint,  SubNormalizeType pNormalize ) {

				return new Primitiv3d.SubParamObject3d(  pDepth, pFact, pCentralPoint, pNormalize );
		}
		//================================================
		public Primitiv3d.SubParamDrawing GetSubParamDrawing(  int pDepth, float pFact, boolean pCentralPoint, SubNormalizeType pNormalize, int pFlagStripFill ) {
				return new Primitiv3d.SubParamDrawing(   pDepth, pFact, pCentralPoint, pNormalize, pFlagStripFill );
		}
		//------------------------------------------------
		final static public void Subdivide( Float3 v1, Float3 v2, Float3 v3,  Float3 v4, int  pDepth,  Primitiv3d.SubParam pParam )		{


				if( pDepth <= 0 ){							
						if( pParam.cFacet == null ) {
								if( pParam.cFlagStripFill == GLU.GLU_SILHOUETTE )
										DrawSquareStrip(  v1, v2, v3, v4 );
								else
										DrawSquareNorm( v1, v2, v3, v4 );
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
								if( pParam.cFlagStripFill == GLU.GLU_SILHOUETTE )
										DrawTriangleStrip(  v1, v2, v3 );
								else
										DrawTriangleNorm( v1, v2, v3 );
						} else {
								pParam.cFacet.add( new Facet( v1, v2, v3 ));
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
		
		final static public Primitiv3d.SubParam  Odron( Primitiv3d.SubParam pParam ) {
				
				Float3 lData[] = new Float3[4];
				
				lData[0] = new Float3(  T,  T,  T );
				lData[1] = new Float3(  T, -T, -T );
				lData[2] = new Float3( -T,  T, -T );
				lData[3] = new Float3( -T, -T,  T );

				pParam.normEffectInit( lData );

				Subdivide( lData[0], lData[1], lData[3], pParam.cDepth, pParam );
				Subdivide( lData[2], lData[1], lData[0], pParam.cDepth, pParam );
				Subdivide( lData[3], lData[2], lData[0], pParam.cDepth, pParam );
				Subdivide( lData[1], lData[2], lData[3], pParam.cDepth, pParam );		
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
