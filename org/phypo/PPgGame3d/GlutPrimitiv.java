package org.phypo.PPgGame3d;

import java.util.*;


import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.gl2.*;



import java.lang.Math;

//*************************************************

public class GlutPrimitiv{
		
		
		//------------------------------------------------
		static public void 
				Torus( float r,  float R, int nsides, int rings )
		{
				int i, j;
				GL2 pGl = Engine.sTheGl;
				
				float ringDelta = (float)((2.0 * Math.PI) / rings);
				float sideDelta = (float)((2.0 * Math.PI) / nsides);

				float theta = 0.0f;
				float cosTheta = 1.0f;
				float sinTheta = 0.0f;
				
				for (i = rings - 1; i >= 0; i--) {
						float theta1 = theta + ringDelta;
						float cosTheta1 = (float)Math.cos(theta1);
						float sinTheta1 = (float)Math.sin(theta1);
						
						pGl.glBegin( GL2.GL_QUAD_STRIP);
						float phi = 0.0f;
						
						for (j = nsides; j >= 0; j--) {
								float cosPhi, sinPhi, dist;
								
								phi += sideDelta;
								cosPhi = (float)Math.cos(phi);
								sinPhi = (float)Math.sin(phi);
								dist = R + r * cosPhi;
								
								
								pGl.glNormal3f( cosTheta1 * cosPhi, -sinTheta1 * cosPhi, sinPhi);
								pGl.glVertex3f( cosTheta1 * dist, -sinTheta1 * dist, r * sinPhi);
								pGl.glNormal3f( cosTheta * cosPhi, -sinTheta * cosPhi, sinPhi);
								pGl.glVertex3f( cosTheta * dist, -sinTheta * dist,  r * sinPhi);
						}
						pGl.glEnd();
						
						theta = theta1;
						cosTheta = cosTheta1;
						sinTheta = sinTheta1;
				}
		}
		
