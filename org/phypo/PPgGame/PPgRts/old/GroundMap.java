package org.phypo.PPg.PPgRts;


import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;

import org.phypo.PPg.PPgUtils.*;
import org.phypo.PPg.PPgData.*;
import org.phypo.PPg.PPgGame.*;

//*************************************************
public class GroundMap{

		
		int cW, cH;
		GroundSquare [] [] cVect = null;


		Color     cBackgroundColor = Color.white;
		Color     getBackgroundColor() { return cBackgroundColor; }

		ImageIcon cOriginalBackgroundImg=null;

		ImageIcon cBackgroundImg = null;

		public ImageIcon getBackgroundImg() { return cBackgroundImg; }


		protected double cSizeSquare = 16; // taille d'une case de terrain en metre
		protected double cSizeSquareInv = 1;
	
		public final double getSizeSquare() { return cSizeSquare; }
		public final double getInvSizeSquare() { return cSizeSquareInv; }
				
				
		public final void setSizeSquare( double pSize ) {
				cSizeSquare = pSize;
				cSizeSquareInv = ((double)1)/cSizeSquare;
		}

		public final int getMeterWidth()  { return (int)(cW*cSizeSquare); }
		public final int getMeterHeight() { return (int)(cH*cSizeSquare); }


		

		//---------------------
		// Create a void map

		public GroundMap( int pW, int pH ) {	
				setSizeSquare(1);

				cW = pW;
				cH = pH;
				
				cVect = new GroundSquare[cW] [cH];	 
				
				for( int y=0; y<cH; y++){
						for( int x=0; x<cW; x++)
								cVect[y][x] = null;
				}					
		}
		//---------------------
		public GroundMap( String pFilename )  throws IOException{	

				setSizeSquare(1);

				File lFile= new File( pFilename );
				
				if( lFile != null ){
						FileReader lFread = new FileReader(lFile  );
						load(lFread);
				}				
		}		
		//---------------------
		final public  int getWidth()  { return cW; }
		final public  int getHeight() { return cH; }
		//---------------------	
		final GroundSquare get( int pX, int pY ){
				if( isOut( pX, pY ) )
						return null;

				return cVect[pX][pY]; 
		}
		//---------------------
		// Find the square with meters coordinate

		final GroundSquare getMeter( double pX, double pY ){
				int lX = (int)(pX*cSizeSquareInv);
				int lY = (int)(pY*cSizeSquareInv);
				
				if( isOut( lX, lY ) )
						return null;

				return cVect[lX][lY]; 
		}
		
		//---------------------
		// Out of the map testing

		final boolean isOut( int pX, int pY ) {		
				if( pX < 0 || pY < 0 || pX >= cW || pY >= cH )
						return true;
				return false;
		}	
		//------------------------------------------------
		public void makeScaledImg() {
				int lSizeX =  (int)(getSizeSquare()*World.Get().cGeneralScale*cW);
				int lSizeY =  (int)(getSizeSquare()*World.Get().cGeneralScale*cH);
				
				System.out.println( "makeScaledImg " + lSizeX +" "+ lSizeY );
				
				if( cOriginalBackgroundImg != null ) {
						cBackgroundImg = new ImageIcon( cOriginalBackgroundImg.getImage().getScaledInstance( lSizeX, lSizeY,	Image.SCALE_SMOOTH ));			
						cBackgroundImg.getImage().setAccelerationPriority(1);
				}
		}
				
		 //------------------------------------------------
		boolean  read( PPgIniFile pIni ) {
				setSizeSquare(  pIni.getdouble(  DefIni.sMap, DefIni.sSizeOfSquareSide,  cSizeSquare ));
				
				cOriginalBackgroundImg = PPgIniFile.ReadIcon( pIni, DefIni.sMap, DefIni.sBackgroundImage, null );				
				//		cBackgroundColor = PPgIniFile.ReadColor( pIni, DefIni.sMap, DefIni.sBackgroundColor, null );				

				return true;
		}
		//------------------------------------------------
		static public GroundMap Read(  PPgIniFile pIni ) {
				
				GroundMap lMap = null;
				

				System.out.println( "GroundMap Read :" +  DefIni.sMap +" " + DefIni.sFile );

				String lMapFile = pIni.get( DefIni.sMap,  DefIni.sFile );
				if( lMapFile == null ){
						System.err.println( "World.read no name for map file" );
						return null;						
				}			
				
				try {
						lMap = new GroundMap( lMapFile );
				} catch( Exception e){
						System.err.println( "map loading failed for " + lMapFile );						
						return null;
				}
				lMap.read( pIni);

				return lMap;
		}
		//---------------------
		// Load the map from a file

		boolean load(  String pFilename ) {	
				
				File lFile= new File( pFilename );

				try {
						if( lFile != null ){
								FileReader lFread = new FileReader(lFile  );
								return load(lFread);
						}
				}
				catch( Exception e){
						System.err.println("catch " + e
															 + " in GroundMap when reading file:" + pFilename );
				}
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


				
				
				cVect = new GroundSquare[cW] [cH];	 
	
				int lY=0;
				while( (lSbuf=lBufread.readLine()) != null) {
						//						System.out.println( "Line  " +lNumline + ">>" + lSbuf );
						
						lNumline++;
						
						//					System.out.println( lNumline + ">>>" + lSbuf );
						if(  lSbuf.length() == 0 || lSbuf.charAt(0) == '#'  || lSbuf.charAt(0) == '\n'
								 || lSbuf.trim().length() == 0  )
								continue;
						
						for( int lX=0; lX< lSbuf.length(); lX++ ) {
								ProtoGroundSquare lProto = ProtoGroundSquare.GetProto( lSbuf.charAt(lX));

								if( lProto == null ) {
										System.err.println( "GroundMap load : Prototype is void for <" + lSbuf.charAt(lX) +">" );
										try {
										Thread.sleep( 10000 );
										}catch(InterruptedException ex){}
										return false;
								}
								
								cVect[lX][lY] = new GroundSquare( lX, lY, lProto );
						}
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
