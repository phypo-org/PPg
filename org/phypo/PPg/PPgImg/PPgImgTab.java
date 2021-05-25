
package org.phypo.PPg.PPgImg;



import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import java.lang.*;


import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.*;
import java.awt.color.ColorSpace;


import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;


//*************************************************

class PPgImgTab{

		int [][][] cTab = null;

		int cWidth = 0;
		int cHeight = 0;

		//------------------------------------------------
		PPgImgTab( BufferedImage pBufIn ){

				cWidth  = pBufIn.getWidth();
				cHeight = pBufIn.getHeight();

				cTab = GetTab3D( pBufIn ); 
		}
		//------------------------------------------------
		PPgImgTab( PPgImgTab pTab ){
				cWidth = pTab.cWidth;
				cHeight = pTab.cHeight;

				cTab = GetTab3D( cWidth, cHeight );

				copyFrom( pTab );
		}
		//------------------------------------------------
		public void copyFrom( PPgImgTab pTab ) {

				for (int lRow = 0; lRow < cHeight; lRow++) {
						for (int lCol = 0; lCol < cWidth; lCol++) {
								for( int i=0; i< 4; i++ ){
										cTab[lRow][lCol][i] = pTab.cTab[lRow][lCol][i];
								}
						}
				}
		}
		//------------------------------------------------
		public BufferedImage getImage(){
				return GetBufferedImage( cTab, cWidth, cHeight );
		}

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		static int [][][] GetTab3D( int pW, int pH ){

				int [][][] lData = new int[pH][pW][4];	
				return lData;
		}
		//------------------------------------------------

		//------------------------------------------------

		public static int [][][] GetTab3D( BufferedImage pBufIn ){


				int lWidth  = pBufIn.getWidth();
				int lHeight = pBufIn.getHeight();

				// tableau temporaire
				int [] lPixels = new int [ lWidth*lHeight];

				// extration des pixels
				PixelGrabber lPG = new PixelGrabber(  pBufIn, 0, 0, lWidth, lHeight, lPixels, 0, lWidth );

				try {
						lPG.grabPixels();
				} catch (InterruptedException e) {
						System.err.println("GetTab3D interrupted waiting for pixels!");
						return null;
				}
				if ((lPG.getStatus() & ImageObserver.ABORT) != 0) {
						System.err.println("GetTab3D image fetch aborted or errored");
						return null;
				}

				//conversion en argb

				// allocation du tableau 3d
				int [][][] lData	= GetTab3D(	lWidth, lHeight);	
		
				// Remplissage du tableau 3d
				for (int lRow = 0; lRow < lHeight; lRow++) {
						for (int lCol = 0; lCol < lWidth; lCol++) {
								lData[lRow][lCol][0] = (lPixels[lRow * lWidth + lCol] >> 24) & 0xff;	// alpha
								lData[lRow][lCol][0] = (lPixels[lRow * lWidth + lCol] >> 16) & 0xff;  // red 
								lData[lRow][lCol][0] = (lPixels[lRow * lWidth + lCol] >>  8) & 0xff;  // green
								lData[lRow][lCol][0] = (lPixels[lRow * lWidth + lCol]      ) & 0xff;  // blue								
						}
				}
				return lData;
		}
		//------------------------------------------------
			
		public static BufferedImage GetBufferedImage( int [][][] pData, int pW, int pH ){


				int [] lPixels = new int[pW*pH];

				int lIndex = 0;

				for(int lRow = 0;lRow < pH; lRow++){	
	
						for(int lCol = 0; lCol < pW; lCol++){
								
								lPixels[ lIndex++ ] = 
										((pData[lRow][lCol][0] << 24)
										   & 0xFF000000)
										| ((pData[lRow][lCol][1] << 16)
											 & 0x00FF0000)
										| ((pData[lRow][lCol][2] << 8)
											 & 0x0000FF00)
										| ((pData[lRow][lCol][3])
											 & 0x000000FF);
						}
				}
				Image lTmpImg = Toolkit.getDefaultToolkit().createImage( new MemoryImageSource( pW, pH, lPixels, 0, pW));

				BufferedImage lBufOut = new BufferedImage( pW, pH, BufferedImage.TYPE_INT_ARGB_PRE);
				lBufOut.getGraphics().drawImage( lTmpImg, 0, 0, null);

				return lBufOut;
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static void Correct(  int [][][] pData, int pW, int pH ){
				for(int lRow = 0;lRow < pH; lRow++){	
						for(int lCol = 0; lCol < pW; lCol++){
								for( int i=0; i< 4; i++ )
										if( pData[lRow][lCol][i] > 255 )
												pData[lRow][lCol][i] = 255;
										else 
												if( pData[lRow][lCol][i] < 0 )
														pData[lRow][lCol][i] = 0;
						}
				}
		}
		//------------------------------------------------
		public static void CorrectRGB(  int [][][] pData, int pW, int pH ){
				for(int lRow = 0;lRow < pH; lRow++){	
						for(int lCol = 0; lCol < pW; lCol++){
								for( int i=1; i< 4; i++ )
										if( pData[lRow][lCol][i] > 255 )
												pData[lRow][lCol][i] = 255;
										else 
												if( pData[lRow][lCol][i] < 0 )
														pData[lRow][lCol][i] = 0;
						}
				}
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static void AddOffset( int [][][] pData, int pW, int pH, int pOffset ) {
				
				for(int lRow = 0;lRow < pH; lRow++){	
						for(int lCol = 0; lCol < pW; lCol++){
								for( int i=1; i< 4; i++ )
										pData[lRow][lCol][i] += pOffset;
						}
				}
		}
		//------------------------------------------------
		public static void Scale( int [][][] pData, int pW, int pH, float pScale  ) {

				for(int lRow = 0;lRow < pH; lRow++){	
						for(int lCol = 0; lCol < pW; lCol++){
								for( int i=1; i< 4; i++ )
										pData[lRow][lCol][i] *= pScale;
						}
				}
		}

}

//*************************************************
