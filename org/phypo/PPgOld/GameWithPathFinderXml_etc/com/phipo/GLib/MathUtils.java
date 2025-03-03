package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;


//***********************************
class MathUtil {
		
		static long cThomasHI[] = {1l, 5l, 19l, 71l, 265l, 989l, 3691l, 13775l, 51409l, 191861l};

		void doThomas ( double [] pNoeuds, double [] pPoles, int pNb)
		{

				double []Xi = new double[pNb];
				
				int i;
				Xi[ 0 ] = (pNoeuds[ 0 ]*6) / 5;
				
				if ( pNb <= 9 )              /* P0, ..., Pnb-1 */
						{
								for (  i=1; i<pNb-1 ; i++ )
										Xi[ i ] = ((6 *pNoeuds[ i ]-Xi[ i - 1 ])*cThomasHI[ i ]) / cThomasHI[ i + 1 ];
								
								Xi[pNb-1] = ((6 * pNoeuds[pNb-1] - Xi[pNb-2]) * cThomasHI[pNb-1]) / (cThomasHI[pNb-1] + cThomasHI[pNb]) ;
						}
				else
						{
								
								for (  i=1; i<9; i++ )
										Xi[ i ] = ((6 *pNoeuds[ i ]-Xi[ i - 1 ])*cThomasHI[ i ]) / cThomasHI[ i + 1 ];
								
								for (  i=9; i<pNb-1; i++ )
										Xi[ i ] = ((6 *pNoeuds[ i ]-Xi[ i - 1 ])*51409l) / 191861l;
								
								Xi[pNb-1] = ((6 * pNoeuds[pNb-1] - Xi[pNb-2]) * 51409l) /243270l ;
						}
				
				
				pPoles[ pNb - 1 ] = Xi[ pNb - 1 ];
				
				if ( pNb > 9 )
						{
								for (  i=pNb-1 ; i>7 ; i-- )
										pPoles[ i - 1 ] = Xi[ i - 1 ]-((pPoles[ i ]*51409l) / 191861l);
								
								for (  i=9 ; i>0 ; i-- )
										pPoles[ i - 1 ] = Xi[ i - 1 ]-((pPoles[ i ]*cThomasHI[ i - 1 ]) / cThomasHI[ i ]);
								
						}
				else
						for (  i=pNb-1 ; i>0 ; i-- )
								pPoles[i-1] = Xi[i-1] - ((pPoles[i] * cThomasHI[i-1]) / cThomasHI[i]);
				
				/*	pPoles[0] = pPoles[1] ;  */
				
				//				dispose Xi;
		}
}

