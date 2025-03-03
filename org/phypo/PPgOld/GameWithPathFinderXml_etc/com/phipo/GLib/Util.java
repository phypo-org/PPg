package com.phipo.GLib;


import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;


import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

import javax.swing.*;


//*************************************************
class Util{
		
		static double sTan30  = Math.tan( Math.PI/6 );
		static double sTan60  = Math.tan( 2*Math.PI/6 );
		static double sTan120 = Math.tan( 4*Math.PI/6 );
		static double sTan150 = Math.tan( 5*Math.PI/6 );
		
		static double sTan210 = Math.tan( 7*Math.PI/6 );
		static double sTan240 = Math.tan( 8*Math.PI/6 );
		static double sTan300 = Math.tan( 10*Math.PI/6 );
		static double sTan330 = Math.tan( 11*Math.PI/6 );
		
		
		enum Orientation {
				POS_NORTH("NORTH", 0, 0.0),
				POS_NE   ("NE",    1, 45.0),
				POS_EAST ("EAST",  2, 90.0),
				POS_SE   ("SE",    3, 135.0),
				POS_SOUTH("SOUTH", 4, 180.0),
				POS_SW   ("SW",    5, 225.0),
				POS_WEST ("WEST",  6, 270.0),
				POS_NW   ("NW",    7, 315.0),
				POS_NONE ("NONE",  8, 0);
				
				String cName=null;
				int    cCode=-1;
				double cAngle=0;
				
				public String getName() { return cName; }
				public int    getCode() { return cCode; }
				public double getAngle() { return cAngle; }
				
				Orientation( String pName, int pCode, double pAngle ) { 
						cName = pName;
						cCode = pCode;
						cAngle = pAngle;
				}
				
				
				public static final HashMap<String,Orientation > sHashOrientation = new HashMap<String,Orientation>();
				public static final Orientation []sTabOrientation = new Orientation[9];
				static {
						for( Orientation  lPoint: Orientation.values() ) {
								sHashOrientation.put( lPoint.cName, lPoint );
								sTabOrientation[lPoint.cCode] = lPoint;
						}
				}
				
				static public Orientation FindByName( String pName ) { return sHashOrientation.get( pName ); }
				static public Orientation Get( int i ) { return sTabOrientation[i];}
		}
		
		//------------------------------------------------
		// On veut aller a une certaine distance d'une entity ( renvoit aussi l'orientation)
		
		static Orientation GetNewPos( Point2D.Double lPos,  Point2D.Double lTargetPos, double lDist, Point2D.Double lDestPos ){
				
				Orientation lDir = Util.GetOrientation( lPos.getX(), lPos.getY(), lTargetPos.getX(), lTargetPos.getY() );
				
				double lX = lTargetPos.getX();
				double lY = lTargetPos.getY();
				
				switch( lDir ){
				case    POS_NORTH :
						lY -= lDist;
						break;
				case 		POS_NE :
						lX += lDist;
						lY -= lDist;
						break;
				case 		POS_EAST:
						lX += lDist;
						break;
				case 		POS_SE  :
						lX += lDist;
						lY -= lDist;
						break;
				case 		POS_SOUTH:
						lY += lDist;
						break;
				case 		POS_SW:   
						lX -= lDist;
						lY += lDist;
						break;
				case 		POS_WEST :
						lX -= lDist;
						break;
				case 		POS_NW:
						lX -= lDist;
						lY -= lDist;
						break;
				default:						
				}
				lDestPos.setLocation( lX, lY );
				
				return lDir;
		}
		//------------------------------------------------
		//  renvoit aussi l'orientation entre deux point
		static Orientation GetOrientation(  double ACenterX, double ACenterY, double BCenterX,  double BCenterY ){
				
				double lDistanceSqr = 0;
				double lDiffX;
				double lDiffY;
				Orientation lPosition = null;
				
				if( ACenterX < BCenterX ){
						// a gauche
						lDiffX = BCenterX - ACenterX;
						
						if( ACenterY < BCenterY ){						
								// en haut
								
								lDiffY = BCenterY - ACenterY;
								
								double lTan = lDiffY/lDiffX;
								if( lTan < 0.58 )
										lPosition = Orientation.POS_WEST;
								else if( lTan > 1.73 )
										lPosition = Orientation.POS_NORTH;
								else 
										lPosition = Orientation.POS_NW;								
						} else {
								// en bas
								lDiffY = ACenterY - BCenterY;					 			
								
								double lTan = lDiffY/lDiffX;
								if( lTan < 0.58 )
										lPosition = Orientation.POS_WEST;
								else if( lTan > 1.73 )
										lPosition = Orientation.POS_SOUTH;
								else 
									
	lPosition = Orientation.POS_SW;								
						}
				}
				else {
						// a droite
						lDiffX = ACenterX - BCenterX;
						
						if( ACenterY < BCenterY ){						
								// en haut
								lDiffY = BCenterY - ACenterY;

								double lTan = lDiffY/lDiffX;
								if( lTan < 0.58 )
										lPosition = Orientation.POS_EAST;
								else if( lTan > 1.73 )
										lPosition = Orientation.POS_NORTH;
								else 
										lPosition = Orientation.POS_NE;								
						} else {
								// en bas
								lDiffY = ACenterY - BCenterY;					 			
								
								double lTan = lDiffY/lDiffX;
								if( lTan < 0.58 )
										lPosition = Orientation.POS_EAST;
								else if( lTan > 1.73 )
										lPosition = Orientation.POS_SOUTH;
								else 
										lPosition = Orientation.POS_SE;								
						}
				}
				return lPosition;
		}
		//------------------------------------------------
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
		//------------------------------------------------
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
				
				// On par du principe qu'en general les coin du sprite sont vides,
				// on va donc essayer de determiner la valeur transparente en prenant comme
				// valeur l'un d'entre eux
				
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
								
				System.out.println( "================== Width:" + pWidth + " pHeight:" +  pHeight + " pScale" + pScale );
				
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
