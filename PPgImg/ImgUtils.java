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
import java.awt.image.ShortLookupTable;


import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;


//*************************************************
public class ImgUtils{

		
		//------------------------------------------------
		public static BufferedImage GetSameBufferImage( BufferedImage pBufIn ){
				
				return new BufferedImage( pBufIn.getWidth(),
																	pBufIn.getHeight(),
																	pBufIn.getType()); // TYPE_INT_ARGB_PRE
		}	
		//------------------------------------------------
		public static BufferedImage GetSameBufferImage( BufferedImage pBufIn, int pW, int pH ){
				
				return new BufferedImage( pW,
																	pH,
																	pBufIn.getType()); // TYPE_INT_ARGB_PRE
		}	
		//------------------------------------------------
		public  static BufferedImage CloneImage( BufferedImage pBufIn ){
				BufferedImage lBufOut = GetSameBufferImage( pBufIn );

				Graphics2D lG = lBufOut.createGraphics();

				lG.setComposite( AlphaComposite.Src );
				lG.drawImage( pBufIn, 0, 0,  null); 
				lG.dispose();
				return lBufOut;
		}
		//------------------------------------------------
		public  static BufferedImage Merge( BufferedImage pBufIn1, Image pBufIn2){
				BufferedImage lBufOut = GetSameBufferImage( pBufIn1 );

				Graphics2D lG = lBufOut.createGraphics();

				lG.drawImage( pBufIn1, 0, 0,  null); 
				lG.drawImage( pBufIn2, 0, 0,  null); 
				lG.dispose();

				return lBufOut;				
		}
		//------------------------------------------------
		public  static BufferedImage CopyToImage( Image pBufIn,  BufferedImage pBufOut){
				Graphics2D lG = pBufOut.createGraphics();

				lG.setComposite( AlphaComposite.Src );
				lG.drawImage( pBufIn, 0, 0,  null); 
				lG.dispose();

				return pBufOut;				
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static BufferedImage Convert2GrayScale( BufferedImage pBufIn ){

				BufferedImage lBufOut = GetSameBufferImage( pBufIn );

				ColorSpace lColSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);

				ColorConvertOp lTheOp = new ColorConvertOp( lColSpace, null);
 
				return lTheOp.filter( pBufIn, lBufOut );
		}
		//------------------------------------------------
		public static BufferedImage InvertColor( BufferedImage pBufIn, boolean pR, boolean pG, boolean pB, boolean pA ){

				BufferedImage lBufOut = GetSameBufferImage( pBufIn );
				
				short [] lInvTab = new short[256];
				short [] lNormTab = new short[256];
				
					for( int i=0; i< 256; i++ ){
							lInvTab[i]  = (short)((255 - i));
							lNormTab[i] = (short)i;
					}
					
					//					short[][] lTab4 = new short[][]{lNormTab, lInvTab,lInvTab,lInvTab};
					short[][] lTab4 = new short[][]{ (pR?lInvTab:lNormTab),
																					 (pG?lInvTab:lNormTab),
																					 (pB?lInvTab:lNormTab),
																					 (pA?lInvTab:lNormTab) };

				ShortLookupTable lLook = new ShortLookupTable( 0, lTab4 );
				BufferedImageOp lTheOp = new LookupOp( lLook, null);

				return lTheOp.filter( pBufIn, null );

		}
		//------------------------------------------------
		public static BufferedImage Posterize( BufferedImage pBufIn, int pFact, int pOffset ){

				//		pFact = 255 - pFact;

				BufferedImage lBufOut = GetSameBufferImage( pBufIn );
				
				short [] lPosTab = new short[256];
				short [] lNormTab = new short[256];
				
				for( int i=0; i< 256; i++ ){

						//					System.out.println( "i:" +i +" i/pfact=" +(i/pFact) + " 255/pFact=" + (255/pFact) 
						//															+ "(i/pFact)*(255/pFact)=" +(i/pFact)*(255/pFact)  + "   offset:"  +pOffset );
				//
						//			lPosTab[i]  = (short) ((i/pFact)*(255/pFact)+pOffset);
						int lVal = (((i/pFact)*pFact)+pOffset);

						if( lVal > 255 ){
								lVal = 255;
						}
						lPosTab[i] = (short)lVal;
						lNormTab[i] = (short)i;
				}
				
					//					short[][] lTab4 = new short[][]{lNormTab, lInvTab,lInvTab,lInvTab};
					short[][] lTab4 = new short[][]{ lPosTab, lPosTab, lPosTab, lNormTab}; // RGBA ????

				ShortLookupTable lLook = new ShortLookupTable( 0, lTab4 );
				BufferedImageOp lTheOp = new LookupOp( lLook, null);

				return lTheOp.filter( pBufIn, null );

		}
			//------------------------------------------------
		public static BufferedImage PosterizeRGB( BufferedImage pBufIn, int [] pFact, int [] pOffset  ){

				//		pFact = 255 - pFact;

				BufferedImage lBufOut = GetSameBufferImage( pBufIn );
				
				short [][] lPosTab = new short[3][256];
				short [] lNormTab = new short[256];
				
				for( int i=0; i< 256; i++ ){
						for( int c=0; c<3; c++){

								//					System.out.println( "i:" +i +" i/pfact=" +(i/pFact) + " 255/pFact=" + (255/pFact) 
								//															+ "(i/pFact)*(255/pFact)=" +(i/pFact)*(255/pFact)  + "   offset:"  +pOffset );
								//
								//			lPosTab[i]  = (short) ((i/pFact)*(255/pFact)+pOffset);
								int lVal = (((i/pFact[c])*pFact[c])+pOffset[c]);
								
								if( lVal > 255 ){
										lVal = 255;
								}
								lPosTab[c][i] = (short)lVal;
						}
						lNormTab[i] = (short)i;
				}
					//					short[][] lTab4 = new short[][]{lNormTab, lInvTab,lInvTab,lInvTab};
					short[][] lTab4 = new short[][]{ lPosTab[0], lPosTab[1], lPosTab[2], lNormTab}; // RGBA ????

				ShortLookupTable lLook = new ShortLookupTable( 0, lTab4 );
				BufferedImageOp lTheOp = new LookupOp( lLook, null);

				return lTheOp.filter( pBufIn, null );

		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static BufferedImage RescaleOp( BufferedImage pBufIn, float[] pScale, float[] pOffset ){
				
				BufferedImage lBufOut = GetSameBufferImage( pBufIn );

				RescaleOp filterObj = new RescaleOp( pScale, pOffset, null );

				return filterObj.filter(  pBufIn, lBufOut );
		}
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		public static BufferedImage FlipHor( BufferedImage pBufIn ){

				BufferedImage lBufOut = GetSameBufferImage( pBufIn );

				AffineTransform lTransform = AffineTransform.getScaleInstance(-1, 1);
				lTransform.translate( -pBufIn.getWidth(), 0);

				AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
				if( lOp.filter( pBufIn, lBufOut ) != null ) {
						return lBufOut;
				}
				return null;
		}
		//------------------------------------------------
		public static BufferedImage FlipVer( BufferedImage pBufIn ){

				BufferedImage lBufOut = GetSameBufferImage( pBufIn );


				AffineTransform lTransform = AffineTransform.getScaleInstance(1, -1);
				lTransform.translate( 0, -pBufIn.getHeight());

				AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
				if( lOp.filter( pBufIn, lBufOut ) != null ) {
						return lBufOut;
				}
				return null;
		}
		//------------------------------------------------
		public static BufferedImage RotQuadrant( BufferedImage pBufIn, int pQuad ){
				
				
				AffineTransform lTransform = 
						AffineTransform.getQuadrantRotateInstance( pQuad, pBufIn.getWidth()/2, pBufIn.getHeight()/2 );
				
				AffineTransformOp lOp  = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);
				
				Rectangle         lRect   = lOp.getBounds2D( pBufIn ).getBounds(); 
				

				System.out.println( " x=" + lRect.getX() + " y=" + lRect.getY() + " w=" + lRect.width + " h=" + lRect.height );


				BufferedImage     lBufOut = new BufferedImage( lRect.width, lRect.height,
																											 pBufIn.getType());
				
				// Pour recadrer dans la nlle image
				switch ( pQuad & 3) {	
						
				case 0 :  return pBufIn ;
				case 1 : 
						lTransform.translate( lRect.getX(), -lRect.getY() ); 
						lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);
						break;
						
				case 2 : break;
						
				case 3:
						lTransform.translate( -lRect.getX(), lRect.getY() ); 
						lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);
						break;
				}
				
				
				lOp.createCompatibleDestImage( pBufIn, null );
				
				return lOp.filter( pBufIn, lBufOut );
		}

		//------------------------------------------------
		public static BufferedImage Rot( BufferedImage pBufIn, double pAngle ){

				AffineTransform lTransform = AffineTransform.getRotateInstance( pAngle );

				AffineTransformOp lOp  = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);

				Rectangle         lRect   = lOp.getBounds2D( pBufIn ).getBounds(); 

				System.out.println( " x=" + lRect.getX() + " y=" + lRect.getY() + " w=" + lRect.width + " h=" + lRect.height );


				BufferedImage     lBufOut = new BufferedImage( lRect.width, lRect.height,
																											 pBufIn.getType());		

				int lX=(int)lRect.x;
				int lY=(int)lRect.y;
				
				//				if( lX < 0 ) lX = -lX;
				//				if( lY > 0 ) lY = -lY;
				
				lTransform.translate( -lX, -lY ); 
				lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_BILINEAR);


				lOp.createCompatibleDestImage( pBufIn, null );
				return lOp.filter( pBufIn, lBufOut );
		}
		//------------------------------------------------
		// Creer une image miroir par rapport a un axe vertical

		static public ImageIcon CreateFlipHorImage( ImageIcon pSrc ){


				
				try{
						BufferedImage lBufIn = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE				
						BufferedImage lBufOut = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE
						
						Graphics2D g = lBufIn.createGraphics();
						g.setComposite( AlphaComposite.Src );
						//				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						//													 RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR ); // [NEAREST_NEIGHBOR BILINEAR BICUBIC]
						g.drawImage( pSrc.getImage(), 0, 0,  null); 
						g.dispose();
						
						
						AffineTransform lTransform = AffineTransform.getScaleInstance(-1,1);
						lTransform.translate( -pSrc.getIconWidth(), 0);
						AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
						
						
						if(  lOp.filter( lBufIn, lBufOut ) != null ) {
								ImageIcon lImage = new ImageIcon(	lBufOut ); 
								return lImage;
						}
				}
				catch(Exception e ){
						System.err.println( "Error for CreateFlipHorImage");
						e.printStackTrace(); 
				}
				return null;
		}
		//-----------------------------------------------------------------
		static public ImageIcon CreateFlipScaleImage( ImageIcon pSrc, double pScaleX, double pScaleY ){
				
				try{
						BufferedImage lBufIn = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE				
						BufferedImage lBufOut = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE
						
						Graphics2D g = lBufIn.createGraphics();
						g.setComposite( AlphaComposite.Src );
						//				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						//													 RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR ); // [NEAREST_NEIGHBOR BILINEAR BICUBIC]
						g.drawImage( pSrc.getImage(), 0, 0,  null); 
						g.dispose();
						
						
						AffineTransform lTransform = AffineTransform.getScaleInstance(pScaleX, pScaleY);
						lTransform.translate( -pSrc.getIconWidth(), 0);
						AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
						
						
						if(  lOp.filter( lBufIn, lBufOut ) != null ) {
								ImageIcon lImage = new ImageIcon(	lBufOut ); 
								return lImage;
						}
				}
				catch(Exception e ){
						System.err.println( "CreateFlipScaleImage");
						e.printStackTrace(); 
				}
				return null;
		}
		//-----------------------------------------------------------------
		// Pour la creation de sprite on a partir d'image sans transparence
		// On part du principe que la couleur de fond est homogéne et que les coins sont vides

		static public ImageIcon ForceTransparencie( ImageIcon pSrc ){

				System.out.println(" ============ FORCE_TRANSPARENCIE ======  "  );
				
				int w = pSrc.getIconWidth();
				int h = pSrc.getIconHeight();
				
				int [] lPixels = new int [w*h];
				PixelGrabber pg = new PixelGrabber( pSrc.getImage(), 0, 0, w, h, lPixels, 0, w);
				try{
						pg.grabPixels();
				} catch( InterruptedException e){
						System.err.println( "ForceTransparencie exception: " + e );
						return null;
				}
				
				if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
						System.err.println("ForceTransparencie image fetch aborted or errored");
						return null;
				}
				
				// On par du principe qu'en general les coins du sprite sont vides,
				// on va donc essayer de determiner la valeur transparente en prenant comme
				// valeur l'un d'entre eux ( en les testant deux par deux).
				
				int lTransparent = 0;
				if( lPixels[0] == lPixels[w*h-1] ){
						lTransparent = lPixels[0];
				}
				else 	if( lPixels[0] == lPixels[w-1] ){
						lTransparent = lPixels[0];
				}
				else	if( lPixels[w-1] == lPixels[w*h-1] ){
						lTransparent = lPixels[w-1];
				} 	else return null;
				
				lTransparent = lPixels[0];
				
				//				System.out.printf( "lTransparent:%1$x", lTransparent );
				//				System.out.println();
				
				for( int y=0; y<h; y++ ){
						//						System.out.print( "\t"+y+"\t");
						for( int x=0; x<w; x++){
								
								
								int lPixel = lPixels[x+y*w];
								//								System.out.printf( "%1$x ", lPixel );
								
								if( lPixel == lTransparent ) {
										//										System.out.print( "-");
										lPixels[x+y*w] =0; // on met alpha a 0 !			
								}

								//								if( x == y )
								//										lPixels[x+y*w] =    0xffffff;
						}
						//								System.out.println();
				}
				
				MemoryImageSource lMis = new MemoryImageSource( w, h, lPixels, 0, w );
				lMis.setAnimated( false );
				Image lImage = Toolkit.getDefaultToolkit().createImage( lMis );
				
				
				System.out.println(" ============ OK  ======  "  );
						
				return new ImageIcon( lImage );
		}
		//------------------------------------------------
		// Creation d'une image par rotation d'une autre

		static public ImageIcon CreateRotateImage( ImageIcon pSrc, double pAngle ){

				try{
						BufferedImage lBufIn = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE				
						BufferedImage lBufOut = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE
						
						Graphics2D g = lBufIn.createGraphics();
						g.setComposite( AlphaComposite.Src );
						//				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						//													 RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR ); // [NEAREST_NEIGHBOR BILINEAR BICUBIC]
						g.drawImage( pSrc.getImage(), 0, 0,  null); 
						g.dispose();
						

						AffineTransform lTransform = AffineTransform.getRotateInstance( pAngle, pSrc.getIconWidth()/2.0, pSrc.getIconHeight()/2.0);
						AffineTransformOp lOp = new AffineTransformOp( lTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
						
						if(  lOp.filter( lBufIn, lBufOut ) != null ) {
								ImageIcon lImage = new ImageIcon(	lBufOut ); 
								return lImage;
						}						
				}
				catch(Exception e ){
						System.err.println( "Error for CreateFlipHorImage");
						e.printStackTrace(); 
				}
				return null;
		}
		//------------------------------------------------
		static public boolean WriteImage( BufferedImage pImage, File pFile, String pFormat ){

				Iterator lIterWriters = ImageIO.getImageWritersByFormatName(pFormat); // ou png
				ImageWriter lWriter = (ImageWriter)lIterWriters.next();
				

				try {

						ImageOutputStream llIOs = ImageIO.createImageOutputStream(pFile);
						lWriter.setOutput(llIOs);
						
						
						//    Finally, the image may be written to the output stream:
						
						lWriter.write(pImage);
				}
				catch(Exception e){
						System.err.println( e );
						return false;
				}
				return true;
		}
		//------------------------------------------------
		static public BufferedImage GetBufferedImage( ImageIcon pSrc ){
				BufferedImage lBuf = new BufferedImage( pSrc.getIconWidth(), pSrc.getIconHeight(), BufferedImage.TYPE_INT_ARGB_PRE); // TYPE_INT_ARGB_PRE				
				Graphics2D g = lBuf.createGraphics();
				g.setComposite( AlphaComposite.Src );
				g.drawImage( pSrc.getImage(), 0, 0,  null); 
				g.dispose();
				return lBuf;
		}
		//------------------------------------------------
		static public boolean TransformBmp( File pFile, String pFormat ){

				if( pFile.getName().endsWith( ".bmp" )){
						
						File lNewName = new File(  pFile.getParent(), pFile.getName().substring( 0, pFile.getName().length()-4)+"."+pFormat);

						Image lImg = Bmp.Loadbitmap( pFile );
						if( lImg != null ){

								ImageIcon lIconImg = new ImageIcon( lImg );

								BufferedImage lBuf = GetBufferedImage( lIconImg );
								if( lBuf != null ){
										return WriteImage( lBuf, lNewName, pFormat );
								}
								else
										System.err.println( "convertion failed");
						}
						else
								System.err.println( "bmp read failed");
				}
				else
						System.err.println( "Name not finish by .bmp");
				return false;
		}
		//------------------------------------------------
		static public ImageIcon LoadImageFromFile( File pName, double pWidth, double pHeight, boolean pForceTransparencie, double pScale  ){

				ImageIcon lImageIcon=null;

				File lFile = new File( pName.toString() );
				if( lFile.canRead() == false )
						return null;

				ImageIcon lTmpImage = null;
				if( pName.getName().endsWith( ".bmp" ) ){
						Image lImg = Bmp.Loadbitmap(pName ); 
						if( lImg == null )
								return null;

						lTmpImage = new ImageIcon( lImg );
				}
				else
						lTmpImage = new ImageIcon( pName.getPath() );
				
				//				if( pForceTransparencie ){
				//						lTmpImage = 	ForceTransparencie(lTmpImage );
				//				}
								
				//		System.out.println( "================== Width:" + pWidth + " pHeight:" +  pHeight + " pScale" + pScale );
				
				if( pWidth >0 && pHeight >0 ){																
						lImageIcon = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(pWidth*pScale), 
																																								(int)(pHeight*pScale),
																																								Image.SCALE_SMOOTH ));
												
				}
				else 
				if( pScale != 1 ){																
						lImageIcon = new ImageIcon( lTmpImage.getImage().getScaledInstance( (int)(lTmpImage.getIconWidth()*pScale), 
																																								(int)(lTmpImage.getIconHeight()*pScale),
																																								Image.SCALE_SMOOTH ));
												
				}
				else
						lImageIcon = 	lTmpImage;				

				return lImageIcon;
		}
}
//*************************************************