		//------------------------------------------------
		static public void 
				WireTorus( float r,  float R, int nsides, int rings ){
				GL2 pGl = Engine.sTheGl;

				pGl.glPushAttrib( GL2.GL_POLYGON_BIT);
				pGl.glPolygonMode( GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
				Torus( r, R, nsides,  rings);
				pGl.glPopAttrib();
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		//	static float sDodec[][];

		static Float3 sDodec[];

		static void InitDodecahedron(){
				
				float alpha, beta;

				sDodec = new Float3[20];
				
				alpha = (float)(Math.sqrt(2.0 / (3.0 + Math.sqrt(5.0))));
				beta = (float)(1.0 + Math.sqrt(6.0 / (3.0 + Math.sqrt(5.0)) -
																			 2.0 + 2.0 * Math.sqrt(2.0 / (3.0 + Math.sqrt(5.0)))));
		
				sDodec[0] = new Float3( -alpha, 0, beta);
				sDodec[1] = new Float3(  alpha, 0,  beta);
				sDodec[2] = new Float3( -1f,  -1f, -1f);
				sDodec[3] = new Float3( -1f,  -1f,  1f);
				sDodec[4] = new Float3( -1f,  1f, -1f);
				sDodec[5] = new Float3( -1f,  1f,  1f);
				sDodec[6] = new Float3( 1f,  -1f,  -1f);
				sDodec[7] = new Float3( 1f, -1f,  1f);
				sDodec[8] = new Float3( 1f,  1f,  -1f);
				sDodec[9] = new Float3( 1f,  1f, 1f);
				sDodec[10] = new Float3( beta,  alpha,  0);
				sDodec[11] = new Float3( beta,  -alpha, 0);
				sDodec[12] = new Float3( -beta, alpha,  0);
				sDodec[13] = new Float3( -beta,  -alpha,  0);
				sDodec[14] = new Float3( -alpha,  0,  -beta);
				sDodec[15] = new Float3( alpha, 0,  -beta);
				sDodec[16] = new Float3( 0,  beta,  alpha);
				sDodec[17] = new Float3( 0,  beta,  -alpha);
				sDodec[18] = new Float3( 0,  -beta,  alpha);
				sDodec[19] = new Float3( 0,  -beta,  -alpha);

		}

		final static int  sDodecaIdx[][] = {
				{0, 1, 9, 16, 5},
				{1, 0, 3, 18, 7},
				{1, 7, 11, 10, 9},
				{11, 7, 18, 19, 6},
				{8, 17, 16, 9, 10},
				{2, 14, 15, 6, 19},
				{2, 13, 12, 4, 14},
				{2, 19, 18, 3, 13},
				{3, 0, 5, 12, 13},
				{6, 15, 8, 10, 11},
				{4, 17, 8, 15, 14},
				{4, 12, 5, 16, 17}
		};
		//------------------------------------------------
		/*		
		static void Pentagon( int i, Float3 data[], int ndx[][], Primitiv3d.SubParam pParam) {

				//		System.out.println( "  DrawTriangle "  + i);

				Float3 x0 = data[ ndx[i][0] ];
				Float3 x1 = data[ ndx[i][1] ];
				Float3 x2 = data[ ndx[i][2] ];
				Float3 x3 = data[ ndx[i][4] ];
				Float3 x4 = data[ ndx[i][5] ];

				Primitiv3d.Subdivide5( x0, x1, x2, x3, x4, pParam.cDepth, pParam );	
		}
		*/
		//------------------------------------------------
		static public Primitiv3d.SubParam Dodecahedron( Primitiv3d.SubParam pParam  ) {

				if( sDodec == null )InitDodecahedron();

				pParam.normEffectInit( sDodec );

				Primitiv3d.Subdivide5( sDodec[0], sDodec[ 1], sDodec[ 9], sDodec[ 16], sDodec[ 5], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[1], sDodec[ 0], sDodec[ 3], sDodec[ 18], sDodec[ 7], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[1], sDodec[ 7], sDodec[ 11], sDodec[ 10], sDodec[ 9], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[11], sDodec[ 7], sDodec[ 18], sDodec[ 19], sDodec[ 6], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[8], sDodec[ 17], sDodec[ 16], sDodec[ 9], sDodec[ 10], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[2], sDodec[ 14], sDodec[ 15], sDodec[ 6], sDodec[ 19], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[2], sDodec[ 13], sDodec[ 12], sDodec[ 4], sDodec[ 14], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[2], sDodec[ 19], sDodec[ 18], sDodec[ 3], sDodec[ 13], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[3], sDodec[ 0], sDodec[ 5], sDodec[ 12], sDodec[ 13], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[6], sDodec[ 15], sDodec[ 8], sDodec[ 10], sDodec[ 11], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[4], sDodec[ 17], sDodec[ 8], sDodec[ 15], sDodec[ 14], pParam.cDepth, pParam);
				Primitiv3d.Subdivide5( sDodec[4], sDodec[ 12], sDodec[ 5], sDodec[ 16], sDodec[ 17], pParam.cDepth, pParam);

				return pParam;
																						
		}
	
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		
		static void Triangle( int i, Float3 data[], int ndx[][], Primitiv3d.SubParam pParam) {

				//		System.out.println( "  DrawTriangle "  + i);

				Float3 x0 = data[ ndx[i][0] ];
				Float3 x1 = data[ ndx[i][1] ];
				Float3 x2 = data[ ndx[i][2] ];

				Primitiv3d.Subdivide( x0, x1, x2, pParam.cDepth, pParam );	
		}
		//------------------------------------------------
		/* octahedron data: The octahedron produced is centered at the
			 origin and has radius 1.0 */

		final static Float3 sOcta[] = {	
				new Float3( 1.0f, 0.0f, 0.0f),
				new Float3(-1.0f, 0.0f, 0.0f),
				new Float3(0.0f, 1.0f, 0.0f),
				new Float3(0.0f, -1.0f, 0.0f),
				new Float3(0.0f, 0.0f, 1.0f),
				new Float3(0.0f, 0.0f, -1.0f)				
		};
		final static int  sOctaIdx[][] = {
				{0, 4, 2},
				{1, 2, 4},
				{0, 3, 4},
				{1, 4, 3},
				{0, 2, 5},
				{1, 5, 2},
				{0, 5, 3},
				{1, 3, 5}
		};
		//------------------------------------------------
		static public Primitiv3d.SubParam Octahedron( Primitiv3d.SubParam pParam  ) {
				
				pParam.normEffectInit( sOcta);
	
				for ( int i = 7; i >= 0; i--) {
						Triangle( i, sOcta, sOctaIdx,  pParam);
				}
				return pParam;
		}
		//------------------------------------------------	
		//------------------------------------------------	
		//------------------------------------------------	
		/* icosahedron data: These numbers are rigged to make an
			 icosahedron of radius 1.0 */
		
		static final float sIicosaX =0.525731112119133606f;
		static final float sIicosaZ =0.850650808352039932f;
		
		static Float3 sIicosa[] = {
				new Float3(-sIicosaX, 0, sIicosaZ),
				new Float3(sIicosaX, 0, sIicosaZ),
				new Float3(-sIicosaX, 0, -sIicosaZ),
				new Float3(sIicosaX, 0, -sIicosaZ),
				new Float3(0, sIicosaZ, sIicosaX),
				new Float3(0, sIicosaZ, -sIicosaX),
				new Float3(0, -sIicosaZ, sIicosaX),
				new Float3(0, -sIicosaZ, -sIicosaX),
				new Float3(sIicosaZ, sIicosaX, 0),
				new Float3(-sIicosaZ, sIicosaX, 0),
				new Float3(sIicosaZ, -sIicosaX, 0),
				new Float3(-sIicosaZ, -sIicosaX, 0)
		};
		
		static int sIicosaIdx[][] =		{
				{0, 4, 1},
				{0, 9, 4},
				{9, 5, 4},
				{4, 5, 8},
				{4, 8, 1},
				{8, 10, 1},
				{8, 3, 10},
				{5, 3, 8},
				{5, 2, 3},
				{2, 7, 3},
				{7, 10, 3},
				{7, 6, 10},
				{7, 11, 6},
				{11, 0, 6},
				{0, 1, 6},
				{6, 1, 10},
				{9, 0, 11},
				{9, 11, 2},
				{9, 2, 5},
				{7, 2, 11},
		};

		static  Primitiv3d.SubParam 	Icosahedron( Primitiv3d.SubParam pParam )		{				
				pParam.normEffectInit( sIicosa );

				for ( int i = 19; i >= 0; i-- ) 
						Triangle(i, sIicosa, sIicosaIdx, pParam);
				return pParam;
		}
			
		//------------------------------------------------	
		//------------------------------------------------	
		//------------------------------------------------	
		/* tetrahedron data: */

		final static float  sTetraT =  1.73205080756887729f;
		
		static Float3 sTetra[] =		{
				new Float3( sTetraT, sTetraT, sTetraT),
				new Float3( sTetraT, -sTetraT, -sTetraT),
				new Float3(-sTetraT, sTetraT, -sTetraT),
				new Float3(-sTetraT, -sTetraT, sTetraT)
		};
		
		static int sTetraIdx[][] = {
				{0, 1, 3},
				{2, 1, 0},
				{3, 2, 0},
				{1, 2, 3}
		};
		
		static public  Primitiv3d.SubParam  Tetrahedron( Primitiv3d.SubParam pParam ) {
				pParam.normEffectInit( sTetra );

				for ( int i = 3; i >= 0; i--)
						Triangle(i, sTetra, sTetraIdx, pParam);

				return pParam;
		}

}
//*************************************************
