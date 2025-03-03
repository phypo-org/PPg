package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;

//*************************************************
public class DecorCarte{

		
		int cW, cH;
		DecorCase [] [] cVect = null;
		
		static double sSizeCase = 0; // taille d'une case de terrain en metre
		static double sSizeCaseInv = 0;
		

		final int getMeterWidth()  { return (int)(cW*sSizeCase); }
		final int getMeterHeight() { return (int)(cH*sSizeCase); }


		//---------------------
		public DecorCarte( int pW, int pH ) {	

				sSizeCaseInv = ((double)1)/sSizeCase;

				cW = pW;
				cH = pH;
				
				cVect = new DecorCase[cW] [cH];	 
				
				for( int y=0; y<cH; y++){
						for( int x=0; x<cW; x++)
								cVect[y][x] = null;
				}					
		}
		//---------------------
		public DecorCarte( String pFilename ) {	
				sSizeCaseInv = ((double)1)/sSizeCase;

				File lFile= new File( pFilename );
				
				try {
						if( lFile != null ){
								FileReader lFread = new FileReader(lFile  );
								load(lFread);
						}
				}
				catch( Exception e){
						System.err.println("catch " + e
															 + " in DecorCarte::Carte when reading file:" + pFilename );
				}
		}		
		//---------------------
		final int getWidth()  { return cW; }
		final int getHeight() { return cH; }
		//---------------------	 
		final DecorCase get( int pX, int pY ){
				if( isOut( pX, pY ) )
						return null;

				return cVect[pX][pY]; 
		}
		//---------------------
		// recherche de la case avec la position en metres

		final DecorCase getMeter( double pX, double pY ){
		int lX = (int)(pX*sSizeCaseInv);
				int lY = (int)(pY*sSizeCaseInv);
				
				if( isOut( lX, lY ) )
						return null;

				return cVect[lX][lY]; 
		}
		
		//---------------------
		final boolean isOut( int pX, int pY ) {		
				if( pX < 0 || pY < 0 || pX >= cW || pY >= cH )
						return true;
				return false;
		}	
		//---------------------
		boolean load( InputStreamReader  lFread) throws IOException{		
				
				String lSbuf;
				BufferedReader lBufread = new BufferedReader(lFread);
				int lNumline = 0;
				
				int lSzX=0;
				int lSzY=0;

				lSbuf=lBufread.readLine();
				cW = Integer.decode(lSbuf );

				lSbuf=lBufread.readLine();
				cH = Integer.decode(lSbuf);

				
				cVect = new DecorCase[cW] [cH];	 
	
				int lY=0;
				while( (lSbuf=lBufread.readLine()) != null) {
						//						System.out.println( "Line  " +lNumline + ">>" + lSbuf );

						
						lNumline++;
						
						//					System.out.println( lNumline + ">>>" + lSbuf );
						if(  lSbuf.length() == 0 || lSbuf.charAt(0) == '#'  || lSbuf.charAt(0) == '\n'
								 || lSbuf.trim().length() == 0  )
								continue;
						
						for( int lX=0; lX< lSbuf.length(); lX++ )
								cVect[lX][lY] = new DecorCase( lX, lY, PrototypeCase.GetPrototype( lSbuf.charAt(lX)));

						lY++;

				}				
				if( lY != cH+1 )
						return false;

				return true;
		}
		
		//-----------------------------------------------------------
		//-----------------------------------------------------------
		//-----------------------------------------------------------

}
//*************************************************
