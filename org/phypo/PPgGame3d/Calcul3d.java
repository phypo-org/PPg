package org.phypo.PPgGame3d;

import java.lang.Math;

//*************************************************

public class Calcul3d{



		//------------------------------------------------
		
		public static void  GetMiddle( float []  p1, float [] p2, float [] pRes ) {	
				for( int i = 0; i < p1.length; i++ )
						pRes[ i ] = (p1[ i ] + p2[ i ])*0.5f;
		}
		//------------------------------------------------
		public static void  GetMiddle(  double [] p1,  double [] p2, double [] pRes ) {	
				for( int i = 0; i < p1.length; i++ )
						pRes[ i ] = (p1[ i ] + p2[ i ])*0.5;
		}
		//------------------------------------------------
		public static void Normal( Double3 p1, Double3 p2, Double3 p3, Double3  pNorm ) {
				Normal( p1.get(), p2.get(), p3.get(), pNorm.get() );
		}
		//------------------------------------------------
		public static void Normal( double [] p1, double [] p2, double [] p3, double []  pNorm ) {
				
				double coa, cob, coc ;
				double px1, py1, pz1 ;
				double px2, py2, pz2 ;
				double px3, py3, pz3 ;
				
				double absvec ;
				
				px1 = p1[0] ;
				py1 = p1[1] ;
				pz1 = p1[2] ;
				
				px2 = p2[0] ;
				py2 = p2[1] ;
				pz2 = p2[2] ;
				
				px3 = p3[0] ;
				py3 = p3[1] ;
				pz3 = p3[2] ;
				
				coa = -(py1 * (pz2-pz3) + py2*(pz3-pz1) + py3*(pz1-pz2)) ;
				cob = -(pz1 * (px2-px3) + pz2*(px3-px1) + pz3*(px1-px2)) ;
				coc = -(px1 * (py2-py3) + px2*(py3-py1) + px3*(py1-py2)) ;
				
				// Normalisation
				absvec = Math.sqrt ((double)((coa *coa) + (cob *cob) + (coc *coc)));
				
				if( absvec == 0.0  )
						pNorm[ 0 ] = absvec = 1.0;
				
				absvec = 1.0 / absvec;
				
				pNorm[0] = coa*absvec ;
				pNorm[1] = cob*absvec ;
				pNorm[2] = coc*absvec ;
				}
		//------------------------------------------------
		public static void Normal( Float3 p1, Float3 p2, Float3 p3, Float3  pNorm ) {
				Normal( p1.get(), p2.get(), p3.get(), pNorm.get() );
		}
		//------------------------------------------------
		public static void Normal( float [] p1, float [] p2, float [] p3, float []  pNorm ) {
				
				float coa, cob, coc ;
				float px1, py1, pz1 ;
				float px2, py2, pz2 ;
				float px3, py3, pz3 ;
				
				float absvec ;
				
				px1 = p1[0] ;
				py1 = p1[1] ;
				pz1 = p1[2] ;
				
				px2 = p2[0] ;
				py2 = p2[1] ;
				pz2 = p2[2] ;
				
				px3 = p3[0] ;
				py3 = p3[1] ;
				pz3 = p3[2] ;
				
				coa = -(py1 * (pz2-pz3) + py2*(pz3-pz1) + py3*(pz1-pz2)) ;
				cob = -(pz1 * (px2-px3) + pz2*(px3-px1) + pz3*(px1-px2)) ;
				coc = -(px1 * (py2-py3) + px2*(py3-py1) + px3*(py1-py2)) ;
				
				// Normalisation
				absvec = (float) Math.sqrt (((coa *coa) + (cob *cob) + (coc *coc)));
				
				if( absvec < 0.000000001  )
						absvec = 1.0f;
				
				absvec = 1.0f / absvec;
				
				pNorm[0] = (coa*absvec) ;
				pNorm[1] = (cob*absvec) ;
				pNorm[2] = (coc*absvec) ;
		}
		
		static public double GetAngle( double pCos, double pSin  ){
				
				if( pCos > 1.0 || pCos < -1.0 )		{
						//			std::cout << " ++++++++ ELIMINATION ----------:"  << std::endl;
						return 0;
				}

				double lAngle = Math.acos( pCos );


				double lAngle2 = lAngle*(180/3.14159265358979323846);
				if( pSin < 0 )
						lAngle2 = 360-lAngle2;
	
				return lAngle2;
		}



}

//*************************************************
